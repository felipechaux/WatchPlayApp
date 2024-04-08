package com.example.sensormanager.di

import android.app.Application
import com.example.sensormanager.AccelerometerSensor
import com.example.sensormanager.GyroscopeSensor
import com.example.sensormanager.MeasurableSensor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SensorModule {

    @Singleton
    @Provides
    @Named("accelerometer")
    fun provideAccelerometerSensor(app: Application): MeasurableSensor {
        return AccelerometerSensor(app)
    }

    @Singleton
    @Provides
    @Named("gyroscope")
    fun provideGyroscopeSensor(app: Application): MeasurableSensor {
        return GyroscopeSensor(app)
    }
}
