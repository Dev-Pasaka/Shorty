package com.example.data.repository

import com.example.data.database.Entries
import com.example.data.database.entries.project.Project
import com.example.data.database.queryResults.project.*
import com.example.data.dto.project.GetProject
import com.example.data.dto.project.ProjectType
import com.example.data.dto.project.SubscriptionType
import com.example.domain.repository.GenerateApiKey
import com.example.domain.repository.ProjectRepository
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectRepositoryImpl(
    private val entries:Entries = Entries,
    private val generateApiKey: GenerateApiKey = GenerateApiKeyImpl()
):ProjectRepository {
    override suspend fun createProject(project: Project): CreateProjectQueryResult = withContext(Dispatchers.IO) {

        /** Check if a project with similar name exists */
        val doesProjectNameExist = entries.dbProject.find(Filters.eq(Project::projectName.name, project.projectName)).firstOrNull()
        if (doesProjectNameExist != null) return@withContext CreateProjectQueryResult(message = "Project with similar name already exists")

        /** Get subscriptio quotas and  price rates */
        val getQuotas = entries.dbSubscription.find().firstOrNull()
        val apiKey = generateApiKey.generateApiKey()

        /** Create project in database */
        var createProject = false
        if (project.subscriptionType == SubscriptionType.PREPAID){
            createProject = entries.dbProject.insertOne(
                project.copy(
                    apiKey = apiKey,
                    prePayCredits = when(project.projectType){
                        ProjectType.SANDBOX -> getQuotas?.sandBoxPrepaidCredits ?: 0
                        ProjectType.PRODUCTION -> getQuotas?.livePrepaidCredits ?: 0
                    }
                )
            ).wasAcknowledged()
        }else{
            createProject = entries.dbProject.insertOne(
                project.copy(
                    apiKey = apiKey,
                )
            ).wasAcknowledged()
        }


        if (!createProject) return@withContext CreateProjectQueryResult(
            httpStatusCode = HttpStatusCode.InternalServerError.value,
            message = "An expected error occurred.Failed to create project"
        )
        return@withContext CreateProjectQueryResult(status = true, apiKey = apiKey, message = "Project created successfully")
    }

    override suspend fun deleteProject(projectName: String): DeleteProjectQueryResult = withContext(Dispatchers.IO) {

        /** Check if project exists */
        entries.dbProject.find(Filters.eq(Project::projectName.name, projectName))
            ?: return@withContext DeleteProjectQueryResult(message = "Project not found")

        /** Delete project form Database*/
        val deleteProjectResult = entries.dbProject.deleteOne(Filters.eq(Project::projectName.name, projectName)).wasAcknowledged()
        if (!deleteProjectResult) return@withContext DeleteProjectQueryResult(
            httpStatusCode = HttpStatusCode.InternalServerError.value,
            message = "An expected error occurred. Failed to delete project"
        )

        return@withContext DeleteProjectQueryResult(status = true, message = "Project deleted successfully")
    }

    override suspend fun resetApiKey(projectName: String): ResetApiKeyQueryResult = withContext(Dispatchers.IO) {

        /** Check if project exists */
        entries.dbProject.find(Filters.eq(Project::projectName.name, projectName)).firstOrNull()
            ?: return@withContext ResetApiKeyQueryResult(
                message = "Project with name above does not exist"
            )

        /** Generate new api-key and update the old key with the new key */
        val newApiKey = generateApiKey.generateApiKey()
        entries.dbProject.updateOne(
            Filters.eq(Project::projectName.name, projectName),
            Updates.set(Project::apiKey.name, newApiKey)
        ) ?: return@withContext ResetApiKeyQueryResult(
            message = "Project not found"
        )

        ResetApiKeyQueryResult(status = true, apiKey = newApiKey, message = "Api key reset was successful")
    }

    override suspend fun getAPiKey(projectName: String): GetApiKeyQueryResult  = withContext(Dispatchers.IO){

        /** Check if project exists */
        val result = entries.dbProject.find(Filters.eq(Project::projectName.name, projectName)).firstOrNull()
            ?: return@withContext GetApiKeyQueryResult(
                message = "Project not found"
            )
        return@withContext GetApiKeyQueryResult(
            status = true,
            apiKey = result.apiKey ?: "",
            message = "Kindly store your api key safely."
        )
    }

    override suspend fun verifyApiKey(apiKey: String):VerifyApiKeyQueryResult  = withContext(Dispatchers.IO){
        val result = entries.dbProject.find(Filters.eq(Project::apiKey.name, apiKey)).firstOrNull() ?: return@withContext VerifyApiKeyQueryResult(
            message = "Project not found"
        )
        val isApiKeyValid =  (result.apiKey == apiKey)
        if (!isApiKeyValid){ return@withContext VerifyApiKeyQueryResult(message = "Invalid Api key") }
        VerifyApiKeyQueryResult(message = "Api key is valid", status = true)
    }

    override suspend fun getAllProject(email:String): GetAllProjectQueryResults = withContext(Dispatchers.IO){

        val projects = entries.dbProject.find(Filters.eq(Project::email.name, email)).toList().map {project->
            project.toGetProject().copy(currentBill = calculateBill(projectName = project.projectName) ?: 0.0)
        }
        GetAllProjectQueryResults(projects = projects, message = "Success", status = true)
    }

    override suspend fun getProject(projectName: String): GetProjectQueryResults = withContext(Dispatchers.IO) {
        val project =  entries.dbProject.find(Filters.eq(Project::projectName.name, projectName)).firstOrNull()?.toGetProject()?.copy(
            currentBill = calculateBill(projectName = projectName) ?: 0.0
        )
        return@withContext GetProjectQueryResults(projects = project, status = true, message = "Success")
    }

    override fun calculateBill(projectName: String): Double? {

        /** Get subscription Categories */
        val subscriptionCategory = entries.dbSubscription.find().firstOrNull()

        /** Get project*/
        val project = entries.dbProject.find(Filters.eq(Project::projectName.name, projectName)).firstOrNull()

        /** Calculate current bill */
        return if (project?.projectType == ProjectType.PRODUCTION && project.subscriptionType == SubscriptionType.PREPAID ){
            (subscriptionCategory?.postPayCreditRate?.times(project.prePayCredits))
        }else if (project?.projectType == ProjectType.PRODUCTION && project.subscriptionType == SubscriptionType.POSTPAID){
            subscriptionCategory?.postPayCreditRate?.times(project.prePayCredits)
        }else{
            0.0
        }
    }
}