package com.glucode.eskomseapiclient

interface AccessTokenProvider {
    suspend fun provideAccessToken(): String
}