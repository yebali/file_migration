package com.gmdsoft.filemigration.repository

import com.gmdsoft.filemigration.entity.ShortUrl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ShortUrlRepository : JpaRepository<ShortUrl, String> {
    @Query(value = "SELECT * FROM tbl_shorturl WHERE id LIKE :subString%", nativeQuery = true)
    fun findAllByIdLike(@Param("subString") subString: String): List<ShortUrl>
}
