package com.ddns.dsqlbatchserver.global.util.textParsing

import com.ddns.dsqlbatchserver.global.util.ContentTextParsingUtil
import org.springframework.stereotype.Component
import java.util.regex.Matcher
import java.util.regex.Pattern

@Component
class ContentTextParsingUtilImpl: ContentTextParsingUtil {

    override fun parse(content: String): String {
        var removed = content
        var start = 0
        var end = 0

        while (removed.contains("https://")) {
            start = removed.indexOf("https://", start)
            end = removed.indexOf("\n", start)
            removed = removed.removeRange(start, end)
        }
        return removed
    }

}