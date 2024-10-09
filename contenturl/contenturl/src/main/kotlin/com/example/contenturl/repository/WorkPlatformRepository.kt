package com.example.contenturl.repository

import com.example.contenturl.model.Engagement
import com.example.contenturl.model.PlatformContent
import com.example.contenturl.model.WorkPlatform
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WorkPlatformRepository : JpaRepository<WorkPlatform, Long>
@Repository
interface EngagementRepository : JpaRepository<Engagement, Long>
@Repository
interface PlatformContentRepository : JpaRepository<PlatformContent, Long>
