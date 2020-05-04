package com.example.soundboard

import android.media.MediaRecorder
import android.nfc.Tag
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.View
import java.io.File
import java.lang.Exception

class SoundBiteRecorder internal constructor(private var mediaRecorder: MediaRecorder) {

    var isRecording: Boolean = false

    fun start(audioPath: String) {
        if (isRecording) {
            return
        }
        mediaRecorder = MediaRecorder()
        mediaRecorder.setOnErrorListener { mediaRecorder, messageCode, extraCode ->
            mediaRecorderErrorHandler(mediaRecorder, messageCode, extraCode) }
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
        mediaRecorder.setOutputFile(audioPath)
        mediaRecorder.prepare()
        mediaRecorder.start()
        isRecording = true
    }

    fun stop() {
        if (!isRecording) {
            return
        }
        try {
            mediaRecorder.stop()
            mediaRecorder.release()
        } catch (e: Exception) {
            Log.d("Media recorder stop", e.message)
        }

        isRecording = false
    }

    fun mediaRecorderErrorHandler(mediaRecorder: MediaRecorder, messageCode: Int, extraCode: Int): MediaRecorder.OnErrorListener {
        // TODO: Figure out error handler
        return MediaRecorder.OnErrorListener { mediaRecorder, messageCode, extraCode -> }
    }


}