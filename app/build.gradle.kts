plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.palinuser"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.palinuser"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // AndroidX Essentials
    implementation(libs.coreKtx)
    implementation(libs.appcompat)
    implementation(libs.material)

    // Network & Image
    implementation(libs.retrofit)
    implementation(libs.converterGson)
    implementation(libs.glide)
    kapt(libs.glideCompiler) // ✅ pakai camelCase sesuai TOML

    // UI List & Refresh
    implementation(libs.recyclerview)
    implementation(libs.swiperefresh)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidxJunit)
    androidTestImplementation(libs.espressoCore)
}