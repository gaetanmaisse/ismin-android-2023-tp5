package com.ismin.android

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.lang.IllegalStateException

class CreateBookFragment : Fragment() {


    private lateinit var listener: BookCreator
    private lateinit var edtIsbn: EditText;
    private lateinit var edtTitle: EditText;
    private lateinit var edtAuthor: EditText;
    private lateinit var edtDate: EditText;

    private lateinit var btnSave: Button;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_book, container, false)


        edtIsbn = view.findViewById(R.id.f_create_book_edt_isbn)
        edtTitle = view.findViewById(R.id.f_create_book_edt_title)
        edtAuthor = view.findViewById(R.id.f_create_book_edt_author)
        edtDate = view.findViewById(R.id.f_create_book_edt_date)

        btnSave = view.findViewById(R.id.f_create_book_btn_save)
        btnSave.setOnClickListener {
            val isbn = edtIsbn.text.toString()
            val title = edtTitle.text.toString()
            val author = edtAuthor.text.toString()
            val date = edtDate.text.toString()

            val book = Book(isbn, title, author, date)
            this.listener.onBookCreated(book)
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is BookCreator) {
            this.listener = context;
        } else {
            throw IllegalStateException("$context must implement BookCreator")
        }
    }
}