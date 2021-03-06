package com.home.iamhere;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 * Created by victor on 27.08.16.
 */
public class RequestUserPermission {

    private static final int TAG_CODE_PERMISSION_FINE_LOCATION = 2;
    private static final int TAG_CODE_PERMISSION_COARSE_LOCATION = 3;
    private static final int TAG_CODE_PERMISSION_READ_SMS = 4;
    private static final int TAG_CODE_PERMISSION_READ_PHONE_STATE = 5;
    private static final int TAG_CODE_PERMISSION_READ_CONTACTS = 6;
    private static final int TAG_CODE_PERMISSION_WRITE_CONTACTS = 7;
    private Activity activity;
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_SMS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS
    };

    public RequestUserPermission(Activity activity) {
        this.activity = activity;
    }

    public  void verifyStoragePermissions() {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
        if(ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS)==PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_CONTACTS)==PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CONTACTS}, TAG_CODE_PERMISSION_READ_CONTACTS);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_CONTACTS}, TAG_CODE_PERMISSION_WRITE_CONTACTS);
        }

        if(ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE)==PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS)==PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_SMS}, TAG_CODE_PERMISSION_READ_SMS);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, TAG_CODE_PERMISSION_READ_PHONE_STATE);
        }

        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, TAG_CODE_PERMISSION_FINE_LOCATION);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, TAG_CODE_PERMISSION_COARSE_LOCATION);}
        else {
            Toast.makeText(activity, "Permission error", Toast.LENGTH_LONG).show();
        }

    }
}
