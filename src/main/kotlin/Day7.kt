import java.util.PriorityQueue

object Day7: Day() {

    sealed class File {

        abstract val name: String
        abstract val parent: Directory
        abstract val size: Int

        sealed class Directory(val children: MutableList<File> = mutableListOf()) : File() {

            override val size by lazy { children.sumOf { it.size } }

            fun getAllFolders(): List<Directory> {
                return this.children.filterIsInstance<Directory>().flatMap { it.getAllFolders() } + this
            }

            object RootNode : Directory() {
                override val name: String
                    get() = "/"
                override val parent: Directory
                    get() = RootNode

            }

            data class Dir(
                override val name: String,
                override val parent: Directory,
            ) : Directory()
        }

        data class RegularFile(override val name: String, override val parent: Directory, override val size: Int) : File()
    }

    override fun part1(input: String): Any {
        val root = buildFilesystem(input)
        println(root)
        return gatherAllDirsAtMost100000(root, 100_000).sum()
    }

    private fun gatherAllDirsAtMost100000(root: File, atMostSize: Int): MutableList<Int> {
        fun helper(file: File, result: MutableList<Int>) {
//                is File.RegularFile -> if (file.size <= atMostSize) result[file.name] = file.size
            if (file is File.Directory) {
                file.children.forEach { helper(it, result) }
                if (file.size <= atMostSize) result.add(file.size)
            }
        }


        return mutableListOf<Int>().also { helper(root, it) }.also { it.println() }
    }

    private fun gatherAllDirSizes(root: File): PriorityQueue<File> {
        fun helper(file: File, result: PriorityQueue<File>) {
            if (file is File.Directory) {
                file.children.forEach { helper(it, result) }
                result.add(file)
            }
        }


        return PriorityQueue<File>(compareBy { it.size }).also { helper(root, it) }
    }

    override fun part2(input: String): Any? {
        val root = buildFilesystem(input)
        val totalDiskSpace = 70_000_000
        val needToGetTo = 30_000_000
        val currentFreeSpace = totalDiskSpace - root.size

        return root.getAllFolders().asSequence().filter { currentFreeSpace + it.size >= needToGetTo }.minOf { it.size }
    }

    fun buildFilesystem(input: String): File.Directory {
        val rootNode = File.Directory.RootNode
        var currentNode: File.Directory = rootNode

        input.lines().forEach {
            if (it.isNotBlank()) {
                if (it.startsWith("$ cd")) {
                    val dest = it.substringAfter("$ cd ").trim()
                    currentNode = when (dest) {
                        "/" -> rootNode
                        ".." -> currentNode.parent
                        else -> currentNode.children.first { dir -> dir.name == dest } as File.Directory
                    }
                } else {
                    if (it.contains("$ ls")) {
                        return@forEach
                    }
                    if (it.startsWith("dir")) {
                        val name = it.substringAfter("dir ")
                        currentNode.children.add(File.Directory.Dir(name, currentNode))
                    } else {
                        val (size, name) = it.split(" ")
                        currentNode.children.add(File.RegularFile(name, currentNode, size.toInt()))
                    }
                }
            }
        }
        return rootNode
        /**
         * need to keep track of the current node
         * cd changes current node
         * ls  means keep reading lines to add to children
         *
         **/
    }
}