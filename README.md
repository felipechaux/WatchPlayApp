# Ver Play App - Coding Challenge

This App contains the following features : 

* Loading of Video by URL
* Replay of video by 10 meters of traveled distance - User Location
* Stop of video according to Shake Event - Accelerometer Sensor
* Time control of video (Rewind and Forward) according to Z-AXIS - Gyroscope Sensor
* Volume control of video according to X-AXIS - Gyroscope Sensor

### Architecture

(Clean Architecture) + MVVM 

#### Layers

* App: UI.
* Presentation: View Models.
* UseCases
* RequestLocationManager
* SensorManager
* VideoPlayerManager

### External Libraries

* ExoPlayer
* Accompanist permissions
* Airbnb Lottie Animations

### Unit Tests

* CalculateMetersByLocationUseCaseTest
* GetShakeAccelerationUseCaseTest


## Screenshots 

<p align="center">
  <img width="432" height="888" src="screenshots/splash.png">
  <img width="432" height="888" src="screenshots/home.png">
</p>
