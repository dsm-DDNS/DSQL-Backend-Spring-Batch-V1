package com.ddns.dsqlbatchserver.infra.sns.tistory.response

data class TistoryUploadResponse(
    val tistory: Tistory

)

data class Tistory (
    val status: String,
    val postId: String,
    val urL: String
)

