package com.game.smartremoteapp.activity.home;

import android.content.Intent;
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
 * Created by hongxiu on 2017/10/18.
 */
public class RecordGameActivty extends BaseActivity{
    //未申请发货
    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.image_service)
    ImageButton imageService;
    @BindView(R.id.gamemoney_button)
    Button gamemoneyButton;
    @BindView(R.id.shipments_button)
    Button shipmentsButton;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recordgame;
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

    @OnClick({R.id.image_back, R.id.image_service, R.id.gamemoney_button, R.id.shipments_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                this.finish();
                break;
            case R.id.image_service:
                MyToast.getToast(this,"我是客服按钮").show();
                break;
            case R.id.gamemoney_button:
                //兑换游戏币
                MyToast.getToast(this,"兑换游戏币").show();
                break;
            case R.id.shipments_button:
                //申请发货
                startActivity(new Intent(this,ConsignmentActivity.class));
                break;
        }
    }
}
