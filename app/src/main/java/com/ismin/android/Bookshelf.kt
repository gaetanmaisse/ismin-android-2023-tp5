package com.ismin.android

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

class Bookshelf {

    private val storage = HashMap<String, Book>()

    fun addBook(book: Book) {
        storage[book.isbn] = book
    }

    fun addBooks(books : List<Book>){
        books.forEach{book -> addBook(book)}
    }

    fun getBook(isbn: String): Book {
        val book = storage[isbn]
        if (book == null) {
            throw IllegalArgumentException("Unknown isbn")
        }
        return book
    }

    fun getAllBooks(): ArrayList<Book> {
        return  ArrayList(storage.values
            .sortedBy { book -> book.title })
    }

    fun getBooksOf(author: String): List<Book> {
        return storage.filterValues { book -> book.author.equals(author) }
            .values
            .sortedBy { book -> book.title }
    }

    fun getTotalNumberOfBooks(): Int {
        return storage.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getBooksPublishedBefore(date: LocalDate): List<Book> {
        return storage.filterValues { LocalDate.parse(it.date).isBefore(date) }.values.sortedBy { it.title }
    }

    fun clear() {
        storage.clear()
    }

}
