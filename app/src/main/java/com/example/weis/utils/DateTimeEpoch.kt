package com.example.weis.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateTimeEpoch {

    companion object{
        fun dateToEpochTime(dateString: String, format: String): Long {
            val dateFormat = SimpleDateFormat(format, Locale.getDefault())
            val date = dateFormat.parse(dateString) ?: return -1
            return date.time
        }

        fun epochTimeToDate(epochTime: Long): String {
            val format = "dd/MM/yyyy,hh : mm"
            val dateFormat = SimpleDateFormat(format, Locale.getDefault())
            val date = Date(epochTime)
            return dateFormat.format(date)
        }
    }
}