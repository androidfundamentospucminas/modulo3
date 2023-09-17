package com.walker.modulo3projetoicontatos.mainscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import com.walker.modulo3projetoicontatos.ContactModel
import com.walker.modulo3projetoicontatos.databinding.FragmentContactsBinding


class ContactsFragment : Fragment() {

    private lateinit var binding: FragmentContactsBinding
    private lateinit var adapter: ContactsAdapter

    private var contacts: MutableList<ContactModel> = mutableListOf(
        ContactModel(
            0,
            "Paula",
            "Irmão/Irmã",
            "9599999999",
            email = "mail@gmail.com",
            contactImage = "https://randomuser.me/api/portraits/women/23.jpg",
            instagram = "instagram"
        ),
        ContactModel(
            1,
            "Ricardo",
            "Pai/Mãe",
            "9599999999",
            email = "mail@gmail.com",
            contactImage = "https://randomuser.me/api/portraits/men/54.jpg"
        ),
        ContactModel(
            2,
            "Patricia",
            "Pai/Mãe",
            "9599999999",
            email = "mail@gmail.com",
            contactImage = "https://randomuser.me/api/portraits/women/33.jpg"
        ),
        ContactModel(
            3,
            "Joaquim",
            "Amigo",
            "9599999999",
            email = "mail@gmail.com",
            contactImage = "https://randomuser.me/api/portraits/men/14.jpg"
        )
    )

    private lateinit var originalContacts: MutableList<ContactModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactsBinding.inflate(inflater, container, false)

        originalContacts = contacts

        adapter = ContactsAdapter(contacts, Glide.with(this), originalContacts, activity as ContactDetailDialogFragment.OnInputListener)

        binding.contactsRecyclerView.adapter = adapter
        binding.contactsRecyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })

        binding.floatingAddButton.setOnClickListener {
            (activity as AddEditContactDialogFragment.OnInputListener)
                .openAddContact(originalContacts.size)
        }

        return binding.root
    }

    fun deleteContact(contactModel: ContactModel) {
        contacts.remove(contactModel)
        originalContacts = contacts
        adapter.notifyDataSetChanged()
    }

    fun addToList(contactModel: ContactModel) {
        contacts.add(contactModel)
        originalContacts = contacts
        adapter.notifyDataSetChanged()
    }

    fun updateContact(contactModel: ContactModel) {
        val temp = contacts.update(contactModel.id, contactModel).toMutableList()
        contacts.clear()
        contacts.addAll(temp)
        originalContacts = contacts
        adapter.notifyDataSetChanged()
    }
}

private fun <E> List<E>.update(id: Int, newItem: E) = toMutableList().apply { this[id] = newItem }
