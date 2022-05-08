package com.ddns.dsqlbatchserver.global.batch.service

import com.ddns.dsqlbatchserver.global.batch.data.entity.Post
import com.ddns.dsqlbatchserver.global.batch.data.entity.WritePost

interface BatchProcessService {

    fun process(post: Post): WritePost

}