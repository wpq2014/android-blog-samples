package com.wpq.sample.progress_dialog;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_normalProgressDialog)
    Button mBtnNormalProgressDialog;
    @BindView(R.id.btn_customProgressDialog)
    Button mBtnCustomProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_show, R.id.btn_normalProgressDialog, R.id.btn_customProgressDialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_show:
                test();
                break;
            case R.id.btn_normalProgressDialog:
                startActivity(NormalProgressDialogActivity.newIntent(this));
                break;
            case R.id.btn_customProgressDialog:
                startActivity(CustomProgressDialogActivity.newIntent(this));
                break;
        }
    }

    private void test() {
        final ProgressDialog pd1 = ProgressDialog.show(this, "网络请求", "加载中...");
        // 4s后dismiss
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pd1.dismiss();
            }
        }, 4000);

        // 在1.5s的时候再次调用show
        final ProgressDialog pd2 = new ProgressDialog(this);
        pd2.setMessage("loading...");
//        pd2.setView(LayoutInflater.from(this).inflate(R.layout.dialog_custom_progress, null));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pd2.show();
            }
        }, 1500);
        // 3s时pd2 dismiss
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pd2.dismiss();
            }
        }, 3000);
    }
}
