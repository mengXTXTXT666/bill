package com.tcl.easybill.Utils;

import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.net.Uri;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.tcl.easybill.R;
import com.tcl.easybill.base.MyApplication;


public class ImageUtils {

    /**
     * Save image to the SD card
     *
     * @param photoBitmap
     * @param photoName
     * @param path
     */
    public static String savePhoto(Bitmap photoBitmap, String path,
                                   String photoName) {
        String localPath = null;
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File photoFile = new File(path+"/"+photoName);
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap != null) {
                    if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100,
                            fileOutputStream)) { // 转换完成
                        localPath = photoFile.getPath();
                        fileOutputStream.flush();
                    }
                }
            } catch (FileNotFoundException e) {
                photoFile.delete();
                localPath = null;
                e.printStackTrace();
            } catch (IOException e) {
                photoFile.delete();
                localPath = null;
                e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                        fileOutputStream = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return localPath;
    }

    /**
     * 根据uri读取并压缩图片
     */
    public static Bitmap getBitmapByUri(Uri uri){
        BitmapFactory.Options options = new BitmapFactory.Options();
        String filePath = uri.getPath();
//        options.inJustDecodeBounds = true;
//        int outHeight = options.outHeight;
//        int outWidth = options.outWidth;
//        int scale = Math.max(outHeight / 3000, outWidth / 3000);
//        //scale向下取整,真实取值 2的n次幂
//        options.inSampleSize = scale;
//        options.inJustDecodeBounds = false;
//        Bitmap bitmap = BitmapFactory.decodeFile(filePath,options);
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        Matrix matrix = new Matrix();
        matrix.setScale(0.7f, 0.7f);
        return Bitmap.createBitmap( bitmap, 0, 0,  100, 100,
                matrix, false);
    }


    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int)left, (int)top, (int)right, (int)bottom);
        final Rect dst = new Rect((int)dst_left, (int)dst_top, (int)dst_right, (int)dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    /**
     * 根据图片url设置与之相对应的本地图片
     * @param imgUrl
     * @return
     */
    public static Drawable getDrawable(String imgUrl){
        Drawable drawable = null;
        if(imgUrl.equals("sort_meal.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.sort_meal);
        else if(imgUrl.equals("sort_fruit.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.sort_fruit);
        else if(imgUrl.equals("sort_shopping.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.sort_shopping);
        else if(imgUrl.equals("sort_supplies.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.sort_supplies);
        else if(imgUrl.equals("sort_snack.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.sort_snack);
        else if(imgUrl.equals("sort_vegetable.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.sort_vegetable);
        else if(imgUrl.equals("sort_amusement.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.sort_amusement);
        else if(imgUrl.equals("sort_house.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.sort_house);
        else if(imgUrl.equals("sort_medical.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.sort_medical);
        else if(imgUrl.equals("sort_class.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.sort_class);
        else if(imgUrl.equals("sort_clothes.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.sort_clothes);
        else if(imgUrl.equals("sort_traffic.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.sort_traffic);
        else if(imgUrl.equals("sort_kid.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.sort_kid);
        else if(imgUrl.equals("sort_cosmetology.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.sort_cosmetology);
        else if(imgUrl.equals("sort_older.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.sort_older);
        else if(imgUrl.equals("sort_furniture.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.sort_furniture);
        else if(imgUrl.equals("sort_pet.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.sort_pet);
        else if(imgUrl.equals("sort_book.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.sort_book);
        else if(imgUrl.equals("sort_travel.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.sort_travel);
        else if(imgUrl.equals("sort_4g.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.sort_4g);
        else if(imgUrl.equals("sort_living_expense.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.sort_living_expense);
        else if(imgUrl.equals("sort_red.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.sort_red);
        else if(imgUrl.equals("sort_salary.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.sort_salary);
        else if(imgUrl.equals("sort_financing.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.sort_financing);
        else if(imgUrl.equals("sort_parttimejob.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.sort_parttimejob);
        else if(imgUrl.equals("sort_sociality.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.sort_sociality);
        else if(imgUrl.equals("card_cash.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.card_cash);
        else if(imgUrl.equals("card_account.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.card_account);
        else if(imgUrl.equals("card_account.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.card_account);
        else
            drawable=null;

        return drawable;
    }
}
