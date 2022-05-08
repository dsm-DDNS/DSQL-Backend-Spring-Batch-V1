package com.ddns.dsqlbatchserver.domain.batch.data.entity



class WritePost(
    post: Post
) {

    var title = post.title

    var url: String = post.url
    var create_at: String = post.create_at
    var content: String? = post.content
    var tags: String? = null

    var short_content: String? = null

    fun addTagList(tags: List<String>) {
        var str = ""
        tags.forEach {
            str += ("$it ")
        }
        this.tags = str
    }

    fun insertShortContent(short: String) {
        this.short_content = short
    }
}