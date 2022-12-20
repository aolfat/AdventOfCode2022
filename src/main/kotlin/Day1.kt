import java.util.PriorityQueue

object Day1 : Day() {
    override fun part1(input: String): Any {
        return getMaxN(1, input)
    }

    private fun getMaxN(n: Int, input: String): List<Int> {
        val pQ = PriorityQueue<Int>()
        input.lines().chunkedBy { it.isNotBlank() }
            .sumOf { it.sumOf { calories -> calories.toInt() }
                .also {
                    sum -> pQ.add(sum)
                    if (pQ.size > n) pQ.poll()
                }
            }

        return List(n) { pQ.poll() }
    }

    override fun part2(input: String): Any {
        val top3 = getMaxN(3, input).reversed()
        println(top3)
        return top3.sum()
    }

}
