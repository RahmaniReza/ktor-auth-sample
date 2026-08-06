package com.reza.di

import com.reza.data.api.AuthApiClient
import com.reza.data.repository.AuthRepositoryImpl
import com.reza.domain.repository.AuthRepository
import org.koin.dsl.module

val clientModule = module {
    single { AuthApiClient(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}

val appModules = listOf(networkModule, clientModule)