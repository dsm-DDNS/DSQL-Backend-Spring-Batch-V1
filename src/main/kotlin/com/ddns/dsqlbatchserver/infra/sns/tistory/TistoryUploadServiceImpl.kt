package com.ddns.dsqlbatchserver.infra.sns.tistory


import com.ddns.dsqlbatchserver.infra.sns.SnsService
import com.ddns.dsqlbatchserver.infra.sns.tistory.env.TistoryProperty
import com.ddns.dsqlbatchserver.infra.sns.tistory.response.TistoryUploadResponse
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import org.springframework.web.client.postForEntity
import java.net.URI
import java.net.URL
import java.util.Base64
import java.util.Base64.Encoder

@Service("tistorySnsService")
class TistoryUploadServiceImpl(
    private val restTemplate: RestTemplate,
    private val tistoryProperty: TistoryProperty
): SnsService {

    companion object{
        const val TISTORY_URL = "https://www.tistory.com/apis/post/write"
    }
    private val encoder = Base64.getEncoder()

    override fun upload(title: String, content: String, tags: String): TistoryUploadResponse {
        val encTitle = encoder.encodeToString(title.encodeToByteArray())
        val encContent = encoder.encodeToString(content.encodeToByteArray())
        val encTags = encoder.encodeToString(tags.encodeToByteArray())
        var url: String = (TISTORY_URL + "?access_token=${tistoryProperty.accessToken}" +
                "&output=json&blogName=${tistoryProperty.blogName}" +
                "&title=$encTitle&content=$encContent&visibility=3&category=0" +
                "&slogan=&tag=$encTags")

        println(url)
        val entity = HttpEntity.EMPTY
        entity.headers.contentType = MediaType.APPLICATION_JSON

        return restTemplate.exchange(URI(url), HttpMethod.POST, entity, TistoryUploadResponse::class.java).body!!
    }
}