package com.ddns.dsqlbatchserver.domain.komoran.service

interface KomoranService {

    fun extractContent(content: String): List<String>
}