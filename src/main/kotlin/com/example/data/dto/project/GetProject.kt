package com.example.data.dto.project

import com.example.common.utils.dateAndTimeUtils.DateAndTimeUtils
import com.example.data.database.entries.project.ProjectState
import com.example.data.repository.GenerateApiKeyImpl
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class GetProject(
    val projectId:String,
    val projectName:String,
    val projectState: ProjectState = ProjectState.ACTIVE,
    val subscriptionType: SubscriptionType,
    val projectType: ProjectType,
    val currentBill:Double = 0.0,
    val currentSymbol:String = "KES",
    val createdAt:String,
)
