package com.example.common.utils.dateAndTimeUtils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateAndTimeUtils{

    /***Get the current time in human-readable string*/
    fun currentTime(): String {
        val currentTimestamp = System.currentTimeMillis()
        val instant = Instant.ofEpochMilli(currentTimestamp)
        val localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        /**Format the LocalDateTime object into a human-readable string*/
        return localDateTime.format(formatter)
    }
}