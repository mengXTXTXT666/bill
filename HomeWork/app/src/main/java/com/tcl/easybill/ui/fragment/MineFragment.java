package com.tcl.easybill.ui.fragment;

import android.app.AlertDialog;
import android.app.NotificationManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.math.BigDecimal;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import com.tcl.easybill.R;
import com.tcl.easybill.Utils.UiUtils;
import com.tcl.easybill.Utils.ToastUtils;
import com.tcl.easybill.ui.activity.HomeActivity;
import com.tcl.easybill.ui.activity.LockOnActivity;
import com.tcl.easybill.ui.activity.NotificationActivity;
import com.tcl.easybill.ui.activity.NotifyActivity;
import com.tcl.easybill.ui.activity.PersionalInfoActivity;
import com.tcl.easybill.Utils.LockViewUtil;
import com.tcl.easybill.Utils.SnackbarUtils;
import com.tcl.easybill.mvp.presenter.TotalRecordPresenter;
import com.tcl.easybill.mvp.presenter.impl.TotalRecordPresenterImpl;
import com.tcl.easybill.mvp.views.TotalRecordView;
import com.tcl.easybill.pojo.DataSum;
import com.tcl.easybill.pojo.User;
import com.tcl.easybill.ui.activity.BudgetActivity;
import com.tcl.easybill.ui.widget.RoundImageView;

import static com.tcl.easybill.base.Constants.HOME;
import static com.tcl.easybill.base.Constants.NOTIFY;

public class MineFragment extends HomeBaseFragment implements TotalRecordView {
    @BindView(R.id.record_days)  //days of record
    TextView recordDays;
    @BindView(R.id.record_deals) //deals of record
    TextView recordDeals;
    @BindView(R.id.surplus)     //surplus
    TextView recordSurplus;
    @BindView(R.id.button_list)
    GridView listView;
    @BindView(R.id.headimage) //headImage
    RoundImageView headImage;
    @BindView(R.id.name)
    TextView userName; //userName
    private Boolean isOpen = true;
    private TotalRecordPresenter presenter;
    protected static final int PERSIONAL_INFO = 1;
    private int[] typeIcon = new int[]{
            R.mipmap.voice, R.mipmap.notify, R.mipmap.yusuan, R.mipmap.cyper, R.mipmap.outport,
            R.mipmap.count, R.mipmap.help
    };
    private String[] buttonContent = new String[]{
       "声音开关", "定时提醒", "每月预算", "手势密码", "导出账单", "评分", "帮助"
    };
    private String PackageName = "com.tcl.easybill";
    private int[] rightIcon = new int[]{
            R.drawable.voice_switch, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow,
            R.drawable.arrow, R.drawable.arrow
    };

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isCreated) {
            return;
        }
        if (isVisibleToUser) {
            presenter.getTotalRecord(User.getCurrentUser().getObjectId());
        }
    }

    public static MineFragment newInstance(String info) {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }
    /**
     * onClick react, open the PersionalInfoActivity
     * @param view
     */
    @OnClick ({R.id.headimage})
    protected void onClick(View view){
        switch (view.getId()) {
            case R.id.headimage:
                // TODO: 18-10-9  开启个人信息界面
                Intent intent = new Intent(mContext, PersionalInfoActivity.class);
                startActivityForResult(intent, PERSIONAL_INFO);
                break;
        }
    }
    /**
     * load data
     */
    @Override
    protected  void loadData(){
    }
    /**
     * load user data
     */
    private void initData(){
        // TODO: 18-10-9 载入头像名字，账单数据
        recordDays.setText("0"); //total day
        recordDeals.setText("0"); //total bill
        recordSurplus.setText("0.0"); //balance
        BudgetNotify();
        /*load GridView and add itemClickListener*/
        final List<Map<String, Object>> dataList;
        SimpleAdapter adapter;
        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < typeIcon.length; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("left", typeIcon[i]);
            map.put("text", buttonContent[i]);
            map.put("right",rightIcon[i]);
            dataList.add(map);
        }
        String[] from={"left","text", "right"};
        int[] to={R.id.image_left, R.id.button_show, R.id.image_right};
        adapter=new SimpleAdapter(mContext, dataList, R.layout.button_with_image, from, to);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
//                Toast.makeText(getActivity(),"pick"+position, Toast.LENGTH_LONG).show();
                switch (position) {
                    case 0:
                        /*switch of voice*/
                        ImageView imageView = getActivity().findViewById(R.id.image_right);
                        if(isOpen) {
                            imageView.setImageDrawable(getActivity().getDrawable(R.drawable.close_switch));
                            Toast.makeText(getActivity(), "声音关闭", Toast.LENGTH_LONG).show();
                            listView.setSoundEffectsEnabled(false);
                        }else {
                            imageView.setImageDrawable(getActivity().getDrawable(R.drawable.voice_switch));
                            Toast.makeText(getActivity(), "声音开启", Toast.LENGTH_LONG).show();
                            listView.setSoundEffectsEnabled(true);
                        }
                        isOpen = !isOpen;
                        break;
                    case 1:
                        /*set alarm to send notify*/
                        Intent notifyIntent = new Intent(mContext, NotifyActivity.class);
                        mContext.startActivity(notifyIntent);
                        break;
                    case 2:
                        /*budget*/
                        Intent intent = new Intent(getActivity(), BudgetActivity.class);
                        String outcome= ((HomeActivity)getActivity()).getmData();
                        intent.putExtra("outcome",outcome);
                        getActivity().startActivity(intent);
                        break;
                    case 3:
                        /*sign password*/
                        Intent intent1 = new Intent(getActivity(), LockOnActivity.class);
                        getActivity().startActivity(intent1);
                        break;
                    case 4:
                        /*outport the bill*/
                        ToastUtils.show(mContext,"功能尚未完善");
                        break;
                    case 5:
                        /*comments*/
                        comments();
                        break;
                    case 6:
                        /*help*/
                        Toast.makeText(mContext, "使用文档尚未完成", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });
    }


    public void BudgetNotify(){
        String budget = currentUser.getBudget();
        if (budget!=null && !budget.isEmpty()) {
            String outcome = ((HomeActivity) getActivity()).getmData();
            Float sum = Float.valueOf(budget) - Float.valueOf(outcome);
            if (sum <= 0 && NOTIFY == 0) {
                NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationActivity activity = new NotificationActivity(mNotificationManager, getActivity().getApplicationContext(), HOME);
                activity.notification();
                NOTIFY = 1;
            }
        }
    }
    /**
     * set toolbar right icon
     */
    @Override
    protected  int getItemMenu(){return R.menu.menu_share;}

    /**
     * set right icon onClick
     */
    @Override
    protected  void setItemReact(){
        // TODO: 18-10-9 分享
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
        intent.putExtra(Intent.EXTRA_TEXT, "快来跟我一起记账吧");
        File f = new File(Environment.getExternalStorageDirectory()+"/shared.png");

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, "选择分享途径"));
    }

    @Override
    public void myToolbar(){
        // TODO: 18-9-29 modify
        super.myToolbar();
    }

    @Override
    protected  void beforeDestroy(){}

    @Override
    protected void importantData() {
        initData();
        presenter = new TotalRecordPresenterImpl(this);
        presenter.getTotalRecord(User.getCurrentUser().getObjectId());
        Log.e(TAG, "importantData: "+ User.getCurrentUser().getObjectId());
        if(LockViewUtil.getIschange(mContext)){
            headImage.setImageURI(Uri.parse(LockViewUtil.getImage(mContext)));
        }
    }


    @Override
    public void loadDataSuccess(DataSum tData) {
        float money = tData.getTotalIncome()-tData.getTotalOutcome();
        Log.e("meng666", "loadDataSuccess: "+tData );
        Log.e("meng666", "day"+tData.getRecordDay()+"number"+tData.getRecordNumber()+"money"+money );
        recordDays.setText(String.valueOf(tData.getRecordDay())); //总天数
        recordDeals.setText(String.valueOf(tData.getRecordNumber())); //总笔数
        BigDecimal decimal = UiUtils.getNumber(money);
        recordSurplus.setText(String.valueOf(decimal)); //结余
        userName.setText(currentUser.getUsername());

    }

    @Override
    protected  Toolbar getToolbar(){ return getActivity().findViewById(R.id.tl_mine); }

    @Override
    protected  int getLayoutId(){ return R.layout.fragment_persionalmsg; }

    @Override
    public void loadDataError(Throwable throwable) {
        SnackbarUtils.show(mActivity, throwable.getMessage());
        Log.e(TAG, "loadDataError: "+throwable );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PERSIONAL_INFO){
            /*set the image*/
            if(LockViewUtil.getIschange(mContext)){
                headImage.setImageURI(Uri.parse(LockViewUtil.getImage(mContext)));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * comments, Google Play or T-store
     */
    private void comments(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("评分");
        builder.setMessage("选择应用商店");
        builder.setPositiveButton("T-store", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction("com.example.user.t_store.action.Main");
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                if(mContext.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_ALL)
                        .size() > 0){
                    mContext.startActivity(intent);
                }
            }
        }).setNegativeButton("Google Play", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "还没上架哦", Toast.LENGTH_LONG).show();
                try
                {
                    Uri uri = Uri.parse("market://details?id=" + PackageName);
                    Intent it = new Intent(new Intent(Intent.ACTION_VIEW, uri));
                    it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(it);
                }
                catch (Exception ex)
                {
                    Toast.makeText(mContext, "Couldn't launch the market !", Toast.LENGTH_SHORT).show();
                }
            }
        }).setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "取消", Toast.LENGTH_LONG).show();
            }
        });
        builder.create().show();
    }
}
