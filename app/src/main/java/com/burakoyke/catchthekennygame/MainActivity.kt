package com.burakoyke.catchthekennygame

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import java.lang.Math.random
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var circularProgressBar: ProgressBar
    private lateinit var progressText: TextView
    private lateinit var imageView: ImageView
    private lateinit var constraintLayout: ConstraintLayout
    private var progressStatus = 0
    private var counter = 0
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        circularProgressBar = findViewById(R.id.circularProgressBar)
        progressText = findViewById(R.id.progressText)
        imageView = findViewById(R.id.imageViewKenny)
        constraintLayout = findViewById(R.id.layout)
        val runnable = object : Runnable {
            override fun run() {
                if (progressStatus <= 100) {
                    progressText.text = "$progressStatus%"
                    circularProgressBar.progress = progressStatus
                    progressStatus += 10
                    setRandomPositionWithoutOverlap()

                    handler.postDelayed(this, 1000)
                }
            }
        }
        handler.post(runnable)
    }
    private fun setRandomPositionWithoutOverlap() {
        val maxWidth = constraintLayout.width - imageView.width
        val maxHeight = constraintLayout.height - imageView.height

        do {
            imageView.x = Random.nextInt(0, maxWidth).toFloat()
            imageView.y = Random.nextInt(0, maxHeight).toFloat()
        } while (isOverlappingWithProgressBar())
    }

    private fun isOverlappingWithProgressBar(): Boolean {
        val imageViewLeft = imageView.x
        val imageViewTop = imageView.y
        val imageViewRight = imageView.x + imageView.width
        val imageViewBottom = imageView.y + imageView.height

        val progressBarLeft = circularProgressBar.x
        val progressBarTop = circularProgressBar.y
        val progressBarRight = circularProgressBar.x + circularProgressBar.width
        val progressBarBottom = circularProgressBar.y + circularProgressBar.height

        return !(imageViewRight < progressBarLeft ||
                imageViewLeft > progressBarRight ||
                imageViewBottom < progressBarTop ||
                imageViewTop > progressBarBottom)
    }

    fun kennyClicked(v : View) {
        counter++
        Toast.makeText(this, "Kenny Clicked: $counter", Toast.LENGTH_SHORT).show()
    }
}
