package com.game.smartremoteapp.activity.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.game.smartremoteapp.R;
import com.game.smartremoteapp.base.BaseActivity;
import com.game.smartremoteapp.view.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongxiu on 2017/10/23.
 */
public class NewAddressActivity extends BaseActivity {
    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.preserve_button)
    Button preserveButton;

    @Override
    protected int getLayoutId() {
        //新增地址页面
        return R.layout.activity_newaddress;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.image_back, R.id.preserve_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                this.finish();
                break;
            case R.id.preserve_button:
                MyToast.getToast(this,"保存").show();
                break;
        }
    }
}
