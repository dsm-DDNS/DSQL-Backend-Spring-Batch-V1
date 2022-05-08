package com.ddns.dsqlbatchserver.global.env.config

import com.ddns.dsqlbatchserver.global.batch.data.entity.Post
import com.ddns.dsqlbatchserver.global.batch.data.entity.WritePost
import com.ddns.dsqlbatchserver.global.batch.data.mapper.IsTrueRowMapper
import com.ddns.dsqlbatchserver.global.batch.data.mapper.PostRowMapper
import com.ddns.dsqlbatchserver.global.batch.service.BatchProcessService
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.database.JdbcBatchItemWriter
import org.springframework.batch.item.database.JdbcCursorItemReader
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate


@Configuration
class JobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val dbSource: DatabaseConfiguration,
    @Qualifier("writeJdbcTemplate")
    private val writeJdbcTemplate: JdbcTemplate,
    private val batchProcessService: BatchProcessService
) {
    private val log = LoggerFactory.getLogger(javaClass)

    companion object {
        const val CHUNK_SIZE: Int = 10
        const val TITLE_COLUMN = "title"
        const val URL_COLUMN = "url"
        const val CREATE_AT_COLUMN = "create_at"
        const val CONTENT_COLUMN = "content"
        const val SHORT_CNT_COLUMN = "short_content"
        const val TAGS_COLUMN = "tags"
        const val TABLE_NAME = "post"
        const val JOB_NAME = "Test Job"
        const val LIMIT_SIZE = 30
    }

    @Bean
    fun dsqlDataProcessingJob(): Job {
        return jobBuilderFactory.get(JOB_NAME)
            .start(dsqlPostProcessingStep())
            .build()
    }


    @Bean
    fun dsqlPostProcessingStep(): Step{
        return stepBuilderFactory.get("dsqlPostProcessingStep")
            .chunk<Post, WritePost>(CHUNK_SIZE)
            .reader(dsqlReader())
            .processor(dsqlProcessor())
            .writer(dsqlItemWriter())
            .build()
    }

    @Bean
    fun dsqlReader(): JdbcCursorItemReader<Post> {
        return JdbcCursorItemReaderBuilder<Post>()
            .fetchSize(CHUNK_SIZE)
            .dataSource(dbSource.getReadDatasource())
            .rowMapper(PostRowMapper())
            .sql("SELECT $TITLE_COLUMN, $URL_COLUMN, $CREATE_AT_COLUMN, $CONTENT_COLUMN FROM $TABLE_NAME ORDER BY $CREATE_AT_COLUMN Asc")
            .fetchSize(LIMIT_SIZE)
            .name("jdbcCursorItemReader")
            .build()
    }


    @Bean
    fun dsqlProcessor(): ItemProcessor<Post, WritePost> {
        return ItemProcessor {
            val isTrue = writeJdbcTemplate.query("SELECT EXISTS(" +
                    "    SELECT * FROM POST WHERE title = " + '"' +it.title + '"' + " LIMIT 1) as isTrue;", IsTrueRowMapper()
            )[0]

            if (isTrue.isTrue) {
                return@ItemProcessor null
            }

            val writePost = batchProcessService.process(it)

            log.info("NEW ITEM: {}", writePost)
            return@ItemProcessor writePost
        }
    }

    @Bean
    fun dsqlItemWriter(): JdbcBatchItemWriter<WritePost> {
        return JdbcBatchItemWriterBuilder<WritePost>()
            .dataSource(dbSource.getWriteDatasource())
            .sql("insert into post($TITLE_COLUMN, $URL_COLUMN, $CREATE_AT_COLUMN, $CONTENT_COLUMN, $SHORT_CNT_COLUMN, $TAGS_COLUMN) " +
                    "values (:$TITLE_COLUMN, :$URL_COLUMN, :$CREATE_AT_COLUMN, :$CONTENT_COLUMN, :$SHORT_CNT_COLUMN, :$TAGS_COLUMN)")
            .beanMapped()
            .build()
    }


}
