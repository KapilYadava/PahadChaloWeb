package com.example.pahadchalo.PahadChalo.controller

import com.example.pahadchalo.PahadChalo.Constants
import com.example.pahadchalo.PahadChalo.exception.UserNotFoundException
import com.example.pahadchalo.PahadChalo.model.User
import com.example.pahadchalo.PahadChalo.model.VerificationModel
import com.example.pahadchalo.PahadChalo.repository.UserRepository
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
class UserRegistrationController {

    private lateinit var phoneNo: String

    @Autowired
    private lateinit var userRepository: UserRepository

    @PostMapping("/users")
    fun createUser(@RequestBody user: @Valid User?): ResponseEntity<Any?>? {
        val savedUser = user?.let { userRepository.save(it) }
        val location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser!!.id)
                .toUri()
        return ResponseEntity.created(location).build()
    }

    @GetMapping("/users")
    fun fetchAllUsers(): List<User> {
        return userRepository.findAll()
    }

    @GetMapping("/users/{id}")
    fun findUser(@PathVariable id: Integer): User {
        return userRepository.findById(id).orElseThrow { UserNotFoundException("id:$id") }
    }

    @GetMapping("/{phoneNo}")
    fun phoneNoVerification(@PathVariable phoneNo: String): String? {
        val url = Constants.BASE_URL + "/SMS/+91" + phoneNo + "/AUTOGEN"
        val restTemplate = RestTemplate()
        this.phoneNo = phoneNo
        return restTemplate.getForObject<String>(url, String::class.java)
    }

    @GetMapping("/{sessionId}/{otp}")
    fun otpVerification(@PathVariable otp: String, @PathVariable sessionId: String): String? {
        val url = Constants.BASE_URL + "/SMS/VERIFY/" + sessionId + "/" + otp
        val restTemplate = RestTemplate()
        val result = restTemplate.getForObject<String>(url, String::class.java);
        val resultObject: VerificationModel = Gson().fromJson(result, VerificationModel::class.java)
        if (resultObject.Status.equals("Success")) {
            var user = User()
            user.mobileNo = phoneNo
            createUser(user)
        }
        return result
    }
}