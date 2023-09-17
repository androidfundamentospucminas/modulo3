package com.walker.modulo3projetoicontatos.mainscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.walker.modulo3projetoicontatos.ContactModel
import com.walker.modulo3projetoicontatos.databinding.ContactCardBinding

class ContactsAdapter(
    private var contacts: MutableList<ContactModel>,
    private val glide: RequestManager,
    private var originalContacts: MutableList<ContactModel>,
    private val onInputListener: ContactDetailDialogFragment.OnInputListener
): RecyclerView.Adapter<ContactsAdapter.ViewHolder>(), Filterable {
    private lateinit var binding: ContactCardBinding

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val contactName: TextView = binding.contactName
        val contactRelationship: TextView = binding.contactRelationship
        val contactImage: ImageView = binding.contactImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ContactCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = contacts[position]

        holder.contactName.text = currentItem.name
        holder.contactRelationship.text = currentItem.relationship

        holder.itemView.setOnClickListener {
            onInputListener.openContactDialog(currentItem)
        }

        glide.load(currentItem.contactImage).into(holder.contactImage)

    }

    override fun getFilter(): Filter {
        return customFilter
    }

    private val customFilter: Filter = object : Filter() {
        override fun performFiltering(_searchedValue: CharSequence?): FilterResults {
            val searchedValue: String = _searchedValue.toString()

            var tempList: MutableList<ContactModel>

            if (searchedValue.isEmpty()) {
                //Retornar a lista original
                tempList = originalContacts
            } else {
                val filteredList: MutableList<ContactModel> = ArrayList<ContactModel>()

                for (contact in originalContacts) {
                    if (contact.name.lowercase().contains(searchedValue)) {
                        filteredList.add(contact)
                    }
                }

                tempList = filteredList
            }

            val filterResults = FilterResults()
            filterResults.values = tempList
            return filterResults

        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            contacts = results?.values as MutableList<ContactModel>
            notifyDataSetChanged()
        }

    }

}