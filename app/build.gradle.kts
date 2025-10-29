// build.gradle (Module :app)

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.attendance"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.attendance"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    android {
        // ... other configurations like compileSdk, defaultConfig, etc.

        buildFeatures {
            dataBinding = true
            // Optionally, if you are also using ViewBinding:
            // viewBinding = true
        }

        // ...
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
        // Updated to Java 11 based on your new file structure
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    // AndroidX components (using your provided libs structure)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Your specific implementation dependency
    implementation("androidx.gridlayout:gridlayout:1.0.0")


    // ------------------------------------------------------------------
    // API/Networking Dependencies (Retrofit, Gson, and Coroutines)
    // ------------------------------------------------------------------

    // 1. Retrofit (HTTP Client)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // 2. Converter (To parse JSON responses into Kotlin/Java objects, using GSON here)
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // 3. Kotlin Coroutines (For modern asynchronous API calls)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Coroutine lifecycle scopes (for making API calls tied to Activity/Fragment lifespan)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

}
