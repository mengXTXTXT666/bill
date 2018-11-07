package com.tcl.easybill.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tcl.easybill.R;
import com.tcl.easybill.Utils.DateUtils;
import com.tcl.easybill.Utils.ProgressUtils;
import com.tcl.easybill.mvp.presenter.impl.BillPresenterImpl;
import com.tcl.easybill.mvp.views.BillView;
import com.tcl.easybill.pojo.AllSortBill;
import com.tcl.easybill.pojo.BPay;
import com.tcl.easybill.pojo.SortBill;
import com.tcl.easybill.pojo.TotalBill;

import static com.tcl.easybill.Utils.DateUtils.FORMAT_M;
import static com.tcl.easybill.Utils.DateUtils.FORMAT_Y;
import static com.tcl.easybill.Utils.DateUtils.FORMAT_YMD;

/**
 * revise bill
 */
public class BillEditActivity extends BillAddActivity implements BillView {

    //old Bill
    private Bundle bundle;

    @Override
    protected int getLayout() {
        return R.layout.activity_add;
    }


    @Override
    protected void initEventAndData() {

        presenter=new BillPresenterImpl(this);
        //get old data
        setOldBill();

        // initialize sort data
        initSortView();

        /*set up begin day of time choicer*/
        mYear = Integer.parseInt(DateUtils.getCurYear(FORMAT_Y));
        mMonth = Integer.parseInt(DateUtils.getCurMonth(FORMAT_M));

    }
    /**
     * get old data
     */
    private void setOldBill() {

        bundle = getIntent().getBundleExtra("bundle");
        if (bundle == null)
            return;
        //set up bill's date
        days = DateUtils.long2Str(bundle.getLong("date"), FORMAT_YMD);
        dateTv.setText(days);
        isOutcome = !bundle.getBoolean("income");
        remarkInput = bundle.getString("content");
        DecimalFormat df = new DecimalFormat("######0.00");
        String money = df.format(bundle.getDouble("cost"));
        //decimal rounding
        num = money.split("\\.")[0];
        //get decimal number
        dotNum = "." + money.split("\\.")[1];
        //set up money
        moneyTv.setText(num + dotNum);
    }

    /**
     * select bill's data depend on  name
     *
     * @param name
     * @return
     */
    private SortBill findSortByName(String name) {
        if (isOutcome) {
            for (SortBill e : noteBean.getOutSortList()) {
                if (e.getSortName() == name)
                    return e;
            }
        } else {
            for (SortBill e : noteBean.getInSortList()) {
                if (e.getSortName() == name)
                    return e;
            }
        }
        return null;
    }

    @Override
    public void loadDataSuccess(AllSortBill tData) {
        noteBean=tData;
        setTitleStatus();
    }

    /**
     * set up status
     */
    protected void setTitleStatus() {


        setTitle();

        lastBean = findSortByName(bundle.getString("sortName"));
        // could not find bill in this sort
        //whether this bill has been delete or change income and outcome
        if (lastBean==null)
            lastBean=mDatas.get(0);
        sortTv.setText(lastBean.getSortName());


        initViewPager();
    }

    /**
     * add bill
     */
    public void doCommit() {
        final SimpleDateFormat sdf = new SimpleDateFormat(" HH:mm:ss");
        final String crDate = days + sdf.format(new Date());
        if ((num + dotNum).equals("0.00")) {
            Toast.makeText(this, "唔姆，你还没输入金额", Toast.LENGTH_SHORT).show();
            return;
        }

        ProgressUtils.show(mContext, "正在提交...");
        presenter.update(new TotalBill(bundle.getLong("id"),bundle.getString("rid"),
                Float.valueOf(num + dotNum),remarkInput,currentUser.getObjectId(),
               "no",
                "no",
                lastBean.getSortName(),lastBean.getSortImg(),
                DateUtils.getMillis(crDate),!isOutcome,bundle.getInt("version")+1));
    }


}
