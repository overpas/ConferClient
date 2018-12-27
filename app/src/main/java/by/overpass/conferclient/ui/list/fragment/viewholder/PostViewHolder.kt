package by.overpass.conferclient.ui.list.fragment.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import by.overpass.conferclient.data.dto.PostWithUser
import by.overpass.conferclient.util.formatPostDate
import kotlinx.android.synthetic.main.adapter_item_post.view.*

class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    internal fun setPost(post: PostWithUser) {
        itemView.tvBody.text = post.getBody() ?: ""
        itemView.tvFullName.text = post.getFullName() ?: ""
        itemView.tvTitle.text = post.getTitle() ?: ""
        itemView.tvUsername.text = "(${post.getUsername() ?: ""})"
        itemView.tvDate.text = formatPostDate(post.getDate())
    }

}