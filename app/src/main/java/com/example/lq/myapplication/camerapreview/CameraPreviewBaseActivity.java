package com.example.lq.myapplication.camerapreview;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.lq.myapplication.R;
import com.example.lq.myapplication.gles.CameraUtils;
import com.example.lq.myapplication.gles.FullFrameRect;
import com.example.lq.myapplication.gles.Texture2dProgram;
import com.example.lq.myapplication.utils.ToastUtil2;

import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CameraPreviewBaseActivity extends AppCompatActivity {
    private static final String TAG = CameraPreviewBaseActivity.class.getSimpleName();
    protected GLSurfaceView mSurfaceView;
    protected GLRenderer glRenderer;
    protected float[] mtx = new float[16];
    private int mFuTextureId;
    private Camera mCamera;
    private int cameraWidth = 1280;
    private int cameraHeight = 720;
    private int mCurrentCameraType;
    private byte[] mCameraNV21Byte;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_preview);
        initPreview((GLSurfaceView) findViewById(R.id.gl_view));
    }

    protected void initPreview(GLSurfaceView glSurfaceView) {
        this.mSurfaceView = glSurfaceView;
        mSurfaceView.setEGLContextClientVersion(2);
        glRenderer = new GLRenderer();
        mSurfaceView.setRenderer(glRenderer);
        mSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    class GLRenderer implements GLSurfaceView.Renderer {

        FullFrameRect mFullScreenFUDisplay;
        FullFrameRect mFullScreenCamera;

        public int mCameraTextureId;
        public SurfaceTexture mCameraSurfaceTexture;
        public Texture2dProgram mFullFrameRectTexture2D;

        boolean isFirstOnDrawFrame;

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            Log.e(TAG, "onSurfaceCreated");
            mFullScreenCamera = new FullFrameRect(new Texture2dProgram(
                    Texture2dProgram.ProgramType.TEXTURE_EXT));
            mCameraTextureId = mFullScreenCamera.createTextureObject();
            mCameraSurfaceTexture = new SurfaceTexture(mCameraTextureId);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    handleCameraStartPreview(mCameraSurfaceTexture);
                }
            });
//            mHandler.sendMessage(mHandler.obtainMessage(
//                    CreateLiveShowActivity.MainHandler.HANDLE_CAMERA_START_PREVIEW,
//                    mCameraSurfaceTexture));
            mFullFrameRectTexture2D = new Texture2dProgram(
                    Texture2dProgram.ProgramType.TEXTURE_2D);
            mFullScreenFUDisplay = new FullFrameRect(mFullFrameRectTexture2D);

            isFirstOnDrawFrame = true;
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            Log.e(TAG, "onSurfaceChanged " + width + " " + height);
        }


        @Override
        public void onDrawFrame(GL10 gl) {
            if (isFirstOnDrawFrame) {
                //第一次onDrawFrame并不是由camera内容驱动的
                isFirstOnDrawFrame = false;
                return;
            }

            try {
                mCameraSurfaceTexture.updateTexImage();
                mCameraSurfaceTexture.getTransformMatrix(mtx);
            } catch (Exception e) {
                return;
            }

            if (mCameraNV21Byte == null) {
                mFullScreenCamera.drawFrame(mCameraTextureId, mtx);
                return;
            }

            /**
             * 获取camera数据, 更新到texture
             */



            /**
             * 这里拿到fu处理过后的texture，可以对这个texture做后续操作，如硬编、预览。
             */
            boolean isOESTexture = true; //camera默认的是OES的
//            int flags = isOESTexture ? faceunity.FU_ADM_FLAG_EXTERNAL_OES_TEXTURE : 0;

            mFullScreenCamera.drawFrame(mCameraTextureId, mtx);
            if (mFullScreenFUDisplay == null) {
                return;
            }
            //mFullScreenFUDisplay.drawFrame(fuTex, mtx);
            //mSurfaceView.requestRender();
        }

        public void notifyPause() {
            if (mFullScreenFUDisplay != null) {
                mFullScreenFUDisplay.release(false);
            }

            if (mFullScreenCamera != null) {
                mFullScreenCamera.release(false);
            }

            if (mCameraSurfaceTexture != null) {
                mCameraSurfaceTexture.release();
            }
        }
    }


    private void openCamera(int cameraType, int desiredWidth, int desiredHeight) {

        Log.d(TAG, "openCamera");
        if (mCamera != null) {
            return;
        }

        Camera.CameraInfo info = new Camera.CameraInfo();
        int cameraId = 0;
        int numCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numCameras; i++) {
            Camera.getCameraInfo(i, info);
            if (info.facing == cameraType) {
                cameraId = i;
                mCamera = Camera.open(i);
                mCurrentCameraType = cameraType;
                break;
            }
        }
        if (mCamera == null) {
            for (int i = 0; i < numCameras; i++) {
                Camera.getCameraInfo(i, info);
                if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    cameraId = i;
                    mCamera = Camera.open(i);
                    mCurrentCameraType = Camera.CameraInfo.CAMERA_FACING_BACK;
                    break;
                }
            }
        }

        if (mCamera == null) {
            ToastUtil2.showToast(this, "unable to open camera");
            finish();
        }

        CameraUtils.setCameraDisplayOrientation(this, cameraId, mCamera);

        Camera.Parameters parameters = mCamera.getParameters();
        List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO))
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        mCamera.setDisplayOrientation(90);
        CameraUtils.choosePreviewSize(parameters, desiredWidth, desiredHeight);
        mCamera.setParameters(parameters);

    }

    @Override
    protected void onResume() {
        super.onResume();
        openCamera(Camera.CameraInfo.CAMERA_FACING_FRONT,
                cameraWidth,
                cameraHeight);
        Camera.Size size = mCamera.getParameters().getPreviewSize();
        cameraWidth = size.width;
        cameraHeight = size.height;
        Log.e(TAG, "open camera size " + size.width + " " + size.height);

        mSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSurfaceView.onPause();
    }

    private void handleCameraStartPreview(SurfaceTexture surfaceTexture) {
        if (mCamera == null) {
            return;
        }
        try {
            mCamera.setPreviewCallback(new MyPreviewCallBack());
            mCamera.setPreviewTexture(surfaceTexture);
            surfaceTexture.setOnFrameAvailableListener(new MyOnFrameAvailableListener());
            mCamera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class MyPreviewCallBack implements Camera.PreviewCallback{

        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            mCameraNV21Byte = data;
        }
    }

    class MyOnFrameAvailableListener implements SurfaceTexture.OnFrameAvailableListener{

        @Override
        public void onFrameAvailable(SurfaceTexture surfaceTexture) {
            mSurfaceView.requestRender();
        }
    }



}
