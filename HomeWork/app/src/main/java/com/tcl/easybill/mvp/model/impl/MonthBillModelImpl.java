package com.tcl.easybill.mvp.model.impl;

import java.util.List;

import com.tcl.easybill.Utils.BillUtils;
import com.tcl.easybill.base.BaseObserver;
import com.tcl.easybill.base.LocalRepository;
import com.tcl.easybill.mvp.model.MonthBillModel;
import com.tcl.easybill.pojo.MonthAccount;
import com.tcl.easybill.pojo.TotalBill;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MonthBillModelImpl implements MonthBillModel{

    private MonthBillOnListener listener;

    public  MonthBillModelImpl(MonthBillOnListener listener){
        this.listener = listener;
    }

    @Override
    public void getMonthBills(int id, String year, String month) {
        LocalRepository.getInstance().getTotalBillByUserIdWithYM(id, year, month)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TotalBill>>() {
                    @Override
                    protected void onSuccees(List<TotalBill> bBills) throws Exception {
                        listener.onSuccess(BillUtils.packageAccountList(bBills));
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
    public interface MonthBillOnListener {

        void onSuccess(MonthAccount bean);

        void onFailure(Throwable e);
    }
}
