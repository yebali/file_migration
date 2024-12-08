package com.gmdsoft.filemigration.service

import com.gmdsoft.filemigration.service.command.CopyFile
import com.gmdsoft.filemigration.util.Logger
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files

@Service
class FileService {
    fun copy(command: CopyFile.Command): Result<Unit> {
        val sourceFile = File(command.source)
        val destinationFile = File(command.destination)

        if (!sourceFile.exists()) {
            return Result.failure(NoSuchFileException(sourceFile))
        }

        if (destinationFile.exists()) {
            return Result.failure(FileAlreadyExistsException(destinationFile))
        }

        return runCatching {
            logger.info { "Move ${sourceFile.parentFile} -> ${destinationFile.toPath()}" }

            if (!destinationFile.parentFile.exists()) {
                destinationFile.parentFile.mkdirs()
            }

            Files.copy(sourceFile.toPath(), destinationFile.toPath())
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { e -> Result.failure(e) },
        )
    }

    companion object : Logger()
}
