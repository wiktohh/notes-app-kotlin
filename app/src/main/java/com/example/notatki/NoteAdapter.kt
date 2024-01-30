package com.example.notatki

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.notatki.data.Note
import com.example.notatki.databinding.NoteRowBinding
import kotlinx.coroutines.flow.Flow

class NoteAdapter(private val listener:IAdaptor) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val allNotes = ArrayList<Note>()

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleView : TextView = itemView.findViewById(R.id.titleNote)
        val descriptionView : TextView = itemView.findViewById(R.id.descriptionNote)
        val deleteButton : ImageView = itemView.findViewById(R.id.deleteButton)
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
    }

    fun updateData(newData: List<Note>){
        allNotes.clear()
        allNotes.addAll(newData)
        notifyDataSetChanged()
    }
}

interface IAdaptor{
    fun onNoteClick(note:Note)
}
