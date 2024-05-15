package com.example.data.database

import com.example.common.DatabaseCollectionConfig
import com.example.data.database.Database.database
import com.example.data.database.entries.admin.Subscription
import com.example.data.database.entries.project.Project
import com.example.data.database.entries.shortenUrl.ShortenUrl
import com.example.data.database.entries.user.User
import com.mongodb.client.MongoCollection


object Entries {
    val dbUser: MongoCollection<User> = database.getCollection(DatabaseCollectionConfig.USER_COLLECTION).withDocumentClass(User::class.java)
    val dbProject: MongoCollection<Project> = database.getCollection(DatabaseCollectionConfig.PROJECT_COLLECTION).withDocumentClass(Project::class.java)
    val dbSubscription: MongoCollection<Subscription> = database.getCollection(DatabaseCollectionConfig.SUBSCRIPTION_COLLECTION).withDocumentClass(Subscription::class.java)
    val dbShortenUrl: MongoCollection<ShortenUrl> = database.getCollection(DatabaseCollectionConfig.SHORTEN_URL).withDocumentClass(ShortenUrl::class.java)
}
