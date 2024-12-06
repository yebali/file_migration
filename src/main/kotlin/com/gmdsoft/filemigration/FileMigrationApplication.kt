package com.gmdsoft.filemigration

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
@ConfigurationPropertiesScan("com.gmdsoft.filemigration")
class FileMigrationApplication

fun main(args: Array<String>) {
    runApplication<FileMigrationApplication>(*args)
}
