package com.example.contenturl.model

import jakarta.persistence.*

@Entity
@Table(name = "videos")
data class Video(
    @Id
    @Column(name = "platform_content_id")
    var platformContentId: String,

    @Column(name = "title")
    var title: String,

    @Column(name = "format")
    var format: String,

    @Column(name = "type")
    var type: String,

    @Column(name = "url")
    var url: String,

    @Column(name = "media_url")
    var mediaUrl: String,

    @Column(name = "duration")
    var duration: Int,

    @Column(name = "description")
    var description: String,

    @Column(name = "thumbnail_url")
    var thumbnailUrl: String,

    @Column(name = "published_at")
    var publishedAt: java.time.LocalDateTime
)
