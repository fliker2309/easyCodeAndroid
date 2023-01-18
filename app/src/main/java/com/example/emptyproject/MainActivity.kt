package com.example.emptyproject

import android.os.Bundle
import android.text.Editable
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.example.emptyproject.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var imageUrl: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        imageUrl = savedInstanceState?.getString(KEY_IMAGE_URL) ?: imagesList[
            Random.nextInt(
                imagesList.size
            )
        ]
        binding.mainIV.load(imageUrl)

        binding.textInputEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                val valid = android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()
                binding.textInputLayout.isErrorEnabled = !valid
                val error = if (valid) "" else getString(R.string.invalid_email_message)
                binding.textInputLayout.error = error
                if (valid) Toast.makeText(
                    this@MainActivity,
                    R.string.valid_email_message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_IMAGE_URL, imageUrl)
    }

    companion object {

        const val KEY_IMAGE_URL = "KEY_IMAGE_URL"
        val imagesList = listOf(
            "https://psyfactor.org/lib/i/xrorschach_test.jpg.pagespeed.ic.2RPXdTKY0r.webp",
            "https://psyfactor.org/lib/i/xrorschach_test_1.jpg.pagespeed.ic.giOoaM8WNN.webp",
            "https://psyfactor.org/lib/i/xrorschach_test_2.jpg.pagespeed.ic.SjRMzAv44v.webp",
            "https://psyfactor.org/lib/i/xrorschach_test_4.jpg.pagespeed.ic.c8Xn8M-s1g.webp"
        )
    }

    fun ImageView.load(url: String) {
        Glide.with(this@MainActivity)
            .load(url)
            .into(this)
    }
}
