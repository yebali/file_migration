package com.gmdsoft.filemigration.service
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File

class FileServiceTest {
    @Test
    fun `파일 존재 확인`() {
        val file = File("C:\\Users\\GMD_SOFT\\Desktop\\yebali.txt")

        Assertions.assertThat(file.exists()).isFalse
    }
}
