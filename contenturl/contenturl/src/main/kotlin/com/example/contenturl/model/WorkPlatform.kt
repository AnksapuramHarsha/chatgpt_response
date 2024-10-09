package com.example.contenturl.model

import jakarta.persistence.*


@Entity
data class WorkPlatform(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    val platformId: String,
    val name: String,
    val logoUrl: String
)

@Entity
data class Engagement(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    val likeCount: Int,
    val commentCount: Int,
    val viewCount: Int,
    val shareCount: Int
)

@Entity
data class PlatformContent(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @ManyToOne(cascade = [CascadeType.ALL])
    val workPlatform: WorkPlatform,
    @ManyToOne(cascade = [CascadeType.ALL])
    val engagement: Engagement,
    val platformContentId: String,
    val title: String,
    val format: String,
    val type: String,
    val url: String,
    val mediaUrl: String,
    val duration: Int,
    val description: String,
    val thumbnailUrl: String,
    val publishedAt: String
)
