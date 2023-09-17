package com.walker.modulo3projetoicontatos.mainscreen

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.walker.modulo3projetoicontatos.ContactModel
import com.walker.modulo3projetoicontatos.R
import com.walker.modulo3projetoicontatos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ContactDetailDialogFragment.OnInputListener, AddEditContactDialogFragment.OnInputListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        NavigationUI.setupWithNavController(
            binding.bottomNavigationView,
            findNavController(R.id.fragmentContainerView)
        )
    }

    override fun openContactDialog(contactModel: ContactModel) {
        val fragmentManager = supportFragmentManager
        val newFragment = ContactDetailDialogFragment(contactModel, this)
        newFragment.show(fragmentManager, "ContactDetailDialogFragment")
    }

    override fun deleteContact(contactModel: ContactModel) {
        val navHostFragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView)

        val contactsFragment = navHostFragment?.childFragmentManager?.fragments?.find { it is ContactsFragment } as? ContactsFragment

        contactsFragment?.deleteContact(contactModel)

    }

    override fun openMail(link: String?) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:$link")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                this@MainActivity,
                "There is no email client installed",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun dialPhone(phone: String?) {
        val number = Uri.parse("tel:$phone")
        val callIntent = Intent(Intent.ACTION_DIAL, number)
        startActivity(callIntent)
    }

    override fun openInstagram(username: String?) {
        val uri = Uri.parse("https://instagra.com/_u/$username")
        val instagramIntent = Intent(Intent.ACTION_VIEW, uri)

        instagramIntent.setPackage("com.instagram.android")

        try {
            startActivity(instagramIntent)
        } catch (ex: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://instagram.com/$username")
                )
            )
        }

    }

    override fun openEditDialog(contactModel: ContactModel) {
        val fragmentManager = supportFragmentManager
        val newFragment = AddEditContactDialogFragment(contactToEdit = contactModel)
        newFragment.show(fragmentManager, "AddEditContactDialogFragment")
    }

    override fun openAddContact(nextIndex: Int) {
        val fragmentManager = supportFragmentManager
        val newFragment = AddEditContactDialogFragment(nextIndex)
        newFragment.show(fragmentManager, "AddEditContactDialogFragment")
    }

    override fun addContact(contactModel: ContactModel) {
        val navHostFragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView)

        val contactsFragment = navHostFragment?.childFragmentManager?.fragments?.find { it is ContactsFragment } as? ContactsFragment

        contactsFragment?.addToList(contactModel)
    }

    override fun updateContact(contactModel: ContactModel) {
        val navHostFragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView)

        val contactsFragment = navHostFragment?.childFragmentManager?.fragments?.find { it is ContactsFragment } as? ContactsFragment

        contactsFragment?.updateContact(contactModel)

        val contactDetailFragment = navHostFragment?.parentFragmentManager?.fragments?.find { it is ContactDetailDialogFragment } as? ContactDetailDialogFragment

        contactDetailFragment?.updateContact(contactModel)

    }

    override fun updateProfile(contactModel: ContactModel) {
        val navHostFragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView)

        val profileFragment = navHostFragment?.childFragmentManager?.fragments?.find { it is ProfileFragment } as? ProfileFragment

        profileFragment?.updateProfile(contactModel)
    }
}