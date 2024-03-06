package com.example.birthdayreminder1.ui


import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.birthdayreminder1.R
import com.example.birthdayreminder1.adapter.BirthdayAdapter
import com.example.birthdayreminder1.db.BirthdayDatabase
import com.example.birthdayreminder1.repository.BirthdayRepository
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar_main)

        val database = BirthdayDatabase(this)
        val repository = BirthdayRepository(database)
        val factory = ViewModelFactory(repository)
        val viewModel = ViewModelProviders.of(this,factory).get(BirthdayViewModel::class.java)

        val adapter = BirthdayAdapter(ArrayList(),viewModel)
        rv_main.adapter = adapter
        rv_main.layoutManager = LinearLayoutManager(this)

        viewModel.getAllBirthdayItems().observe(this, Observer {

            adapter.items = it
            adapter.notifyDataSetChanged()


        })

        fab_main.setOnClickListener {

            val intent = Intent(this,BirthdayCardActivity::class.java)
            startActivity(intent)

        }

    }






}