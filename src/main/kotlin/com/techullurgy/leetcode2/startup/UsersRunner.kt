package com.techullurgy.leetcode2.startup

import com.techullurgy.leetcode2.data.entities.LeetcodeUser
import com.techullurgy.leetcode2.data.repositories.UsersRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class UsersRunner(
    private val usersRepository: UsersRepository
): CommandLineRunner {
    override fun run(vararg args: String?) {
        usersRepository.saveAll(users)
    }
}

private val users = listOf(
    LeetcodeUser(name = "Irsath"),
    LeetcodeUser(name = "Riyas"),
    LeetcodeUser(name = "Faisal"),
    LeetcodeUser(name = "Guru"),
    LeetcodeUser(name = "Ibrahim"),
    LeetcodeUser(name = "Fasith"),
    LeetcodeUser(name = "Abinaya"),
)