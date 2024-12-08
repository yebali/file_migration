package com.gmdsoft.filemigration.service

import com.gmdsoft.filemigration.config.MigrationConfig
import com.gmdsoft.filemigration.repository.ShortUrlRepository
import com.gmdsoft.filemigration.service.command.CopyFile
import com.gmdsoft.filemigration.util.Combination
import com.gmdsoft.filemigration.util.Logger
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Service
class MigrationBackgroundService(
    private val shortUrlRepository: ShortUrlRepository,
    private val fileService: FileService,
    private val migrationConfig: MigrationConfig,
) {
    @Async
    fun asyncMigrateFiles() {
        val dirCombination = Combination.getCombinationWithRepetition(('0'..'9') + ('a'..'z'), 3)
        val threadPool = Executors.newFixedThreadPool(8)

        logger.info("[${MigrationBackgroundService::class.simpleName}] Start migration.")

        try {
            dirCombination.forEach { dir ->
                threadPool.submit {
                    try {
                        moveFile(dir)
                    } catch (e: Exception) {
                        logger.error("Error processing element '$dir': ${e.message}", e)
                    }
                }
            }
        } finally {
            threadPool.shutdown()
            try {
                if (!threadPool.awaitTermination(1, TimeUnit.HOURS)) {
                    threadPool.shutdownNow()
                }
            } catch (e: InterruptedException) {
                threadPool.shutdownNow()
            }
        }

        logger.info("[${MigrationBackgroundService::class.simpleName}] Finished migration.")
    }

    private fun moveFile(dir: String) {
        val shortUrls = shortUrlRepository.findAllByIdLike("$dir%")
        if (shortUrls.isEmpty()) return

        logger.info("Tokens:\n${shortUrls.joinToString("\n") { it.id }}")

        shortUrls.forEach { shortUrl ->
            val copyFileResult = fileService.copy(
                command = CopyFile.Command(
                    source = migrationConfig.sourceRootPath + shortUrl.relativePath,
                    destination = migrationConfig.destinationRootPath + "/${shortUrl.id.take(3)}/${shortUrl.id}",
                ),
            )

            copyFileResult.fold(
                onSuccess = { logger.info("Token '${shortUrl.id}' succeeded.") },
                onFailure = { logger.error("Token '${shortUrl.id}' failed. e-> $it") },
            )
        }
    }

    companion object : Logger()
}
