package com.example.data.database.queryResults.project

import com.example.data.database.entries.project.Project
import com.example.data.dto.project.GetProject
import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class GetAllProjectQueryResults(
    val httpStatusCode: Int = HttpStatusCode.OK.value,
    val status:Boolean = false,
    val message:String = "",
    val projects:List<GetProject> = emptyList()
)
