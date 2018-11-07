package com.tcl.easybill.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import com.tcl.easybill.R;
import com.tcl.easybill.ui.activity.BillAddActivity;
import com.tcl.easybill.ui.activity.BillEditActivity;
import com.tcl.easybill.Utils.ImageUtils;
import com.tcl.easybill.pojo.SortBill;

/**
 * 账单分类Adapter（AddBillAdapter)
 */
public class BookNoteAdapter extends RecyclerView.Adapter<BookNoteAdapter.ViewHolder> {

    private BillAddActivity mContext;
    private BillEditActivity eContext;
    private LayoutInflater mInflater;
    private List<SortBill> mDatas;

    private List<Boolean> isClicks; //定义一个集合记录选中的item

    private OnBookNoteClickListener onBookNoteClickListener;

    public void setmDatas(List<SortBill> mDatas) {
        this.mDatas = mDatas;
    }

    public BookNoteAdapter(BillAddActivity context, List<SortBill> datas) {
        this.mContext = context;
        this.eContext = null;
        this.mInflater = LayoutInflater.from(context);
        this.mDatas = datas;

        /*集合所有item设为非选中*/
        isClicks = new ArrayList<>();
        for(int i = 0;i<datas.size();i++){
            isClicks.add(false);
        }
    }

    public BookNoteAdapter(BillEditActivity context, List<SortBill> datas) {
        this.mContext = null;
        this.eContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mDatas = datas;

    }

    public void setOnBookNoteClickListener(OnBookNoteClickListener listener) {
        if (onBookNoteClickListener == null)
            this.onBookNoteClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return (mDatas == null) ? 0 : mDatas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_tb_type, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.title.setText(mDatas.get(position).getSortName());
        holder.img.setImageDrawable(ImageUtils.getDrawable(mDatas.get(position).getSortImg()));

        //添加监听,刷新记录选中的item
        if(onBookNoteClickListener!=null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    for(int i = 0; i <isClicks.size();i++){
                        isClicks.set(i,false);
                    }
                    isClicks.set(position,true);
                    notifyDataSetChanged();
                    onBookNoteClickListener.OnClick(position);
                }
            });
        }
        holder.itemView.setTag(holder.img);
        //判断更改属性
        if(isClicks.get(position)){
            holder.img.setBackground(mContext.getDrawable(R.drawable.item_click));
        }else{
            holder.img.setBackground(mContext.getDrawable(R.drawable.item_unclick));
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {
        private TextView title;
        private ImageView img;
        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.item_tb_type_tv);
            img = (ImageView) view.findViewById(R.id.item_tb_type_img);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onBookNoteClickListener.OnClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            onBookNoteClickListener.OnLongClick(getAdapterPosition());
            return false;
        }
    }

    /**
     * 自定义分类选择接口
     */
    public interface OnBookNoteClickListener {
        void OnClick(int index);
        void OnLongClick(int index);
    }


}
