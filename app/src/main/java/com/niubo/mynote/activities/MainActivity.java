package com.niubo.mynote.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.niubo.mynote.R;
import com.niubo.mynote.adapters.NotesAdapter;
import com.niubo.mynote.database.NotesDatabase;
import com.niubo.mynote.entities.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD_NOTE = 1;

    // RecyclerView, to display sets of notes link in the main layout.
    private RecyclerView notesRecyclerView;

    //sets of notes to show in the recyclerview
    private List<Note> noteList;

    // The RecyclerView requests those views, and binds the views to their data, by calling methods in the adapter.
    private NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageAddNoteMain = findViewById(R.id.imageAddNoteMain);

        imageAddNoteMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        new Intent(getApplicationContext(), CreateNoteActivity.class),
                        REQUEST_CODE_ADD_NOTE
                );
            }
        });

        notesRecyclerView = findViewById(R.id.notesRecyclerView);

        /*
        The layout manager arranges the individual elements in your list.
        You can use one of the layout managers provided by the RecyclerView library, or you can define your own.
        Layout managers are all based on the library's LayoutManager abstract class.
         */
        notesRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );

        noteList = new ArrayList<>();
        notesAdapter = new NotesAdapter(noteList);
        notesRecyclerView.setAdapter(notesAdapter);

        // fetch all notes from database to logcat when this activity launched.
        getNotes();

    }


    private void getNotes() {
        class GetNotesTask extends AsyncTask<Void, Void, List<Note>> {

            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NotesDatabase.getNotesDatabase(getApplicationContext())
                        .noteDao().getAllNotes();
            }

            /*
             * We checked if the note is empty it means the app is just started since we have
             * declared it as a global variable, in this case, we are adding all notes from the
             * database to this note list and notify the adapter about the new dataset. In another
             * case, if the note list is note empty then it means notes are already loaded from
             * the database so we are just adding only the latest note to the note list and notify
             * adapter about new note inserted. And last we scrolled our recycler view to the top.
             */

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);

//                Log.d("MY_NOTES", notes.toString());

                if (noteList.size() == 0) {
                    noteList.addAll(notes);
                    notesAdapter.notifyDataSetChanged();
                } else {
                    noteList.add(0, notes.get(0));
                    notesAdapter.notifyItemInserted(0);
                }
                notesRecyclerView.smoothScrollToPosition(0);
            }
        }
        new GetNotesTask().execute();
    }

    /**
     * Since we have started "CreateNoteActivity" for the result, we need to
     * handle the result in "onActivityResult" method to update the note list
     * after adding a note from "CreateNoteActivity"
     *
     * */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK) {
            getNotes();
        }
    }
}