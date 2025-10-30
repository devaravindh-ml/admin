package com.example.attendance.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendance.network.RetrofitClient
import com.example.attendance.network.SummaryResponse
import kotlinx.coroutines.launch

/**
 * ViewModel to manage and expose attendance data to the UI.
 */
class AttendanceViewModel : ViewModel() {

    // LiveData to hold the fetched attendance summary
    private val _summary = MutableLiveData<SummaryResponse?>()
    val summary: LiveData<SummaryResponse?> = _summary

    // LiveData to track the loading state (to show a spinner/progress bar)
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    // LiveData to handle error messages
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    /**
     * Initiates the API call to fetch attendance data.
     * @param subject The selected subject filter.
     * @param dateRange The selected date range filter.
     */
    fun fetchAttendanceSummary(subject: String, dateRange: String) {
        // Clear previous error and set loading state
        _errorMessage.value = null
        _isLoading.value = true

        // Launch the coroutine on the ViewModel's scope
        viewModelScope.launch {
            try {
                // IMPORTANT: Ensure BASE_URL in RetrofitClient.kt is set to
                // http://10.0.2.2:5000/ if testing on an Android emulator!

                // Call the suspended function defined in AttendanceService
                val result = RetrofitClient.attendanceService.getAttendanceSummary(subject, dateRange)

                // Update LiveData with the successful result
                _summary.value = result

            } catch (e: Exception) {
                // Handle network errors (e.g., connection refused, timeout, JSON parsing error)
                _errorMessage.value = "Failed to load attendance data: ${e.message}"
                e.printStackTrace()
            } finally {
                // Ensure loading indicator is dismissed
                _isLoading.value = false
            }
        }
    }
}
