package com.glucode.eskomseapiclient.core.di

import com.glucode.eskomseapiclient.EskomSeAPIClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object Dependencies: KoinComponent {
    val eskomSeAPIClient: EskomSeAPIClient by inject()
}