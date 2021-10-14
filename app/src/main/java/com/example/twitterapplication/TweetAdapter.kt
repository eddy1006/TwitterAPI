package com.example.twitterapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class  TweetAdapter(private val context: Context, private val list:ArrayList<TweetResult>): RecyclerView.Adapter<TweetAdapter.TweetViewHolder>(){

    inner class TweetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tweet_id: TextView = itemView.findViewById(R.id.textView2)
        val tweet_text: TextView = itemView.findViewById(R.id.textView3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
        val viewHolder = TweetViewHolder(LayoutInflater.from(context).inflate(R.layout.tweets,parent,false))
        return  viewHolder
    }

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
       holder.tweet_id.text = "tweet_id: "+list[position].id
        holder.tweet_text.text = "Text: "+list[position].text
    }

    override fun getItemCount(): Int {
        return list.size
    }

}