package com.example.pahadchalo.PahadChalo.model

import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "CarUser")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int = -1,
        @Size(min = 2, message = "First name should have at least 2 or more characters")
        val firstName: String? = null,
        val lastName: String? = null,
        @NotNull
        var mobileNo: String? = null,
        val address: String? = null,
        @Email
        val email: String? = null,
        @NotNull
        val carNo: String? = null,
        @Size(min = 12, max = 12, message = "Aadhaar no must have 12 digits")
        val aadhaarNo: String? = null,
        val seatAvailable: Int? = 0)
