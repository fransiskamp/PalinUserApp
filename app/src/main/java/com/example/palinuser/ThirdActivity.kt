package com.example.palinuser

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdActivity : AppCompatActivity() {
    private lateinit var userAdapter: UserAdapter
    private lateinit var rvUsers: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var tvEmpty: TextView

    private var currentPage = 1
    private var isLoading = false
    private var isLastPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        rvUsers = findViewById(R.id.rvUsers)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        tvEmpty = findViewById(R.id.tvEmpty)

        userAdapter = UserAdapter(mutableListOf()) { user ->
            SecondActivity.selectedUser = "${user.first_name} ${user.last_name}"
            finish()
        }

        rvUsers.layoutManager = LinearLayoutManager(this)
        rvUsers.adapter = userAdapter

        loadUsers(currentPage)

        swipeRefresh.setOnRefreshListener {
            userAdapter.clear()
            currentPage = 1
            isLastPage = false
            loadUsers(currentPage)
        }

        rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                if (!rv.canScrollVertically(1) && !isLoading && !isLastPage) {
                    currentPage++
                    loadUsers(currentPage)
                }
            }
        })

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }

    private fun loadUsers(page: Int) {
        isLoading = true
        ApiService.instance.getUsers(page).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                swipeRefresh.isRefreshing = false
                isLoading = false

                if (response.isSuccessful) {
                    val users = response.body()?.data ?: emptyList()
                    if (users.isEmpty()) {
                        isLastPage = true
                        tvEmpty.visibility = View.VISIBLE
                    } else {
                        tvEmpty.visibility = View.GONE
                        userAdapter.addUsers(users)
                    }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                swipeRefresh.isRefreshing = false
                isLoading = false
                Toast.makeText(this@ThirdActivity, "Failed to load users", Toast.LENGTH_SHORT).show()
            }
        })
    }
}