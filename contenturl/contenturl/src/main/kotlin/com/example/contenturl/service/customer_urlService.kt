package com.example.contenturl.service
import com.example.contenturl.model.Engagement
import com.example.contenturl.model.PlatformContent
import com.example.contenturl.model.WorkPlatform
import com.example.contenturl.repository.EngagementRepository
import com.example.contenturl.repository.PlatformContentRepository
import com.example.contenturl.repository.WorkPlatformRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.contenturl.repository.customer_urlsRepository
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.stereotype.Service

@Service
@EnableScheduling
class customer_urlService @Autowired constructor(
    private val customerUrlsrepository: customer_urlsRepository,
    private val workPlatformRepository: WorkPlatformRepository,
    private val engagementRepository: EngagementRepository,
    private val platformContentRepository: PlatformContentRepository
) {
    private var userId: Long = 1
    @PostConstruct
    fun init() {
        GlobalScope.launch {
            while (true) {
                fetchPostsForUser(userId)
                delay(5 * 60 * 1000)
            }
        }
    }

    suspend fun fetchPostsForUser(userId: Long) {
        val user = customerUrlsrepository.findById(userId)
        user?.let {
            val client = OkHttpClient()

            val mediaType = "application/json".toMediaType()
            val body = """
        {
            "profile_url": "${it.get().customer_url}",
            "work_platform_id": "9bb8913b-ddd9-430b-a66a-d74d846e6c66",
            "content_type": "REELS",
            "offset": 0,
            "limit": 1
        }
    """.trimIndent().toRequestBody(mediaType)
//            println("Fetching posts for the ${it.get().customer_url}")
//            delay(1000)

            val request = Request.Builder()
                .url("https://api.staging.insightiq.ai/v1/social/creators/contents/fetch")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Basic MDBlNThmNDUtN2RjNi00NzA3LWJmZjktZmRiYmVjMzIzNGVmOjE0YTg0NzM1LWQwYTItNGIyZC1hZDMwLTk2MjEyMDVlYzhhMQ==")
                .build()
            try {
                val response: Response = client.newCall(request).execute()
                if (response.isSuccessful) {
//                    println("Response: ${response.body?.string()}")
                    val responseData=response.body?.string()
                    saveContentFromResponse(responseData);

                } else {
                    println("Request failed: ${response.code} ${response.message}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setUserId(newUserId: Long) {
        userId = newUserId
    }


    fun saveContentFromResponse(response: String?) {
        val mapper = ObjectMapper()
        val jsonNode = mapper.readTree(response)

        // Get the "data" array
        val dataNode = jsonNode.get("data")

        // Check if dataNode is an array and has at least one element
        if (dataNode.isArray && dataNode.size() > 0) {
            // Access the first element of the array
            val contentNode = dataNode[0]

            // Extract WorkPlatform
            val workPlatformNode = contentNode.get("work_platform")
            val workPlatform = WorkPlatform(
                platformId = workPlatformNode.get("id").asText(),
                name = workPlatformNode.get("name").asText(),
                logoUrl = workPlatformNode.get("logo_url").asText()
            )

            // Extract Engagement
            val engagementNode = contentNode.get("engagement")
            val engagement = Engagement(
                likeCount = engagementNode.get("like_count").asInt(),
                commentCount = engagementNode.get("comment_count").asInt(),
                viewCount = engagementNode.get("view_count").asInt(),
                shareCount = engagementNode.get("share_count").asInt()
            )

            // Create PlatformContent
            val platformContent = PlatformContent(
                workPlatform = workPlatformRepository.save(workPlatform),  // Save WorkPlatform first
                engagement = engagementRepository.save(engagement),      // Save Engagement first
                platformContentId = contentNode.get("platform_content_id").asText(),
                title = contentNode.get("title").asText(),
                format = contentNode.get("format").asText(),
                type = contentNode.get("type").asText(),
                url = contentNode.get("url").asText(),
                mediaUrl = contentNode.get("media_url").asText(),
                duration = contentNode.get("duration").asInt(),
                description = contentNode.get("description").asText(),
                thumbnailUrl = contentNode.get("thumbnail_url").asText(),
                publishedAt = contentNode.get("published_at").asText()
            )

            // Save PlatformContent
            platformContentRepository.save(platformContent)
        } else {
            println("No data available in response.")
        }
    }
}



