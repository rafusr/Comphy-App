package com.comphy.photo.data.repository

import com.comphy.photo.data.source.remote.client.ApiService
import com.comphy.photo.data.source.remote.response.community.category.CommunityResponse
import com.comphy.photo.data.source.remote.response.community.create.CreateCommunityBody
import com.google.gson.Gson
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class CommunityRepository @Inject constructor(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher,
) {

    suspend fun createCommunity(
        createCommunityBody: CreateCommunityBody,
        onError: (errorResponse: CommunityResponse) -> Unit,
        onException: (exceptionResponse: String?) -> Unit
    ) = flow {
        val response = apiService.createCommunity(createCommunityBody)
        response.suspendOnSuccess { emit(data) }
            .onError {
                val responseResult: CommunityResponse =
                    Gson().fromJson(this.errorBody?.string(), CommunityResponse::class.java)
                onError(responseResult)
                Timber.tag("On Error").e(message())
            }
            .onException {
                onException(message)
                Timber.tag("On Exception").e(message())
            }
    }.flowOn(ioDispatcher)

    suspend fun leaveCommunity(
        communityId: Int,
        onErrorNorException: (exceptionResponse: String?) -> Unit
    ) = flow {
        val response = apiService.leaveCommunity(communityId)
        response.suspendOnSuccess { emit(data) }
            .onError {
                val responseResult: CommunityResponse =
                    Gson().fromJson(this.errorBody?.string(), CommunityResponse::class.java)
                onErrorNorException(responseResult.message)
                Timber.tag("On Error").e(message())
            }
            .onException {
                onErrorNorException(message)
                Timber.tag("On Exception").e(message())
            }
    }

    suspend fun getCommunityCategories() =
        flow {
            val response = apiService.getCommunityCategories()
            response.suspendOnSuccess { emit(data) }
                .onError { Timber.tag("On Error").e(message()) }
                .onException { Timber.tag("On Exception").e(message()) }
        }.flowOn(ioDispatcher)

    suspend fun getCreatedCommunities(
        onException: () -> Unit
    ) = flow {
        val response = apiService.getCreatedCommunities()
        response.suspendOnSuccess { emit(data) }
            .onError { Timber.tag("On Error").e(message()) }
            .onException {
                onException()
                Timber.tag("On Exception").e(message())
            }
    }.flowOn(ioDispatcher)

    suspend fun getJoinedCommunities(
        onException: () -> Unit
    ) = flow {
        val response = apiService.getJoinedCommunities()
        response.suspendOnSuccess { emit(data) }
            .onError { Timber.tag("On Error").e(message()) }
            .onException {
                onException()
                Timber.tag("On Exception").e(message())
            }
    }.flowOn(ioDispatcher)
}