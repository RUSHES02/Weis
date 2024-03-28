package com.example.weis.viewModel

import androidx.lifecycle.ViewModel

class GoalViewModel : ViewModel() {

    fun convertDate(year: Int, month: Int, day: Int) : String {
        return ("$day/$month/${year%100}")
    }

    fun convertTime(hour: Int, minute: Int) : String{
        val amOrPm = if (hour < 12) "AM" else "PM"
        return ("${hour % 12}:$minute $amOrPm")
    }
}