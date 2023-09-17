package com.walker.modulo3projetoicontatos.mainscreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.walker.modulo3projetoicontatos.ContactModel
import com.walker.modulo3projetoicontatos.R
import com.walker.modulo3projetoicontatos.databinding.DialogFragmentAddEditContactBinding


class AddEditContactDialogFragment(
    private val nextIndex: Int? = null,
    private val contactToEdit: ContactModel? = null,
    private val isEditProfile: Boolean = false
) : DialogFragment() {

    private val placeholder: String = "https://st3.depositphotos.com/6672868/13701/v/450/depositphotos_137014128-stock-illustration-user-profile-icon.jpg"
    private lateinit var binding: DialogFragmentAddEditContactBinding
    private var pickedImage: String? = null


    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            pickedImage = uri.toString()
            Glide.with(this).load(pickedImage).into(binding.contactImage)
        } else {
            Log.d("PickVisualMedia", "No media selected")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentAddEditContactBinding.inflate(inflater, container, false)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)

        contactToEdit?.let {
            binding.screenTitle.text = resources.getString(R.string.editContacts)
            binding.contactNameEditText.setText(it.name)
            binding.relationshipTextView.setText(it.relationship)
            binding.contactPhoneEditText.setText(it.phoneNumber)
            binding.contactInstagramEditText.setText(it.instagram)
            binding.contactFacebookEditText.setText(it.facebook)
            binding.contactMailEditText.setText(it.email)
        }

        if (isEditProfile) {
            binding.screenTitle.text = resources.getString(R.string.editProfile)
            binding.contactRelationshipInputLayout.isVisible = false
        }

        Glide.with(this).load(contactToEdit?.contactImage ?: placeholder).into(binding.contactImage)

        val relationshipTypes = resources.getStringArray(R.array.relationship)
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, relationshipTypes)
        binding.relationshipTextView.setAdapter(arrayAdapter)

        binding.contactImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.buttonCancel.setOnClickListener {
            dismiss()
        }

        binding.buttonConfirm.setOnClickListener {
            nextIndex?.let {
                if (binding.contactPhoneEditText.text.isNullOrBlank() ||
                    binding.contactMailEditText.text.isNullOrBlank() ||
                    binding.contactMailEditText.text.isNullOrBlank() ||
                    binding.relationshipTextView.text.isNullOrBlank()
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Preencha todos os campos indicados* como obrigatórios.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                (activity as? OnInputListener)?.addContact(
                    ContactModel(
                        nextIndex,
                        binding.contactNameEditText.text.toString(),
                        binding.relationshipTextView.text.toString(),
                        binding.contactPhoneEditText.text.toString(),
                        binding.contactInstagramEditText.text.toString(),
                        binding.contactFacebookEditText.text.toString(),
                        binding.contactMailEditText.text.toString(),
                        pickedImage ?: placeholder
                    )
                )
            } ?: run {
                contactToEdit?.let {
                    if (isEditProfile) {
                        (activity as? OnInputListener)?.updateProfile(
                            ContactModel(
                                it.id,
                                binding.contactNameEditText.text.toString(),
                                binding.relationshipTextView.text.toString(),
                                binding.contactPhoneEditText.text.toString(),
                                binding.contactInstagramEditText.text.toString(),
                                binding.contactFacebookEditText.text.toString(),
                                binding.contactMailEditText.text.toString(),
                                pickedImage ?: it.contactImage
                            )
                        )
                    } else {
                        (activity as? OnInputListener)?.updateContact(
                            ContactModel(
                                it.id,
                                binding.contactNameEditText.text.toString(),
                                binding.relationshipTextView.text.toString(),
                                binding.contactPhoneEditText.text.toString(),
                                binding.contactInstagramEditText.text.toString(),
                                binding.contactFacebookEditText.text.toString(),
                                binding.contactMailEditText.text.toString(),
                                pickedImage ?: it.contactImage
                            )
                        )
                    }
                }
            }

            dismiss()
        }

        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.FullScreenDialog
    }

    interface OnInputListener {
        fun openAddContact(nextIndex: Int)
        fun addContact(contactModel: ContactModel)
        fun updateContact(contactModel: ContactModel)
        fun updateProfile(contactModel: ContactModel)
    }
}