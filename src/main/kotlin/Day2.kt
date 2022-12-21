object Day2 : Day() {
    enum class Hand(val points: Int, val encodings: Set<String>) {

        ROCK(1, setOf("A", "X")) {
            override val beats: Hand
                get() = SCISSOR
        },
        PAPER(2, setOf("B", "Y")) {
            override val beats: Hand
                get() = ROCK
        },
        SCISSOR(3, setOf("C", "Z")) {
            override val beats: Hand
                get() = PAPER
        };

        fun against(otherHand: Hand): Int {
            return when {
                otherHand.name == this.name -> 3
                this.beats.name == otherHand.name -> 6
                else -> 0
            }
        }

        fun losesAgainst(): Hand {
            val values = Hand.values()
            return values.first { it.beats == this }
        }

        abstract val beats: Hand

        companion object {

            fun fromEncoding(s: String): Hand =
                Hand.values().first { it.encodings.contains(s) }

            fun calculateScore(myHand: Hand, opponentHand: Hand): Int {
//                println("myHand + $myHand;")
//                println("oppHand + $opponentHand ")
                val againstScore = myHand.against(opponentHand)
//                println(againstScore)
//                println(myHand.points)
//                println(againstScore + myHand.points)
                return againstScore + myHand.points
            }

        }

    }

    override fun part1(input: String): Any {
        return input.lines().sumOf {
            if (it.isNotBlank()) {
                val encodings = it.split(" ")
                val opponentHand = Hand.fromEncoding(encodings[0])
                val myHand = Hand.fromEncoding(encodings[1])
                Hand.calculateScore(myHand, opponentHand)
            } else {
                0
            }
        }
        /**
         * Score is:
         *  shape you selected (1 for Rock, 2 for Paper, and 3 for Scissors)
         *  outcome of the round (0 if you lost, 3 if the round was a draw, and 6 if you won)
         *
         *  winning:
         *      Rock defeats Scissors, Scissors defeats Paper, and Paper defeats Rock
         *
         *  first col is what you play
         *  rock = A, X
         *  paper = B, Y
         *  scissor = C, Z
         *
         *  win(yourHand, theirHand) + yourHand.points
         */
    }


    override fun part2(input: String): Any? {
        return input.lines().sumOf {
            if (it.isNotBlank()) {
                val encodings = it.split(" ")
                val opponentHand = Hand.fromEncoding(encodings[0])
                val myHand = when (encodings[1]) {
                    "X" -> opponentHand.beats
                    "Y" -> opponentHand
                    else -> opponentHand.losesAgainst()
                }
                // x means I need to lose
                // y means I need to draw
                // z means I need to win
                // figure out what I need to play
                // then calc score, which I
                Hand.calculateScore(myHand, opponentHand)
            } else {
                0
            }
        }
    }
}