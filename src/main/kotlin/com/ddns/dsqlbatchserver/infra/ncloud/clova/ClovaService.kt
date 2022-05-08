package com.ddns.dsqlbatchserver.infra.ncloud.clova

interface ClovaService {

    fun extractContent(title: String, content: String): String
}