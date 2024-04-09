plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.requestlocationmanager"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(libs.accompanist.permission)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    implementation(libs.play.services.location)
    testImplementation(libs.junit)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockito.inline)
    androidTestImplementation(libs.androidx.junit)
    ksp(libs.hilt.compiler)
}