package com.walker.modulo3projetoicontatos.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.walker.modulo3projetoicontatos.ContactModel
import com.walker.modulo3projetoicontatos.R
import com.walker.modulo3projetoicontatos.databinding.DialogFragmentContactDetailBinding

class ContactDetailDialogFragment(
    private var contactModel: ContactModel,
    private val onInputListener: OnInputListener,
): DialogFragment() {

    private lateinit var binding: DialogFragmentContactDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentContactDetailBinding.inflate(inflater, container, false)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)

        bindingContactModel(contactModel)

        return binding.root
    }

    private fun bindingContactModel(contactModel: ContactModel) {
        if (contactModel.instagram.isNullOrBlank()) {
            binding.buttonInstagram.isVisible = false
        } else {
            binding.buttonInstagram.onClickListener {
                onInputListener.openInstagram(contactModel.instagram)
            }
        }

        if (contactModel.email.isNullOrBlank()) {
            binding.buttonMail.isVisible = false
        } else {
            binding.buttonMail.onClickListener {
                onInputListener.openMail(contactModel.email)
            }
        }

        if (contactModel.phoneNumber.isNullOrBlank()) {
            binding.buttonCall.isVisible = false
        } else {
            binding.buttonCall.onClickListener {
                onInputListener.dialPhone(contactModel.phoneNumber)
            }
        }

        binding.contactName.text = contactModel.name
        binding.contactRelationship.text = contactModel.relationship
        binding.infoFacebook.setInfoValue(contactModel.facebook)
        binding.infoPhone.setInfoValue(contactModel.phoneNumber)
        binding.infoInstagram.setInfoValue(contactModel.instagram)
        binding.infoMail.setInfoValue(contactModel.email)

        binding.closeButton.setOnClickListener {
            dismiss()
        }

        binding.buttonDelete.setOnClickListener {
            onInputListener.deleteContact(contactModel)
            dismiss()
        }

        binding.editButton.setOnClickListener {
            onInputListener.openEditDialog(contactModel)
        }

        Glide.with(this).load(contactModel.contactImage).into(binding.contactImage)

    }

    override fun getTheme(): Int {
        return R.style.FullScreenDialog
    }

    interface OnInputListener {
        fun openContactDialog(contactModel: ContactModel)
        fun deleteContact(contactModel: ContactModel)
        fun openMail(link: String?)
        fun dialPhone(phone: String?)
        fun openInstagram(username: String?)
        fun openEditDialog(contactModel: ContactModel)
    }

    fun updateContact(contactModel: ContactModel) {
        bindingContactModel(contactModel)
        this.contactModel = contactModel
    }
}