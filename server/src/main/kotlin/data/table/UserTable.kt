package com.reza.data.table

import org.jetbrains.exposed.sql.Table

object UsersTable : Table("users") {
    val id = integer("id").autoIncrement()
    val email = varchar("email", 255).uniqueIndex()
    val passwordHash = varchar("password_hash", 255)

    override val primaryKey = PrimaryKey(id)
}