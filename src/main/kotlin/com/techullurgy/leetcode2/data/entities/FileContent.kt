package com.techullurgy.leetcode2.data.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
data class FileContent(
    @Id
    @GeneratedValue
    val id: Int = 0,
    @Column(columnDefinition = "TEXT")
    val c: String,
    val creplaceStr: String,
    @Column(columnDefinition = "TEXT")
    val cpp: String,
    val cppReplaceStr: String,
    @Column(columnDefinition = "TEXT")
    val java: String,
    val javaReplaceStr: String,
    @Column(columnDefinition = "TEXT")
    val python: String,
    val pythonReplaceStr: String,
    @Column(columnDefinition = "TEXT")
    val javascript: String,
    val javascriptReplaceStr: String
)
