package com.example.attendance.network

/**
 * Data class representing a single student's attendance entry.
 * This should match the structure of an item in the 'records' list from the server.
 */
data class AttendanceRecord(
    val studentId: String,
    val studentName: String,
    val date: String, // e.g., "2025-10-29"
    val status: String, // e.g., "Present", "Absent", "Late"
    val checkInTime: String? // Optional time of check-in, e.g., "09:05 AM"
)

/**
 * The main data class representing the complete response from the API endpoint.
 * Corresponds to the JSON structure returned by the /api/attendance/summary endpoint.
 */
data class SummaryResponse(
    // Summary statistics for the selected criteria
    val totalRecords: Int,
    val totalPresent: Int,
    val totalAbsent: Int,
    val attendancePercentage: Double,

    // The detailed list of attendance records
    val records: List<AttendanceRecord>
)
