package com.example.attendance.ui

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.attendance.R
import com.example.attendance.databinding.ActivityAttendanceBinding
import com.example.attendance.network.AttendanceRecord
import com.example.attendance.network.RetrofitClient
import kotlinx.coroutines.launch

class AttendanceActivity : AppCompatActivity() {

    // 1. Declare binding variable using 'lateinit'
    private lateinit var binding: ActivityAttendanceBinding

    // Use companion object to define constants (like table header colors)
    companion object {
        const val HEADER_TEXT_SIZE = 16f
        const val ROW_PADDING = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 2. Initialize binding within onCreate
        binding = ActivityAttendanceBinding.inflate(layoutInflater)

        // REMOVED: Unnecessary and potentially harmful parent check block
        /*
        if (binding.root.parent != null) {
            (binding.root.parent as ViewGroup).removeView(binding.root)
        }
        */

        setContentView(binding.root)

        // Setup filter Spinners
        setupSpinners()

        // Set up button click listener to fetch data
        binding.buttonApplyFilter.setOnClickListener {
            fetchAttendanceData()
        }

        // Load initial data (optional: load everything by default)
        fetchAttendanceData()
    }

    /**
     * Sets up the data for the Subject and Date Range Spinners.
     */
    private fun setupSpinners() {
        // Load data from strings.xml
        val subjectOptions = resources.getStringArray(R.array.subject_options)
        val dateRangeOptions = resources.getStringArray(R.array.date_range_options)

        // Adapter for Subject Spinner
        val subjectAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            subjectOptions
        )
        binding.spinnerSubject.adapter = subjectAdapter

        // Adapter for Date Range Spinner
        val dateRangeAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            dateRangeOptions
        )
        binding.spinnerDateRange.adapter = dateRangeAdapter
    }

    /**
     * Performs the asynchronous API call to fetch attendance data using Coroutines.
     */
    private fun fetchAttendanceData() {
        val selectedSubject = binding.spinnerSubject.selectedItem.toString()
        val selectedDateRange = binding.spinnerDateRange.selectedItem.toString()

        // Use Coroutines to run network operation off the main thread
        lifecycleScope.launch {
            try {
                // Clear previous results
                binding.tableLayoutRecords.removeAllViews()

                // 1. Fetch Summary Data
                val summaryResponse = RetrofitClient.attendanceService.getAttendanceSummary(
                    subject = selectedSubject,
                    dateRange = selectedDateRange
                )

                // 2. Update Summary Cards
                binding.textValueTotalDays.text = summaryResponse.totalDays.toString()
                binding.textValuePresent.text = summaryResponse.presentCount.toString()
                binding.textValueAbsent.text = summaryResponse.absentCount.toString()

                // Calculate and display percentage
                val percentage = if (summaryResponse.totalDays > 0) {
                    (summaryResponse.presentCount * 100) / summaryResponse.totalDays
                } else 0
                binding.textValuePercentage.text = "$percentage%"


                // 3. Fetch Detailed Records
                val records = RetrofitClient.attendanceService.getAttendanceRecords(
                    subject = selectedSubject,
                    dateRange = selectedDateRange
                )

                // 4. Populate Table
                populateAttendanceTable(records)

            } catch (e: Exception) {
                Log.e("API_CALL", "Error fetching data: ${e.message}", e)
                // Optionally show a user-friendly error message
                binding.textValueTotalDays.text = "Error"
            }
        }
    }

    /**
     * Dynamically builds the attendance table rows.
     */
    private fun populateAttendanceTable(records: List<AttendanceRecord>) {
        val tableLayout = binding.tableLayoutRecords

        // Ensure the table header is always present
        addTableHeader(tableLayout)

        if (records.isEmpty()) {
            addNoRecordsRow(tableLayout)
            return
        }

        // Add records to the table
        records.forEachIndexed { index, record ->
            val row = TableRow(this).apply {
                layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )
                // Alternate background color for readability
                if (index % 2 != 0) {
                    setBackgroundColor(ContextCompat.getColor(context, R.color.light_gray_background))
                }
                setPadding(0, ROW_PADDING, 0, ROW_PADDING)
            }

            // Date Column
            addTextViewToRow(row, record.date, 0.4f)

            // Subject Column
            addTextViewToRow(row, record.subject, 0.4f)

            // Status Column
            val statusText = if (record.status == "Present") "Present ✅" else "Absent ❌"
            val colorRes = if (record.status == "Present") android.R.color.holo_green_dark else android.R.color.holo_red_dark
            addTextViewToRow(row, statusText, 0.2f, ContextCompat.getColor(this, colorRes))

            tableLayout.addView(row)
        }
    }

    /**
     * Utility function to create and add a TextView to a TableRow.
     */
    private fun addTextViewToRow(
        row: TableRow,
        text: String,
        weight: Float,
        color: Int = ContextCompat.getColor(this, android.R.color.black)
    ) {
        val textView = TextView(this).apply {
            this.text = text
            setTextColor(color)
            textSize = 14f
            layoutParams = TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.WRAP_CONTENT,
                weight
            ).apply {
                setMargins(10, 0, 10, 0)
            }
        }
        row.addView(textView)
    }

    /**
     * Adds the static header row to the table.
     */
    private fun addTableHeader(tableLayout: ViewGroup) {
        val headerRow = TableRow(this).apply {
            setBackgroundColor(ContextCompat.getColor(context, R.color.purple_header_color)) // Custom color
            setPadding(0, ROW_PADDING, 0, ROW_PADDING)
        }

        addHeaderTextView(headerRow, "Date", 0.4f)
        addHeaderTextView(headerRow, "Subject", 0.4f)
        addHeaderTextView(headerRow, "Status", 0.2f)

        tableLayout.addView(headerRow)
    }

    /**
     * Utility function for creating header TextViews.
     */
    private fun addHeaderTextView(row: TableRow, text: String, weight: Float) {
        val textView = TextView(this).apply {
            this.text = text
            // Note: `textStyle` needs to be imported or fully qualified
            // It's safer to use android.graphics.Typeface
            typeface = android.graphics.Typeface.DEFAULT_BOLD
            setTextColor(ContextCompat.getColor(context, android.R.color.white))
            textSize = HEADER_TEXT_SIZE
            layoutParams = TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.WRAP_CONTENT,
                weight
            ).apply {
                setMargins(10, 0, 10, 0)
            }
            gravity = if (text == "Status") android.view.Gravity.END else android.view.Gravity.START
        }
        row.addView(textView)
    }

    /**
     * Adds a row indicating no records were found.
     */
    private fun addNoRecordsRow(tableLayout: ViewGroup) {
        val row = TableRow(this).apply {
            layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            setPadding(0, ROW_PADDING * 2, 0, ROW_PADDING * 2)
        }

        val textView = TextView(this).apply {
            text = "No attendance records found for the selected filters."
            textSize = 16f
            layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1.0f // Take up all available space
            )
            gravity = android.view.Gravity.CENTER
            setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
        }

        row.addView(textView)
        tableLayout.addView(row)
    }
}
