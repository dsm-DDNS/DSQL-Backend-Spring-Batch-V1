package com.ddns.dsqlbatchserver.domain.batch.data.mapper

import com.ddns.dsqlbatchserver.domain.batch.data.dto.IsTrue
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class IsTrueRowMapper: RowMapper<IsTrue> {

    override fun mapRow(rs: ResultSet, rowNum: Int): IsTrue {
        return IsTrue(
            rs.getBoolean("isTrue")
        )
    }

}