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
        val targetFile = File(command.target)

        if (!sourceFile.exists()) {
            return Result.failure(NoSuchFileException(sourceFile))
        }

        if (targetFile.exists()) {
            return Result.failure(FileAlreadyExistsException(targetFile))
        }

        return runCatching {
            Files.copy(sourceFile.toPath(), targetFile.toPath())
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { e -> Result.failure(e) },
        )
    }

    companion object : Logger()
}
