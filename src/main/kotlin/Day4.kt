object Day4 : Day() {

    private fun toRange(ass: String): IntRange {
        val (start, end) = ass.split('-')
        return (start.toInt()..end.toInt())
    }

    private infix fun IntRange.overlapsAll(other: IntRange): Boolean {
        return this.first >= other.first && this.last <= other.last
    }

    override fun part1(input: String): Any =
        overlap(input) { r1, r2 ->
            r1 overlapsAll r2 || r2 overlapsAll r1
        }


    override fun part2(input: String): Any {
        return overlap(input) { r1, r2 ->
            (r1 intersect r2).isNotEmpty()
        }
    }

    /**
     * this is way too fancy but was cool to try and make higher order fn's
     *
     * for future self: https://stackoverflow.com/questions/48709655/kotlin-pass-method-reference-to-function
     *
     */
    private fun overlap(input: String, fn: (r1: IntRange, r2: IntRange) -> Boolean): Int =
        input.lines().asSequence().filter { it.isNotBlank() }.count {
//            it.println()
            val (ass1, ass2) = it.split(',') // hehehe
            val range1 = toRange(ass1)
            val range2 = toRange(ass2)
            fn(range1, range2)
        }
    }

