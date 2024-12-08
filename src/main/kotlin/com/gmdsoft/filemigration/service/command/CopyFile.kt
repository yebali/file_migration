package com.gmdsoft.filemigration.service.command

interface CopyFile {
    data class Command(
        val source: String,
        val destination: String,
    )
}
