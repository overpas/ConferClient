package by.overpass.conferclient.ui.popular.adapter

import android.support.v7.util.DiffUtil
import by.overpass.conferclient.data.dto.PostWithUser

class PostDiff(
    private val oldList: List<PostWithUser>,
    private val newList: List<PostWithUser>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].post.id == newList[newItemPosition].post.id

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

}