package com.example.attendance.network

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit interface defining the API endpoints for fetching attendance data.
 * The 'suspend' keyword indicates this is a coroutine function, allowing safe
 * execution off the main thread (since you added coroutine dependencies).
 */
interface AttendanceService {

    /**
     * Fetches the attendance summary and detailed records based on filter criteria.
     * * @param subject The subject selected by the user (e.g., "Frontend Programming").
     * @param dateRange The date range selected (e.g., "Last 7 Days").
     * @return A SummaryResponse object containing statistics and the list of records.
     */
    @GET("/api/attendance/summary")
    suspend fun getAttendanceSummary(
        @Query("subject") subject: String,
        @Query("dateRange") dateRange: String
    ): SummaryResponse

    // You could add other API endpoints here if needed, like:
    // @GET("/api/subjects")
    // suspend fun getAvailableSubjects(): List<String>
}
