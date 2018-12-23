package by.overpass.conferclient.data.mapper

import by.overpass.conferclient.data.network.dto.PostTree
import com.google.gson.GsonBuilder

class PostTreeMapper {

    private val gson by lazy {
        GsonBuilder()
            .create()
    }

    fun map(postTree: PostTree): by.overpass.conferclient.data.db.entity.PostTree =
        by.overpass.conferclient.data.db.entity.PostTree(
            postTree.id,
            gson.toJson(postTree)
        )

    fun map(postTree: by.overpass.conferclient.data.db.entity.PostTree): PostTree =
        gson.fromJson(postTree.json, PostTree::class.java)

}