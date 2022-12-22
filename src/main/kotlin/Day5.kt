//fun main() {
//    val input = """
//    [D]
//[N] [C]
//[Z] [M] [P]
// 1   2   3
//
//move 1 from 2 to 1
//move 3 from 1 to 3
//move 2 from 2 to 1
//move 1 from 1 to 2
//    """.trimIndent()
//
//    input.lines().println()
//
//    val a = input.lines().first { """(\d +)+""".toRegex().containsMatchIn(it) }
//    val numberOfDigits = a.filter { it.isDigit() }.length
//    val stacks = (1..numberOfDigits).map { ArrayDeque<Char>() }
////    println(a)
//    input.lines().asSequence().takeWhile { it != a }.forEach {
//        it.chunked(4).forEachIndexed { index, s ->
//            if (s.isNotBlank()) {
//                val char = s.first { chunk -> chunk.isLetter() }
//                stacks[index].addFirst(char)
//            }
//        }
//    }
//    println(stacks)
//    val instr = input.lines().asSequence().filter { it.contains("move") }.map { instr ->
//        val digits = """\d+""".toRegex().findAll(instr).map { it.value.toInt() }.toList()
//        require(digits.size == 3)
//        Day5.Instruction(digits[0], digits[1] - 1, digits[2] - 1)
//    }.toList()
//    println(instr)
//
//    instr.forEach { instr ->
//        repeat(instr.moveCount) {
//            val removed = stacks[instr.from].removeLast()
//            stacks[instr.to].addLast(removed)
//        }
//    }
//
//    println(stacks)
//    stacks.map { it.last() }.joinToString("").println()
//}

object Day5: Day() {
    override fun part1(input: String): Any? {
        val stacks = getStacks(input)
        val instructions = getInstructions(input)
        println(instructions)
        applyInstructions(stacks, instructions)
        return stacks.map { it.last() }.joinToString("")
    }

    private fun applyInstructions(stacks: List<ArrayDeque<Char>>, instructions: List<Instruction>, version: Int = 1) {
        instructions.forEach { instr ->
            if (version == 2) {
                val removed = stacks[instr.from].takeLast(instr.moveCount)
                repeat(instr.moveCount) { stacks[instr.from].removeLast() }
                stacks[instr.to].addAll(removed)
            }
            else {
                repeat(instr.moveCount) {
                    val removed = stacks[instr.from].removeLast()
                    stacks[instr.to].addLast(removed)
                }
            }
        }
    }

    data class Instruction(val moveCount: Int, val from: Int, val to: Int)

    private fun getInstructions(input: String): List<Instruction> {
        return input.lines().asSequence().filter { it.contains("move") }.map { instr ->
            val digits = """\d+""".toRegex().findAll(instr).map { it.value.toInt() }.toList()
            require(digits.size == 3)
            Instruction(digits[0], digits[1] - 1, digits[2] - 1)
        }.toList()
    }

    private fun getStacks(input: String): List<ArrayDeque<Char>> {
        val lineWithStackCount = input.lines().first { """(\d +)+""".toRegex().containsMatchIn(it) }
        val numberOfDigits = lineWithStackCount.filter { it.isDigit() }.length
        val stacks = (1..numberOfDigits).map { ArrayDeque<Char>() }

        input.lines().asSequence().takeWhile { it != lineWithStackCount }.forEach {
            it.chunked(4).forEachIndexed { index, s ->
                if (s.isNotBlank()) {
                    val char = s.first { chunk -> chunk.isLetter() }
                    stacks[index].addFirst(char)
                }
            }
        }
        return stacks
    }

//    private fun getStacks(input: String): Any {
//        input.filter { """(\d )+""".toRegex().matches(it) }
//    }

    override fun part2(input: String): Any? {
        val stacks = getStacks(input)
        val instructions = getInstructions(input)
        println(instructions)
        applyInstructions(stacks, instructions, 2)
        return stacks.map { it.last() }.joinToString("")
    }
}