package com.alexis.search.di

import android.content.Context
import androidx.room.Room
import com.alexis.search.data.local.datasource.CityDataSource
import com.alexis.search.data.local.room.CityDataBase
import com.alexis.search.data.local.room.dao.CityDao
import com.alexis.search.data.local.room.repository.CityRepositoryImpl
import com.alexis.search.domain.repository.ICityRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DispatcherIO

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DispatcherDefault

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {

    @Provides
    @Singleton
    fun provideSerializer(): Gson = Gson()

    @DispatcherIO
    @Singleton
    @Provides
    fun provideDispacherIO(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @DispatcherDefault
    @Singleton
    @Provides
    fun provideDispacherDefault(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    @Singleton
    @Provides
    fun providerRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CityDataBase::class.java, "SearchDataBase").build()

    @Singleton
    @Provides
    fun providerCityDao(dataBase: CityDataBase) = dataBase.getCityDao()

    @Singleton
    @Provides
    fun providerCityRepository(cityDao: CityDao, cityDataSource: CityDataSource): ICityRepository {
        return CityRepositoryImpl(cityDao, cityDataSource)
    }

}