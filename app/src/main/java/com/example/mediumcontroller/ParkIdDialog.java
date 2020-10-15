package com.example.mediumcontroller;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;


class ParkIdDialog extends Dialog {

    private Context context;
    private EditText parkIdInputEditText;
    private Button parkIdOkButton;
    private String parkId;
    private OnCloseListener listener;

    public ParkIdDialog(@NonNull Context context) {
        super(context);
    }

    public ParkIdDialog(Context context, OnCloseListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_parkid);
        setCanceledOnTouchOutside(false);
        initView();
    }

    // 初始化弹窗
    private void initView() {
        parkIdInputEditText = findViewById(R.id.parkIdInputEditText);
        parkIdOkButton = findViewById(R.id.parkIdOkButton);

        parkIdOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parkId = parkIdInputEditText.getText().toString();
                if (listener != null) {
                    listener.onClick(parkId);
                    if (StringUtils.isEmpty(parkId)) {
                        Toast.makeText(context, "请填写有效的停车场Id", Toast.LENGTH_SHORT).show();
                    }else{
                        dismiss();
                    }
                }
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public interface OnCloseListener {
        void onClick(String parkId);
    }

}
