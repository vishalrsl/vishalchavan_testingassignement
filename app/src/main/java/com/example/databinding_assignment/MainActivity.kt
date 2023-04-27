package com.example.databinding_assignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.databinding_assignment.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private  var binding: ActivityMainBinding? = null
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding?.viewmodel = mainViewModel
        binding?.lifecycleOwner = this

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}