package com.reza.plugins

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabase() {
    val jdbcUrl = "jdbc:postgresql://localhost:5432/mydb"
    val dbUser = "postgres"
    val dbPassword = "password"

    // Running Flyway Migrations
    val flyway = Flyway.configure()
        .dataSource(jdbcUrl, dbUser, dbPassword)
        .load()

    flyway.migrate()

    // Configuring HikariCP Connection Pool
    val config = HikariConfig().apply {
        driverClassName = "org.postgresql.Driver"
        this.jdbcUrl = jdbcUrl
        username = dbUser
        password = dbPassword
        maximumPoolSize = 10
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    }

    // Connecting Exposed to the DataSource
    val dataSource = HikariDataSource(config)
    Database.connect(dataSource)
}