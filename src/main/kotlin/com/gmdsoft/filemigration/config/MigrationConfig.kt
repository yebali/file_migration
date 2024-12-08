package com.gmdsoft.filemigration.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("migration")
data class MigrationConfig(
    val sourceRootPath: String,
    val destinationRootPath: String,
)
