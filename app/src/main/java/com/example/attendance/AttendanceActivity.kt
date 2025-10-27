package com.example.attendance

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// --- 1. Data Class for Attendance Record ---
data class AttendanceRecord(
    val date: String,
    val subject: String,
    val status: String, // "Present" or "Absent"
    val isPresent: Boolean
)

class AttendanceActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    // Sample Data (This would typically come from an API or database)
    private val attendanceData = listOf(
        AttendanceRecord("10-09-2025", "Maths", "Absent", false),
        AttendanceRecord("03-09-2025", "Hindhi", "Absent", false),
        AttendanceRecord("02-09-2025", "Maths", "Present", true),
        AttendanceRecord("01-09-2025", "Software Engineering", "Present", true),
        AttendanceRecord("01-09-2025", "Maths", "Present", true),
        AttendanceRecord("01-09-2025", "Hindhi", "Absent", false),
        AttendanceRecord("01-09-2025", "Data Structure", "Absent", false),
        AttendanceRecord("30-09-2025", "Frontend Programming", "Absent", false),
        AttendanceRecord("28-09-2025", "Data Structure", "Present", true),
        AttendanceRecord("22-09-2025", "Maths", "Absent", false)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // CRUCIAL: Using your specified layout file name (student.xml)
        setContentView(R.layout.student)

        // --- 2. Initialize Views and Set Up Logic ---
        setupStatBoxes()
        setupRecyclerView()
        setupButtons()
    }

    // --- 3. Function to Set Up Stat Boxes (Handles dynamic color) ---
    private fun setupStatBoxes() {
        // Find the GridLayout in the main XML
        val statGrid = findViewById<LinearLayout>(R.id.stat_grid_layout)

        // Define the data and corresponding colors
        val stats = listOf(
            Triple("Total Days", "10", "#42A5F5"), // Blue
            Triple("Present", "4", "#4CAF50"),     // Green
            Triple("Absent", "6", "#F44336"),      // Red
            Triple("Percentage", "40.0%", "#9C27B0") // Purple
        )

        // Loop through the children (the included CardViews) and apply data/color
        for (i in 0 until stats.size) {
            // Since GridLayout includes might not be direct children in all setups,
            // we find the root layout and assume the include IDs are correctly defined.
            // Note: Since we don't have direct access to the child views of the GridLayout
            // by their `include` ID (stat_total_days, etc.) without inflating them,
            // we will find the root GridLayout and iterate over its children.

            // We use the findViewById approach for the root stat boxes
            // to ensure we find the correct include root elements.
            val statIds = listOf(R.id.stat_total_days, R.id.stat_present, R.id.stat_absent, R.id.stat_percentage)
            val statView = findViewById<View>(statIds[i])

            val (label, value, colorHex) = stats[i]

            // Find the inner views by their IDs defined in stat_box.xml
            val backgroundLayout = statView.findViewById<LinearLayout>(R.id.stat_background_layout)
            val labelTextView = statView.findViewById<TextView>(R.id.stat_label)
            val valueTextView = statView.findViewById<TextView>(R.id.stat_value)

            // Set dynamic properties
            val statColor = Color.parseColor(colorHex)
            backgroundLayout.setBackgroundColor(statColor)

            // Update Text
            labelTextView.text = label
            valueTextView.text = value

            // Set text color to white for contrast
            labelTextView.setTextColor(Color.WHITE)
            valueTextView.setTextColor(Color.WHITE)
        }
    }

    // --- 4. Function to Set Up RecyclerView (Handles the Attendance List) ---
    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.attendance_records_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AttendanceAdapter(attendanceData, this)
    }

    // --- 5. Function for Button Clicks ---
    private fun setupButtons() {
        val applyFilterButton = findViewById<Button>(R.id.apply_filter_button)
        val backToHomeButton = findViewById<Button>(R.id.back_to_home_button)

        applyFilterButton.setOnClickListener {
            // Logic to read from Spinners/EditTexts and filter the data
            Toast.makeText(this, "Applying filter...", Toast.LENGTH_SHORT).show()
        }

        backToHomeButton.setOnClickListener {
            Toast.makeText(this, "Navigating to Home...", Toast.LENGTH_SHORT).show()
            finish() // Example: close this activity
        }
    }
}

// --- 6. RecyclerView Adapter ---
class AttendanceAdapter(private val records: List<AttendanceRecord>, private val context: Context) :
    RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder>() {

    inner class AttendanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // IDs defined in attendance_row.xml MUST be correctly referenced here
        val date: TextView = itemView.findViewById(R.id.row_date)
        val subject: TextView = itemView.findViewById(R.id.row_subject)
        val status: TextView = itemView.findViewById(R.id.row_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.attendance_row, parent, false)
        return AttendanceViewHolder(view)
    }

    override fun onBindViewHolder(holder: AttendanceViewHolder, position: Int) {
        val record = records[position]

        holder.date.text = record.date
        holder.subject.text = record.subject
        holder.status.text = record.status

        // Set dynamic status color and icon based on presence
        val statusColor = if (record.isPresent) Color.parseColor("#4CAF50") else Color.parseColor("#F44336")
        holder.status.setTextColor(statusColor)

        // R.drawable references here require the drawables to exist
        val drawableId = if (record.isPresent) R.drawable.ic_check_green else R.drawable.ic_close_red

        // This sets the drawable to the end of the TextView
        val drawable = ContextCompat.getDrawable(context, drawableId)
        drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)

        holder.status.setCompoundDrawablesWithIntrinsicBounds(
            null, // left
            null, // top
            drawable, // right/end
            null  // bottom
        )
    }

    override fun getItemCount() = records.size
}
