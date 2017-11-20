package com.game.smartremoteapp.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.game.smartremoteapp.R;
import com.game.smartremoteapp.activity.wechat.WeChatPayActivity;
import com.game.smartremoteapp.base.BaseActivity;
import com.game.smartremoteapp.view.FillingCurrencyDialog;
import com.game.smartremoteapp.view.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongxiu on 2017/11/15.
 */
public class SelectRechargeTypeActiivty extends BaseActivity {
    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.image_service)
    ImageButton imageService;
    @BindView(R.id.select_account_tv)
    TextView selectAccountTv;
    @BindView(R.id.select_money_tv)
    TextView selectMoneyTv;
    @BindView(R.id.layout_wechat)
    RelativeLayout layoutWechat;
    private FillingCurrencyDialog fillingCurrencyDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_selectrechargetype;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        selectAccountTv.setText("15335756655");
        selectMoneyTv.setText("1000");

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

    @OnClick({R.id.image_back, R.id.image_service, R.id.layout_wechat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.image_service:
                MyToast.getToast(this,"我是客服").show();
                break;
            case R.id.layout_wechat:
                getMoney();

                break;
        }
    }

    private void getMoney() {
        fillingCurrencyDialog = new FillingCurrencyDialog(this, R.style.easy_dialog_style);
        fillingCurrencyDialog.show();
        fillingCurrencyDialog.setDialogClickListener(myDialogClick);
    }

    private FillingCurrencyDialog.MyDialogClick myDialogClick = new FillingCurrencyDialog.MyDialogClick() {
        @Override
        public void getMoney10(String money) {
            Intent intent = new Intent(SelectRechargeTypeActiivty.this, WeChatPayActivity.class);
            intent.putExtra("money", money);
            startActivity(intent);
        }

        @Override
        public void getMoney20(String money) {
            Intent intent = new Intent(SelectRechargeTypeActiivty.this, WeChatPayActivity.class);
            intent.putExtra("money", money);
            startActivity(intent);
        }

        @Override
        public void getMoney50(String money) {
            Intent intent = new Intent(SelectRechargeTypeActiivty.this, WeChatPayActivity.class);
            intent.putExtra("money", money);
            startActivity(intent);
        }

        @Override
        public void getMoney100(String money) {
            Intent intent = new Intent(SelectRechargeTypeActiivty.this, WeChatPayActivity.class);
            intent.putExtra("money", money);
            startActivity(intent);
        }
    };
}
