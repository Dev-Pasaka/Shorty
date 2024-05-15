package com.example.data.database

import com.example.common.utils.DatabaseConfig
import com.example.data.database.Database.database
import com.example.data.database.entries.admin.Subscription
import com.example.data.database.entries.emails.Email
import com.example.data.database.entries.project.Project
import com.example.data.database.entries.user.User
import com.mongodb.client.MongoCollection


object Entries {
    val dbUser: MongoCollection<User> = database.getCollection(DatabaseConfig.USER_COLLECTION).withDocumentClass(User::class.java)
    val dbProject: MongoCollection<Project> = database.getCollection(DatabaseConfig.PROJECT_COLLECTION).withDocumentClass(Project::class.java)
    val dbSubscription: MongoCollection<Subscription> = database.getCollection(DatabaseConfig.SUBSCRIPTION_COLLECTION).withDocumentClass(Subscription::class.java)
    val dbEmails:MongoCollection<Email> = database.getCollection(DatabaseConfig.EMAILS_COLLECTION).withDocumentClass(Email::class.java)
}
