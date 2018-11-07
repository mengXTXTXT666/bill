package com.tcl.easybill.ui.widget;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;


import com.tcl.easybill.R;

public class EditDialog extends Dialog {
    private String TAG = "EditDialog";
    private Activity mContext;
    private String tip = "请输入内容", titleName = "定时提醒";
    private Boolean isSet = false;
    private String input = "默认提醒内容";
    public  EditDialog(Activity context){
        super(context, R.style.ActionSheetDialogStyle);
        this.mContext = context;
    }

    @Override
    public void show() {
        super.show();
        final EditText editText = new EditText(mContext);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(titleName);
        builder.setMessage(tip);
        builder.setView(editText);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                input = editText.getText().toString();
                Log.v(TAG, input);
                isSet = true;
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "取消", Toast.LENGTH_LONG).show();
            }
        });
        builder.create().show();
    }


    public String getInput(){
        return this.input;
    }
    public void setTip(String string){
        this.tip = string;
    }
    public void setTitleName(String string){
        this.titleName = string;
    }
}
