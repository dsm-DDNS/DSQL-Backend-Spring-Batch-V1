package com.ddns.dsqlbatchserver.global.batch.service

import com.ddns.dsqlbatchserver.global.batch.data.entity.Post
import com.ddns.dsqlbatchserver.global.batch.data.entity.WritePost
import com.ddns.dsqlbatchserver.domain.komoran.service.KomoranService
import com.ddns.dsqlbatchserver.global.util.ContentTextParsingUtil
import com.ddns.dsqlbatchserver.infra.alarm.AlarmService
import com.ddns.dsqlbatchserver.infra.ncloud.clova.ClovaService
import com.ddns.dsqlbatchserver.infra.sns.SnsService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class BatchProcessServiceImpl(
    @Qualifier("facebookSnsService") private val facebookSnsService: SnsService,
    @Qualifier("tistorySnsService") private val tistorySnsService: SnsService,
    private val komoranService: KomoranService,
    private val clovaService: ClovaService,
    private val alarmService: AlarmService
): BatchProcessService {


    @Async
    override fun process(post: Post): WritePost {
        var writePost = WritePost(
            post
        )

        //Komoran 통해 주요 단어 추출
        val tags = komoranService.extractContent(post.title)
        writePost.addTagList(tags)
        post.content?.let {
            //Clova를 통해 Content 요약
            val short = clovaService.extractContent(post.title, post.content!!)
            writePost.insertShortContent(short)
        }
//        //SNS Upload
//        //tistory
//        val out = tistorySnsService.upload(writePost.title, writePost.shrtCnt!!, writePost.tags!!)
//        println(out)
//        //Alarm
//        alarmService.sendAlarm(writePost.title)

        return writePost
    }

}