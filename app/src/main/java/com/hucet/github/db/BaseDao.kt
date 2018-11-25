package com.hucet.github.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg items: T): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(items: List<T>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(obj: List<T>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg obj: T)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun createRepoIfNotExists(obj: T): Long
}