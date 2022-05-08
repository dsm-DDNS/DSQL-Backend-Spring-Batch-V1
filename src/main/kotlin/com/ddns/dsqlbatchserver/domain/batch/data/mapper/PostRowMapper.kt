package com.ddns.dsqlbatchserver.domain.batch.data.mapper

import com.ddns.dsqlbatchserver.domain.batch.data.entity.Post
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet


class PostRowMapper: RowMapper<Post> {

    companion object {
        const val TITLE_COLUMN = "title"
        const val URL_COLUMN = "url"
        const val CREATE_AT_COLUMN = "create_at"
        const val CONTENT_COLUMN = "content"
    }

    override fun mapRow(rs: ResultSet, rowNum: Int): Post {
        return Post(
            rs.getString(TITLE_COLUMN),
            rs.getString(URL_COLUMN),
            rs.getString(CREATE_AT_COLUMN),
            rs.getString(CONTENT_COLUMN)
        )
    }


}