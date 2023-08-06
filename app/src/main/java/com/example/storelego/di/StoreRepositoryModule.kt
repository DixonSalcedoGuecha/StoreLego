package com.example.storelego.di

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
    abstract fun recipeRepository(repo: ProductsRepositoryImp): ProductsRepository
}