package com.ddns.dsqlbatchserver.infra.sns.facebook

import com.ddns.dsqlbatchserver.infra.sns.SnsService
import com.ddns.dsqlbatchserver.infra.sns.tistory.response.TistoryUploadResponse
import org.springframework.stereotype.Service

@Service("facebookSnsService")
class FacebookUploadServiceImpl: SnsService {
    override fun upload(title: String, content: String, tags: String): TistoryUploadResponse {
        TODO("Not yet implemented")
    }
}