package com.ddns.dsqlbatchserver

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableScheduling
@EnableBatchProcessing
class DsqlBatchServerApplication

fun main(args: Array<String>) {
    runApplication<DsqlBatchServerApplication>(*args)
}
