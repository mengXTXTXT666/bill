package com.tcl.easybill.Utils;

import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tcl.easybill.pojo.DataSum;
import com.tcl.easybill.pojo.MonthAccount;
import com.tcl.easybill.pojo.MonthBillForChart;
import com.tcl.easybill.pojo.MonthDetailAccount;
import com.tcl.easybill.pojo.ShareBill;
import com.tcl.easybill.pojo.TotalBill;

import static com.tcl.easybill.Utils.DateUtils.getEndDayOfWeek;

public class BillUtils {

    static  Boolean  isToday = false;
    private static String TAG = "meng111";

    public static MonthDetailAccount packageDetailList2(List<TotalBill> list/*,String date*/) {
        MonthDetailAccount bean = new MonthDetailAccount();
        float t_income = 0;
        float t_outcome = 0;
        List<MonthDetailAccount.DaylistBean> daylist = new ArrayList<>();
        List<TotalBill> beanList = new ArrayList<>();
        float income = 0;
        float outcome = 0;



        String preDay = "";  //记录前一天的时间
        for (int i = 0; i < list.size(); i++) {
            TotalBill TotalBill = list.get(i);
            //计算总收入支出
            if (TotalBill.isIncome())
                t_income += TotalBill.getCost();
            else
                t_outcome += TotalBill.getCost();

            //判断后一个账单是否于前者为同一天
            if (i == 0 || preDay.equals(DateUtils.getDay(TotalBill.getCrdate()))) {

                if (TotalBill.isIncome())
                    income += TotalBill.getCost();
                else
                    outcome += TotalBill.getCost();
                beanList.add(TotalBill);

                if (i==0)
                    preDay = DateUtils.getDay(TotalBill.getCrdate());
            } else {
                //局部变量防止引用冲突
                List<TotalBill> tmpList = new ArrayList<>();
                tmpList.addAll(beanList);
                MonthDetailAccount.DaylistBean tmpDay = new MonthDetailAccount.DaylistBean();
                tmpDay.setList(tmpList);
                tmpDay.setMoney("支出：" + UiUtils.getNumber(outcome) + " 收入：" + UiUtils.getNumber(income));
                tmpDay.setTime(preDay);
                daylist.add(tmpDay);

                //清空前一天的数据
                beanList.clear();
                income = 0;
                outcome = 0;

                //添加数据
                if (TotalBill.isIncome())
                    income += TotalBill.getCost();
                else
                    outcome += TotalBill.getCost();
                beanList.add(TotalBill);
                preDay = DateUtils.getDay(TotalBill.getCrdate());
            }
        }

        if (beanList.size() > 0) {
            //局部变量防止引用冲突
            List<TotalBill> tmpList = new ArrayList<>();
            tmpList.addAll(beanList);
            MonthDetailAccount.DaylistBean tmpDay = new MonthDetailAccount.DaylistBean();
            tmpDay.setList(tmpList);
            tmpDay.setMoney("支出：" + UiUtils.getNumber(outcome) + " 收入：" + UiUtils.getNumber(income));
            tmpDay.setTime(DateUtils.getDay(beanList.get(0).getCrdate()));
            daylist.add(tmpDay);

        }

        bean.setT_income(String.valueOf(t_income));
        bean.setT_outcome(String.valueOf(t_outcome));
        bean.setDaylist(daylist);
        return bean;
    }

    /**
     * 账单按天分类
     * @param list
     * @return
     */
    public static MonthDetailAccount packageDetailList(List<TotalBill> list) {
        MonthDetailAccount bean = new MonthDetailAccount();
        float t_income = 0;
        float t_outcome = 0;
        List<MonthDetailAccount.DaylistBean> daylist = new ArrayList<>();
        List<TotalBill> beanList = new ArrayList<>();
        float income = 0;
        float outcome = 0;
        String preDay = "";  //记录前一天的时间
        for (int i = 0; i < list.size(); i++) {
            TotalBill TotalBill = list.get(i);
            //计算总收入支出
            if (TotalBill.isIncome())
                t_income += TotalBill.getCost();
            else
                t_outcome += TotalBill.getCost();

            //判断后一个账单是否于前者为同一天
            if (i == 0 || preDay.equals(DateUtils.getDay(TotalBill.getCrdate()))) {

                if (TotalBill.isIncome())
                    income += TotalBill.getCost();
                else
                    outcome += TotalBill.getCost();
                beanList.add(TotalBill);

                if (i==0)
                    preDay = DateUtils.getDay(TotalBill.getCrdate());
            } else {
                //局部变量防止引用冲突
                List<TotalBill> tmpList = new ArrayList<>();
                tmpList.addAll(beanList);
                MonthDetailAccount.DaylistBean tmpDay = new MonthDetailAccount.DaylistBean();
                tmpDay.setList(tmpList);
                tmpDay.setMoney("支出：" + UiUtils.getNumber(outcome) + " 收入：" + UiUtils.getNumber(income));
                tmpDay.setTime(preDay);
                //daylist.add添加数据，获得最终结果
                daylist.add(tmpDay);

                //清空前一天的数据
                beanList.clear();
                income = 0;
                outcome = 0;

                //添加数据
                if (TotalBill.isIncome())
                    income += TotalBill.getCost();
                else
                    outcome += TotalBill.getCost();
                beanList.add(TotalBill);
                preDay = DateUtils.getDay(TotalBill.getCrdate());
            }
        }

        if (beanList.size() > 0) {
            //局部变量防止引用冲突
            List<TotalBill> tmpList = new ArrayList<>();
            tmpList.addAll(beanList);
            MonthDetailAccount.DaylistBean tmpDay = new MonthDetailAccount.DaylistBean();
            tmpDay.setList(tmpList);
            tmpDay.setMoney("支出：" + UiUtils.getNumber(outcome) + " 收入：" + UiUtils.getNumber(income));
            tmpDay.setTime(DateUtils.getDay(beanList.get(0).getCrdate()));
            if (tmpDay.getTime().equals(DateUtils.getCurDay()))
                isToday = true;
            daylist.add(tmpDay);

        }

        bean.setT_income(String.valueOf(t_income));
        bean.setT_outcome(String.valueOf(t_outcome));
        bean.setDaylist(daylist);
        return bean;
    }
    /**
     * 获取总数据
     */
    public static DataSum getDataSum(List<TotalBill> list){
        DataSum bean = new DataSum();
       // String userName="";
        float t_income = 0;
        float t_outcome = 0;
        int record = list.size();
        int sum = 0;
        String preDay = "";  //记录前一天的时间

        for (int i=0; i<list.size(); i++){
            TotalBill totalBill = list.get(i);
            if (totalBill.isIncome()){
                t_income +=totalBill.getCost();
            }else {
                t_outcome += totalBill.getCost();
            }
            if (i == 0 ){
                sum =1;
                preDay = DateUtils.getDay(totalBill.getCrdate());
            }else if (preDay.equals(DateUtils.getDay(totalBill.getCrdate()))){

            }else {
                sum += 1;
            }
            bean.setTotalIncome(t_income);
            bean.setTotalOutcome(t_outcome);
            bean.setRecordDay(sum);
            bean.setRecordNumber(record);

        }
        return bean;
    }
    /**
     * 账单按类型分类
     * @param list
     * @return
     */
    public static MonthBillForChart packageChartList(List<TotalBill> list) {
        MonthDetailAccount detail = new MonthDetailAccount();
        detail = BillUtils.packageDetailList(list);
        List<MonthDetailAccount.DaylistBean> detailList = detail.getDaylist();

        MonthBillForChart bean = new MonthBillForChart();
       /* float t_income = 0;
        float t_outcome = 0;*/

        Map<String, List<TotalBill>> mapIn = new HashMap<>();
        Map<String, Float> moneyIn = new HashMap<>();
        Map<String, List<TotalBill>> mapOut = new HashMap<>();
        Map<String, Float> moneyOut = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            TotalBill TotalBill = list.get(i);
            //计算总收入支出
           /* if (TotalBill.isIncome()) t_income += TotalBill.getCost();
            else t_outcome += TotalBill.getCost();*/

            //账单分类
            String sort = TotalBill.getSortName();
            List<TotalBill> listBill;
            if (TotalBill.isIncome()) {
                if (mapIn.containsKey(sort)) {
                    listBill = mapIn.get(sort);
                } else {
                    listBill = new ArrayList<>();
                }
                if (moneyIn.containsKey(sort))
                    moneyIn.put(sort, moneyIn.get(sort) + TotalBill.getCost());
                else
                    moneyIn.put(sort, TotalBill.getCost());
                listBill.add(TotalBill);
                mapIn.put(sort, listBill);
            } else {
                if (mapOut.containsKey(sort)) {
                    listBill = mapOut.get(sort);
                } else {
                    listBill = new ArrayList<>();
                }
                if (moneyOut.containsKey(sort))
                    moneyOut.put(sort, moneyOut.get(sort) + TotalBill.getCost());
                else
                    moneyOut.put(sort, TotalBill.getCost());
                listBill.add(TotalBill);
                mapOut.put(sort, listBill);
            }
        }

        List<MonthBillForChart.SortTypeList> outSortlist = new ArrayList<>();    //账单分类统计支出
        List<MonthBillForChart.SortTypeList> inSortlist = new ArrayList<>();    //账单分类统计收入

        for (Map.Entry<String, List<TotalBill>> entry : mapOut.entrySet()) {
            MonthBillForChart.SortTypeList sortTypeList = new MonthBillForChart.SortTypeList();
            sortTypeList.setList(entry.getValue());
            sortTypeList.setSortName(entry.getKey());
            sortTypeList.setSortImg(entry.getValue().get(0).getSortImg());
            sortTypeList.setMoney(moneyOut.get(entry.getKey()));
            sortTypeList.setBack_color(StringUtils.randomColor());
            outSortlist.add(sortTypeList);
        }
        for (Map.Entry<String, List<TotalBill>> entry : mapIn.entrySet()) {
            MonthBillForChart.SortTypeList sortTypeList = new MonthBillForChart.SortTypeList();
            sortTypeList.setList(entry.getValue());
            sortTypeList.setSortName(entry.getKey());
            sortTypeList.setSortImg(entry.getValue().get(0).getSortImg());
            sortTypeList.setMoney(moneyIn.get(entry.getKey()));
            sortTypeList.setBack_color(StringUtils.randomColor());
            inSortlist.add(sortTypeList);
        }

        bean.setOutSortlist(outSortlist);
        bean.setInSortlist(inSortlist);
        bean.setDaylist(detailList);
       /* bean.setTotalIn(t_income);
        bean.setTotalOut(t_outcome);*/
        return bean;
    }

    /**
     * 账单按支付方式分类
     * @param list
     * @return
     */
    public static MonthAccount packageAccountList(List<TotalBill> list) {

        MonthAccount bean = new MonthAccount();
        float t_income = 0;
        float t_outcome = 0;

        Map<String, List<TotalBill>> mapAccount = new HashMap<>();
        Map<String, Float> mapMoneyIn = new HashMap<>();
        Map<String, Float> mapMoneyOut = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            TotalBill TotalBill = list.get(i);
            //计算总收入支出
            if (TotalBill.isIncome()) t_income += TotalBill.getCost();
            else t_outcome += TotalBill.getCost();

            String pay = TotalBill.getPayName();

            if (mapAccount.containsKey(pay)) {
                List<TotalBill> TotalBills = mapAccount.get(pay);
                TotalBills.add(TotalBill);
                mapAccount.put(pay, TotalBills);
            } else {
                List<TotalBill> TotalBills = new ArrayList<>();
                TotalBills.add(TotalBill);
                mapAccount.put(pay, TotalBills);
            }

            if (TotalBill.isIncome()) {
                if (mapMoneyIn.containsKey(pay)) {
                    mapMoneyIn.put(pay, mapMoneyIn.get(pay) + TotalBill.getCost());
                } else {
                    mapMoneyIn.put(pay, TotalBill.getCost());
                }
            } else {
                if (mapMoneyOut.containsKey(pay)) {
                    mapMoneyOut.put(pay, mapMoneyOut.get(pay) + TotalBill.getCost());
                } else {
                    mapMoneyOut.put(pay, TotalBill.getCost());
                }
            }
        }

        List<MonthAccount.PayTypeListBean> payTypeListBeans = new ArrayList<>();    //账单分类统计支出
        for (Map.Entry<String, List<TotalBill>> entry : mapAccount.entrySet()) {
            MonthAccount.PayTypeListBean payTypeListBean = new MonthAccount.PayTypeListBean();
            payTypeListBean.setBills(entry.getValue());
            //先判断当前支付方式是否有输入或支出
            //因为有可能只有支出或收入
            if (mapMoneyIn.containsKey(entry.getKey()))
                payTypeListBean.setIncome(mapMoneyIn.get(entry.getKey()));
            if (mapMoneyOut.containsKey(entry.getKey()))
                payTypeListBean.setOutcome(mapMoneyOut.get(entry.getKey()));
            payTypeListBean.setPayImg(entry.getValue().get(0).getPayImg());
            payTypeListBean.setPayName(entry.getValue().get(0).getPayName());
            payTypeListBeans.add(payTypeListBean);
        }

        bean.setTotalIn(t_income);
        bean.setTotalOut(t_outcome);
        bean.setList(payTypeListBeans);
        return bean;
    }

    /**
     * ShareBill=>TotalBill
     *
     * @param ShareBill
     * @return
     */
    public static TotalBill toTotalBill(ShareBill ShareBill) {
        TotalBill TotalBill = new TotalBill();
        TotalBill.setRid(ShareBill.getObjectId());
        TotalBill.setVersion(ShareBill.getVersion());
        TotalBill.setIncome(ShareBill.getIncome());
        TotalBill.setCrdate(ShareBill.getCrdate());
        TotalBill.setSortImg(ShareBill.getSortImg());
        TotalBill.setSortName(ShareBill.getSortName());
        TotalBill.setPayImg(ShareBill.getPayImg());
        TotalBill.setPayName(ShareBill.getPayName());
        TotalBill.setUserid(ShareBill.getUserid());
        TotalBill.setContent(ShareBill.getContent());
        TotalBill.setCost(ShareBill.getCost());

        return TotalBill;
    }
}
