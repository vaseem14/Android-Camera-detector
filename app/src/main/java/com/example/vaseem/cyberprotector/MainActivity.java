package com.example.vaseem.cyberprotector;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Handler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button button;

    private CameraManager cameraManager;
    String cameraId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraManager = (CameraManager)
                getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        button=findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                Handler yourHandler = new Handler();

                manager.registerAvailabilityCallback(new CameraManager.AvailabilityCallback() {
                    @Override
                    public void onCameraAvailable(String cameraId) {
                        super.onCameraAvailable(cameraId);

                    }

                    @Override
                    public void onCameraUnavailable(String cameraId) {
                        super.onCameraUnavailable(cameraId);
                        Toast.makeText(MainActivity.this, " camera running", Toast.LENGTH_SHORT).show();


                    }
                },yourHandler);

            }
        });


    }

}
