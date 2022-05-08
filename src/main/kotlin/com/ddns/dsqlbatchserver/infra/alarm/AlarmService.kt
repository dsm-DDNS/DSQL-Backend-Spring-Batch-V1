package com.ddns.dsqlbatchserver.infra.alarm

interface AlarmService {

    fun sendAlarm(title: String): String
}