package com.niubo.mynote.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.niubo.mynote.R;
import com.niubo.mynote.entities.Note;

import java.util.List;

//Several different classes work together to build your dynamic list.
//RecyclerView
//RecyclerView.ViewHolder
//RecyclerView.Adapter
//LayoutManager

/**
 * The RecyclerView requests those views, and binds the views to their data,
 * by calling methods in the adapter. You define the adapter by extending RecyclerView.Adapter.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private List<Note> notes;

    public NotesAdapter(List<Note> notes) {
        this.notes = notes;
    }

    //When you define your adapter, you need to override three key methods:
    //onCreateViewHolder()
    //onBindViewHolder()
    //getItemCount()

    /**
     * onCreateViewHolder(): RecyclerView calls this method whenever it needs to create a new ViewHolder.
     * The method creates and initializes the ViewHolder and its associated View,
     * but does not fill in the view's contents—the ViewHolder has not yet been bound to specific data.
     */

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // the constructor of the view holder finds three TextViews from given View.
        return new NoteViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_note,
                        parent,
                        false
                )
        );
    }

    /**
     * onBindViewHolder(): RecyclerView calls this method to associate a ViewHolder with data.
     * The method fetches the appropriate data and uses the data to fill in the view holder's layout.
     * For example, if the RecyclerView dislays a list of names,
     * the method might find the appropriate name in the list and fill in the view holder's TextView widget.
     */

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.NoteViewHolder holder, int position) {
        // fill these three textViews with given note object
        holder.setNote(notes.get(position));
    }

    /**
     * getItemCount(): RecyclerView calls this method to get the size of the data set.
     * For example, in an address book app, this might be the total number of addresses.
     * RecyclerView uses this to determine when there are no more items that can be displayed.
     */

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * Each individual element in the list is defined by a view holder object.
     * When the view holder is created, it doesn't have any data associated with it.
     * After the view holder is created, the RecyclerView binds it to its data.
     * You define the view holder by extending RecyclerView.ViewHolder.
     */

    static class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle, textSubtitle, textDateTime;

        // the constructor of the view holder, find three TextViews from given View.
        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textSubtitle = itemView.findViewById(R.id.textSubtitle);
            textDateTime = itemView.findViewById(R.id.textDateTime);
        }

        // fill these three textViews with given note object
        void setNote(Note note) {
            textTitle.setText(note.getTitle());
            if (note.getSubtitle().trim().isEmpty()) {
                textSubtitle.setVisibility(View.GONE);
            } else {
                textSubtitle.setText(note.getSubtitle());
            }
            textDateTime.setText(note.getDateTime());
        }

    }
}
