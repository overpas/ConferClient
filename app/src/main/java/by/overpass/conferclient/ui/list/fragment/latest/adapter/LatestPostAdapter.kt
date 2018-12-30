package by.overpass.conferclient.ui.list.fragment.latest.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import by.overpass.conferclient.data.dto.PostWithUser
import by.overpass.conferclient.ui.list.fragment.viewholder.PostViewHolder
import by.overpass.conferclient.R
import by.overpass.conferclient.ui.post.activity.PostActivity
import kotlinx.android.synthetic.main.adapter_item_post.view.*

private val postItemDiff = object : DiffUtil.ItemCallback<PostWithUser>() {

    override fun areItemsTheSame(old: PostWithUser, new: PostWithUser): Boolean =
        old.getPostId() == new.getPostId()

    override fun areContentsTheSame(old: PostWithUser, new: PostWithUser): Boolean =
        old == new

}

class LatestPostAdapter : PagedListAdapter<PostWithUser, PostViewHolder>(postItemDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): PostViewHolder {
        return LayoutInflater.from(parent.context)
            .run { inflate(R.layout.adapter_item_post, parent, false) }
            .run { PostViewHolder(this) }
    }

    override fun onBindViewHolder(viewHolder: PostViewHolder, position: Int) {
        getItem(position)?.run {
            viewHolder.setPost(this)
            viewHolder.itemView.setOnClickListener {
                PostActivity.start(
                    viewHolder.itemView.context,
                    getPostId()
                )
            }
            viewHolder.itemView.ivSwitchFullShort.setOnClickListener {
                viewHolder.switch()
            }
        }
    }

}