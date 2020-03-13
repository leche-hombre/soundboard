package com.example.soundboard;

import android.graphics.Canvas;


public interface WaveformRenderer {
    void render(Canvas canvas, byte[] waveform);
}