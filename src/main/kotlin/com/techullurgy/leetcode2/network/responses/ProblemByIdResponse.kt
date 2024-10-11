package com.techullurgy.leetcode2.network.responses

import com.techullurgy.leetcode2.data.entities.LeetcodeUser
import com.techullurgy.leetcode2.network.models.ProblemDTO
import com.techullurgy.leetcode2.network.models.SubmissionDTO

data class ProblemByIdResponse(
    val problem: ProblemDTO,
    val submissions: List<SubmissionDTO>,
    val user: LeetcodeUser
)