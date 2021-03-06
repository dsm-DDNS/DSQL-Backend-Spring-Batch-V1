package com.ddns.dsqlbatchserver.domain.komoran.service

import kr.co.shineware.nlp.komoran.core.Komoran
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class KomoranSeviceImpl(
    private val komoran: Komoran
): KomoranService {
    private val log = LoggerFactory.getLogger(this.javaClass)

    companion object{
        const val DAEDOEK = "대덕"
        const val SW = "소프트웨어"
        const val MEISTER = "마이스터"
    }


    override fun extractContent(sentence: String): List<String> {
        var s = sentence
        if (sentence.contains(SW)||sentence.contains(MEISTER)) {
            s = sentence.replace(SW, "").replace(MEISTER, "").replace(DAEDOEK, "")
        }
        val result = komoran.analyze(s)

        val tags = result.nouns.stream().filter{ it.length >= 2 }.toList()
        return tags

    }
}
