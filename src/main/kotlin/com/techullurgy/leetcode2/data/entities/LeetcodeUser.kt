package com.techullurgy.leetcode2.data.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
data class LeetcodeUser(
    @Id @GeneratedValue
    val id: Int = 0,
    val name: String
)