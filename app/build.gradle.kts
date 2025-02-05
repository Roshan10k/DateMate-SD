plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    buildFeatures {
        viewBinding = true
    }
    namespace = "com.example.datemate_sd"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.datemate_sd"
        minSdk = 27
        targetSdk = 34
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
    // Core dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material) // For Material Design components like RangeSlider
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("com.airbnb.android:lottie:6.6.1")
    implementation("com.google.android.material:material:1.9.0")

    implementation(platform("com.google.firebase:firebase-bom:33.8.0"))
    implementation ("com.google.firebase:firebase-messaging")

    // Firebase dependencies
    implementation ("com.google.firebase:firebase-firestore-ktx:24.0.0")
    implementation ("com.google.firebase:firebase-auth:21.0.1")

    // OkHttp for sending push notifications
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")

    //for image saving to database
    implementation("com.cloudinary:cloudinary-android:2.1.0")
    implementation("com.squareup.picasso:picasso:2.8")


}
