package com.glucode.eskomseapiclient

import com.glucode.eskomseapiclient.models.StatusResponse
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


interface EskomSeAPIClient {
    suspend fun getStatus(): NetworkResult<StatusResponse>
}

internal class EskomSeAPIClientImpl(
    private val httpClient: HttpClient
): EskomSeAPIClient {
    override suspend fun getStatus(): NetworkResult<StatusResponse> {
        val response: HttpResponse = httpClient.get {
            url {
                protocol = URLProtocol.HTTPS
                host = "developer.sepush.co.za"
                path("business/2.0")
                appendPathSegments("status")
            }
        }

        return try {
            NetworkResult.Success(response.body<StatusResponse>())
        } catch (e: Exception) {
            println(e)
            NetworkResult.GeneralError()
        }
    }
}