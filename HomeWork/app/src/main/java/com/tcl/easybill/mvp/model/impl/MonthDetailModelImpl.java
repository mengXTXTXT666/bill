package com.tcl.easybill.mvp.model.impl;

import java.util.List;

import com.tcl.easybill.Utils.BillUtils;
import com.tcl.easybill.base.BaseObserver;
import com.tcl.easybill.base.LocalRepository;
import com.tcl.easybill.mvp.model.MonthDetailModel;
import com.tcl.easybill.pojo.MonthDetailAccount;
import com.tcl.easybill.pojo.TotalBill;
import com.tcl.easybill.pojo.base;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MonthDetailModelImpl implements MonthDetailModel{
    private MonthDetailOnListener listener;

    public MonthDetailModelImpl(MonthDetailOnListener listener) {
        this.listener = listener;
    }
    /**
     * call back
     */
    public interface MonthDetailOnListener {

        void onSuccess(MonthDetailAccount bean);

        void onSuccess(base bean);

        void onFailure(Throwable e);
    }
    @Override
    public void getMonthDetailBills(String id, String year, String month) {
        LocalRepository.getInstance().getTotalBillByUserIdWithYM2(id, year, month)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TotalBill>>() {
                    @Override
                    protected void onSuccees(List<TotalBill> bBills) throws Exception {
                        listener.onSuccess(BillUtils.packageDetailList(bBills));
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        listener.onFailure(e);
                    }
                });
    }

    @Override
    public void getDayCost(int id, String year, String month/* String date*/) {
        LocalRepository.getInstance().getTotalBillByUserIdWithYM(id, year, month)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TotalBill>>() {
                    @Override
                    protected void onSuccees(List<TotalBill> bBills) throws Exception {
                        listener.onSuccess(BillUtils.packageDetailList(bBills));
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        listener.onFailure(e);
                    }
                });
    }

    @Override
    public void delete(Long id) {
        LocalRepository.getInstance().deleteTotalBillById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<Long>() {
                    @Override
                    protected void onSuccees(Long l) throws Exception {
                        listener.onSuccess(new base());
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        listener.onFailure(e);
                    }
                });
    }

    @Override
    public void update(TotalBill bBill) {
        LocalRepository.getInstance()
                .updateTotalBill(bBill)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<TotalBill>() {
                    @Override
                    protected void onSuccees(TotalBill bBill) throws Exception {
                        listener.onSuccess(new base());
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
}
