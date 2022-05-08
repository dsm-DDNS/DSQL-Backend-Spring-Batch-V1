package com.ddns.dsqlbatchserver.domain.batch.service

import com.ddns.dsqlbatchserver.domain.batch.data.entity.Post
import com.ddns.dsqlbatchserver.domain.batch.data.entity.WritePost

interface BatchProcessService {

    fun process(post: Post): WritePost

}