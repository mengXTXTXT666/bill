package com.tcl.easybill.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import com.tcl.easybill.R;
import com.tcl.easybill.Utils.DateUtils;
import com.tcl.easybill.Utils.ImageUtils;
import com.tcl.easybill.Utils.LockViewUtil;
import com.tcl.easybill.Utils.SnackbarUtils;
import com.tcl.easybill.Utils.StringUtils;
import com.tcl.easybill.mvp.presenter.UserInfoPresenter;
import com.tcl.easybill.mvp.presenter.impl.UserInfoPresenterImp;
import com.tcl.easybill.mvp.views.UserInfoView;
import com.tcl.easybill.pojo.Person;
import com.tcl.easybill.ui.widget.RoundImageView;
import com.tcl.easybill.ui.widget.TextWithImg;

public class PersionalInfoActivity extends BaseActivity implements UserInfoView{
    @BindView(R.id.name_text)
    TextWithImg userName;
    @BindView(R.id.gender_text)
    TextWithImg gender;
    @BindView(R.id.email_text)
    TextWithImg email;
    @BindView(R.id.share_text)
    TextWithImg shareAccount;
    @BindView(R.id.persional_head)
    RoundImageView perisonalHead;

    private UserInfoPresenter presenter;

    private android.support.v7.app.AlertDialog iconDialog;
    private android.support.v7.app.AlertDialog genderDialog;
    private android.support.v7.app.AlertDialog phoneDialog;
    private android.support.v7.app.AlertDialog emailDialog;

    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    protected static final int GENDER_MAN = 0;
    protected static final int GENDER_FEMALE = 1;
    protected static final int ASK_CAMERA = 2;
    private Uri imageUri;

    private String[] permissions = {Manifest.permission.CAMERA};




    @Override
    protected void initEventAndData() {
        presenter = new UserInfoPresenterImp(this);
        userName.setText(currentUser.getUsername());
        gender.setText(getGender(currentUser.getGender()));
        email.setText(currentUser.getEmail());
        getShareNumber();
        if(LockViewUtil.getIschange(mContext)){
            perisonalHead.setImageURI(Uri.parse(LockViewUtil.getImage(mContext)));
            Log.i("----", LockViewUtil.getImage(mContext));
            Log.i("----", "have image");
        }else {
            Log.i("----","no image");
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_persional;
    }


    @OnClick ({R.id.name_persional, R.id.head_persional, R.id.gender_persional, R.id.email_persional,
    R.id.logout, R.id.back_persional, R.id.share_account})
    protected void onClick(View view) {
        switch (view.getId()) {
           case R.id.name_persional:
               changUserName();
               break;
           case R.id.head_persional:
               changeHead();
               break;
           case R.id.gender_persional:
               changeGender();
               break;
           case R.id.email_persional:
                changeEmail();
               break;
           case R.id.logout:
               logout();
               break;
           case R.id.back_persional:
               finish();
               break;
            case R.id.share_account:
               break;

       }
    }
    /**
     * modify email
     */
    private void changeEmail(){
        final EditText emailEditText = new EditText(PersionalInfoActivity.this);
        emailEditText.setText(currentUser.getEmail());
        /*Move cursor to end of text*/
        emailEditText.setSelection(currentUser.getEmail().length());
        if (emailDialog == null) {
            emailDialog = new android.support.v7.app.AlertDialog.Builder(this)
                    .setTitle("邮箱")
                    .setView(emailEditText)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String input = emailEditText.getText().toString();
                            if (input.equals("")) {
                                Toast.makeText(getApplicationContext(), "内容不能为空！" + input,
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                if (StringUtils.checkEmail(input)) {
                                    currentUser.setEmail(input);
                                    email.setText(input);
                                    doUpdate();
                                } else {
                                    Toast.makeText(PersionalInfoActivity.this,
                                            "请输入正确的邮箱格式", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    })
                    .setNegativeButton("取消", null)
                    .create();
        }
        if (!emailDialog.isShowing()) {
            emailDialog.show();
        }
    }
    /**
     * Generate the share code according to the registration time.
     */
    public void getShareNumber(){
        if (currentUser.getShareid() == 0){
            String shareNumber ;
            int updateNUmber =0;
            int right = (int)(Math.random()*90+10);
            String time =  currentUser.getCreatedAt();
            Date date = DateUtils.str2Date(time);
            Long l = date.getTime();
            int left =  Math.abs(l.intValue()) ;
            shareNumber = left + String.valueOf(right);
            updateNUmber = l.intValue()+right;
            shareAccount.setText(shareNumber);
            currentUser.setShareid(updateNUmber);
            doUpdate();
        }else {
            shareAccount.setText(String.valueOf(Math.abs(currentUser.getShareid())));
        }

    }
    /**
     * modify username
     */
    private void changUserName(){
        SnackbarUtils.show(mContext, "社会人行不改名，坐不改姓！！！！");
    }
    private void changeGender(){
        if (genderDialog == null) {
            genderDialog = new android.support.v7.app.AlertDialog.Builder(this).setItems(new String[]{"男", "女"},
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case GENDER_MAN: // 男性
                                    /*if(currentUser.getGender()== 0 ){
                                        currentUser.setGender(1);
                                    }*/
                                    if(currentUser.getGender() == 0){
                                        currentUser.setGender(1);
                                        doUpdate();
                                    }
                                    break;
                                case GENDER_FEMALE: // 女性
                                    /*if(currentUser.getGender()== 0 ){
                                        currentUser.setGender(2);
                                    }*/
                                    if(currentUser.getGender() ==1){
                                        currentUser.setGender(0);
                                        doUpdate();
                                    }
                                    break;
                            }
                            gender.setText(getGender(currentUser.getGender()));
                            SnackbarUtils.show(mContext, "更改成功，同时恭喜手术成功");
                        }
                    }).create();
        }
        if (!genderDialog.isShowing()) {
            genderDialog.show();
        }
    }
    /**
     * sync data
     */
    public void doUpdate() {
        if (currentUser == null)
            return;
       // ProgressUtils.show(PersionalInfoActivity.this, "正在修改...");
        presenter.update(currentUser);

    }
    /**
     * revise gender
     */
    public String getGender(int i){
        String gender ;
        if (i==1){
            gender = "男";
        }else {
            gender = "女";
        }
        return gender;
    }
    /**
     * logout
     */
    private void logout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("退出登陆并返回到登陆界面？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO: 18-10-18 这里注销
                LockViewUtil.setIslogin(mContext, false);
                Intent intent  = new Intent(mContext,UesrLoginActivity.class);
                startActivity(intent);
                finish();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }

    /**
     * getPermission
     */
    private void askPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            /*check permissions*/
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permissions, ASK_CAMERA);
                }else {
                    takePicture();
                }
            }
        }
    }

    /**
     * change the headImage
     */
    private void changeHead(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("选择模式");
        builder.setPositiveButton("相册", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CHOOSE_PICTURE);
            }
        }).setNegativeButton("拍照", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "拍照", Toast.LENGTH_LONG).show();
                askPermission();
            }
        });
        builder.create().show();

    }


    private void takePicture(){
        imageUri = createImageUri(PersionalInfoActivity.this);
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PICTURE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED) {
            delteImageUri(mContext, imageUri);
            Log.i("----","cancel");
            return;
        }else {
            switch (requestCode) {
                // read pictures
                case CHOOSE_PICTURE:
                    try {
                        imageUri = data.getData();
                        perisonalHead.setImageURI(imageUri);
                        /*save the image*/
                        /*delete the image that save before*/
                        if(LockViewUtil.getIschange(mContext)){
                            LockViewUtil.clearImage(mContext);
                            Log.i("----", "have image" + imageUri.toString());
                        }
                        LockViewUtil.saveImage(mContext,imageUri.toString());
                        LockViewUtil.setIschange(mContext,true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                // capture
                case TAKE_PICTURE:
                    try {
                        Log.i("----", "change image");
                        perisonalHead.setImageURI(imageUri);
                        /*delete the image that save before*/
                        if(LockViewUtil.getIschange(mContext)){
                            LockViewUtil.clearImage(mContext);
                            Log.i("----", "have image" + imageUri.toString());
                        }
                        LockViewUtil.saveImage(mContext,imageUri.toString());
                        LockViewUtil.setIschange(mContext,true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;

            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



    /**
     * create a uri to save the photo of capture
     * @param context
     * @return
     */
    private static Uri createImageUri(Context context) {
        String name = "takePhoto" + System.currentTimeMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, name);
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, name + ".jpeg");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        return uri;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case ASK_CAMERA:
                        if (grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                            takePicture();
                        }else {
                            Toast.makeText(this, "你拒绝了该要求", Toast.LENGTH_SHORT).show();
                        }
                break;
            default:
        }
    }

    public static void delteImageUri(Context context, Uri uri) {
        context.getContentResolver().delete(uri, null, null);

    }

    @Override
    public void loadDataSuccess(Person tData) {

    }

    @Override
    public void loadDataError(Throwable throwable) {

    }

}
