package com.example.soundboard

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category ORDER BY id DESC")
    fun getOrderedCategories(): LiveData<List<Category>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(category: Category)

    @Query("DELETE FROM category")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(category: Category)
}