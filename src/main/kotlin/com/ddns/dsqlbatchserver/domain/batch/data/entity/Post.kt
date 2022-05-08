package com.ddns.dsqlbatchserver.domain.batch.data.entity

import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany


@Entity
class Post(
    title: String,
    @Column(name = "url") var url: String,
    @Column(name = "create_at") var create_at: String,
    @Column(name = "content") var content: String?
) {

    @Id
    @Column(name = "title")
    var title = title

}