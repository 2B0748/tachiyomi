package tachiyomi.data.di

import android.app.Application
import com.pushtorefresh.storio3.sqlite.StorIOSQLite
import com.pushtorefresh.storio3.sqlite.impl.DefaultStorIOSQLite
import tachiyomi.core.db.DbOpenHelper
import tachiyomi.data.category.resolver.CategoryTypeMapping
import tachiyomi.data.category.table.CategoryTable
import tachiyomi.data.category.table.MangaCategoryTable
import tachiyomi.data.chapter.model.Chapter
import tachiyomi.data.chapter.resolver.ChapterTypeMapping
import tachiyomi.data.chapter.table.ChapterTable
import tachiyomi.data.manga.resolver.MangaTypeMapping
import tachiyomi.data.manga.table.MangaTable
import tachiyomi.domain.category.Category
import tachiyomi.domain.manga.model.Manga
import javax.inject.Inject
import javax.inject.Provider

internal class StorIOProvider @Inject constructor(
  private val context: Application
) : Provider<StorIOSQLite> {

  override fun get(): StorIOSQLite {
    return DefaultStorIOSQLite.builder()
      .sqliteOpenHelper(createDb())
      .addTypeMapping(Manga::class.java, MangaTypeMapping())
      .addTypeMapping(Chapter::class.java, ChapterTypeMapping())
      .addTypeMapping(Category::class.java, CategoryTypeMapping())
      .build()
  }

  private fun createDb(): DbOpenHelper {
    val callbacks = listOf(MangaTable, ChapterTable, CategoryTable, MangaCategoryTable)
    return DbOpenHelper(context, "tachiyomi.db", 1, callbacks)
  }

}