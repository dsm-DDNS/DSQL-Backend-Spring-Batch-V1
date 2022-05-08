package com.ddns.dsqlbatchserver.infra.sns.tistory.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("tistory")
@ConstructorBinding
data class TistoryProperty (
    val accessToken: String,
    val blogName: String
//    val appId: String,
//    val secretKey: String
)