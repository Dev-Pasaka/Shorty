package com.example.domain.repository

import com.example.data.database.entries.project.Project
import com.example.data.database.queryResults.project.*
import com.example.data.dto.project.GetProject

interface ProjectRepository {

    suspend fun createProject(project: Project):CreateProjectQueryResult
    suspend fun deleteProject(projectName: String, email: String):DeleteProjectQueryResult
    suspend fun resetApiKey(projectName: String, email: String): ResetApiKeyQueryResult
    suspend fun getAPiKey(projectName: String, email: String): GetApiKeyQueryResult
    suspend fun verifyApiKey(apiKey:String):VerifyApiKeyQueryResult
    suspend fun getAllProject(email:String):GetAllProjectQueryResults
    suspend fun getProject(projectName: String, email: String):GetProjectQueryResults
    fun calculateBill(projectName: String):Double?
}