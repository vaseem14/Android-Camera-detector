package com.example.vaseem.Daimon;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button button;
    boolean a;
    Switch aSwitch;
    private CameraManager cameraManager;
    String cameraId;
    public void openDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("WARNING!!");
        builder.setMessage("Camera is Running!");
        builder.setNegativeButton("Cancel",null);
        builder.setIcon(R.drawable.icon);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    public void open(){
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("WARNING!!");
        builder.setMessage("Camera is Not Running!");
        builder.setNegativeButton("Cancel",null);
        builder.setIcon(R.drawable.icon);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    public void cam(){
        if (a==true){
            Toast.makeText(MainActivity.this, " camera running", Toast.LENGTH_SHORT).show();

        }

    }



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
        aSwitch=findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (aSwitch.isChecked()){
                    CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                    Handler yourHandler = new Handler();

                    manager.registerAvailabilityCallback(new CameraManager.AvailabilityCallback() {
                        @Override
                        public void onCameraAvailable(String cameraId) {
                            super.onCameraAvailable(cameraId);
                            a=false;

                        }

                        @Override
                        public void onCameraUnavailable(String cameraId) {
                            super.onCameraUnavailable(cameraId);
                            a=true;
                            cam();


                        }
                    },yourHandler);


                }
                else{
                    aSwitch.setChecked(!aSwitch.isChecked());

                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a==true){
                    openDialog();

                }else{
                    open();
                }

            }
        });



    }

}