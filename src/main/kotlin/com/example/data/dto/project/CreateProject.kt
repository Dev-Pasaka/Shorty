package com.example.data.dto.project

import kotlinx.serialization.Serializable

@Serializable
data class CreateProject(
    val projectName:String,
    val subscriptionType: SubscriptionType,
    val projectType: ProjectType,
)