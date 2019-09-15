package com.gssirohi.techticz.voicebook.ui.voicebook

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techticz.app.R
import com.techticz.app.utils.AppPref
import com.techticz.database.model.VoiceBook
import kotlinx.android.synthetic.main.create_voice_book_card_layout.view.*

import kotlinx.android.synthetic.main.voice_book_card_layout.view.*
import kotlinx.android.synthetic.main.voice_book_card_layout.view.card_book

class VoiceBookAdapter(val listener:BookListner): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val bookList = mutableListOf<VoiceBook>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 1) {
            var view = View.inflate(parent.context, R.layout.create_voice_book_card_layout, null)
            return EmptyViewHolder(view,listener)
        } else {
            var view = View.inflate(parent.context, R.layout.voice_book_card_layout, null)
            return BookViewHolder(view,listener)
        }

    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun getItemViewType(position: Int): Int {
        if(position == 0){
            return 1
        } else {
            return 2
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when{
            holder is BookViewHolder->{
                holder.bindData(bookList.get(position))
            }
            holder is EmptyViewHolder->{
                holder.bindData(bookList.get(position))
            }
        }
    }

    class BookViewHolder(val view: View,val listner:BookListner):RecyclerView.ViewHolder(view){

        init {

        }

        fun bindData(book:VoiceBook){
            view.tv_book_title.text = book.title
            view.tv_memo_count.text = "${book.memoCount} voice memo"
            view.card_book.setCardBackgroundColor(book.color)
            if(book.id == AppPref.getDefaultBookId(view.context)){
                view.tv_default_label.visibility = View.VISIBLE
            } else {
                view.tv_default_label.visibility = View.INVISIBLE
            }
            view.setOnClickListener { listner.onBookClicked(book) }
        }
    }

    class EmptyViewHolder(val view: View,val listner:BookListner):RecyclerView.ViewHolder(view){

        init {

        }

        fun bindData(book:VoiceBook){
            view.fab_create_book.setOnClickListener { listner.onBookClicked(book) }
        }
    }

    fun setData(books:List<VoiceBook>){
        bookList.clear()
        var emptyBook = VoiceBook(-1,"",0,0)
        bookList.add(emptyBook)
        bookList.addAll(books)
        notifyDataSetChanged()
    }

    interface BookListner{
        fun onBookClicked(book:VoiceBook)
    }
}