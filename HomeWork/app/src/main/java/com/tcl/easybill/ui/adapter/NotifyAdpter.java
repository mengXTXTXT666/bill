package com.tcl.easybill.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import com.tcl.easybill.R;


public class NotifyAdpter extends RecyclerView.Adapter<NotifyAdpter.MyViewHolder>{
    private Context context;
    private List<String> mdatas;
    private LayoutInflater mInflater;
    private OnNotifyClickListener onNotifyClickListener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.notify_item, parent, false);
        return new MyViewHolder(view);
    }

    public NotifyAdpter(Context context, List<String> datas) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mdatas = datas;
    }

    @Override
    public void onBindViewHolder(@NonNull NotifyAdpter.MyViewHolder holder, int position) {
        Log.i("----",mdatas.get(position));
        holder.tv.setText(mdatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mdatas.size();
    }

    /**
     * add new item
     * @param position
     * @param time
     */
    public void addData(int position, String time) {
        mdatas.add(position, time);
        notifyItemInserted(position);

    }

    /**
     * delete item
     * @param position
     */
    public void removeData(int position) {
        mdatas.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv;
        public MyViewHolder(View view){
            super(view);
            tv = (TextView) view.findViewById(R.id.tv_time);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            onNotifyClickListener.OnClick(getAdapterPosition());

        }
    }

    public void setOnNotifyClickListener(OnNotifyClickListener listener) {
        if (onNotifyClickListener == null)
            this.onNotifyClickListener = listener;
    }

    /**
     * onClick interface
     */
    public interface OnNotifyClickListener {
        void OnClick(int index);
    }
}
