package com.vaseem.vaseem.daimon;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button button;
    boolean a,b;
    Switch aSwitch,bswitch;
    private CameraManager cameraManager;
    String cameraId;
    public void openDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("WARNING!!");
        builder.setMessage("Camera is Running!");
        builder.setNegativeButton("Cancel",null);
        builder.setIcon(R.drawable.icon);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    public void open(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("ALERT");
        builder.setMessage("Camera is Not Running!");
        builder.setNegativeButton("Cancel",null);
        builder.setIcon(R.drawable.icon);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    public void cam(){
        if (a==true&&b==true){
            Toast.makeText(this, " camera running", Toast.LENGTH_SHORT).show();

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
        bswitch=findViewById(R.id.switch2);
        bswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (bswitch.isChecked()){
                    b=true;
                }else{
                    b=false;
                }
            }
        });
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



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Exit");
            builder.setMessage("Close the app?");
            builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);
                }
            });
            builder.setNegativeButton("cancel",null);
            builder.setIcon(R.drawable.icon);
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_share) {
            Intent intent1=new Intent(Intent.ACTION_SEND);
            intent1.setType("text/plain");
            String content="Daimon: An android app to protect your privacy   " +
                    "https://play.google.com/store/apps/details?id=com.vaseem.vaseem.daimon",subject="Daimon";
            intent1.putExtra(Intent.EXTRA_SUBJECT,subject);
            intent1.putExtra(Intent.EXTRA_TEXT,content);
            startActivity(Intent.createChooser(intent1,"Share using"));

        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("mailto:vaseem14n@gmail.com?subject=" );
            intent.setData(data);
            startActivity(intent);


        }else if(id==R.id.nav_about){
            Intent intent=new Intent(this,Main3Activity.class);
            startActivity(intent);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
