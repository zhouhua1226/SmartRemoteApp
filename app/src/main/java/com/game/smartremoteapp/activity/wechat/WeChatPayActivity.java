package com.game.smartremoteapp.activity.wechat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.game.smartremoteapp.R;
import com.game.smartremoteapp.base.BaseActivity;
import com.game.smartremoteapp.view.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongxiu on 2017/10/10.
 */
public class WeChatPayActivity extends BaseActivity {
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.amount_tv)
    TextView amountTv;
    @BindView(R.id.btn_ok)
    Button btnOk;
    private Intent intent;
    private String money;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wechatpay;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        intent=getIntent();
        money=intent.getStringExtra("money");
        amountTv.setText(money);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_back, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                this.finish();
                break;
            case R.id.btn_ok:
                MyToast.getToast(this,money+"元").show();
                break;
        }
    }
}
