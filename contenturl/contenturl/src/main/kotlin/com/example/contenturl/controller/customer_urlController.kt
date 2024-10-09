package com.example.contenturl.controller


import com.example.contenturl.service.customer_urlService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

//import com.example.contenturl.model.customer_urls
//import com.example.contenturl.service.customer_urlService
//import org.springframework.http.HttpStatus
//import org.springframework.http.ResponseEntity
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.PathVariable
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RestController
//import java.util.*
//
//@RequestMapping("profile")
//@RestController
//class customer_urlController(private val customerUrlservice: customer_urlService) {
//
//    @GetMapping("/urls")
//    fun getAllCustomersUrls(): ResponseEntity<List<customer_urls>> {
//        val customers = customerUrlservice.getAllCustomersUrls()
//        return ResponseEntity(customers, HttpStatus.OK)
//    }
//
//    @GetMapping("/{id}")
//    fun findById(@PathVariable id: Long): String {
//        return customerUrlservice.findById(id);
//    }
//}
@RequestMapping("profile")
@RestController
class customer_urlController(private val customerUrlservice: customer_urlService) {

    @GetMapping("{id}")
    suspend fun fetchPostsForUser(@PathVariable id: Long): ResponseEntity<String> {
        return try {
            customerUrlservice.setUserId(id)
            customerUrlservice.fetchPostsForUser(id)
            ResponseEntity.ok("Fetching posts for user ID: $id")
        } catch (e: Exception) {
            ResponseEntity.status(500).body("Error fetching posts for user ID: $id")
        }
    }
}
