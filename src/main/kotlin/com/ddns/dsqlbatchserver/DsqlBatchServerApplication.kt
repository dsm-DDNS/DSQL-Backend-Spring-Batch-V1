package com.ddns.dsqlbatchserver

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableBatchProcessing
@ConfigurationPropertiesScan
class DsqlBatchServerApplication

fun main(args: Array<String>) {
    runApplication<DsqlBatchServerApplication>(*args)
}
