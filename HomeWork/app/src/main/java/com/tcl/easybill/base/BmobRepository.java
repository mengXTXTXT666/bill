package com.tcl.easybill.base;



import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.UpdateListener;
import com.tcl.easybill.Utils.BillUtils;
import com.tcl.easybill.pojo.ShareBill;
import com.tcl.easybill.pojo.TotalBill;

public class BmobRepository {

    private static final String TAG = "BmobRepository";

    private static volatile BmobRepository sInstance;

    private BmobRepository() {
    }

    public static BmobRepository getInstance() {
        if (sInstance == null) {
            synchronized (BmobRepository.class) {
                if (sInstance == null) {
                    sInstance = new BmobRepository();
                }
            }
        }
        return sInstance;
    }
    /**
     * delete bill
     */
    public void deleteBills(String id){
        ShareBill shareBill = new ShareBill();
        shareBill.setObjectId(id);
        shareBill.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    Log.e(TAG, "done: 成功 " );
                }else {
                    Log.e(TAG, "done: 失败" );
                }
            }
        });
    }
    /**********************batch operate***************************/
    /**
     * batch upload bill
     *
     * @param list
     */
    public void saveBills(List<BmobObject> list, final List<TotalBill> listB) {
        new BmobBatch().insertBatch(list).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> o, BmobException e) {
                if (e == null) {
                    for (int i = 0, n = o.size(); i < n; i++) {
                        if (o.get(i).isSuccess()) {
                            //when upload success update local-bill ,prevent repeat sync bill
                            TotalBill TotalBill = listB.get(i);
                            TotalBill.setRid(o.get(i).getObjectId());
                            LocalRepository.getInstance().updateTotalBillByBmob(TotalBill);
                        }
                    }

                }
            }
        });
    }

    /**
     * batch update bill
     *
     * @param list
     */
    public void updateBills(List<BmobObject> list) {

        new BmobBatch().updateBatch(list).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> o, BmobException e) {
                if (e == null) {

                }
            }
        });
    }

    /**
     * batch delete bill
     *
     * @param list
     */
    public void deleteBills(List<BmobObject> list) {

        new BmobBatch().deleteBatch(list).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> o, BmobException e) {
                if (e == null) {

                }
            }
        });
    }

    /**************************sync bill******************************/
    /**
     * sync bill
     */
    public void syncBill(String userid) {

        BmobQuery<ShareBill> query = new BmobQuery<>();
        query.addWhereEqualTo("userid", userid);
        //force return 50 data,default return 10 data
        query.setLimit(500);
        //executing query method
        query.findObjects(new FindListener<ShareBill>() {
            @Override
            public void done(List<ShareBill> object, BmobException e) {
                if (e == null) {
                    List<TotalBill> TotalBills = LocalRepository.getInstance().getTotalBills();
                    //bills to upload
                    List<BmobObject> listUpload = new ArrayList<>();
                    List<TotalBill> listTotalBillUpdate = new ArrayList<>();
                    //bills to update
                    List<BmobObject> listUpdate = new ArrayList<>();
                    //bills to delete
                    List<BmobObject> listDelete = new ArrayList<>();

                    HashMap<String, TotalBill> bMap = new HashMap<>();


                    for (TotalBill TotalBill : TotalBills) {
                        if (TotalBill.getRid() == null) {
                            //upload success
                            listUpload.add(new ShareBill(TotalBill));
                            //when upload bill ,update local data
                            listTotalBillUpdate.add(TotalBill);
                        } else
                            bMap.put(TotalBill.getRid(), TotalBill);
                    }

                    HashMap<String, ShareBill> cMap = new HashMap<>();
                    //storage server bill to map
                    for (ShareBill ShareBill : object) {
                        cMap.put(ShareBill.getObjectId(), ShareBill);
                    }

                    List<TotalBill> listsave = new ArrayList<>();
                    List<TotalBill> listdelete = new ArrayList<>();
                    for (Map.Entry<String, TotalBill> entry : bMap.entrySet()) {
                        String rid = entry.getKey();
                        TotalBill TotalBill=entry.getValue();
                        if (cMap.containsKey(rid)) {
                            if (TotalBill.getVersion() < 0) {
                                //bills to delete
                                listDelete.add(new ShareBill(TotalBill));
                                listdelete.add(TotalBill);
                            } else {
                                //服务器端数据过期
                                if (TotalBill.getVersion()>cMap.get(rid).getVersion()) {
                                    listUpdate.add(new ShareBill(TotalBill));
                                }
                            }
                            cMap.remove(rid);
                        }
                    }
                    //batch submit to server
                    if(!listUpload.isEmpty()) saveBills(listUpload,listTotalBillUpdate);
                    if(!listUpdate.isEmpty()) updateBills(listUpdate);
                    if(!listDelete.isEmpty()) deleteBills(listDelete);

                    //ShareBill==》TotalBill
                    for (Map.Entry<String, ShareBill> entry : cMap.entrySet()) {
                        //bills to save to local
                        listsave.add(BillUtils.toTotalBill(entry.getValue()));
                    }
                    //batch operate local database
                    LocalRepository.getInstance().saveTotalBills(listsave);
                    LocalRepository.getInstance().deleteBills(listdelete);
                    // sync success
                    EventBus.getDefault().post(new SyncEvent(100));
                }
                else
                    EventBus.getDefault().post(new SyncEvent(200));
            }
        });
    }

}
