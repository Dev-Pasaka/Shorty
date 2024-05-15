package com.example.data.database.entries.project

import com.example.common.utils.dateAndTimeUtils.DateAndTimeUtils
import com.example.data.dto.project.GetProject
import com.example.data.dto.project.ProjectType
import com.example.data.dto.project.SubscriptionType
import com.example.data.repository.GenerateApiKeyImpl
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Project(
    val projectId:String = ObjectId().toString() ,
    val apiKey:String? = GenerateApiKeyImpl().generateApiKey() ,
    val projectName:String,
    val email:String,
    val projectState:ProjectState = ProjectState.ACTIVE,
    val subscriptionType: SubscriptionType,
    val projectType: ProjectType,
    val postPayCredits:Int = 0,
    val prePayCredits:Int = 0,
    val createdAt:String = DateAndTimeUtils.currentTime(),
){
    fun toGetProject() = GetProject(
        projectId = projectId,
        projectName = projectName,
        projectState = projectState,
        subscriptionType = subscriptionType,
        projectType = projectType,
        createdAt = createdAt
    )
}