package com.ismin.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val ARG_BOOKS = "param1"

class BookListFragment : Fragment() {
    private var books: ArrayList<Book> = arrayListOf()
    private lateinit var bookAdapter: BookAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            books = it.getSerializable(ARG_BOOKS) as ArrayList<Book>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_book_list, container, false)

        recyclerView = view.findViewById(R.id.f_book_list_rcv_books)
        bookAdapter = BookAdapter(books)
        recyclerView.adapter = bookAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(books: ArrayList<Book>) =
            BookListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_BOOKS, books)
                }
            }
    }
}