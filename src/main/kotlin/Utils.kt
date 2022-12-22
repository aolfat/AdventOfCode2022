import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import java.util.ArrayList
import java.util.concurrent.TimeUnit
import kotlin.system.measureNanoTime

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src/main/resources", "$name.txt")
    .readText()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

inline fun <T> Iterable<T>.chunkedBy(predicate: (T) -> Boolean): List<List<T>> {
    val result = ArrayList<List<T>>()
    val partitionedList = ArrayList<T>()
    forEach {
        if (predicate(it)) {
            partitionedList.add(it)
        } else {
            result.add(ArrayList(partitionedList))
            partitionedList.clear()
        }
    }
    return result
}

fun measure(block: () -> Unit) {
    val nanoTime = measureNanoTime(block)
    TimeUnit.NANOSECONDS.toMillis(nanoTime).println()
}