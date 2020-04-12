package com.example.pahadchalo.PahadChalo.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException(message: String?) : RuntimeException(message) {
    companion object {
        /**
         *
         */
        private const val serialVersionUID = 1L
    }
}