package com.tcl.easybill.mvp.model.impl;

import java.util.List;

import com.tcl.easybill.Utils.BillUtils;
import com.tcl.easybill.base.BaseObserver;
import com.tcl.easybill.base.LocalRepository;
import com.tcl.easybill.mvp.model.MonthChartModel;
import com.tcl.easybill.pojo.MonthBillForChart;
import com.tcl.easybill.pojo.TotalBill;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MonthChartModelImpl implements MonthChartModel {
    private MonthChartOnListener listener;

    public MonthChartModelImpl(MonthChartOnListener listener) {
        this.listener = listener;
    }

    @Override
    public void getMonthChartBills(String id, String year, String month) {
        LocalRepository.getInstance().getTotalBillByUserIdWithYM2(id, year, month)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TotalBill>>() {
                    @Override
                    protected void onSuccees(List<TotalBill> bBills) throws Exception {
                        listener.onSuccess(BillUtils.packageChartList(bBills));
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        listener.onFailure(e);
                    }
                });
    }

    @Override
    public void getDayDetailBills(int id, String year, String month, String day) {
        LocalRepository.getInstance().getTotalBillByUserIdWithYM(id, year, month)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TotalBill>>() {
                    @Override
                    protected void onSuccees(List<TotalBill> bBills) throws Exception {
                        listener.onSuccess(BillUtils.packageChartList(bBills));
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        listener.onFailure(e);
                    }
                });
    }

    @Override
    public void onUnsubscribe() {

    }

    /**
     * call back
     */
    public interface MonthChartOnListener {

        void onSuccess(MonthBillForChart bean);

        void onFailure(Throwable e);
    }
}
