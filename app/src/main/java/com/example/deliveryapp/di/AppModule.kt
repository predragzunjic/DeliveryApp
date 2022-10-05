package com.example.deliveryapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.deliveryapp.DeliveryAppDao
import com.example.deliveryapp.DeliveryAppDatabase
import com.example.deliveryapp.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(app, DeliveryAppDatabase::class.java, "DeliveryApp.db")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideDeliveryAppDao(db: DeliveryAppDatabase) = db.getDao()

    @Singleton
    @Provides
    fun provideClientDao(db: DeliveryAppDatabase) = db.getClientDao()

    @Singleton
    @Provides
    fun provideRestaurantDao(db: DeliveryAppDatabase) = db.getRestaurantDao()

    @Singleton
    @Provides
    fun provideTownDao(db: DeliveryAppDatabase) = db.getTownDao()


}