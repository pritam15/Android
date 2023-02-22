package com.example.jetnoteapp.data

import com.example.jetnoteapp.model.Notes

class NotesDataSource {

        fun getNotes() : List<Notes>{
            return listOf(
                Notes(title = "A good Day", description = "We went on a Vacation"),
                Notes(title = "Movie Time", description = "watching movie with friends"),
                Notes(title = "Android Compose", description = "Working On Compose Project"),
                Notes(title = "Movie Time", description = "watching movie with family"),
            )
        }
}