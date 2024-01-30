package com.example.notatki

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.notatki.data.Note
import com.example.notatki.databinding.NoteRowBinding
import kotlinx.coroutines.flow.Flow

class NoteAdapter(private val listener:IAdaptor) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var allNotes = ArrayList<Note>()

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleView : TextView = itemView.findViewById(R.id.titleNote)
        val descriptionView : TextView = itemView.findViewById(R.id.descriptionNote)
        val deleteButton : ImageView = itemView.findViewById(R.id.deleteButton)
        val editButton : ImageView = itemView.findViewById(R.id.editButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val viewHolder = NoteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_row,parent,false))
        viewHolder.deleteButton.setOnClickListener{
            listener.onNoteClick(allNotes[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentItem = allNotes[position]
        holder.titleView.text = currentItem.title
        holder.descriptionView.text = currentItem.description
        val backgroundColor = try {
            if (currentItem.category.isNotEmpty()) {
                Color.parseColor(currentItem.category)
            } else {
                Color.parseColor("#FFFFFF") // Domyślny kolor, gdy category jest puste
            }
        } catch (e: IllegalArgumentException) {
            Color.parseColor("#FFFFFF") // Domyślny kolor w przypadku błędu parsowania
        }


        holder.itemView.setBackgroundColor(backgroundColor)
        holder.editButton.setOnClickListener {
            listener.onEditNoteClick(currentItem)
        }
    }

    fun updateData(newData: List<Note>){
        allNotes.clear()
        allNotes.addAll(newData)
        notifyDataSetChanged()
    }
    fun filterData(filteredNotes: List<Note>) {
        allNotes = ArrayList(filteredNotes)
        notifyDataSetChanged()
    }
}

interface IAdaptor{
    fun onNoteClick(note:Note)
    fun onEditNoteClick(note:Note)
}
