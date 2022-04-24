package com.kgp.myaccount.db

import androidx.room.TypeConverter
import java.time.LocalDateTime

class TypeConverter {
    @TypeConverter
    fun fromLocalDateTimeToString(localDateTime: LocalDateTime): String {
        return localDateTime.toString()
    }

    @TypeConverter
    fun fromStringToLocalDateTime(string: String): LocalDateTime {
        return LocalDateTime.parse(string)
    }
}