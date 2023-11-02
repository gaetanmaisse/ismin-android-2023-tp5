package com.ismin.android

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), BookCreator {

    private val bookshelf = Bookshelf()

    private val floatingActionButton: FloatingActionButton by lazy {
        findViewById(R.id.a_main_btn_create_book)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()

        displayListFragment()

        floatingActionButton.setOnClickListener {
            displayCreateBookFragment()
        }
    }

    private fun displayListFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.a_main_lyt_fragment,
            BookListFragment.newInstance(bookshelf.getAllBooks())
        )
        transaction.commit()
        floatingActionButton.visibility = View.VISIBLE
    }

    private fun displayCreateBookFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.a_main_lyt_fragment,
            CreateBookFragment()
        )
        transaction.commit()
        floatingActionButton.visibility = View.GONE
    }

    private fun initData() {
        bookshelf.addBook(
            Book(
                "978-2253004226",
                "Le meilleur des mondes",
                "Aldous Huxley",
                "1932-01-01"
            )
        )
        bookshelf.addBook(Book("978-2070413119", "1984", "George Orwell", "1949-06-08"))
        bookshelf.addBook(Book("978-2070368229", "Fahrenheit 451", "Ray Bradbury", "1953-10-01"))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                bookshelf.clear()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBookCreated(book: Book) {
        bookshelf.addBook(book)
        displayListFragment()
    }
}