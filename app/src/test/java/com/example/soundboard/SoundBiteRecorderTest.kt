package com.example.soundboard

import android.media.MediaRecorder
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

private const val AUDIO_FILE_PATH = "/test/"

@RunWith(MockitoJUnitRunner::class)
class SoundBiteRecorderTest {

    @Mock
    private lateinit var mockMediaRecorder: MediaRecorder
    private lateinit var soundBiteRecorder: SoundBiteRecorder

    @Before
    fun init() {
        soundBiteRecorder = SoundBiteRecorder(mockMediaRecorder)
    }

    @Test
    fun soundBiteRecorder_IsRecording_IsTrue() {
        soundBiteRecorder.start(AUDIO_FILE_PATH)
        assertTrue(soundBiteRecorder.isRecording)
    }

    @Test
    fun soundBiteRecorder_IsRecording_IsFalse() {
        assertFalse(soundBiteRecorder.isRecording)
    }

    @Test
    fun soundBiteRecorder_IsRecordingWhenStopped_IsFalse() {
        soundBiteRecorder.start(AUDIO_FILE_PATH)
        soundBiteRecorder.stop()
        assertFalse(soundBiteRecorder.isRecording)
    }
}
