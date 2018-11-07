package com.tcl.easybill.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;
import com.tcl.easybill.R;
import com.tcl.easybill.Utils.ImageUtils;
import com.tcl.easybill.Utils.UiUtils;
import com.tcl.easybill.pojo.MonthBillForChart;
/**
 * ChartFragment
 */
public class MonthChartAdapter extends RecyclerView.Adapter<MonthChartAdapter.ViewHolder>{

    private List<MonthBillForChart.SortTypeList> dataList;
    private LayoutInflater mInflater;
    private String come;
    private Context mContext;
    private Drawable drawable;
    private String name;
    private boolean isIncome;

    public MonthChartAdapter(Context context, List<MonthBillForChart.SortTypeList>  dataList){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this. dataList = dataList;
    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.chartadapter, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
       drawable = ImageUtils.getDrawable(dataList.get(position).getSortImg());
       name = dataList.get(position).getSortName();
       come =String.valueOf(dataList.get(position).getMoney());
       isIncome = dataList.get(position).getList().get(0).isIncome();

        holder.title.setText(name);
        holder.image.setImageDrawable(drawable);
        if(isIncome)
            holder.money.setText("+"+ UiUtils.getSmallNumber(come));
        else
            holder.money.setText("-"+UiUtils.getSmallNumber(come));

    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView image;
        private TextView title;
        private TextView money;
        private RelativeLayout item_layout;

        public ViewHolder(View view){
            super(view);

            image = (ImageView)view.findViewById(R.id.circle_img) ;
            title = (TextView) view.findViewById(R.id.title);
            money = (TextView) view.findViewById(R.id.money);
            item_layout = (RelativeLayout)view.findViewById(R.id.item_layout);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
            }
        }
    }

}
