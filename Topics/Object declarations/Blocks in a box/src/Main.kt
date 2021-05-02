class Block(val color: String) {
    object BlockProperties {
        var length = 6
        var width = 4
        
        fun blocksInBox(l: Int, w: Int): Int = (l / length) * (w / width)
    }
}
