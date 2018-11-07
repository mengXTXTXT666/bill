package com.tcl.easybill.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tcl.easybill.R;
import com.tcl.easybill.Utils.ImageUtils;
import com.tcl.easybill.Utils.LockViewUtil;
import com.tcl.easybill.ui.widget.CircleImageView;
import com.tcl.easybill.ui.widget.RoundImageView;

import butterknife.BindView;
import butterknife.OnClick;

public class AccountActivity extends BaseActivity {
    @BindView(R.id.head_account)
    RoundImageView headImage;
    @BindView(R.id.account_layout)
    LinearLayout accountLayout;
    @BindView(R.id.name_account)
    TextView nameAccount;
    @Override
    protected int getLayout() {
        return R.layout.activity_account;
    }

    @Override
    protected void initEventAndData() {
        nameAccount.setText("我的");
        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PersionalInfoActivity.class);
                startActivity(intent);
            }
        });
        if(LockViewUtil.getIschange(mContext)){
            headImage.setImageURI(Uri.parse(LockViewUtil.getImage(mContext)));
        }

        accountLayout.findViewById(R.id.imageView_my)
                .setBackground(mContext.getDrawable(R.drawable.persional));
        accountLayout.findViewById(R.id.imageView_share)
                .setBackground(mContext.getDrawable(R.drawable.shared));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(LockViewUtil.getIschange(mContext)){
//            Bitmap photo = ImageUtils.getBitmapByUri(Uri.parse(LockViewUtil.getImage(mContext)));
//            headImage.setImageBitmap(photo);
//        }

    }

    @OnClick ({R.id.back_account, R.id.head_account})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.back_account:
                finish();
                break;
            case R.id.head_account:
                Intent persionalIntent = new Intent(mContext, PersionalInfoActivity.class);
                startActivity(persionalIntent);
            default:
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(LockViewUtil.getIschange(mContext)){
            headImage.setImageURI(Uri.parse(LockViewUtil.getImage(mContext)));
        }
    }
}
