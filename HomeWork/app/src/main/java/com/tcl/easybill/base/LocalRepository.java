package com.tcl.easybill.base;


import android.util.Log;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.tcl.easybill.Utils.DateUtils;
import com.tcl.easybill.greendao.DaoSession;
import com.tcl.easybill.greendao.SortBillDao;
import com.tcl.easybill.greendao.TotalBillDao;

import com.tcl.easybill.pojo.BPay;

import com.tcl.easybill.pojo.SortBill;
import com.tcl.easybill.pojo.TotalBill;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class LocalRepository {

//    private static final String TAG = "LocalRepository";
//    private static final String DISTILLATE_ALL = "normal";
//    private static final String DISTILLATE_BOUTIQUES = "distillate";

    private static volatile LocalRepository sInstance;
    private DaoSession mSession;

    private LocalRepository() {
        mSession = DaoDBHelper.getInstance().getSession();
    }

    public static LocalRepository getInstance() {
        if (sInstance == null) {
            synchronized (LocalRepository.class) {
                if (sInstance == null) {

                    sInstance = new LocalRepository();
                }
            }
        }
        return sInstance;
    }

    /******************************save**************************************/
    public Observable<TotalBill> saveTotalBill(final TotalBill bill) {
        return Observable.create(new ObservableOnSubscribe<TotalBill>() {
            @Override
            public void subscribe(ObservableEmitter<TotalBill> e) throws Exception {
                mSession.getTotalBillDao().insert(bill);
                e.onNext(bill);
                e.onComplete();
            }
        });
    }

    /**
     * batch add bills
     * @param TotalBills
     */
    public void saveTotalBills( List<TotalBill> TotalBills) {
       mSession.getTotalBillDao().insertInTx(TotalBills);
    }



    public Long saveSortBill(SortBill sort) {
        return mSession.getSortBillDao().insert(sort);
    }

    /**
     * batch add payment
     *
     * @param pays
     */
    public void saveBPays(List<BPay> pays) {
        for (BPay pay : pays)
            saveBPay(pay);
    }

    /**
     * batch add bill's sort
     *
     * @param sorts
     */
    public void saveBsorts(List<SortBill> sorts) {
        for (SortBill sort : sorts)
            saveBSort(sort);
    }

    /**
     * batch add bill
     * @param
     */

    public Long saveBPay(BPay pay) {
        return mSession.getBPayDao().insert(pay);
    }

    public Long saveBSort(SortBill sort) {
        return mSession.getSortBillDao().insert(sort);
    }

    /******************************get**************************************/
    public TotalBill getTotalBillById(int id) {
        return mSession.getTotalBillDao().queryBuilder()
                .where(TotalBillDao.Properties.Id.eq(id)).unique();
    }

    public List<TotalBill> getTotalBills() {
        return mSession.getTotalBillDao().queryBuilder().list();
    }

    public Observable<List<TotalBill>> getTotalBillByUserId(int id) {
        QueryBuilder<TotalBill> queryBuilder = mSession.getTotalBillDao()
                .queryBuilder()
                .where(TotalBillDao.Properties.Userid.eq(id));
        return queryListToRx(queryBuilder);
    }

    public Observable<List<TotalBill>> getTotalBillByUserIdWithYM(int id, String year, String month) {
        String startStr = year + "-" + month + "-00 00:00:00";
        Date date = DateUtils.str2Date(startStr);
        Date endDate = DateUtils.addMonth(date, 1);
        QueryBuilder<TotalBill> queryBuilder = mSession.getTotalBillDao()
                .queryBuilder()
                .where(TotalBillDao.Properties.Crdate.between(DateUtils.getMillis(date), DateUtils.getMillis(endDate)))
                .where(TotalBillDao.Properties.Version.ge(0))
                .orderDesc(TotalBillDao.Properties.Crdate);
        return queryListToRx(queryBuilder);
    }
    public Observable<List<TotalBill>> getDataSum(String id) {
        QueryBuilder<TotalBill> queryBuilder = mSession.getTotalBillDao()
                .queryBuilder()
                .where(TotalBillDao.Properties.Userid.eq(id))
                .where(TotalBillDao.Properties.Version.ge(0))
                .orderDesc(TotalBillDao.Properties.Crdate);
        Log.e("mengss", "getDataSum: "+queryBuilder.toString() );
        return queryListToRx(queryBuilder);
    }
    public Observable<List<TotalBill>> getTotalBillByUserIdWithYM2(String id, String year, String month) {
        String startStr = year + "-" + month + "-01 00:00:00";
        Log.e("Local", "getTotalBillByUserIdWithYM2: "+startStr );
        Date date = DateUtils.str2Date(startStr);
        Date endDate = DateUtils.addMonth(date, 1);
        QueryBuilder<TotalBill> queryBuilder = mSession.getTotalBillDao()
                .queryBuilder()
                .where(TotalBillDao.Properties.Userid.eq(id))
                .where(TotalBillDao.Properties.Crdate.between(DateUtils.getMillis(date), DateUtils.getMillis(endDate)))
                .where(TotalBillDao.Properties.Version.ge(0))
                .orderDesc(TotalBillDao.Properties.Crdate);
        return queryListToRx(queryBuilder);
    }
    public Observable<List<TotalBill>> getTotalBillByUserIdWithAll(String id) {
        QueryBuilder<TotalBill> queryBuilder = mSession.getTotalBillDao()
                .queryBuilder()
                .where(TotalBillDao.Properties.Userid.eq(id));
        return queryListToRx(queryBuilder);
    }

    public Observable<List<SortBill>> getSortBill(boolean income){
        QueryBuilder<SortBill> queryBuilder = mSession.getSortBillDao()
                .queryBuilder()
                .where(SortBillDao.Properties.Income.eq(income));
        return queryListToRx(queryBuilder);
    }

    public Observable<List<SortBill>> getSortBill(){
        QueryBuilder<SortBill> queryBuilder = mSession.getSortBillDao()
                .queryBuilder();
        return queryListToRx(queryBuilder);
    }

    /******************************update**************************************/

    /**
     * sync bill
     * @param bill
     */
    public void updateTotalBillByBmob(TotalBill bill) {
        mSession.getTotalBillDao().update(bill);
    }

    /**
     * update bill
     * @param bill
     * @return
     */
    public Observable<TotalBill> updateTotalBill(final TotalBill bill) {
       
        return Observable.create(new ObservableOnSubscribe<TotalBill>() {
            @Override
            public void subscribe(ObservableEmitter<TotalBill> e) throws Exception {
                mSession.getTotalBillDao().update(bill);
                e.onNext(bill);
                e.onComplete();
            }
        });
    }

    /******************************delete**************************************/
    /**
     * delete bill's sort
     * @param id
     */
    public void deleteSortBillById(Long id){
        mSession.getSortBillDao().deleteByKey(id);
    }

    /**

    /**
     * sync bill (delete bill)
     * @param TotalBills
     */
    public void deleteBills(List<TotalBill> TotalBills){
        mSession.getTotalBillDao().deleteInTx(TotalBills);
    }

    /**
     * delete total local bill
     */
    public void deleteAllBills(){
        deleteBills(getTotalBills());
    }

    public Observable<Long> deleteTotalBillById(Long id) {
        mSession.getTotalBillDao().deleteByKey(id);
        return Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> e) throws Exception {
                e.onNext(new Long(0));
                e.onComplete();
            }
        });
    }

    /******************************Rx**************************************/
    private <T> Observable<T> queryToRx(final QueryBuilder<T> builder) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                T data = builder.list().get(0);
                e.onNext(data);
                e.onComplete();
            }
        });
    }

    private <T> Observable<List<T>> queryListToRx(final QueryBuilder<T> builder) {
        return Observable.create(new ObservableOnSubscribe<List<T>>() {
            @Override
            public void subscribe(ObservableEmitter<List<T>> e) throws Exception {
                List<T> data = builder.list();
                Log.e("m2", "subscribe: "+data.size());
                e.onNext(data);
                e.onComplete();
            }
        });
    }

}
