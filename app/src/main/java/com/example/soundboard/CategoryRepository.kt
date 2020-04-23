package com.example.soundboard

import androidx.lifecycle.LiveData

class CategoryRepository(private val categoryDao: CategoryDao) {

    val allCategories: LiveData<List<Category>> = this.categoryDao.getOrderedCategories()

    suspend fun insert(category: Category) {
        this.categoryDao.insert(category)
    }

    suspend fun delete(category: Category) {
        this.categoryDao.delete(category)
    }
}