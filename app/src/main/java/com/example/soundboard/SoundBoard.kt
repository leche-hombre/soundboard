package com.example.soundboard

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SoundBoard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound_board)

        val btnRoar : Button = findViewById(R.id.btn_roar)
        btnRoar.setOnClickListener {
            val mediaPlayer : MediaPlayer = MediaPlayer.create(this, R.raw.do_the_roar)
            mediaPlayer.start()
        }

        val btnDoIt : Button = findViewById(R.id.btn_do_it)
        btnDoIt.setOnClickListener {
            val mediaPlayer : MediaPlayer = MediaPlayer.create(this, R.raw.do_it)
            mediaPlayer.start()
        }

        val btnILoveYouDaddy : Button = findViewById(R.id.btn_i_love_you_daddy)
        btnILoveYouDaddy.setOnClickListener {
            val mediaPlayer : MediaPlayer = MediaPlayer.create(this, R.raw.i_love_you_daddy)
            mediaPlayer.start()
        }
    }
}
