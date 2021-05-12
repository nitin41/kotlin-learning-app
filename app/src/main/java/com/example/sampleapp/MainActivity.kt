package com.example.sampleapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sampleapp.APIServices.ApiFunctions
import com.example.sampleapp.APIServices.apis
import com.example.sampleapp.model.User
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val activity = this@MainActivity
    private lateinit var myApi:apis
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var regButton: Button
    private val Name = "AComputerEngineer"
    private val TOKEN_KEY = "token_key"
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var token:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()
        sharedPreferences = getSharedPreferences(Name, Context.MODE_PRIVATE)
        token = sharedPreferences.getString(TOKEN_KEY, null).toString()
        val retrofit = ApiFunctions.instances
        myApi = retrofit.create(apis::class.java)
        initialViews()
    }

    private fun initialViews () {
        email = findViewById<EditText>(R.id.login_email) as EditText
        password = findViewById<EditText>(R.id.login_pass) as EditText
        loginButton = findViewById<Button>(R.id.login_button) as Button
        regButton = findViewById<Button>(R.id.reg_button) as Button

        loginButton.setOnClickListener() {
            val eml = email.text.toString().trim()
            val pas = password.text.toString().trim()
            if (eml.isEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(eml).matches()) {
                Toast.makeText(this@MainActivity, "Invalid email ID!", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if(pas.isEmpty()){
                Toast.makeText(this@MainActivity, "Password should not be empty!", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            myApi.login(eml, pas).enqueue(object : Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.code() == 200) {
                        val body = response.body()?.string()
                        val responseObj = JSONObject(body)
                        val token = responseObj.get("token").toString()
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString(TOKEN_KEY, token)
                        editor.apply();
                        val accountsIntent = Intent(activity, UserLists::class.java)
                        startActivity(accountsIntent)
                        finish();
                    }
                    else{
                        Toast.makeText(this@MainActivity, "Login failed!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            })
        }

        regButton!!.setOnClickListener() {
            val accountsIntent = Intent(activity, UserSingle::class.java)
            startActivity(accountsIntent)
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d("Token", "Token ${token}")
        if (token !== "null"){
            val i = Intent(this@MainActivity, UserLists::class.java)
            startActivity(i)
        }

    }
}