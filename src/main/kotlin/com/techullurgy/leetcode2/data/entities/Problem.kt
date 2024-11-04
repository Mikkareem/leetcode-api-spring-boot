package com.techullurgy.leetcode2.data.entities

import com.techullurgy.leetcode2.domain.model.Difficulty
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne

@Entity
data class Problem(
    @Id
    @GeneratedValue
    val problemNo: Int = 0,
    val title: String,
    val description: String,
    val difficulty: Difficulty,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "snippet_id")
    val snippet: Snippet,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "file_content_id")
    val fileContent: FileContent,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "problem_id")
    val testcases: MutableList<Testcase> = mutableListOf(),

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "problem_id")
    val testcaseFormats: MutableList<TestcaseFormat> = mutableListOf()
)