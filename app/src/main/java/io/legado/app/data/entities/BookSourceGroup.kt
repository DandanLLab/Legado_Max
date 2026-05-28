package io.legado.app.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import io.legado.app.help.book.isLocal
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "book_source_groups",
    indices = [Index(value = ["name", "author"], unique = true)]
)
data class BookSourceGroup(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(defaultValue = "0")
    var groupId: Long = 0,
    @ColumnInfo(defaultValue = "")
    var name: String = "",
    @ColumnInfo(defaultValue = "")
    var author: String = "",
    var coverUrl: String? = null,
    var intro: String? = null,
    @ColumnInfo(defaultValue = "0")
    var group: Long = 0,
    @ColumnInfo(defaultValue = "0")
    var bestBookUrl: String? = null,
    @ColumnInfo(defaultValue = "0")
    var bestTotalChapterNum: Int = 0,
    var bestLatestChapterTitle: String? = null,
    @ColumnInfo(defaultValue = "0")
    var bestLatestChapterTime: Long = 0,
    @ColumnInfo(defaultValue = "0")
    var sourceCount: Int = 0,
    @ColumnInfo(defaultValue = "0")
    var activeSourceCount: Int = 0
) : Parcelable {

    fun upBestFromBooks(books: List<Book>) {
        sourceCount = books.size
        activeSourceCount = books.count { !it.isLocal && it.canUpdate }
        val best = books.maxByOrNull { it.totalChapterNum }
        if (best != null) {
            if (bestTotalChapterNum <= best.totalChapterNum) {
                bestBookUrl = best.bookUrl
                bestTotalChapterNum = best.totalChapterNum
                bestLatestChapterTitle = best.latestChapterTitle
                bestLatestChapterTime = best.latestChapterTime
            }
            if (coverUrl.isNullOrBlank()) {
                coverUrl = best.coverUrl
            }
            if (intro.isNullOrBlank()) {
                intro = best.intro
            }
        }
    }
}
