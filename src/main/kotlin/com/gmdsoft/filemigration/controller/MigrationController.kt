package com.gmdsoft.filemigration.controller

import com.gmdsoft.filemigration.controller.rest.MigrateFileRest
import com.gmdsoft.filemigration.service.MigrationService
import com.gmdsoft.filemigration.service.command.MigrateFile
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/migration")
class MigrationController(
    private val migrationService: MigrationService,
) {
    @PostMapping
    fun migrate(
        @RequestBody request: MigrateFileRest.Request,
    ): MigrateFileRest.Response {
        return MigrateFileRest.Response.from(
            results = migrationService.migrateFiles(
                command = MigrateFile.Command(tokens = request.tokens),
            ),
        )
    }
}
