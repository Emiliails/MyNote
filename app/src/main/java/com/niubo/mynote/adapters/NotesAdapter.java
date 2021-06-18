package com.niubo.mynote.adapters;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.niubo.mynote.R;
import com.niubo.mynote.entities.Note;

import java.util.List;

//Several different classes work together to build your dynamic list.
//RecyclerView (see in activity_main.xml)
//RecyclerView.ViewHolder
//RecyclerView.Adapter(see in MainActivity.java)
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
        // find the elements of the "card"
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
        // set the textTitle, textSubtitle, textDateTime, and the color of the "card"
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
        LinearLayout layoutNote;
        RoundedImageView imageNote;

        // find the elements of the "card"
        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textSubtitle = itemView.findViewById(R.id.textSubtitle);
            textDateTime = itemView.findViewById(R.id.textDateTime);
            layoutNote = itemView.findViewById(R.id.layoutNote);
            imageNote = itemView.findViewById(R.id.imageNote);

        }

        // set the elements of the "card"
        // for example, textTitle, textSubtitle, textDateTime, the color of the "card"
        void setNote(Note note) {

            // fill these three textViews with given note object
            textTitle.setText(note.getTitle());
            if (note.getSubtitle().trim().isEmpty()) {
                textSubtitle.setVisibility(View.GONE);
            } else {
                textSubtitle.setText(note.getSubtitle());
            }
            textDateTime.setText(note.getDateTime());

            // set the color of background of the layout to the color of the note
            GradientDrawable gradientDrawable = (GradientDrawable) layoutNote.getBackground();
            if (note.getColor() != null) {
                gradientDrawable.setColor(Color.parseColor(note.getColor()));
            } else {
                gradientDrawable.setColor(Color.parseColor("#333333"));
            }

            // add the note image to the card
            if (note.getImagePath() != null) {
                imageNote.setImageBitmap(BitmapFactory.decodeFile(note.getImagePath()));
                imageNote.setVisibility(View.VISIBLE);
                int a = imageNote.getVisibility();
                System.out.println(a);
            } else {
                imageNote.setVisibility(View.GONE);
            }
        }
    }
}
