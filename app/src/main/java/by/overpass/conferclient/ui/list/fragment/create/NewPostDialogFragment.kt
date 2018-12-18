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
import by.overpass.conferclient.util.getVm
import by.overpass.conferclient.util.shortToast
import by.overpass.conferclient.viewmodel.login.ListViewModel
import kotlinx.android.synthetic.main.dialog_new_post.*

class NewPostDialogFragment : DialogFragment() {

    private var newPostDialogCreator: NewPostDialogCreator? = null

    private lateinit var viewModel: ListViewModel

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
        viewModel = getVm(activity!!, ListViewModel::class.java, ListViewModel.Factory::class.java)
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
        shortToast(R.string.cancel)
        dismiss()
    }

    private fun onSendClicked() {
        setLoading()
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
                unsetLoading()
                shortToast(postCreationStatus.message)
                dismiss()
            }
            is PostCreationStatus.Success -> {
                unsetLoading()
                shortToast("New Post Created with id = ${postCreationStatus.postId}")
                dismiss()
            }
            else -> {
                // TODO: Loading
            }
        }
    }

    private fun setLoading() {
        pbLoading.visibility = View.VISIBLE
        ivChatImage.visibility = View.INVISIBLE
        btnCancel.isClickable = false
        btnSend.isClickable = false
        etTitle.isFocusable = false
        etTitle.isFocusableInTouchMode = false
        etBody.isFocusable = false
        etBody.isFocusableInTouchMode = false
    }

    private fun unsetLoading() {
        pbLoading.visibility = View.GONE
        ivChatImage.visibility = View.VISIBLE
        btnCancel.isClickable = true
        btnSend.isClickable = true
        etTitle.isFocusable = true
        etTitle.isFocusableInTouchMode = true
        etBody.isFocusable = true
        etBody.isFocusableInTouchMode = true
    }

    interface NewPostDialogCreator {
        fun offerToCreateNewPost()
    }

    companion object {
        val TAG = NewPostDialogFragment::class.java.simpleName

        fun newInstance() = NewPostDialogFragment()
    }

}