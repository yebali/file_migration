package com.gmdsoft.filemigration.service.command

interface MigrateFile {
    data class Command(
        val tokens: List<String>,
    )
}
