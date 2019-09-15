package com.gssirohi.techticz.voicebook.ui.voicememo

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techticz.app.R
import com.techticz.data.model.VoiceMemo
import kotlinx.android.synthetic.main.empty_recent_memo_card.view.*


class VoiceMemoAdapter(val listener:MemoListner,val viewMode:Int): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val memoList = mutableListOf<VoiceMemo>()

    val spanSizeLookup: GridLayoutManager.SpanSizeLookup by lazy {
        object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return 2
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(memoList[position].id){
            -1 -> 1
            else-> 2

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 1){
            var view = View.inflate(parent.context, R.layout.empty_recent_memo_card,null)
            return EmptyMemoViewHolder(view,listener)
        } else {
            var view = VoiceMemoCard(parent.context)
            view.setCardViewMode(viewMode)
            return MemoViewHolder(view,listener)
        }

    }


    override fun getItemCount(): Int {
        return memoList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when{
            holder is MemoViewHolder->{
                holder.bindData(memoList.get(position),position)
            }

            holder is EmptyMemoViewHolder->{
                holder.bindData(memoList[position],position)
            }
        }
    }

    fun setData(memoList: List<VoiceMemo>) {
        this.memoList.clear()
        this.memoList.addAll(memoList)
        notifyDataSetChanged()
    }


    class MemoViewHolder(val view: View, val listner:MemoListner):RecyclerView.ViewHolder(view){

        init {

        }

        fun bindData(memo: VoiceMemo, position: Int){
            (view as VoiceMemoCard).bindMemo(memo,object:MemoAudioPlayer.PlayerListner{
                override fun playClicked() {
                    listner?.onPlayClicked(memo,position)
                }
            },object:VoiceMemoCard.MemoActionListener{
                override fun onDeleteClicked(memo: VoiceMemo) {
                    listner?.onMemoDeleteClicked(memo)
                }

                override fun onShareClicked(memo: VoiceMemo) {
                    listner?.onMemoShareClicked(memo,position)
                }

            })
        }

    }

    class EmptyMemoViewHolder(val view: View, val listner:MemoListner):RecyclerView.ViewHolder(view){

        init {

        }

        fun bindData(memo: VoiceMemo, position: Int){
            view.fab_create_first_memo.setOnClickListener{
                listner?.onCreateDefaultMemo()
            }
        }

    }

    interface MemoListner{
        fun onMemoDeleteClicked(memo:VoiceMemo)
        fun onMemoShareClicked(memo: VoiceMemo, position: Int)
        fun onPlayClicked(memo:VoiceMemo,position: Int)
        fun onCreateDefaultMemo()
    }


}