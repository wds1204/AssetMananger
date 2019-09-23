package com.sun.assetmananger;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.sun.assetmananger.skin.SkinManager;

import java.io.File;

public class MainActivity extends BaseSkinActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Here, thisActivity is the current activity
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                            当某条权限之前已经请求过，并且用户已经拒绝了该权限时，shouldShowRequestPermissionRationale ()方法返回的是true
                } else {
//                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                }
            }
        } else {
//            createOutPublicDir();
        }

//        ImageView image = findViewById(R.id.image);
//        Log.e("TAG", "image"+image);
        //androidx.appcompat.widget.AppCompatImageView

        //android.widget.ImageView
    }

    public void change(View view) {
        String skinPath = Environment.getExternalStorageDirectory() + File.separator + "skin.skin";

        //手机内部sdcard文件夹下,不要放在扩展的sdcard中.
        Log.e("TAG", "skinPath==="+skinPath);
        SkinManager.getInstance().loadSkin(skinPath);


    }

    public void jump(View view) {

        startActivity(new Intent(this,MainActivity.class));
    }

    public void reset(View view) {

        SkinManager.getInstance().restoreDefault();
    }
}
