package com.example.lq.myapplication.textureview;

import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;

import com.example.lq.myapplication.R;

import java.io.IOException;

public class TextureDemoActivity extends AppCompatActivity {
    private TextureView textureView;
    private MediaPlayer mediaPlayer;
    private AssetFileDescriptor assetFileDescriptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texture_demo);
        try {
            assetFileDescriptor = getAssets().openFd("aaab.mp4");
        } catch (IOException e) {
            e.printStackTrace();
        }

        textureView = findViewById(R.id.texture);
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                Log.e("texture","texture : onSurfaceTextureAvailable  " + width + "  " + height);
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),assetFileDescriptor.getStartOffset(),assetFileDescriptor.getLength());
                    Surface surface1 = new Surface(surface);
                    mediaPlayer.setSurface(surface1);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.prepareAsync();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mediaPlayer.start();
                        }
                    });
//                    surface.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() {
//                        @Override
//                        public void onFrameAvailable(SurfaceTexture surfaceTexture) {
//                            Log.e("texture","SurfaceTexture: onFrameAvailable");
//                        }
//                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                Log.e("texture","texture : onSurfaceTextureSizeChanged  " + width + "  " + height);
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                Log.e("texture","texture : onSurfaceTextureDestroyed  ");
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
//                Log.e("texture","texture : onSurfaceTextureUpdated  ");

            }
        });
    }
}
