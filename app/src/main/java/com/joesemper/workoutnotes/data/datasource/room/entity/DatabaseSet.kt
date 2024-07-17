package com.joesemper.workoutnotes.data.datasource.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
//    foreignKeys = [
//        ForeignKey(
//            entity = DatabaseExerciseSet::class,
//            childColumns = ["exerciseSetId"],
//            parentColumns = ["id"]
//        )
//    ]
)

data class DatabaseSet(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo val exerciseId: Long = 0,
    @ColumnInfo val indexNumber: Int = 0,
    @ColumnInfo val weight: Int = 0,
    @ColumnInfo val repetitions: Int = 0,
    @ColumnInfo val sets: Int = 0
)