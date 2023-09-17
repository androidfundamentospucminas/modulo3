package com.walker.modulo3projetoicontatos.mainscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.walker.modulo3projetoicontatos.ContactModel
import com.walker.modulo3projetoicontatos.R
import com.walker.modulo3projetoicontatos.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private var profile: ContactModel = ContactModel(
        -1,
        "",
        "",
        ""
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        if (savedInstanceState != null) {
            val data: ContactModel = savedInstanceState.getSerializable("profile") as ContactModel
            profile = data
        }

        binding.editButton.setOnClickListener {
            activity?.supportFragmentManager?.let {
                val newFragment = AddEditContactDialogFragment(contactToEdit = profile, isEditProfile = true)
                newFragment.show(it, "AddEditContactDialogFragment")
            }
        }
        bindProfile(profile)

        return binding.root
    }

    fun updateProfile(contactModel: ContactModel) {
        profile = contactModel
        bindProfile(profile)
    }

    private fun bindProfile(contactModel: ContactModel) {
        binding.contactName.text = contactModel.name
        binding.infoMail.setInfoValue(contactModel.email)
        binding.infoFacebook.setInfoValue(contactModel.facebook)
        binding.infoPhone.setInfoValue(contactModel.phoneNumber)

        Glide.with(this).load(contactModel.contactImage).into(binding.contactImage)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("profile", profile)
    }
}