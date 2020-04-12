package com.example.pahadchalo.PahadChalo.repository

import com.example.pahadchalo.PahadChalo.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Integer> {
}