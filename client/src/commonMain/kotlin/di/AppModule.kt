package com.reza.di

import com.reza.data.api.ApiClient
import com.reza.data.repository.AuthRepositoryImpl
import com.reza.domain.repository.AuthRepository
import org.koin.dsl.module

val clientModule = module {
    single { ApiClient(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}

val appModules = listOf(networkModule, clientModule)