package ru.tregubowww.andersen_home_work_3

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)
        button.setOnClickListener {
            startActivity(createLoadPictureActivityIntent(this))
        }
    }

    companion object {
        fun createLoadPictureActivityIntent(context: Context) = Intent(context, LoadPictureActivity::class.java)
    }
}