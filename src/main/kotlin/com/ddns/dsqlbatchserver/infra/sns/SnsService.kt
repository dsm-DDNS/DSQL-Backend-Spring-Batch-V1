package com.ddns.dsqlbatchserver.infra.sns

import com.ddns.dsqlbatchserver.infra.sns.tistory.response.TistoryUploadResponse

interface SnsService {

    fun upload(title: String, content: String, tags: String): TistoryUploadResponse
}