package com.gmdsoft.filemigration.service

import com.gmdsoft.filemigration.repository.ShortUrlRepository
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class MigrationBackgroundService(
    private val shortUrlRepository: ShortUrlRepository,
) {
    @Async
    fun asyncMigrateFiles() {
    }
}
