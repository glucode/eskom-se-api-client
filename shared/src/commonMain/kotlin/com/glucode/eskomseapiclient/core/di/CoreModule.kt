package com.glucode.eskomseapiclient.core.di

import com.glucode.eskomseapiclient.AccessTokenProvider
import com.glucode.eskomseapiclient.EskomSeAPIClient
import com.glucode.eskomseapiclient.EskomSeAPIClientImpl
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module


internal class CoreModule(
    private val accessTokenProvider: AccessTokenProvider,
    enableHttpLogging: Boolean = false
) {
    val module = module {
        single<HttpClient> {
            HttpClient {
                install(ContentNegotiation) {
                    json(
                        Json {
                            prettyPrint = true
                            isLenient = true
                        }
                    )
                }
                if (enableHttpLogging) {
                    install(Logging) {
                        logger = Logger.DEFAULT
                        level = LogLevel.ALL
                    }
                }
            }.apply {
                plugin(HttpSend).intercept { request ->
                    val token = accessTokenProvider.provideAccessToken()
                    request.header("token", token)
                    execute(request)
                }
            }
        }

        single<EskomSeAPIClient> {
            EskomSeAPIClientImpl(
                httpClient = get()
            )
        }
    }
}
