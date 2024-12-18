package com.techullurgy.leetcode2.domain.mappers

import com.techullurgy.leetcode2.data.entities.Submission
import com.techullurgy.leetcode2.network.models.SubmissionDTO

fun Submission.toSubmissionDTO() = SubmissionDTO(
    id = id,
    language = language,
    code = code,
    result = verdict,
    time = time,
    problem = problem.id,
    user = user
)