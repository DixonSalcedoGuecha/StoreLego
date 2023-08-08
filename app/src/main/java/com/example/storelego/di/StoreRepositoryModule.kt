package com.example.storelego.di

import com.example.storelego.repository.LoginRepository
import com.example.storelego.repository.LoginRepositoryImp
import com.example.storelego.repository.ProductsRepository
import com.example.storelego.repository.ProductsRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
abstract class StoreRepositoryModule {
    @Binds
    @Singleton
    abstract fun storeRepository(repo: ProductsRepositoryImp): ProductsRepository

    @Binds
    @Singleton
    abstract fun loginRepository(repo: LoginRepositoryImp): LoginRepository
}