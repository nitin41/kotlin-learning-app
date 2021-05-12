package com.example.sampleapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.APIServices.ApiFunctions
import com.example.sampleapp.APIServices.apis
import com.example.sampleapp.adapters.UsersRecyclerAdapter
import com.example.sampleapp.model.User
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserLists : AppCompatActivity() {
    private val activity = this@UserLists
    private lateinit var recyclerViewUsers: RecyclerView
    private lateinit var myApi:apis
    private val Name = "AComputerEngineer"
    private val TOKEN_KEY = "token_key"
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var token:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_lists)
        sharedPreferences = getSharedPreferences(Name, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(TOKEN_KEY, null).toString()
        val retrofit = ApiFunctions.instances
        myApi = retrofit.create(apis::class.java)
        initViews()
        initObjects()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_post, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId === R.id.logout_button){
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.clear()

            // below line will apply empty
            // data to shared prefs.

            // below line will apply empty
            // data to shared prefs.
            editor.apply()

            // starting mainactivity after
            // clearing values in shared preferences.

            // starting mainactivity after
            // clearing values in shared preferences.
            val i = Intent(this@UserLists, MainActivity::class.java)
            startActivity(i)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * This method is to initialize views
     */
    private fun initViews() {
        recyclerViewUsers = findViewById<View>(R.id.recyclerViewUsers) as RecyclerView
    }

    /**
     * This method is to initialize objects to be used
     */
    private fun initObjects() {
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerViewUsers.layoutManager = mLayoutManager
        recyclerViewUsers.itemAnimator = DefaultItemAnimator()
        recyclerViewUsers.setHasFixedSize(true)

        myApi.users().enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.code() == 200) {
                    val body = response.body()?.string()
                    val data = JSONObject(body)
                    //creating json array
                    val usersList = data.getJSONArray("data")
                    runOnUiThread {
                        recyclerViewUsers.adapter = UsersRecyclerAdapter(usersList)
                    }
                }
                else{
                    Toast.makeText(activity, "User list fetch failed!", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        })
    }
}

class UsersFeed(val users: List<User>) {
    fun count(): Int {
        Log.d("count", "Count ${users.size}")
        return users.size
    }
};
