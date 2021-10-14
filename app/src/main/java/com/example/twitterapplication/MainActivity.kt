package com.example.twitterapplication

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    inner class TwitterTask(private val name: String) : AsyncTask<Any, Void, String>() {

        override fun doInBackground(vararg params: Any?): String {
            var response: String = ""
            val connection = URL("https://api.twitter.com/2/users/by/username/${name}").openConnection() as HttpURLConnection
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
            val temp = json.optJSONObject("data")
            val intent = Intent(this@MainActivity,ShowTweets::class.java)
            intent.putExtra("extra",temp!!.optString("id"))
            startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            val name = editTextTextPersonName.text.toString()
            if (name.isNotEmpty()) {
                TwitterTask(name).execute()
            } else {
                Toast.makeText(this, "Please enter a valid user name ", Toast.LENGTH_SHORT).show()
            }
        }
    }
}