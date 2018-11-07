package com.tcl.easybill.mvp.model.impl;

import java.util.List;

import com.tcl.easybill.Utils.BillUtils;
import com.tcl.easybill.base.BaseObserver;
import com.tcl.easybill.base.LocalRepository;
import com.tcl.easybill.mvp.model.TotalRecordModel;
import com.tcl.easybill.pojo.DataSum;
import com.tcl.easybill.pojo.TotalBill;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TotalRecordModelImpl implements TotalRecordModel {
    private TotalRecordOnListener listener;
    @Override
    public void getTotalRecord(String id) {
        LocalRepository.getInstance().getDataSum(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TotalBill>>() {
                    @Override
                    protected void onSuccees(List<TotalBill> Bills) throws Exception {
                        listener.onSuccess(BillUtils.getDataSum(Bills));
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        listener.onFailure(e);
                        e.printStackTrace();
                    }
                });
    }

    public TotalRecordModelImpl(TotalRecordOnListener listener){
        this.listener = listener;
    }

    public interface TotalRecordOnListener {

        void onSuccess(DataSum bean);

        void onFailure(Throwable e);
    }

    @Override
    public void onUnsubscribe() {

    }
}
