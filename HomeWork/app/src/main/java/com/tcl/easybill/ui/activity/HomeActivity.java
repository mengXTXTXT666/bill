package com.tcl.easybill.ui.activity;





import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.List;

import butterknife.BindView;
import com.tcl.easybill.R;
import com.tcl.easybill.Utils.ProgressUtils;
import com.tcl.easybill.Utils.SharedPUtils;
import com.tcl.easybill.Utils.ToastUtils;
import com.tcl.easybill.base.BmobRepository;
import com.tcl.easybill.base.Constants;
import com.tcl.easybill.base.LocalRepository;
import com.tcl.easybill.pojo.AllSortBill;
import com.tcl.easybill.pojo.SortBill;
import com.tcl.easybill.ui.MyViewPager;
import com.tcl.easybill.ui.adapter.ViewPagerAdapter;
import com.tcl.easybill.ui.fragment.BillFragment;
import com.tcl.easybill.ui.fragment.ChartFragment;
import com.tcl.easybill.ui.fragment.MineFragment;


public class HomeActivity extends BaseActivity  {

   /* private MyViewPager viewPager;*/
    private MenuItem menuItem;
    public String mData;
    private static final int RESULTCODE =0;
    /*private BottomNavigationView bottomNavigationView;*/
    private BillFragment bill_fFragment;
    private ChartFragment chart_fragment;
    private MineFragment mine_fragment;
    private long mExitTime;
    private int currentItem = 0;
    ViewPagerAdapter adapter;
    @BindView(R.id.viewpager)
    MyViewPager viewPager ;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    public static Context getContext() {
        return getContext();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    public String getmData() {
        return mData;
    }

    public void setmData(String mData) {
        this.mData = mData;
    }
    @Override
    protected void initEventAndData() {
        Log.e(TAG, "initEventAndData: " );
        TAG = "meng111";
        /*FloatingActionButton*/
        initFab();
        // it is the first the user open this APP
        if(SharedPUtils.isFirstStart(mContext)){
            Log.i(TAG,"第一次进入将默认账单分类添加到数据库");
            AllSortBill note= new Gson().fromJson(Constants.BILL_NOTE, AllSortBill.class);
            List<SortBill> sorts=note.getOutSortList();
            sorts.addAll(note.getInSortList());
            LocalRepository.getInstance().saveBsorts(sorts);
            LocalRepository.getInstance().saveBPays(note.getPayinfo());
        }
//        viewPager.setOffscreenPageLimit(3);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_bill:
                                viewPager.setCurrentItem(0);
                                currentItem = 0;
                                break;
                            case R.id.item_chart:
                                viewPager.setCurrentItem(1);
                                currentItem = 1;
                                break;
                            case R.id.item_mine:
                                viewPager.setCurrentItem(2);
                                currentItem = 2;
                                break;
                        }
                        return false;
                    }
                });


        bottomNavigationView.setItemIconTintList(null);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }


        });

        setupViewPager(viewPager);
    }



    //fragment change
    private void setupViewPager(ViewPager viewPager) {
        Log.e(TAG, "setupViewPager: " );
        adapter= new ViewPagerAdapter(getSupportFragmentManager());
        bill_fFragment = new BillFragment();
        chart_fragment = new ChartFragment();
        mine_fragment = new MineFragment();
        adapter.addFragment(bill_fFragment);
        adapter.addFragment(chart_fragment);
        adapter.addFragment(mine_fragment);
        viewPager.setAdapter(adapter);
    }

    /**
     * add suspend button
     */
    private void initFab(){
        /*parent button*/
        FloatingActionButton floatingActionButton = new FloatingActionButton.Builder(this).build();
        floatingActionButton.setBackground(mContext.getDrawable(R.drawable.add_bill3));
        /*item button*/
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        SubActionButton addbill = itemBuilder.build();
        addbill.setBackground(mContext.getDrawable(R.drawable.add_icon));
        SubActionButton refreshbill = itemBuilder.build();
        refreshbill.setBackground(mContext.getDrawable(R.drawable.refresh_icon));

        /*set FloatingActionMenu*/
        final FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(addbill)
                .addSubActionView(refreshbill)
                .attachTo(floatingActionButton)
                .build();
        /*size and locate*/
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(180, 180);
        params.setMargins(0, 0, 0, 100);
        floatingActionButton.setPosition(4, params);

        /*onClick react*/
        addbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressUtils.show(HomeActivity.this, "正在加载...");
                /*open BillAddActivity*/
                Intent intent = new Intent(HomeActivity.this, BillAddActivity.class);
                startActivityForResult(intent,RESULTCODE);
                actionMenu.close(true);

            }
        });
        refreshbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*refresh the data*/
                Log.e(TAG, "onClick: " );
                BmobRepository.getInstance().syncBill(currentUser.getObjectId());
                actionMenu.close(true);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult: " );
        super.onActivityResult(requestCode, resultCode, data);
        adapter.notifyDataSetChanged();
        ProgressUtils.dismiss();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setupViewPager(viewPager);
        viewPager.setCurrentItem(currentItem);

    }


    /**
     * call the exit() when press the back twice
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * finish the app
     */
    private void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtils.show(mContext, "再按一次退出应用");
            mExitTime = System.currentTimeMillis();
        } else {
            //用户退出处理
            finish();
            System.exit(0);
        }
    }
}
