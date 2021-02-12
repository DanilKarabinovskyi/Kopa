package com.example.kopa.model

data class Declaration (
        var id: String,
        var price: String,
        var model: String,
        var material: String,
        var size: String,
        var sizeRegion: String,
        var sizeLength: String,
        var sizeWidth: String,
        var liked: Boolean = false,
        var selled: Boolean = false,
        var description:String = "",
        var photoArray: MutableList<String> = mutableListOf<String>()
)