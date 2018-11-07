package com.tcl.easybill.ui.widget;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class LockViewCircle {
    //默认值
    public static final int DEFAULT_COLOR = Color.BLACK;
    public static final int DEFAULT_BOUND = 1;
    public static final int DEFAULT_CENTER_BOUND = 15;
    //状态值
    public static final int STATUS_DEFAULT = 0;
    public static final int STATUS_TOUCH = 1;
    public static final int STATUS_SUCCESS = 2;
    public static final int STATUS_FAILED = 3;

    //圆形的中点X、Y坐标
    private int centerX;
    private int centerY;
    //圆形的颜色值
    private int colorDefault = DEFAULT_COLOR;
    private int colorSuccess;
    private int colorFailed;
    //圆形的宽度
    private int bound = DEFAULT_BOUND;
    //中心的宽度
    private int centerBound = DEFAULT_CENTER_BOUND;
    //圆形的半径
    private int radius;
    //圆形的状态
    private int status = STATUS_DEFAULT;
    //圆形的位置
    private int position;

    public LockViewCircle(int centerX, int centerY, int colorSuccess, int colorFailed, int radius, int position){
        super();
        this.centerX = centerX;
        this.centerY = centerY;
        this.colorSuccess = colorSuccess;
        this.colorFailed = colorFailed;
        this.radius = radius;
        this.position = position;
    }

    public LockViewCircle(int centerX, int centerY, int colorDefault, int colorSuccess, int colorFailed, int bound,
                          int centerBound, int radius, int status, int position){
        super();
        this.centerX = centerX;
        this.centerY = centerY;
        this.colorDefault = colorDefault;
        this.colorSuccess = colorSuccess;
        this.colorFailed = colorFailed;
        this.bound = bound;
        this.centerBound = centerBound;
        this.radius = radius;
        this.status = status;
        this.position = position;
    }

    public int getCenterX(){
        return centerX;
    }

    public void setCenterX(int centerX){
        this.centerX = centerX;
    }

    public int getCenterY(){
        return centerY;
    }

    public void setCenterY(int centerY){
        this.centerY = centerY;
    }

    public int getColorDefault(){
        return colorDefault;
    }

    public void setColorDefault(int colorDefault){
        this.colorDefault = colorDefault;
    }

    public int getColorSuccess(){
        return colorSuccess;
    }

    public void setColorSuccess(int colorSuccess){
        this.colorSuccess = colorSuccess;
    }

    public int getColorFailed(){
        return colorFailed;
    }

    public void setColorFailed(int colorFailed){
        this.colorFailed = colorFailed;
    }

    public int getBound(){
        return bound;
    }

    public void setBound(int bound){
        this.bound = bound;
    }

    public int getCenterBound(){
        return centerBound;
    }

    public void setCenterBound(int centerBound){
        this.centerBound = centerBound;
    }

    public int getRadius(){
        return radius;
    }

    public void setRadius(int radius){
        this.radius = radius;
    }

    public int getStatus(){
        return status;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public int getPosition(){
        return position;
    }

    public void setPosition(int position){
        this.position = position;
    }

    /**
     * @Description:改变圆球当前状态
     */
    public void changeStatus(int status){
        this.status = status;
    }

    /**
     * @Description:绘制这个圆形
     */
    public void draw(Canvas canvas , Paint paint){
        switch(status){
            case STATUS_DEFAULT:
                paint.setColor(colorDefault);
                break;
            case STATUS_TOUCH:
            case STATUS_SUCCESS:
                paint.setColor(colorSuccess);
                break;
            case STATUS_FAILED:
                paint.setColor(colorFailed);
                break;
            default:
                paint.setColor(colorDefault);
                break;
        }
        paint.setStyle(Paint.Style.FILL);
        //绘制中心实心圆
        canvas.drawCircle(centerX, centerY, centerBound, paint);
        //绘制空心圆

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(centerBound);
        canvas.drawCircle(centerX, centerY, centerBound, paint);
    }
}

