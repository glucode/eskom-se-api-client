package com.glucode.eskomseapiclient.core.di

import com.glucode.eskomseapiclient.AccessTokenProvider
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

class DI(
    host: String,
    accessTokenProvider: AccessTokenProvider,
    enableHttpLogging: Boolean = false,
    appDeclaration: KoinAppDeclaration? = null
) {

    init {
        startKoin{
            modules(
                CoreModule(
                    host,
                    accessTokenProvider,
                    enableHttpLogging
                ).module
            )
            appDeclaration?.let { it() }
        }
    }
}