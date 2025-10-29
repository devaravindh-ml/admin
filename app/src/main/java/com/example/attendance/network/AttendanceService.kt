package com.example.attendance.network

import com.google.gson.annotations.SerializedName

/**
 * Data class representing a single attendance record, typically used in the table.
 */
data class AttendanceRecord(
    val date: String,
    val subject: String,
    val status: String // "Present" or "Absent"
)

/**
 * Data class representing the main summary response, used to populate the cards.
 */
data class SummaryResponse(
    val totalDays: Int,
    val presentCount: Int,
    val absentCount: Int,
    val percentage: Float, // e.g., 40.0

    // The list of detailed records for the table
    @SerializedName("records")
    val attendanceRecords: List<AttendanceRecord>
)
