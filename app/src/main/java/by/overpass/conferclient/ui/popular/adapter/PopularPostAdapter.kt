package by.overpass.conferclient.ui.popular.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.overpass.conferclient.R
import by.overpass.conferclient.data.dto.PostWithUser
import com.chauthai.swipereveallayout.ViewBinderHelper


class PopularPostAdapter : RecyclerView.Adapter<PopularPostAdapter.ViewHolder>() {

    private val viewBinderHelper = ViewBinderHelper()

    var posts: List<PostWithUser> = listOf()
        set(value) {
            field = value
            val postDiff = PostDiff(field, value)
            val diffResult = DiffUtil.calculateDiff(postDiff)
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.ite_pos, parent, false)
        )
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        //viewBinderHelper.bind(viewHolder.itemView.swipeLayout, posts[position].post.id.toString())
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private fun setPost(post: PostWithUser) {

        }

    }

}