package com.dienty.structure.common

import com.dienty.structure.data.adapter.NetworkResponseAdapterFactory
import com.dienty.structure.data.repository.ExampleRepository
import com.dienty.structure.data.repository.ExampleRepositoryImpl
import com.dienty.structure.data.service.ExampleService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val API_TIME_OUT = 10L
    const val BASE_URL = "https://random-data-api.com/api/"

    init {
        //System.loadLibrary("app-values")
    }

    @DefaultInterceptor
    @Provides
    fun providesInterceptor() = Interceptor { chain ->
        val request = chain.request().newBuilder().build()
        return@Interceptor chain.proceed(request)
    }

    @DefaultClient
    @Provides
    @Singleton
    fun providesClient(
        @DefaultInterceptor interceptor: Interceptor,
        converterFactory: GsonConverterFactory,
        adapterFactory: NetworkResponseAdapterFactory
    ): Retrofit {
        val httpClient = OkHttpClient.Builder().connectTimeout(API_TIME_OUT, TimeUnit.SECONDS)
        httpClient.addInterceptor(interceptor)
        httpClient.authenticator(Authenticator.NONE)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(adapterFactory)
            .build()
    }

    @Provides
    @Reusable
    fun provideExampleService(@DefaultClient retrofit: Retrofit): ExampleService {
        return retrofit.create(ExampleService::class.java)
    }

    @Provides
    @Reusable
    fun providesExampleRepository(service: ExampleService): ExampleRepository =
        ExampleRepositoryImpl(service)

    private external fun getBaseUrl(): String
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultClient