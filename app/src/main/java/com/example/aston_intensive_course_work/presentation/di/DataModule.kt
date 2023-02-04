package com.example.aston_intensive_course_work.presentation.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.example.aston_intensive_course_work.data.client.RetrofitService
import com.example.aston_intensive_course_work.data.data_base.CacheDataBase
import com.example.aston_intensive_course_work.data.data_base.converter.ArrayConverter
import com.example.aston_intensive_course_work.data.data_base.converter.LocationSimpleItemConverter
import com.example.aston_intensive_course_work.data.repository.CharacterRepository
import com.example.aston_intensive_course_work.data.repository.EpisodeRepository
import com.example.aston_intensive_course_work.data.repository.LocationRepository
import com.example.aston_intensive_course_work.domain.interfaces.CharacterRepositoryInterface
import com.example.aston_intensive_course_work.domain.interfaces.EpisodeRepositoryInterface
import com.example.aston_intensive_course_work.domain.interfaces.LocationRepositoryInterface
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://rickandmortyapi.com/api/"

@Module
class DataModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideRetrofitService(): RetrofitService {
        val logging = HttpLoggingInterceptor().setLevel(level = HttpLoggingInterceptor.Level.BASIC)
        val client = OkHttpClient.Builder().addInterceptor(interceptor = logging).build()

        val retrofitClient: Retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofitClient.create(RetrofitService::class.java)
    }

    @Provides
    @Singleton
    fun provideCacheDataBase(): CacheDataBase {
        return Room.databaseBuilder(
            context = context,
            klass = CacheDataBase::class.java,
            name = "cache_data_base"
        )
            .addTypeConverter(typeConverter = ArrayConverter())
            .addTypeConverter(typeConverter = LocationSimpleItemConverter())
            .build()
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun provideCharacterRepositoryInterface(
        retrofitService: RetrofitService,
        cacheDataBase: CacheDataBase,
        connectivityManager: ConnectivityManager
    ): CharacterRepositoryInterface =
        CharacterRepository(
            retrofit = retrofitService,
            cache = cacheDataBase,
            connectivityManager = connectivityManager
        )

    @Provides
    @Singleton
    fun provideLocationRepositoryInterface(
        retrofitService: RetrofitService,
        cacheDataBase: CacheDataBase,
        connectivityManager: ConnectivityManager
    ): LocationRepositoryInterface =
        LocationRepository(
            retrofit = retrofitService,
            cache = cacheDataBase,
            connectivityManager = connectivityManager
        )

    @Provides
    @Singleton
    fun provideEpisodeRepositoryInterface(
        retrofitService: RetrofitService,
        cacheDataBase: CacheDataBase,
        connectivityManager: ConnectivityManager
    ): EpisodeRepositoryInterface =
        EpisodeRepository(
            retrofit = retrofitService,
            cache = cacheDataBase,
            connectivityManager = connectivityManager
        )
}