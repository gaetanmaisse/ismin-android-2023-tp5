package com.ismin.android

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val SERVER_BASE_URL = "https://bookshelf-gme.cleverapps.io/"

class MainActivity : AppCompatActivity(), BookCreator {

    private val bookshelf = Bookshelf()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(SERVER_BASE_URL)
        .build()

    private val bookService = retrofit.create(BookService::class.java)

    private val floatingActionButton: FloatingActionButton by lazy {
        findViewById(R.id.a_main_btn_create_book)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bookService.getAllBooks()
            .enqueue(object : Callback<List<Book>> {
                override fun onResponse(
                    call: Call<List<Book>>,
                    response: Response<List<Book>>
                ) {
                    val allBooks: List<Book> = response.body()!!
                    bookshelf.addBooks(allBooks)
                    displayListFragment()
                }

                override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })

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
        bookService.createBook(book)
            .enqueue {
                onResponse = {
                    val bookFromServer: Book? = it.body()
                    bookshelf.addBook(bookFromServer!!)
                    displayListFragment()
                }

                onFailure = {
                    Toast.makeText(this@MainActivity, it?.message, Toast.LENGTH_SHORT).show()
                }
            }

        displayListFragment()
    }
}