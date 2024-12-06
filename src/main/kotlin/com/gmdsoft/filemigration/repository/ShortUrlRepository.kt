package com.gmdsoft.filemigration.repository

import com.gmdsoft.filemigration.entity.ShortUrl
import org.springframework.data.jpa.repository.JpaRepository

interface ShortUrlRepository : JpaRepository<ShortUrl, String>
