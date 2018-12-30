package by.overpass.conferclient.ui.list.fragment.popular.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import by.overpass.conferclient.R
import by.overpass.conferclient.data.dto.PostWithUser
import by.overpass.conferclient.ui.list.fragment.viewholder.PostViewHolder
import by.overpass.conferclient.ui.post.activity.PostActivity
import kotlinx.android.synthetic.main.adapter_item_post.view.*


class PopularPostAdapter : RecyclerView.Adapter<PostViewHolder>() {

    var posts: List<PostWithUser> = listOf()
        set(value) {
            val diffResult = DiffUtil.calculateDiff(PostDiff(field, value))
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): PostViewHolder {
        return LayoutInflater.from(parent.context)
            .run { inflate(R.layout.adapter_item_post, parent, false) }
            .run { PostViewHolder(this) }
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(viewHolder: PostViewHolder, position: Int) {
        viewHolder.setPost(posts[position])
        viewHolder.itemView.setOnClickListener {
            PostActivity.start(viewHolder.itemView.context, posts[position].getPostId())
        }
        viewHolder.itemView.ivSwitchFullShort.setOnClickListener {
            viewHolder.switch()
        }
    }

}