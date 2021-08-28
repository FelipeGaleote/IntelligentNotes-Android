package com.felipeg.intelligentnotes.annotation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.felipeg.intelligentnotes.R
import com.felipeg.intelligentnotes.annotation.data.model.Annotation
import org.joda.time.format.ISODateTimeFormat
import java.util.*

class MyAnnotationsAdapter(private val annotations: List<Annotation>) : RecyclerView.Adapter<MyAnnotationsAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val noteTitle : TextView = view.findViewById(R.id.note_title)
        val noteContent : TextView = view.findViewById(R.id.note_content)
        val noteCreationDate : TextView = view.findViewById(R.id.note_creation_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_annotation_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.noteTitle.text = annotations[position].title
        holder.noteContent.text = annotations[position].content
        val noteCreationDate = ISODateTimeFormat.dateTimeNoMillis().parseDateTime(annotations[position].creationDate).toLocalDateTime()
        holder.noteCreationDate.text = noteCreationDate.toString("d MMMM yyyy, H:mm", Locale.getDefault())
    }

    override fun getItemCount(): Int {
        return annotations.size
    }
}