package com.example.timebarteryeah.models

class User (
    val name: String?,
    val email: String?,
    val password: String?,
    val photo: String?,
    val timeBalance: Long?,
    val timeSpent: Long?,
    val point: Long?,
    val level: Long?
) {

    data class Builder(
        private var name: String? = null,
        private var email: String? = null,
        private var password: String? = null,
        private var photo: String? = null,
        private var timeBalance: Long? = null,
        private var timeSpent: Long? = null,
        private var point: Long? = null,
        private var level: Long? = null
    ) {

        fun setName(name: String?) = apply { this.name = name }
        fun setEmail(email: String?) = apply { this.email = email }
        fun setPassword(password: String?) = apply { this.password = password }
        fun setPhoto(photo: String?) = apply { this.photo = photo }
        fun setTimeBalance(timeBalance: Long?) = apply { this.timeBalance = timeBalance }
        fun setTimeSpent(timeSpent: Long?) = apply { this.timeSpent = timeSpent }
        fun setPoint(point: Long?) = apply { this.point = point }
        fun setLevel(level: Long?) = apply { this.level = level }
        fun build() = User(name, email, password, photo, timeBalance, timeSpent, point, level)

    }

}