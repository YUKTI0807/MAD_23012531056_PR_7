package com.personal.mad_23012531056_pr_7

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.personal.mad_23012531056_pr_7.db.DatabaseHelper
import java.io.Serializable

class PersonAdapter(
    private val context: MainActivity,
    private val array: ArrayList<Person>,
    private val db: DatabaseHelper
) : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    inner class PersonViewHolder(val bindingView: View) : RecyclerView.ViewHolder(bindingView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val bindingView = LayoutInflater.from(parent.context).inflate(R.layout.person_items_view, parent, false)
        return PersonViewHolder(bindingView)
    }

    override fun getItemCount(): Int = array.size

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        with(holder) {
            with(array[position]) {
                bindingView.findViewById<TextView>(R.id.person_name).text = this.name
                bindingView.findViewById<TextView>(R.id.person_email).text = this.email
                bindingView.findViewById<TextView>(R.id.person_phone).text = this.phone
                bindingView.findViewById<TextView>(R.id.person_address).text = this.address
                val obj = this as Serializable
                bindingView.findViewById<ImageButton>(R.id.delete_button).setOnClickListener {
                    context.deletePerson(holder.adapterPosition)
                }
            }
        }
    }

    // helper to refresh list
    fun updateList(newList: List<Person>) {
        array.clear()
        array.addAll(newList)
        notifyDataSetChanged()
    }
}
