package com.example.dashcamx;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Arrays;

public class RecordFragment extends Fragment {

    private CameraCaptureSession myCameraCaptureSession;
    private String myCameraID;
    private CameraManager myCameraManager;
    private CameraDevice myCameraDevice;
    private TextureView myTextureView;
    private CaptureRequest.Builder myCaptureRequestBuilder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_record, container, false);
        myCameraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
        openCamera();
        return view;
    }

    private CameraDevice.StateCallback myStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            myCameraDevice = camera;
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            myCameraDevice.close();
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            myCameraDevice.close();
            myCameraDevice = null;
        }
    };


    private void openCamera() {
        try {

//            myCameraID = myCameraManager.getCameraIdList()[0];
//
//            if (requestPermissions(Manifest.permission.CAMERA); != PackageManager.PERMISSION_GRANTED) {
//                return;
//            }
//            myCameraManager.openCamera(myCameraID, myStateCallback, null);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void recording(){
        Button recButton = (Button) getView().findViewById(R.id.record_button);
         recButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code that turns on/off recording will go here
                Toast.makeText(getActivity(),
                        "This a toast message",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void cameraPreview(View view) {

        SurfaceTexture mySurfaceTexture = myTextureView.getSurfaceTexture();
        Surface mySurface = new Surface(mySurfaceTexture);

        try {
            myCaptureRequestBuilder = myCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            myCaptureRequestBuilder.addTarget(mySurface);

            myCameraDevice.createCaptureSession(Arrays.asList(mySurface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    myCameraCaptureSession = session;
                    myCaptureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
                    try {
                        myCameraCaptureSession.setRepeatingRequest(myCaptureRequestBuilder.build(), null, null);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {

                }
            }, null);
        }
        catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }
}
