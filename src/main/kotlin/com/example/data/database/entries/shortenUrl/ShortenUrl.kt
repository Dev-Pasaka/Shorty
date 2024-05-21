package com.example.data.database.entries.shortenUrl

import com.example.common.utils.dateAndTimeUtils.DateAndTimeUtils
import com.example.data.dto.shorten.GetShortenUrlDto
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
@Serializable
data class ShortenUrl(
    val id:String = ObjectId().toString(),
    val email:String? = null,
    val projectName:String,
    val longUrl:String,
    val shortenUrl: String? = null,
    val clicks:Int = 0,
    val createdAt:String = DateAndTimeUtils.currentTime(),
    val analytics: List<Analytics> = emptyList()
){
    fun toGetShortenUrl():GetShortenUrlDto{
        return GetShortenUrlDto(
            projectName = projectName,
            longUrl = longUrl,
            shortUrl = shortenUrl ,
            clicks = clicks,
            analytics = analytics,
            createdAt = createdAt
        )
    }
}