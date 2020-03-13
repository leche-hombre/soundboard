package com.example.soundboard

import android.graphics.Color
import android.media.MediaPlayer
import android.media.audiofx.Visualizer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button



class SoundBoard : AppCompatActivity(), Visualizer.OnDataCaptureListener {

    private var waveformView: WaveformView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound_board)

        waveformView = findViewById(R.id.waveform_view)

        val rendererFactory : RenderFactory = RenderFactory()
        waveformView?.setRenderer(rendererFactory.createSimpleWaveformRenderer(Color.GREEN, Color.DKGRAY))

        val btnRoar : Button = findViewById(R.id.btn_roar)
        btnRoar.setOnClickListener {
            val mediaPlayer : MediaPlayer = MediaPlayer.create(this, R.raw.do_the_roar)

            val visualiser : Visualizer = Visualizer(mediaPlayer.audioSessionId)
            visualiser.setDataCaptureListener(this, Visualizer.getMaxCaptureRate(), true, false)
            visualiser.setCaptureSize(256)
            visualiser.enabled = true

            mediaPlayer.start()
        }

//        val btnDoIt : Button = findViewById(R.id.btn_do_it)
//        btnDoIt.setOnClickListener {
//            val mediaPlayer : MediaPlayer = MediaPlayer.create(this, R.raw.do_it)
//            mediaPlayer.start()
//        }
//
//        val btnILoveYouDaddy : Button = findViewById(R.id.btn_i_love_you_daddy)
//        btnILoveYouDaddy.setOnClickListener {
//            val mediaPlayer : MediaPlayer = MediaPlayer.create(this, R.raw.i_love_you_daddy)
//            mediaPlayer.start()
//        }
    }

    override fun onWaveFormDataCapture(visualiser: Visualizer?, waveform: ByteArray?, p2: Int) {
        if (waveformView != null) {
            waveformView?.setWaveform(waveform)
        }
    }

    override fun onFftDataCapture(p0: Visualizer?, p1: ByteArray?, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
