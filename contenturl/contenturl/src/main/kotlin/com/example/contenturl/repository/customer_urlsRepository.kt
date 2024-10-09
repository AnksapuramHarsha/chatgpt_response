package com.example.contenturl.repository

import com.example.contenturl.model.customer_urls
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface customer_urlsRepository:JpaRepository<customer_urls,Long> {

}