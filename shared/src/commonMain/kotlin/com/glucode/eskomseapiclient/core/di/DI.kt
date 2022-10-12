package com.glucode.eskomseapiclient.core.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

class DI(appDeclaration: KoinAppDeclaration = {}) {

    constructor(): this({})

    init {
        startKoin{
            modules(
                coreModule
            )
            appDeclaration()
        }
    }
}