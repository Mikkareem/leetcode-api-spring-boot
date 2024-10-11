package com.techullurgy.leetcode2.data.entities

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
data class Testcase(
    @Id
    @GeneratedValue
    val testcaseId: Long = 0,

    val testcaseNo: Int,
    val isHidden: Boolean = true,

    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    val problem: Problem? = null,

    @OneToMany(mappedBy = "testcase", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val details: MutableList<TestcaseInput> = mutableListOf()
)

@Entity
data class TestcaseInput(
    @Id
    @GeneratedValue
    val id: Long = 0,

    val inputName: String,
    val inputValue: String,
    val typeMask: Long,

    @ManyToOne
    @JoinColumn(name = "testcase_id", nullable = false)
    val testcase: Testcase? = null
)