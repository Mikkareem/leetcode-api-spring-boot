package com.techullurgy.leetcode2.data.repositories

import com.techullurgy.leetcode2.data.entities.Submission
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface SubmissionsRepository: JpaRepository<Submission, Int> {
    @Query("SELECT s FROM Submission s WHERE s.problem.problemNo = :problemId AND s.user.id = :userId ORDER BY time DESC")
    fun getAllSubmissionsForProblem(@Param("problemId") problemId: Int, @Param("userId") userId: Int): List<Submission>
}