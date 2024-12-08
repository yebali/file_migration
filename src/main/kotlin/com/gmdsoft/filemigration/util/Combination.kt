package com.gmdsoft.filemigration.util

object Combination {
    fun getCombinationWithRepetition(n: List<Char>, r: Int): List<String> {
        // 모든 조합을 저장할 리스트
        val combinations = mutableListOf<String>()

        // 조합 생성 함수
        fun generate(current: String, depth: Int) {
            if (depth == r) {
                combinations.add(current) // r 길이의 조합 완성
                return
            }
            for (char in n) {
                generate(current + char, depth + 1) // 다음 문자를 추가
            }
        }

        // 초기 호출
        generate("", 0)
        return combinations
    }
}
