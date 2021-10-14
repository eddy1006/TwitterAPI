package com.example.twitterapplication

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_show_tweets.*
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class ShowTweets : AppCompatActivity() {
    inner class ShowAllTweets(private val id: String) : AsyncTask<Any, Void, String>() {

        override fun doInBackground(vararg params: Any?): String {
            var response: String = ""
            val connection = URL("https://api.twitter.com/2/users/${id}/tweets").openConnection() as HttpURLConnection
            connection.setRequestProperty(
                "Authorization",
                "Bearer AAAAAAAAAAAAAAAAAAAAAHu1UgEAAAAAmJKhTkBtqiFLNBYImtjrxb%2BXl08%3D6S4ouzXt9105eROwGGCaVdGqE7dNTqXE7OZbVO3QjCuXINObX7"
            )
            response = connection.inputStream.bufferedReader().readText()
            connection.disconnect()
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val json = JSONObject(result!!)
            val type = json.optJSONArray("data")
            var i =0
            val tweetList: ArrayList<TweetResult> = ArrayList()
            while(i<type!!.length() && i<10){
                val tweet = TweetResult(text = type.getJSONObject(i).getString("text"),id = type.getJSONObject(i).getString("id"))
                tweetList.add(tweet)
                i++
            }
            val adapter = TweetAdapter(this@ShowTweets,tweetList)
            recycler.layoutManager = LinearLayoutManager(this@ShowTweets)
            recycler.adapter = adapter
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_tweets)
        val id  = intent.getStringExtra("extra")
        ShowAllTweets(id!!).execute()
    }
}