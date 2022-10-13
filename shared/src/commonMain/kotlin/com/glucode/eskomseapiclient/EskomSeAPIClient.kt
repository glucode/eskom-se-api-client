package com.glucode.eskomseapiclient

import com.glucode.eskomseapiclient.models.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

sealed class NetworkResult<T: Any?>(val data: T? = null) {
    class Success<T: Any?>(data: T?): NetworkResult<T>(data)
    abstract class Error<T: Any?>(val message: String? = null): NetworkResult<T>()
    class NetworkError<T: Any?>(message: String? = null): Error<T>(message)
    class HttpError<T: Any?>(message: String? = null): Error<T>(message)
    class GeneralError<T: Any?>(message: String? = null): Error<T>(message)
}

enum class TestEvent(internal val value: String? = null) {
    NONE,
    CURRENT("current"),
    FUTURE("future")
}

interface EskomSeAPIClient {
    suspend fun getStatus(): NetworkResult<StatusResponse>
    suspend fun getAreaInformation(areaId: String, testEvent: TestEvent = TestEvent.NONE): NetworkResult<AreaInformationResponse>
    suspend fun getTopicsNearby(lat: Double, lon: Double): NetworkResult<TopicsNearbyResponse>
    suspend fun getAreasNearby(lat: Double, lon: Double): NetworkResult<AreasNearbyResponse>
    suspend fun searchAreas(text: String): NetworkResult<AreasSearchResponse>
    suspend fun getAllowance(): NetworkResult<AllowanceResponse>
}
internal class EskomSeAPIClientImpl(
    private val host: String,
    private val httpClient: HttpClient
): EskomSeAPIClient {

    private val baseUrl get() = URLBuilder().apply {
        protocol = URLProtocol.HTTPS
        host = this@EskomSeAPIClientImpl.host
        path("business/2.0")
    }

    override suspend fun getStatus(): NetworkResult<StatusResponse> = baseUrl.appendPathSegments("status").build().let {
        getResponse(it)
    }

    override suspend fun getAreaInformation(areaId: String, testEvent: TestEvent): NetworkResult<AreaInformationResponse> = baseUrl.appendPathSegments("area").apply {
        parameters.append("id", areaId)
        testEvent.value?.let {
            parameters.append("test", it)
        }
    }.build().let {
        getResponse(it)
    }

    override suspend fun getTopicsNearby(lat: Double, lon: Double): NetworkResult<TopicsNearbyResponse> = baseUrl.appendPathSegments("topics_nearby").apply {
        parameters.append("lat", lat.toString())
        parameters.append("lon", lon.toString())
    }.build().let {
        getResponse(it)
    }

    override suspend fun getAreasNearby(lat: Double, lon: Double): NetworkResult<AreasNearbyResponse> = baseUrl.appendPathSegments("areas_nearby").apply {
        parameters.append("lat", lat.toString())
        parameters.append("lon", lon.toString())
    }.build().let {
        getResponse(it)
    }

    override suspend fun searchAreas(text: String): NetworkResult<AreasSearchResponse> = baseUrl.appendPathSegments("areas_search").apply {
        parameters.append("text", text)
    }.build().let {
        getResponse(it)
    }

    override suspend fun getAllowance(): NetworkResult<AllowanceResponse> = baseUrl.appendPathSegments("api_allowance").build().let {
        getResponse(it)
    }

    private suspend inline fun <reified T> getResponse(url: Url): NetworkResult<T> {
        val response = httpClient.get(url)

        return try {
            NetworkResult.Success(response.body<T>())
        } catch (e: Exception) {
            println(e)
            NetworkResult.GeneralError()
        }
    }
}