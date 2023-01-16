package com.example.emptyproject

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

private const val URL =
    "htt[s://zavistnik.com/wp-content/uploads/2020/03/Android-kursy-zastavka.jpg"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val image = findViewById<ImageView>(R.id.main_IV)
        val netImage = NetImage(
            URL,
            object : ImageCallback {
                override fun success(bitmap: Bitmap) = runOnUiThread{
                    image.setImageBitmap(bitmap)
                }

                override fun failed() {
                    Snackbar.make(image, "failed", Snackbar.LENGTH_SHORT).show()
                }
            }
        )
        netImage.start()
    }
}
