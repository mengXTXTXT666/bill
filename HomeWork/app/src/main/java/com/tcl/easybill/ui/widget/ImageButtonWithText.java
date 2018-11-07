package com.tcl.easybill.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tcl.easybill.R;

import static android.view.Gravity.CENTER;
import static android.view.Gravity.CENTER_HORIZONTAL;

public class ImageButtonWithText extends LinearLayout {
    public RoundImageView imageView;
    public TextView textView;

    public ImageButtonWithText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageButtonWithText);
        /*
        * 在attrs.xml添加属性：
        *   <declare-styleable name="ImageButtonWithText">
             <attr name="picture" format="reference"/>
            </declare-styleable>
        * */
        int picture_id = a.getResourceId(R.styleable.ImageButtonWithText_picture, -1);
        /**
         * Recycle the TypedArray, to be re-used by a later caller. After calling
         * this function you must not ever touch the typed array again.
         */
        a.recycle();
        imageView = new RoundImageView(context, attrs);
        imageView.setPadding(0, 0, 0, 0);
        // TODO: 18-10-9 给imageview加上大小限制
        imageView.setLayoutParams(new ViewGroup.LayoutParams(80,
                80));

        /**
         * Sets a drawable as the content of this ImageView.
         * This does Bitmap reading and decoding on the UI
         * thread, which can cause a latency hiccup.  If that's a concern,
         * consider using setImageDrawable(android.graphics.drawable.Drawable) or
         * setImageBitmap(android.graphics.Bitmap) instead.
         * 直接在UI线程读取和解码Bitmap，可能会存在潜在的性能问题
         * 可以考虑使用 setImageDrawable(android.graphics.drawable.Drawable)
         * 或者setImageBitmap(android.graphics.Bitmap) 代替
         */
        imageView.setImageResource(picture_id);
        textView = new TextView(context, attrs);
        /**
         * Sets the horizontal alignment of the text and the
         * vertical gravity that will be used when there is extra space
         * in the TextView beyond what is required for the text itself.
         */
        //水平居中
        textView.setGravity(Gravity.TOP|CENTER_HORIZONTAL);
        textView.setPadding(0, 0, 0, 0);
        setClickable(true);
        setFocusable(true);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        setTextColor(R.color.startColor);
        setOrientation(LinearLayout.VERTICAL);
        setGravity(CENTER_HORIZONTAL);
        addView(imageView);
        addView(textView);
    }

    public void setText(int resId) {
        textView.setText(resId);
    }

    public void setText(String buttonText) {
        textView.setText(buttonText);
    }

    public void setTextColor(int color) {
        textView.setTextColor(color);
    }
    public void setImageView(int image){imageView.setImageDrawable(getResources().getDrawable(image));}
    public void setImageUri(Uri uri){imageView.setImageURI(uri);}

}