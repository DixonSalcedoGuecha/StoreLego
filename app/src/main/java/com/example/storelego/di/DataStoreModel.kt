package com.example.storelego.di

import android.content.Context
import androidx.room.Room
import com.example.storelego.datasource.DbBuyDataSource
import com.example.storelego.datasource.RestBuyDataSource
import com.example.storelego.datasource.RestDetailDataSource
import com.example.storelego.datasource.RestProductDataSource
import com.example.storelego.model.dao.ProductsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModel {

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

    @Singleton
    @Provides
    fun restBuyDataSource(retrofit: Retrofit): RestBuyDataSource =
        retrofit.create(RestBuyDataSource::class.java)

    @Singleton
    @Provides
    fun dbBuyDataSource(@ApplicationContext context: Context): DbBuyDataSource {
        return Room.databaseBuilder(context,DbBuyDataSource::class.java, "shopping_cart_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun recipeDao(db: DbBuyDataSource): ProductsDao = db.productsDao()

}