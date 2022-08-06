package com.example.tordiceroller

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.tordiceroller.databinding.ActivityMainBinding
import com.example.tordiceroller.main.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.mainViewModel = viewModel
        binding.lifecycleOwner = this

        binding.imageView6d.setOnClickListener { rollDice(6) }
        binding.imageView12d.setOnClickListener { rollDice(12) }

    }

    private fun rollDice(numberOfSides: Int) {
        if (viewModel.canRoll.value!!) {
            viewModel.roll(numberOfSides)
            val resultText = viewModel.resultText.value!!
            createPopupWindow(resultText)
        }
    }

    private fun createPopupWindow(text: String) {
        val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.popup_window, null)

        val popupWindow = PopupWindow(
            view,
            500,
            400
        )

        val popupTextView = view.findViewById<TextView>(R.id.popup_textview)
        val popupButton = view.findViewById<Button>(R.id.popup_button)

        popupTextView.text = text

        popupWindow.showAtLocation(
            inflater.inflate(R.layout.activity_main,null), // Location
            Gravity.CENTER,
            0,
            0
        )

        popupButton.setOnClickListener{
            viewModel.popupWindowClosed()
            popupWindow.dismiss()
        }
    }
}


