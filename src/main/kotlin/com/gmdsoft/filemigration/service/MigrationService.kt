package com.gmdsoft.filemigration.service

import com.gmdsoft.filemigration.config.MigrationConfig
import com.gmdsoft.filemigration.repository.ShortUrlRepository
import com.gmdsoft.filemigration.service.command.CopyFile
import com.gmdsoft.filemigration.service.command.MigrateFile
import com.gmdsoft.filemigration.util.Logger
import org.springframework.stereotype.Service

@Service
class MigrationService(
    private val shortUrlRepository: ShortUrlRepository,
    private val migrationBackgroundService: MigrationBackgroundService,
    private val fileService: FileService,
    private val migrationConfig: MigrationConfig,
) {
    fun migrateFiles(command: MigrateFile.Command): List<Pair<String, Result<Unit>>> {
        if (command.tokens.isEmpty()) {
            migrationBackgroundService.asyncMigrateFiles()

            return emptyList()
        }

        val shortUrls = shortUrlRepository.findAllById(command.tokens)

        logger.info { "[${MigrationService::class.simpleName}] Start migration. " }
        logger.info { "Tokens: ${shortUrls.joinToString("\n") { it.id }}" }

        return shortUrls
            .map { shortUrl ->
                shortUrl.id to fileService.copy(
                    command = CopyFile.Command(
                        source = migrationConfig.sourceRootPath + shortUrl.relativePath,
                        target = migrationConfig.targetRootPath + "${shortUrl.id.take(3)}/${shortUrl.id}",
                    ),
                )
            }
    }

    companion object : Logger()
}
