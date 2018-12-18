package by.overpass.conferclient.ui.list.fragment.popular.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.overpass.conferclient.R
import by.overpass.conferclient.data.dto.PostWithUser
import by.overpass.conferclient.util.formatPostDate
import kotlinx.android.synthetic.main.item_post.view.*


class PopularPostAdapter : RecyclerView.Adapter<PopularPostAdapter.ViewHolder>() {

    var posts: List<PostWithUser> = listOf()
        set(value) {
            val diffResult = DiffUtil.calculateDiff(
                PostDiff(
                    field,
                    value
                )
            )
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return LayoutInflater.from(parent.context)
                .run { inflate(R.layout.item_post, parent, false) }
                .run {
                    ViewHolder(
                        this
                    )
                }
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.setPost(posts[position])
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal fun setPost(post: PostWithUser) {
            itemView.tvBody.text = post.getBody() ?: ""
            itemView.tvFullName.text = post.getFullName() ?: ""
            itemView.tvTitle.text = post.getTitle() ?: ""
            itemView.tvUsername.text = "(${post.getUsername() ?: ""})"
            itemView.tvDate.text = formatPostDate(post.getDate())
        }

    }

}