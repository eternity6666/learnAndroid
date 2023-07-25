package com.yzh.demoapp.data.item

data class DataItem private constructor(
    val builder: Builder
) {
    val id = builder.id
    val title = builder.title
    val subTitle = builder.subTitle
    val thirdTitle = builder.thirdTitle
    val imageUrl = builder.imageUrl
    val height = builder.height
    val width = builder.width
    val color = builder.color
    val backgroundColor = builder.backgroundColor

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var id: Int = 0
        var title: String = ""
        var subTitle: String = ""
        var thirdTitle: String = ""
        var imageUrl: String = ""
        var height: Int = 0
        var width: Int = 0
        var color: Int = 0
        var backgroundColor: Int = 0

        fun id(id: Int): Builder {
            this.id = id
            return this
        }

        fun title(title: String?): Builder {
            this.title = title?:""
            return this
        }

        fun subTitle(subTitle: String?): Builder {
            this.subTitle = subTitle?:""
            return this
        }

        fun thirdTitle(thirdTitle: String?): Builder {
            this.thirdTitle = thirdTitle?:""
            return this
        }

        fun imageUrl(imageUrl: String?): Builder {
            this.imageUrl = imageUrl?:""
            return this
        }

        fun height(height: Int?): Builder {
            this.height = height?:0
            return this
        }

        fun width(width: Int?): Builder {
            this.width = width?:0
            return this
        }

        fun color(color: Int?): Builder {
            this.color = color?:0
            return this
        }

        fun backgroundColor(backgroundColor: Int?): Builder {
            this.backgroundColor = backgroundColor?:0
            return this
        }

        fun build() : DataItem {
            return DataItem(this)
        }
    }
}
