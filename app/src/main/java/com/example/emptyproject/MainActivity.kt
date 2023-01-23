package com.example.emptyproject

import android.os.Bundle
import android.text.Editable
import android.util.Patterns.EMAIL_ADDRESS
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.example.emptyproject.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
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
        listener()

        binding.textInputEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                super.onTextChanged(s, p1, p2, p3)
                binding.loginButton.isEnabled = true
                binding.textInputLayout.isErrorEnabled = false
            }

            override fun afterTextChanged(s: Editable?) {
                val input = s.toString()
                if (input.endsWith("@g")) {
                    val fullMail = "${input}mail.com"
                    binding.textInputEditText.setText(fullMail)
                }
            }
        })
    }

    private fun listener() {
        binding.loginButton.isEnabled = false
        binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            binding.loginButton.isEnabled = isChecked
        }
        binding.loginButton.setOnClickListener {
            if (EMAIL_ADDRESS.matcher(binding.textInputEditText.text.toString()).matches()) {
                binding.loginButton.isEnabled = false
                Snackbar.make(binding.loginButton, "Go to postLogin", Snackbar.LENGTH_SHORT).show()
            } else {
                binding.textInputLayout.isErrorEnabled = true
                binding.textInputLayout.error = getString(R.string.invalid_email_message)
            }
        }
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

    private fun ImageView.load(url: String) {
        Glide.with(this@MainActivity)
            .load(url)
            .into(this)
    }
}
