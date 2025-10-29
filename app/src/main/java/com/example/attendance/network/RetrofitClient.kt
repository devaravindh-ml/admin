package com.example.attendance.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton object to initialize Retrofit and provide the AttendanceService instance.
 * Replace BASE_URL with the actual address of your backend API.
 */
object RetrofitClient {
    // IMPORTANT: Replace this with your actual server URL!
    // For local testing, use the IP address of your host machine (e.g., "http://10.0.2.2:8080/")
    private const val BASE_URL = "https://your-api-domain.com/"

    // 1. Lazy initialization of the Retrofit instance
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            // Add the Gson converter you enabled in the build.gradle file
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 2. Lazy initialization of the AttendanceService interface
    // This is the object you will call methods on (e.g., RetrofitClient.attendanceService.getAttendanceSummary())
    val attendanceService: AttendanceService by lazy {
        retrofit.create(AttendanceService::class.java)
    }
}
