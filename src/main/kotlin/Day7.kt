import java.util.PriorityQueue

object Day7: Day() {

    sealed interface File {
        val name: String
        val parent: Directory?
        val size: Int
    }

    data class Directory(
        override val name: String,
        override val parent: Directory? = null,
        val children: MutableMap<String, File> = mutableMapOf()
    ) : File {

        override val size: Int by lazy {
                children.values.sumOf { it.size }.also { println("${this.name} hi") }
            }
    }

    data class RegularFile(override val name: String, override val parent: Directory, override val size: Int) : File

    private fun traverseWithFunction(root: Directory, fn: (Directory) -> Unit) {
        fun helper(file: File) {
            if (file is Directory) {
                file.children.values.forEach { helper(it) }
                fn(file)
            }
        }

        helper(root)
    }

    override fun part1(input: String): Any {
        val root = buildFilesystem(input)
        var sum = 0
        traverseWithFunction(root) {
            if (it.size <= 100_000) sum += it.size
        }
        return sum
    }


    override fun part2(input: String): Any {
        val root = buildFilesystem(input)
        val totalDiskSpace = 70_000_000
        val needToGetTo = 30_000_000
        val currentFreeSpace = totalDiskSpace - root.size

        var min = root.size
        traverseWithFunction(root) {
            if ((it.size + currentFreeSpace) >= needToGetTo) min = min.coerceAtMost(it.size)
        }
        return min
    }

    fun buildFilesystem(input: String): Directory {
        val rootNode = Directory("/", null)
        var pwd: Directory = rootNode

        input.lines().forEach {
            if (it.isNotBlank()) {
                if (it.startsWith("$ cd")) {
                    val dest = it.substringAfter("$ cd ").trim()
                    pwd = when (dest) {
                        "/" -> rootNode
                        ".." -> pwd.parent!!
                        else -> pwd.children[dest]!! as Directory
                    }
                } else {
                    if (it.contains("$ ls")) {
                        return@forEach
                    }
                    if (it.startsWith("dir")) {
                        val name = it.substringAfter("dir ")
                        pwd.children[name] = Directory(name, pwd)
                    } else {
                        val (size, name) = it.split(" ")
                        pwd.children[name] = RegularFile(name, pwd, size.toInt())
                    }
                }
            }
        }
        return rootNode
    }
}