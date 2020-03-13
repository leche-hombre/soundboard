package com.example.soundboard;

import androidx.annotation.ColorInt;

public class RenderFactory {

    public WaveformRenderer createSimpleWaveformRenderer(@ColorInt int foreground, @ColorInt int background) {
        return SimpleWaveformRenderer.newInstance(background, foreground);
    }
}
