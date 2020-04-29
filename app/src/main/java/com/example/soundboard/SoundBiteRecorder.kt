package com.example.soundboard

import android.media.MediaRecorder
import android.util.Log

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
        mediaRecorder.stop()
        mediaRecorder.release()
        isRecording = false
    }

    fun mediaRecorderErrorHandler(mediaRecorder: MediaRecorder, messageCode: Int, extraCode: Int): MediaRecorder.OnErrorListener {
        // TODO: Figure out error handler
        return MediaRecorder.OnErrorListener { mediaRecorder, messageCode, extraCode -> }
    }


}