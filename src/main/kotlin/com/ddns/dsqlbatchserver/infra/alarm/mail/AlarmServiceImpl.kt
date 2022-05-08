package com.ddns.dsqlbatchserver.infra.alarm.mail

import com.ddns.dsqlbatchserver.infra.alarm.AlarmService
import org.springframework.stereotype.Service


@Service
class AlarmServiceImpl: AlarmService {

    override fun sendAlarm(title: String): String {
        TODO("Not yet implemented")
    }
}