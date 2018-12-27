package by.overpass.conferclient.ui.list.fragment.create

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.overpass.conferclient.R
import by.overpass.conferclient.data.dto.PostCreationStatus
import by.overpass.conferclient.ui.post.activity.PostActivity
import by.overpass.conferclient.util.parentVm
import by.overpass.conferclient.util.shortToast
import by.overpass.conferclient.util.text
import by.overpass.conferclient.viewmodel.post.PostingViewModel
import kotlinx.android.synthetic.main.dialog_new_post.*
import timber.log.Timber

class NewPostDialogFragment : DialogFragment() {

    private var newPostDialogCreator: NewPostDialogCreator? = null

    private val postingViewModel: PostingViewModel by parentVm(PostingViewModel.Factory::class.java)

    private var postId: Long = 0

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is NewPostDialogCreator) {
            newPostDialogCreator = context
        } else {
            throw ClassCastException("$context must implement ${NewPostDialogCreator::class.java.simpleName}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (postId != 0L) {
            inflater.inflate(R.layout.dialog_reply_to_post, container, false)
        } else {
            inflater.inflate(R.layout.dialog_new_post, container, false)
        }
    }

    override fun onViewCreated(theView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(theView, savedInstanceState)
        btnSend.setOnClickListener { view ->
            onSendClicked()
        }
        btnCancel.setOnClickListener {
            onCancelClicked()
        }
    }

    override fun onDetach() {
        super.onDetach()
        newPostDialogCreator = null
    }

    private fun onCancelClicked() {
        dismiss()
    }

    private fun onSendClicked() {
        postingViewModel
            .newPost(etTitle.text(), etBody.text(), postId)
            .observe(this, Observer {
                if (it != null) {
                    onPostCreationStatusChanged(it)
                }
            })
    }

    private fun onPostCreationStatusChanged(postCreationStatus: PostCreationStatus) {
        when (postCreationStatus) {
            is PostCreationStatus.Error -> {
                setLoading(false)
                shortToast(postCreationStatus.message)
                dismiss()
            }
            is PostCreationStatus.Success -> {
                setLoading(false)
                Timber.d("New Post Created with id = ${postCreationStatus.postId}")
                if (postId == 0L) {
                    context?.run {
                        PostActivity.start(this, postCreationStatus.postId)
                    }
                } else {
                    postingViewModel.update()
                }
                dismiss()
            }
            else -> {
                setLoading(true)
            }
        }
    }

    private fun setLoading(loading: Boolean) {
        pbLoading.visibility = if (loading) View.VISIBLE else View.GONE
        ivChatImage.visibility = if (loading) View.INVISIBLE else View.VISIBLE
        btnCancel.isClickable = !loading
        btnSend.isClickable = !loading
        etTitle.isFocusable = !loading
        etTitle.isFocusableInTouchMode = !loading
        etBody.isFocusable = !loading
        etBody.isFocusableInTouchMode = !loading
    }

    interface NewPostDialogCreator {
        fun offerToCreateNewPost(postId: Long)
    }

    companion object {
        val TAG = NewPostDialogFragment::class.java.simpleName

        fun newInstance(postId: Long) = NewPostDialogFragment().apply {
            this.postId = postId
        }
    }

}