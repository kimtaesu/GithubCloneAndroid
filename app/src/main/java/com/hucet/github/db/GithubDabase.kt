package com.hucet.github.db

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hucet.github.vo.persistance.Repo
import com.hucet.github.vo.persistance.RepoSearchResult

/**
 * Main database description.
 */
@Database(
    entities = [
        Repo::class,
        RepoSearchResult::class
    ],
    version = 1,
    exportSchema = false
)
abstract class GithubDabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao

    companion object {

        private var INSTANCE: GithubDabase? = null

        @Synchronized
        fun getInstance(context: Context): GithubDabase {
            if (INSTANCE == null) {
                INSTANCE = buildDatabase(context)
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        @Synchronized
        fun getInstanceInMemory(context: Context): GithubDabase {
            INSTANCE = Room.inMemoryDatabaseBuilder(context.applicationContext, GithubDabase::class.java)
                    .allowMainThreadQueries()
                    .populate(context)
                    .build()
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): GithubDabase =
                Room.databaseBuilder(context, GithubDabase::class.java, "git_db")
                        .populate(context)
                        .build()
    }
}

private fun <T : RoomDatabase> RoomDatabase.Builder<T>.populate(context: Context): RoomDatabase.Builder<T> = this.addCallback(object : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
    }
})