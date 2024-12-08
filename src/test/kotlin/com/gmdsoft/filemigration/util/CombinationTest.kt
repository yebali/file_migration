package com.gmdsoft.filemigration.util

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class CombinationTest {
    @Test
    fun `0~9, a~z 사이의 문자 3개로 이우러진 조합의 갯수`() {
        val chars = ('0'..'9') + ('a'..'z')

        val combination = Combination.getCombinationWithRepetition(chars, 3)

        Assertions.assertThat(combination.size).isEqualTo(46656)
    }
}
