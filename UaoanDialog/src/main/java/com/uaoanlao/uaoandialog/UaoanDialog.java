package com.uaoanlao.uaoandialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UaoanDialog {
    private AlertDialog.Builder tc;
    private AlertDialog.Builder loading;
    private View view;
    private Activity activity;
    private AlertDialog show;
    private LinearLayout layout;
    private int TYPE_DIALOG=1; //对话框类型 1=普通对话框 2=自定义对话框
    public UaoanDialog Dialog(Activity context){
        activity=context;
        view=context.getLayoutInflater().inflate(R.layout.dialog_uaoan_layout,null);
        layout=view.findViewById(R.id.layout);
        tc=new AlertDialog.Builder(context)
                .setView(view);
        return this;
    }

    public UaoanDialog BottomDialog(Activity context){
        activity=context;
        view=context.getLayoutInflater().inflate(R.layout.dialog_uaoan_layout,null);
        layout=view.findViewById(R.id.layout);
        tc=new AlertDialog.Builder(context)
                .setView(view);
        return this;
    }

    public UaoanDialog setLoadingDialog(Activity context,OnLoadingDialog loadingDialog){
        onLoadingDialog=loadingDialog;
        activity=context;
        View vw=context.getLayoutInflater().inflate(R.layout.loading_dialog,null);
        loading=new AlertDialog.Builder(context)
                .setView(vw);
        TextView title=vw.findViewById(R.id.title);
       AlertDialog sh=loading.show();
        sh.setCanceledOnTouchOutside(false);
        sh.getWindow().setBackgroundDrawable(new ColorDrawable());
        onLoadingDialog.onCreate(sh,title);
        sh.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                onLoadingDialog.onKey(dialog,keyCode,event);
                return true;
            }
        });
        return this;
    }
    private OnLoadingDialog onLoadingDialog;
    public interface OnLoadingDialog{
        void onCreate(AlertDialog loading, TextView title);
        void onKey(DialogInterface dialog, int keyCode, KeyEvent event);
    }
    public UaoanDialog setTitle(String nr){
        TextView title=view.findViewById(R.id.title);
        title.setText(nr);

        return this;
    }
    public UaoanDialog setMessage(String nr){
        TYPE_DIALOG=1;
        TextView con=new TextView(activity);
        con.setText(nr);
        getTextSet(con);
        con.setTextSize(dp2px(5.5F));
        con.setTextColor(Color.parseColor("#99000000"));
        con.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params=new WindowManager.LayoutParams();
        params.height= ViewGroup.LayoutParams.WRAP_CONTENT;
        params.width= ViewGroup.LayoutParams.MATCH_PARENT;
        con.setLayoutParams(params);
        layout.addView(con);
        return this;
    }

    //设置文本可长按选择和链接可点击
    private void getTextSet(TextView textView){
        textView.setSelected(true);
        // textView.setSelectAllOnFocus(true);
        textView.setTextIsSelectable(true);
        Linkify.addLinks(textView, Linkify.WEB_URLS);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public UaoanDialog addView(OnViewLayout vi){
        TYPE_DIALOG=2;
        onViewLayout=vi;
        onViewLayout.onView(view,this);

        return this;
    }
    public UaoanDialog addView(View con,int g){
        TYPE_DIALOG=2;
        if (g!=ViewGroup.LayoutParams.WRAP_CONTENT){
            WindowManager.LayoutParams params=new WindowManager.LayoutParams();
            params.height= dp2px(g);
            params.width= ViewGroup.LayoutParams.MATCH_PARENT;
            con.setLayoutParams(params);
        }else {
            WindowManager.LayoutParams params=new WindowManager.LayoutParams();
            params.height= ViewGroup.LayoutParams.WRAP_CONTENT;
            params.width= ViewGroup.LayoutParams.MATCH_PARENT;
            con.setLayoutParams(params);
        }

        layout.addView(con);

        return this;
    }

    private OnViewLayout onViewLayout;
    public interface OnViewLayout{
        void onView(View vv, UaoanDialog tc);
    }
    public UaoanDialog setOnOKClickListener(String nr,OnOKClickListener onok){
        okClickListener=onok;
        TextView ok=view.findViewById(R.id.ok_text);
        ok.setText(nr);
        LinearLayout oklin=view.findViewById(R.id.ok);
        oklin.setVisibility(View.VISIBLE);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okClickListener.onClick(v,show);
            }
        });

        return this;
    }
    public UaoanDialog setOnOKClickListener(String nr,int color,OnOKClickListener onok){
        okClickListener=onok;
        TextView ok=view.findViewById(R.id.ok_text);
        ok.setTextColor(color);
        ok.setText(nr);
        LinearLayout oklin=view.findViewById(R.id.ok);
        oklin.setVisibility(View.VISIBLE);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okClickListener.onClick(v,show);
            }
        });

        return this;
    }

    private OnOKClickListener okClickListener;
    public interface OnOKClickListener{
        void onClick(View v, AlertDialog tc);
    }

    public UaoanDialog setOnNOClickListener(String nr,OnNOClickListener onno){
        noClickListener=onno;
        TextView no=view.findViewById(R.id.no_text);
        no.setText(nr);
        LinearLayout nolin=view.findViewById(R.id.no);
        nolin.setVisibility(View.VISIBLE);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noClickListener.onClick(v,show);
            }
        });

        return this;
    }

    public UaoanDialog setOnNOClickListener(String nr,int color,OnNOClickListener onno){
        noClickListener=onno;
        TextView no=view.findViewById(R.id.no_text);
        no.setTextColor(color);
        no.setText(nr);
        LinearLayout nolin=view.findViewById(R.id.no);
        nolin.setVisibility(View.VISIBLE);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noClickListener.onClick(v,show);
            }
        });

        return this;
    }

    private OnNOClickListener noClickListener;
    public interface OnNOClickListener{
        void onClick(View v, AlertDialog tc);
    }

   /* public UaoanDialog setOKVisibility(int ks){
        LinearLayout ok=view.findViewById(R.id.ok);
        ok.setVisibility(ks);

        return this;
    }

    public UaoanDialog setNOVisibility(int ks){
        LinearLayout no=view.findViewById(R.id.no);
        no.setVisibility(ks);

        return this;
    }*/
    public UaoanDialog show(){
        AlertDialog sh=tc.show();
        show=sh;
        if (TYPE_DIALOG==1){
            setDialogSize(250);
        }else {
            setDialogSize(300);
        }
        show.getWindow().setBackgroundDrawable(new ColorDrawable());
        return this;
    }

    public UaoanDialog dismiss(){
        show.dismiss();

        return this;
    }

    private int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, activity.getResources().getDisplayMetrics());
    }
    //Dialog弹窗大小
    private void setDialogSize(int size){
        if (size!=0){
            show.getWindow().setLayout(dp2px(size), LinearLayout.LayoutParams.WRAP_CONTENT);
        }else{
            show.getWindow().setLayout(dp2px(300), LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }
    public UaoanDialog setCancelable(boolean bo){
        show.setCancelable(bo);
        return this;
    }
}
