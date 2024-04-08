package com.example.weis.viewModel

import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GoalViewModel : ViewModel() {

    fun convertDate(year: Int, month: Int, day: Int) : String {
        return ("$day/${month + 1}/${year%100}")
    }

    fun convertTime(hour: Int, minute: Int) : String{
        val amOrPm = if (hour < 12) "AM" else "PM"
        return ("${hour % 12}:$minute $amOrPm")
    }
}