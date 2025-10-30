plugins {
    // Assuming these reference entries in your version catalog (libs.versions.toml)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.attendance"
    // Keep compileSdk up-to-date
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.attendance"
        minSdk = 24
        targetSdk = 36 // Keep targetSdk matching compileSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // ✅ View Binding Configuration (Correctly placed here)
    buildFeatures {
        viewBinding = true
        // Set dataBinding to false unless you specifically plan to use it
        dataBinding = false
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
        sourceCompatibility = JavaVersion.VERSION_17 // Recommended for modern Android
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17" // Match JVM target to Java version
    }
}


dependencies {

    // ------------------------------------------------------------------
    // AndroidX components (Keeping your libs aliases, assuming they are up to date)
    // ------------------------------------------------------------------
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Your specific implementation dependency
    implementation("androidx.gridlayout:gridlayout:1.0.0") // Latest official version

    // ------------------------------------------------------------------
    // API/Networking Dependencies (Retrofit, Gson, and Coroutines)
    // ------------------------------------------------------------------

    // 1. Retrofit (HTTP Client) - 2.9.0 is still the stable version
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // 2. Converter (To parse JSON responses into Kotlin/Java objects)
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // 3. Kotlin Coroutines (Updated from 1.7.3 to 1.8.1 for stability)
    // 1.8.1 is the latest stable version and offers better compatibility with recent AndroidX libs
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    // Coroutine lifecycle scopes (Updated from 2.6.2 to 2.8.0 for stability)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0")

    // Optional: for debugging network traffic (4.12.0 is latest)
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
