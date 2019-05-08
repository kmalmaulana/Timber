package com.example.timebarteryeah.models

class Post(
    val userId: String?,
    val title: String?,
    val desc: String?,
    val date: Long?,
    val category: HashMap<String, Any>?,
    val duration: Long?,
    val photo: String?,
    val auth: String?,
    val documentId: String?,
    val acceptedBy: String?,
    val status: String?,
    val timeDone: Long?
) {
    data class Builder(
        private var userId: String? = null,
        private var title: String? = null,
        private var desc: String? = null,
        private var date: Long? = null,
        private var category: HashMap<String, Any>? = null,
        private var duration: Long? = null,
        private var photo: String? = null,
        private var auth: String? = null,
        private var documentId: String? = null,
        private var acceptedBy: String? = null,
        private var status: String? = null,
        private var timeDone: Long? = null
    ) {
        fun setUserId(userId: String?) = apply { this.userId = userId }
        fun setTitle(title: String?) = apply { this.title = title }
        fun setDesc(desc: String?) = apply { this.desc = desc }
        fun setDate(date: Long?) = apply { this.date = date }
        fun setCategory(category: HashMap<String, Any>?) = apply { this.category = category }
        fun setDuration(duration: Long?) = apply { this.duration = duration }
        fun setPhoto(photo: String?) = apply { this.photo = photo }
        fun setAuth(auth: String?) = apply { this.auth = auth }
        fun setDocumentId(documentId: String?) = apply { this.documentId = documentId }
        fun setAcceptedBy(acceptedBy: String?) = apply { this.acceptedBy = acceptedBy }
        fun setStatus(status: String?) = apply { this.status = status }
        fun setTimeDone(timeDone: Long?) = apply { this.timeDone = timeDone }
        fun build() = Post(userId, title, desc, date, category, duration, photo, auth, documentId, acceptedBy, status, timeDone)
    }
}