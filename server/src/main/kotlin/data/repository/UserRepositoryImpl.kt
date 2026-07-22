package com.reza.data.repository

import com.reza.data.table.UsersTable
import com.reza.domain.model.User
import com.reza.domain.repository.UserRepository
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserRepositoryImpl : UserRepository {

    override suspend fun createUser(email: String, passwordHash: String): User? = newSuspendedTransaction {
        val insertStatement = UsersTable.insert {
            it[UsersTable.email] = email
            it[UsersTable.passwordHash] = passwordHash
        }
        insertStatement.resultedValues?.singleOrNull()?.let { row ->
            User(
                id = row[UsersTable.id],
                email = row[UsersTable.email],
                passwordHash = row[UsersTable.passwordHash]
            )
        }
    }

    override suspend fun findByEmail(email: String): User? = newSuspendedTransaction {
        UsersTable.selectAll().where { UsersTable.email eq email }
            .map { row ->
                User(
                    id = row[UsersTable.id],
                    email = row[UsersTable.email],
                    passwordHash = row[UsersTable.passwordHash]
                )
            }
            .singleOrNull()
    }
}