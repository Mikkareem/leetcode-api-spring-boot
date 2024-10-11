package com.techullurgy.leetcode2.data.repositories

import com.techullurgy.leetcode2.data.entities.LeetcodeUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository: JpaRepository<LeetcodeUser, Int>