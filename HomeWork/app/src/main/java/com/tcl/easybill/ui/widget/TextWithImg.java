package com.tcl.easybill.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tcl.easybill.R;

import static android.view.Gravity.CENTER_HORIZONTAL;
import static android.view.Gravity.CENTER_VERTICAL;

public class TextWithImg extends LinearLayout{
    private TextView textView;
    private ImageView imageView;
    public TextWithImg(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageButtonWithText);
        a.recycle();
        /*textView*/
        textView = new TextView(context, attrs);
        textView.setGravity(Gravity.END|CENTER_VERTICAL);
        textView.setPadding(0, 0, 10, 0);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setTextSize(18);
        textView.setTextColor(getResources().getColor(R.color.persion_text));
        /*imageView*/
        imageView = new RoundImageView(context, attrs);
        imageView.setPadding(5, 0, 5, 0);
        // TODO: 18-10-9 给imageview加上大小限制
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        imageView.setImageResource(picture_id);
        imageView.setBackground(getResources().getDrawable(R.drawable.arrow));
        /*set the textView display with a imageView*/
        setClickable(true);
        setFocusable(true);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(CENTER_VERTICAL);
        addView(textView);
        addView(imageView);
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
    public void setTextSize(float size){textView.setTextSize(size);}
    public void setImageView(int image){imageView.setImageDrawable(getResources().getDrawable(image));}

}
