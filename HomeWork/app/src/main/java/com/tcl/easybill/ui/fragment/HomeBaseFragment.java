package com.tcl.easybill.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;


import java.io.File;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.tcl.easybill.R;
import com.tcl.easybill.Utils.LockViewUtil;
import com.tcl.easybill.pojo.Person;
import com.tcl.easybill.ui.activity.AccountActivity;
import com.tcl.easybill.ui.activity.PersionalInfoActivity;
import com.tcl.easybill.ui.activity.SearchAll;
import com.tcl.easybill.ui.widget.ImageButtonWithText;


/**
 * Created by Canxuan.zeng.
 * 带Toolbar的Fragment基类
 */

public abstract class HomeBaseFragment extends Fragment {
    protected String TAG;
    protected View mView;
    protected Activity mActivity;
    protected Context mContext;
    protected Toolbar toolbar;
    protected Person currentUser;
    LinearLayout navigationView;
    DrawerLayout mDrawerLayout;
    //Fragment的View加载完毕的标记
    private boolean isViewCreated;
    //Fragment对用户可见的标记
    private boolean isUIVisible;
    protected boolean isCreated = false;

    private Unbinder mUnBinder;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        // 设置 TAG
        TAG = this.getClass().getSimpleName();
        //当前用户
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreated = true;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);
        mUnBinder = ButterKnife.bind(this, mView);
        return mView;

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        currentUser= Person.getCurrentUser(Person.class);
        setHasOptionsMenu(true);
        myToolbar();
        importantData();
        lazyLoad();
    }


    public void myToolbar(){
        /* set  toolbar  and show */
        toolbar = getToolbar();
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        toolbar.setTitle("");//设置Toolbar标题
        toolbar.inflateMenu(getItemMenu());
        toolbar.setNavigationIcon(R.drawable.menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mDrawerLayout.openDrawer(Gravity.LEFT);
                /*暂时不开启账单切换Activity*/
                Intent accountIntent = new Intent(mContext, PersionalInfoActivity.class);
                startActivity(accountIntent);
            }
        });

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        Log.d(TAG,"onCreateOptionsMenu");
        inflater.inflate(getItemMenu(), menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Log.d(TAG,"onOptionsItemSelected");

        switch (item.getItemId())
        {
            case R.id.action_edit:
                setItemReact();
//                Intent intent = new Intent(getActivity(), SearchAll.class);
//                startActivity(intent);
                break;
//            case R.id.action_share:
//                Intent shareIt=new Intent(Intent.ACTION_SEND);
//                shareIt.setType("image/*");
//                shareIt.putExtra(Intent.EXTRA_SUBJECT, "Share");
//                shareIt.putExtra(Intent.EXTRA_TEXT, "快来跟我一起记账吧");
//                File f = new File(Environment.getExternalStorageDirectory()+"/shared.png");
//
//                shareIt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(Intent.createChooser(shareIt, "选择分享途径"));
//                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }

    private void lazyLoad() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
        beforeDestroy();
    }


    protected abstract void importantData();
    protected abstract void loadData();
    protected void setToolbar(View v){
        getToolbar().addView(v);
    }
    protected abstract Toolbar getToolbar();
    protected abstract int getLayoutId();
    protected abstract void beforeDestroy();
    protected abstract int getItemMenu();
    protected abstract void setItemReact();


}
