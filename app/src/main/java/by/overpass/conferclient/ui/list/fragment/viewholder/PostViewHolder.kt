package by.overpass.conferclient.ui.list.fragment.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import by.overpass.conferclient.R
import by.overpass.conferclient.data.dto.PostWithUser
import by.overpass.conferclient.util.bind
import by.overpass.conferclient.util.formatPostDate

private const val SHORT_MAX_LINES = 5

class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tvBody by bind<TextView>(R.id.tvBody)
    private val tvFullName by bind<TextView>(R.id.tvFullName)
    private val tvTitle by bind<TextView>(R.id.tvTitle)
    private val tvUsername by bind<TextView>(R.id.tvUsername)
    private val tvDate by bind<TextView>(R.id.tvDate)
    private val ivSwitchFullShort by bind<ImageView>(R.id.ivSwitchFullShort)

    internal fun setPost(post: PostWithUser) {
        minimize()
        tvBody.text = post.getBody() ?: ""
        tvFullName.text = post.getFullName() ?: ""
        tvTitle.text = post.getTitle() ?: ""
        val usernameText = "(${post.getUsername() ?: ""})"
        tvUsername.text = usernameText
        tvDate.text = formatPostDate(post.getDate())
    }

    fun switch() {
        val maxLines = if (tvBody.maxLines == SHORT_MAX_LINES) {
            ivSwitchFullShort.setImageResource(R.drawable.ic_up)
            Int.MAX_VALUE
        } else {
            ivSwitchFullShort.setImageResource(R.drawable.ic_down)
            SHORT_MAX_LINES
        }
        tvBody.maxLines = maxLines
    }

    private fun minimize() {
        tvBody.maxLines = SHORT_MAX_LINES
        ivSwitchFullShort.setImageResource(R.drawable.ic_down)
    }

}