package com.tcl.easybill.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.widget.TextView;

import butterknife.BindView;

import com.syd.oden.gesturelock.view.GestureLockViewGroup;
import com.syd.oden.gesturelock.view.listener.GestureEventListener;
import com.syd.oden.gesturelock.view.listener.GestureUnmatchedExceedListener;
import com.tcl.easybill.R;
import com.tcl.easybill.Utils.LockViewUtil;
import com.tcl.easybill.Utils.ProgressUtils;


public class UnlockUI extends BaseActivity{
    @BindView(R.id.unlock_lockview)
    GestureLockViewGroup mGestureLockViewGroup;
    @BindView(R.id.unlock_tv)
    TextView tv_state;
    @Override
    protected int getLayout() {
        return R.layout.activity_unlock_ui;
    }

    /**
     * unlock UI
     */
    @Override
    protected void initEventAndData() {
        gestureEventListener();
        gestureRetryLimitListener();
    }


    /**
     * 手势密码监听
     */
    private void gestureEventListener() {
        mGestureLockViewGroup.setGestureEventListener(new GestureEventListener() {
            @Override
            public void onGestureEvent(boolean matched) {
                if (!matched) {
                    tv_state.setTextColor(Color.RED);
                    tv_state.setText("手势密码错误");
                } else {
                    tv_state.setTextColor(Color.BLACK);
                    tv_state.setText("手势密码正确");
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    ProgressUtils.show(mContext, "正在登录...");
                    finish();
                }
            }
        });
    }
    private void gestureRetryLimitListener() {
        mGestureLockViewGroup.setGestureUnmatchedExceedListener(5, new GestureUnmatchedExceedListener() {
            @Override
            public void onUnmatchedExceedBoundary() {
                tv_state.setTextColor(Color.RED);
                tv_state.setText("错误次数过多，请稍后再试!");
            }
        });
    }
}
