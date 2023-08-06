package com.example.storelego.di

import com.example.storelego.datasource.RestDetailDataSource
import com.example.storelego.datasource.RestProductDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModel {

    @Singleton
    @Provides
    @Named("BaseUrl")
    fun providesBaseUrl() = "https://us-central1-api-back-admission-test.cloudfunctions.net/"

    @Singleton
    @Provides
    fun providerRetrofit(@Named("BaseUrl") baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    @Singleton
    @Provides
    fun restProductsDataSource(retrofit: Retrofit): RestProductDataSource =
        retrofit.create(RestProductDataSource::class.java)

    @Singleton
    @Provides
    fun restDetailDataSource(retrofit: Retrofit): RestDetailDataSource =
        retrofit.create(RestDetailDataSource::class.java)

}