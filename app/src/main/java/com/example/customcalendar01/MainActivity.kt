package com.example.customcalendar01

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val calendarGrid = calendar_grid
        updateCalendar(this, calendarGrid, LocalDate.now())
    }
}




fun updateCalendar(context: Context, gridView: GridView, refDate: LocalDate) {

    val gridDates = mutableListOf<Int>()

    val startOffset = LocalDate.of(refDate.year, refDate.month, 1).dayOfWeek.value-1
    Log.d("TAG", "start day of week is $startOffset")

    val daysInMonth = refDate.lengthOfMonth()
    Toast.makeText(context, "there are $daysInMonth days in the current month", Toast.LENGTH_LONG).show()

    // Fill in days from prev month
    val daysInPrevMonth = refDate.minusMonths(1).lengthOfMonth()

    if(startOffset > 0) {
        for (i in daysInPrevMonth-startOffset+1..daysInPrevMonth) {
            gridDates.add(i)
        }
    }

    // Fill in days in current month
    var i = 1
    while (i <= daysInMonth) {
        gridDates.add(i)
        i++
    }


    // Fill in days in next month
    val daysOnLastRow = gridDates.count().rem(7)

    if(gridDates.count() < 7*6 && daysOnLastRow != 0) {
        val daysToFill = 7 - daysOnLastRow
        for (j in 1..daysToFill) {
            gridDates.add(j)
        }
    } else {
        Log.d("TAG", "No need to fill in next month dates")
    }


    Log.d("TAG", "The date array is: $gridDates")

    val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, gridDates)
    gridView.adapter = adapter
}
