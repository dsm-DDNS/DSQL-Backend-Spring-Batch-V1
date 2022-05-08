package com.ddns.dsqlbatchserver.global.env.config

import com.ddns.dsqlbatchserver.global.env.property.DataSourceListProperty
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource


@Configuration
class DatabaseConfiguration(
    private val prop: DataSourceListProperty
) {
    companion object {
        const val READ_DATASOURCE = "readDatasource"
        const val WRITE_DATASOURCE = "writeDatasource"
        const val WRITE_JDBC_TEMPLATE = "writeJdbcTemplate"
    }


    @Bean(name = [READ_DATASOURCE])
    @Primary
    fun getReadDatasource(): DataSource {
        return getDatasource(prop.read.driverClassName, prop.read.url, prop.read.userName, prop.read.password)
    }

    @Bean(name = [WRITE_DATASOURCE])
    fun getWriteDatasource(): DataSource {
        return getDatasource(prop.write.driverClassName, prop.write.url, prop.write.userName, prop.write.password)
    }


    private fun getDatasource(driverClassName: String, url: String, username: String, password: String): DataSource {
        return DataSourceBuilder.create()
            .driverClassName(driverClassName)
            .url(url)
            .username(username)
            .password(password)
            .build()
    }

    @Bean(name = arrayOf(WRITE_JDBC_TEMPLATE))
    fun jdbcTemplate(): JdbcTemplate {
        val template = JdbcTemplate(getReadDatasource())
        template.dataSource = getWriteDatasource()
        return template
    }



}