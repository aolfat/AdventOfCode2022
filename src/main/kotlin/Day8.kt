object Day8: Day() {

    enum class Direction {
        TOP, BOTTOM, LEFT, RIGHT
    }

    override fun part1(input: String): Any? {
        val input: Array<Array<Int>> = parseInput(input)
        val rows = input.size
        val cols = input[0].size

        val trackingArray = Array(input.size) { Array(input[0].size) { mutableMapOf<Direction, Pair<Int, Int>>() } }

        for (i in 0 until rows) {
            // init outer most of each row
            trackingArray[i][0][Direction.LEFT] = input[i][0] to i
            trackingArray[i][cols - 1][Direction.RIGHT] = input[i][cols - 1] to cols - 1
        }

        for (j in 0 until cols) {
            // init top and bottom
            trackingArray[0][j][Direction.TOP] = input[0][j] to j
            trackingArray[rows-1][j][Direction.BOTTOM] = input[rows - 1][j] to rows - 1
        }

        for (i in 1 until rows - 1) {
            for (j in 1 until cols - 1) {
                val treeHeight = input[i][j]
                // do left
                val leftPair = trackingArray[i][j - 1][Direction.LEFT]!!
                trackingArray[i][j][Direction.LEFT] = if (treeHeight >= leftPair.first) treeHeight to j else leftPair
                // do top
                val topPair = trackingArray[i - 1][j][Direction.TOP]!!
                trackingArray[i][j][Direction.TOP] = if (treeHeight >= topPair.first) treeHeight to i else topPair
            }
        }

        for (i in (rows - 2) downTo 1) {
            for (j in (cols - 2) downTo 1) {
                val treeHeight = input[i][j]
                // do right
                val rightPair = trackingArray[i][j + 1][Direction.RIGHT]!!
                trackingArray[i][j][Direction.RIGHT] = if (treeHeight >= rightPair.first) treeHeight to j else rightPair
                // do bottom
                val bottomPair = trackingArray[i + 1][j][Direction.BOTTOM]!!
                trackingArray[i][j][Direction.BOTTOM] = if (treeHeight >= bottomPair.first) treeHeight to i else bottomPair
            }
        }

        var sum = 0
        for (i in 1 until rows - 1) {
            for (j in 1 until cols - 1) {
                val treeHeight = input[i][j]
                if (treeHeight > trackingArray[i][j-1][Direction.LEFT]!!.first ||
                    treeHeight > trackingArray[i][j+1][Direction.RIGHT]!!.first ||
                    treeHeight > trackingArray[i-1][j][Direction.TOP]!!.first ||
                    treeHeight > trackingArray[i+1][j][Direction.BOTTOM]!!.first) {
                    sum += 1
                }
            }
        }
//
//        var max = 0
//        for (i in 1 until rows - 1) {
//            for (j in 1 until cols - 1) {
//                val treeHeight = input[i][j]
//
//                val (maxLeft, indexLeft) = trackingArray[i][j - 1][Direction.LEFT]!!
//                val left = if (treeHeight > maxLeft) j else j - indexLeft
//
//                val (maxRight, indexRight) = trackingArray[i][j + 1][Direction.RIGHT]!!
//                val right = if (treeHeight > maxRight) cols - 1 - j else indexRight - j
//
//                val (maxTop, indexTop) = trackingArray[i-1][j][Direction.TOP]!!
//                val top = if (treeHeight > maxTop) i else i - indexTop
//
//                val (maxBottom, indexBottom) = trackingArray[i + 1][j][Direction.BOTTOM]!!
//                val bottom = if (treeHeight > maxBottom) rows - 1 - i else indexBottom - i
//                val scenicScore = left * right * top * bottom
//                max = max.coerceAtLeast(scenicScore)
//            }
//        }


//        prettyPrint(trackingArray)

        val perim = (rows * 2) + ((cols - 2) * 2)
//        perim.println()
//        sum.println()
        return sum + perim
    }

    private fun parseInput(input: String) = input.lines().filter { it.isNotBlank() }
        .map { it.map { c -> c.digitToInt() }.toTypedArray() }
        .toTypedArray()

    private fun prettyPrint(
        trackingArray: Array<Array<MutableMap<Direction, Pair<Int, Int>>>>
    ) {
        for (row in trackingArray) {
            for (element in row) {
                print("$element ")
            }
//            println()
        }
    }


    override fun part2(input: String): Any? {
        val input: Array<Array<Int>> = parseInput(input)

        var maxScore = 0
        input.forEachIndexed { i, row ->
            row.forEachIndexed { j, elem ->
                val curTreeHeight = elem
                if ( !(i == 0 || i == input.size - 1 || j == 0 || j == input[0].size - 1) ) {
                    var rDistance = 0
                    row.slice(j + 1 until row.size).takeWhile { rDistance++; elem > it }
                    var lDistance = 0
                    row.slice(j - 1 downTo 0).takeWhile { lDistance++; elem > it }
                    var topDistance = 0
                    (i - 1 downTo 0).takeWhile { topDistance++; elem > input[it][j] }
                    var bottomDistance = 0
                    (i + 1 until input[0].size).takeWhile { bottomDistance++; elem > input[it][j] }
                    val scenicScore = rDistance * lDistance * topDistance * bottomDistance
                    maxScore = maxScore.coerceAtLeast(scenicScore)
                }
            }
        }

        return maxScore
    }
}