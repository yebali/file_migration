package com.gmdsoft.filemigration.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import javax.naming.InvalidNameException

@Entity
@Table(name = "tbl_shorturl")
class ShortUrl(
    @Id
    val id: String,
    val fileType: String,
    val pgmCode: String,
    val revision: Int,
    val filePath: String,
    val expiredDate: LocalDateTime,
    val expiredCount: Int,
) {
    val relativePath: String
        get() {
            return when (this.fileType) {
                "full" -> "/${this.pgmCode}/full/${this.filePath}"
                "partial" -> "/${this.pgmCode}/partial/${this.filePath}"
                "svn" -> "${this.pgmCode}/${this.revision}/${this.filePath}"
                else -> throw InvalidNameException("File type is not in 'full', 'partial', 'svn'.")
            }
        }
}
