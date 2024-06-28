package com.joesemper.workoutnotes.data.datasource.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
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
    @ColumnInfo val exerciseSetId: Long = 0,
    @ColumnInfo val index: Int = 0,
    @ColumnInfo val weight: Int = 0,
    @ColumnInfo val repetitions: Int = 0,
    @ColumnInfo val sets: Int = 0
)