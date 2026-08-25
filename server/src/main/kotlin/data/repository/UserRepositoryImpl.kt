package com.reza.data.repository

import com.reza.data.table.UsersTable
import com.reza.domain.repository.UserRepository
import model.UserProfileResponse
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserRepositoryImpl : UserRepository {

    override suspend fun createUser(email: String, passwordHash: String): UserProfileResponse? = newSuspendedTransaction {
        addLogger(StdOutSqlLogger)

        val insertStatement = UsersTable.insert {
            it[UsersTable.email] = email
            it[UsersTable.passwordHash] = passwordHash
        }
        insertStatement.resultedValues?.singleOrNull()?.let { row ->
            UserProfileResponse(
                userId = row[UsersTable.id],
                email = row[UsersTable.email],
                passwordHash = row[UsersTable.passwordHash]
            )
        }
    }

    override suspend fun findByEmail(email: String): UserProfileResponse? = newSuspendedTransaction {
        addLogger(StdOutSqlLogger)

        UsersTable.selectAll().where { UsersTable.email eq email }
            .map { row ->
                UserProfileResponse(
                    userId = row[UsersTable.id],
                    email = row[UsersTable.email],
                    passwordHash = row[UsersTable.passwordHash]
                )
            }
            .singleOrNull()
    }
}