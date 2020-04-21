package com.example.soundboard

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Category::class], version = 1, exportSchema = false)
public abstract class CategoryRoomDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

    companion object {

        @Volatile
        private var INSTANCE: CategoryRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): CategoryRoomDatabase {
            // This is a singleton, therefore if the instance already exists return it so we don't create a new instance of the db
            val currentInstance = INSTANCE
            if (currentInstance != null) {
                return currentInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CategoryRoomDatabase::class.java,
                    "category_database"
                ).addCallback(CategoryDatabaseCallback(scope)).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class CategoryDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.categoryDao())
                }
            }
        }

        suspend fun populateDatabase(categoryDao: CategoryDao) {
            // Delete all content here.
            categoryDao.deleteAll()

            // Add sample categories.
            var category = Category(0, "Shrek")
            categoryDao.insert(category)
            category = Category(0, "Memes")
            categoryDao.insert(category)
        }
    }
}