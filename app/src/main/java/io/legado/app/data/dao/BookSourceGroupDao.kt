package io.legado.app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.legado.app.data.entities.BookSourceGroup
import kotlinx.coroutines.flow.Flow

@Dao
interface BookSourceGroupDao {

    @Query("select * from book_source_groups where groupId = :groupId")
    fun getByGroupId(groupId: Long): BookSourceGroup?

    @Query("select * from book_source_groups where name = :name and author = :author")
    fun getByNameAuthor(name: String, author: String): BookSourceGroup?

    @Query("select * from book_source_groups where bestBookUrl = :bookUrl")
    fun getByBestBookUrl(bookUrl: String): BookSourceGroup?

    @Query("select * from book_source_groups order by bestLatestChapterTime desc")
    fun flowAll(): Flow<List<BookSourceGroup>>

    @Query("select * from book_source_groups where `group` & :groupId > 0 order by bestLatestChapterTime desc")
    fun flowByGroup(groupId: Long): Flow<List<BookSourceGroup>>

    @Query("select * from book_source_groups order by bestLatestChapterTime desc")
    fun getAll(): List<BookSourceGroup>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg bookSourceGroups: BookSourceGroup)

    @Update
    fun update(vararg bookSourceGroups: BookSourceGroup)

    @Delete
    fun delete(vararg bookSourceGroups: BookSourceGroup)

    @Query("delete from book_source_groups where groupId = :groupId")
    fun deleteByGroupId(groupId: Long)

    @Query("delete from book_source_groups where bestBookUrl not in (select bookUrl from books)")
    fun deleteOrphaned()
}
