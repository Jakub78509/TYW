package com.example.tyw.utils

import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale

class DateUtilitis {
    companion object{
        private val CZECH_FORMAT = "dd. MM. yyyy"
        private val ENGLISH_FORMAT = "yyyy/MM/dd"


        fun getDateString(date: Long): String {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = date

            val formater: SimpleDateFormat
            if(LanguageUtils.isLanguageCzech()){
                formater = SimpleDateFormat(CZECH_FORMAT, Locale.GERMAN)
            }
            else{
                formater = SimpleDateFormat(ENGLISH_FORMAT, Locale.GERMAN)
            }

            return formater.format(calendar.time)
        }
    }
}