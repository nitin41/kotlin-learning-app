package com.example.sampleapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.R
import com.example.sampleapp.model.User
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject

class UsersRecyclerAdapter(private val listUsers: JSONArray) :
    RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        // inflating recycler item view
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_recycler, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
            val user = listUsers.get(position)
            val gson = GsonBuilder().create()
            val usersFeed = gson.fromJson(user.toString(), User::class.java)
            holder.textViewId.text = "Id".plus(":- ").plus(usersFeed.id)
            holder.textViewName.text = "Name".plus(":- ").plus(usersFeed.first_name).plus(" ").plus(usersFeed.last_name)
            holder.textViewEmail.text = "Email".plus(":- ").plus(usersFeed.email)
            val avatar = usersFeed.avatar.toString()
            Picasso.get().load(avatar).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return listUsers.length()
    }

    /**
     * ViewHolder class
     */
    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textViewId: TextView
        var textViewName: TextView
        var textViewEmail: TextView
        var imageView: ImageView

        init {
            textViewId = view.findViewById<View>(R.id.textViewId) as TextView
            textViewName = view.findViewById<View>(R.id.textViewName) as TextView
            textViewEmail = view.findViewById<View>(R.id.textViewEmail) as TextView
            imageView = view.findViewById<View>(R.id.imageView) as ImageView
        }
    }
}
