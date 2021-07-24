package ru.tregubowww.andersen_home_work_3

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.net.URL

class LoadPictureActivity : AppCompatActivity() {

    private lateinit var buttonLibraryLoading: Button
    private lateinit var buttonStandardLoading: Button
    private lateinit var editText: EditText
    private lateinit var imageView: ImageView

    private var text: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_picture)

        initView()
        setOnClickListenerButtonLibraryLoading()
        setOnClickListenerButtonStandardLoading()
    }

    private fun setOnClickListenerButtonStandardLoading() {
        buttonStandardLoading.setOnClickListener {
            text = editText.text.toString()

            val mUiHandler = Handler()
            val myThread = Thread {
                try {
                    val url = URL(text)
                        .openConnection()
                        .getInputStream()
                    val bmp = BitmapFactory.decodeStream(url)
                    mUiHandler.post {
                        imageView.setImageBitmap(bmp)
                    }
                } catch (e: Exception) {
                    mUiHandler.post {
                        Toast.makeText(this, ERROR_MESSAGE, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            myThread.start()
        }
    }

    private fun setOnClickListenerButtonLibraryLoading() {
        buttonLibraryLoading.setOnClickListener {
            text = editText.text.toString()

            Glide.with(this)
                .load(text)
                .listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Toast.makeText(this@LoadPictureActivity, ERROR_MESSAGE, Toast.LENGTH_SHORT).show()
                        return false;
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                })
                .into(imageView)
        }
    }

    private fun initView() {
        buttonLibraryLoading = findViewById(R.id.buttonLibraryLoading)
        buttonStandardLoading = findViewById(R.id.buttonStandardLoading)
        editText = findViewById(R.id.editText)
        imageView = findViewById(R.id.imageView)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(KEY_EDIT_TEXT_EXTRA, text)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        text = savedInstanceState.getString(KEY_EDIT_TEXT_EXTRA)
        Glide.with(this)
            .load(text)
            .onlyRetrieveFromCache(true)
            .into(imageView);
        editText.setText(text)
        super.onRestoreInstanceState(savedInstanceState)
    }

    companion object {
        private const val KEY_EDIT_TEXT_EXTRA = "KEY_EDIT_TEXT"
        private const val ERROR_MESSAGE = "Неправильный адрес.Исправьте и повторите попытку"
    }
}