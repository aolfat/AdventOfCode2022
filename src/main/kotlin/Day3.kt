object Day3 : Day() {
    override fun part1(input: String): Any =
        input.lines().sumOf {
            if (it.isNotBlank()) {
                val chunks = it.chunked(it.length/2)
                val charsInComponent1 = chunks[0].toCharArray().toSet()
                val charsInComponent2 = chunks[1].toCharArray().toSet()
                val intersect = charsInComponent1.intersect(charsInComponent2)
                require(intersect.size == 1)
                getPriority(intersect.first())
            } else {
                0
            }
        }


    override fun part2(input: String): Any {
        return input.lines().asSequence().filter { it.isNotBlank() }.chunked(3).sumOf {
            val g1 = it[0].toCharArray().toSet()
            val g2 = it[1].toCharArray().toSet()
            val g3 = it[2].toCharArray().toSet()
            val commonItem = g1 intersect g2 intersect g3
            require(commonItem.size == 1)
            getPriority(commonItem.first())
        }
    }

    private fun getPriority(char: Char): Int {
        val validKeys = ('a'..'z').toSet() + ('A'..'Z').toSet()
        require(char in validKeys)
        return if (char in 'a'..'z') char.code - 96 else char.code - 38
    }
}