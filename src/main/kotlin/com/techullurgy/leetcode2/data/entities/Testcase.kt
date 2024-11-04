package com.techullurgy.leetcode2.data.entities

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne

@Entity
data class Testcase(
    @Id
    @GeneratedValue
    val testcaseId: Long = 0,

    val isHidden: Boolean = true,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "testcase_id")
    val inputs: MutableList<TestcaseInput> = mutableListOf()
)

@Entity
data class TestcaseInput(
    @Id
    @GeneratedValue
    val id: Long = 0,

    @Column(name = "input_value")
    val value: String,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "format_id")
    val format: TestcaseFormat
)

@Entity
data class TestcaseFormat(
    @Id
    @GeneratedValue
    val id: Long = 0,

    val name: String,
    val typeMask: Long,
    val displayOrder: Int
)