package com.gmdsoft.filemigration.controller.rest

import com.fasterxml.jackson.annotation.JsonUnwrapped

interface MigrateFileRest {
    data class Request(
        val tokens: List<String> = emptyList(),
    )

    data class Response(
        @JsonUnwrapped
        val migrations: List<Migration>,
    ) {
        data class Migration(
            val token: String,
            val status: Status,
            val message: String,
        )

        enum class Status(value: Boolean?) {
            SUCCESS(true),
            FAILURE(false),
            IN_PROGRESS(null),
            ;

            companion object {
                fun from(value: Boolean): Status {
                    return when (value) {
                        true -> SUCCESS
                        false -> FAILURE
                    }
                }
            }
        }

        companion object {
            fun from(results: List<Pair<String, Result<Unit>>>): Response =
                Response(
                    migrations = if (results.isEmpty()) {
                        listOf(
                            Migration(
                                token = "",
                                status = Status.IN_PROGRESS,
                                message = "Migration progressing in back ground",
                            ),
                        )
                    } else {
                        results.map { (token, result) ->
                            result.fold(
                                onSuccess = {
                                    Migration(
                                        token = token,
                                        status = Status.SUCCESS,
                                        message = "-",
                                    )
                                },
                                onFailure = { e ->
                                    Migration(
                                        token = token,
                                        status = Status.FAILURE,
                                        message = e.message ?: "-",
                                    )
                                },
                            )
                        }
                    },
                )
        }
    }
}