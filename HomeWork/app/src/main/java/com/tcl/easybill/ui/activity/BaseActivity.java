package com.tcl.easybill.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.tcl.easybill.pojo.Person;


public abstract class BaseActivity extends AppCompatActivity {

    protected Activity mContext;
    protected Person currentUser;//current user
    private Unbinder mUnBinder;

    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};//permissions
    private AlertDialog dialog;

    protected static String TAG;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        /*set the screen orientation*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*hide action bar*/
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置 TAG
        TAG = this.getClass().getSimpleName();
        //
        super.onCreate(savedInstanceState);
        mContext = this;

        /*set the app is fullScreen*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(getLayoutInflater().inflate(getLayout(), null, true));
        /*register ButterKnife*/
        mUnBinder = ButterKnife.bind(this);
        /*get the info of currentUser*/
        currentUser= Person.getCurrentUser(Person.class);

        /*if version is higher than 23, ask the permissions*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // check whether has this permission
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            // check whether has this permission  GRANTED---get  DINIED---defense
            if (i != PackageManager.PERMISSION_GRANTED) {
                showDialogTipUserRequestPermission();
            }
        }

        initEventAndData();
        //ActivityManagerUtils.mActivities.add(this);

    }


    // ask for permission
    private void showDialogTipUserRequestPermission() {

        new AlertDialog.Builder(this)
                .setTitle("存储权限不可用")
                .setMessage("由于需要获取存储空间，为你存储个人信息\n否则，您可能将无法正常使用本程序")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRequestPermission();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    //  start request to get permission
    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 321);
    }

    // request permission callback
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // check whether user refuse
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        // prompt user to turn on permission
                        showDialogTipUserGoToAppSettting();
                    } else
                        finish();
                } else {
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // prompt user to turn on permission in Setting
    private void showDialogTipUserGoToAppSettting() {

        dialog = new AlertDialog.Builder(this)
                .setTitle("存储权限不可用")
                .setMessage("请在-应用设置-权限-中，允许支付宝使用存储权限来保存用户数据")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
                    }
                }).setCancelable(false).show();
    }

    // jump to Setting
    private void goToAppSetting() {
        Intent intent = new Intent();

        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);

        startActivityForResult(intent, 123);
    }

    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // check whether has permission
                int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                // check whether has this permission  GRANTED---get  DINIED---defense
                if (i != PackageManager.PERMISSION_GRANTED) {
                    showDialogTipUserGoToAppSettting();
                } else {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        //ActivityManagerUtils.mActivities.remove(this);
    }


    /**
     * keep the app alive when it goto background
     * finish the activity when press the back
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected abstract int getLayout();
    protected abstract void initEventAndData();

}
