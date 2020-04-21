package com.example.soundboard

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "categoryName")
    val categoryName: String
    // TODO: Figure out how to make this data structure viable
//    @ColumnInfo(name = "lastModified")
//    val lastModifiedDateTime: DateTime
)