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
import by.overpass.conferclient.util.parentVm
import by.overpass.conferclient.util.shortToast
import by.overpass.conferclient.viewmodel.list.ListViewModel
import kotlinx.android.synthetic.main.dialog_new_post.*

class NewPostDialogFragment : DialogFragment() {

    private var newPostDialogCreator: NewPostDialogCreator? = null

    private val viewModel: ListViewModel by parentVm(ListViewModel.Factory::class.java)

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
        return inflater.inflate(R.layout.dialog_new_post, container, false)
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
        viewModel.newPost().observe(this, Observer {
            if (it != null) {
                onPostCreationStatusChanged(it)
            }
        })
    }

    private fun onPostCreationStatusChanged(postCreationStatus: PostCreationStatus) {
        when (postCreationStatus) {
            is PostCreationStatus.Error -> {
                // TODO: Error
                setLoading(false)
                shortToast(postCreationStatus.message)
                dismiss()
            }
            is PostCreationStatus.Success -> {
                setLoading(false)
                shortToast("New Post Created with id = ${postCreationStatus.postId}")
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
        fun offerToCreateNewPost()
    }

    companion object {
        val TAG = NewPostDialogFragment::class.java.simpleName

        fun newInstance() = NewPostDialogFragment()
    }

}