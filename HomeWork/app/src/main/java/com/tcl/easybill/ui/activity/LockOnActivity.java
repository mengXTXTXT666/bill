package com.tcl.easybill.ui.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.syd.oden.gesturelock.view.GestureLockViewGroup;
import com.syd.oden.gesturelock.view.listener.GestureEventListener;
import com.syd.oden.gesturelock.view.listener.GesturePasswordSettingListener;
import com.syd.oden.gesturelock.view.listener.GestureUnmatchedExceedListener;
import com.tcl.easybill.R;
import com.tcl.easybill.Utils.LockViewUtil;
import com.tcl.easybill.Utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;


public class LockOnActivity extends BaseActivity{
    @BindView(R.id.lockview_text)
    TextView tv_state;
    @BindView(R.id.gesturelock)
    GestureLockViewGroup mGestureLockViewGroup;
    @BindView(R.id.switch_lockview)
    Switch lockViewSwh;
    private boolean isReset = false;
    private Boolean isVerify = false;
    @Override
    protected int getLayout() {
        return R.layout.activity_lockview;
    }

    @Override
    protected void initEventAndData() {

//        gestureEventListener();
//        gesturePasswordSettingListener();
//        gestureRetryLimitListener();
        swhHandle();
        if(!mGestureLockViewGroup.isSetPassword()){
            tv_state.setText("请启用手势密码");
        }else {
            tv_state.setText("");
        }

        if(LockViewUtil.getIslock(mContext)){
            lockViewSwh.setChecked(true);
        }

    }

    @OnClick({R.id.switch_lockview, R.id.back_persional})
    protected void onClick(View v){
        switch (v.getId()) {
            //login out this activity
            case R.id.back_persional:
                finish();
                break;
            /*after user revise gesture password,click this button will clear the gesture password */
//            case R.id.modify_lockview:
//                modifyHandle();
//                break;
        }
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
                        isVerify = true;
                        tv_state.setText("手势密码正确");
                    }
            }

        });
    }

    private void gesturePasswordSettingListener() {
        mGestureLockViewGroup.setGesturePasswordSettingListener(new GesturePasswordSettingListener() {
            @Override
            public boolean onFirstInputComplete(int len) {
                if (len > 3) {
                    tv_state.setTextColor(Color.BLACK);
                    tv_state.setText("再次绘制手势密码");
                    return true;
                } else {
                    tv_state.setTextColor(Color.RED);
                    tv_state.setText("最少连接4个点，请重新输入!");
                    return false;
                }

            }

            @Override
            public void onSuccess() {
                tv_state.setTextColor(Color.BLACK);
                ToastUtils.show(mContext, "密码设置成功");
                tv_state.setText("手势密码已启用！");
                LockViewUtil.setIslock(mContext, true);
            }

            @Override
            public void onFail() {
                ToastUtils.show(mContext, "与上一次绘制不一致，请重新绘制");
                resetGesturePattern();
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

    private void resetGesturePattern(){
        mGestureLockViewGroup.removePassword();
        setGestureWhenNoSet();
        mGestureLockViewGroup.resetView();
    }


    private void setGestureWhenNoSet(){
        if (!mGestureLockViewGroup.isSetPassword()){
            tv_state.setTextColor(Color.BLACK);
            tv_state.setText("绘制手势密码");
        }
    }

    /**
     * Switch
     * Click on Open to get the current status of the gesture password,
     * no password click invalid; password to see if it has been verified,
     * no validation invalid, then turn on the gesture password click Close direct stop gesture password
     * (not clear record)
     */
    private void swhHandle(){
        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(!(mGestureLockViewGroup.getPassword().equals(""))){
//                    if(isChecked) {
//                        lockViewSwh.setChecked(true);
//                        LockViewUtil.setIslock(mContext, true);
//                        Toast.makeText(mContext, "手势密码已启用", Toast.LENGTH_SHORT).show();
//                    }else {
//                        lockViewSwh.setChecked(false);
//                        LockViewUtil.setIslock(mContext, false);
//                        Toast.makeText(mContext, "手势密码已取消", Toast.LENGTH_SHORT).show();
//                    }
//                }else{
//                    lockViewSwh.setChecked(false);
//                    Toast.makeText(mContext, "当前无手势密码", Toast.LENGTH_SHORT).show();
//                }
                if(isChecked){
                    mGestureLockViewGroup.setVisibility(VISIBLE);
                    gestureEventListener();
                    gesturePasswordSettingListener();
                    gestureRetryLimitListener();
                }else {
                    if(LockViewUtil.getIslock(mContext)) {
                        if (isVerify) {
                            modifyHandle();
                            mGestureLockViewGroup.setVisibility(INVISIBLE);
                            LockViewUtil.setIslock(mContext, false);
                        }else {
                            lockViewSwh.setChecked(true);
                            tv_state.setText("请先验证密码!");
                        }
                    }else {
                        mGestureLockViewGroup.setVisibility(INVISIBLE);
                        mGestureLockViewGroup.removePassword();
                        mGestureLockViewGroup.resetView();
                    }
                }
            }
        };
        lockViewSwh.setOnCheckedChangeListener(listener);
    }

    private void modifyHandle(){
        isReset = true;
        tv_state.setTextColor(Color.BLACK);
        ToastUtils.show(mContext, "密码已清除!");
        mGestureLockViewGroup.removePassword();
        setGestureWhenNoSet();
        mGestureLockViewGroup.resetView();
    }



}
