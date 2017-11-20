package com.game.smartremoteapp.activity.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.game.smartremoteapp.R;
import com.game.smartremoteapp.base.BaseActivity;
import com.game.smartremoteapp.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongxiu on 2017/9/26.
 */
public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.tv_about_phone_name)
    TextView tvAboutPhoneName;
    @BindView(R.id.tv_about_version)
    TextView tvAboutVersion;
    @BindView(R.id.tv_about_company)
    TextView tvAboutCompany;
    @BindView(R.id.tv_about_website)
    TextView tvAboutWebsite;

    //关于我们
    @Override
    protected int getLayoutId() {
        return R.layout.activity_aboutus;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        tvAboutPhoneName.setText("【第一抓娃娃】客服热线: 400-821-5561");
        tvAboutVersion.setText("当前版本:"+"1.0.0");
        tvAboutCompany.setText("版权所有:"+"上海炎亿网络科技有限公司");
        tvAboutWebsite.setText("http://www.111WAWA.com");

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

    @OnClick({R.id.image_back,R.id.tv_about_website})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                this.finish();
                break;
//            case R.id.submit_bt:
//                Toast.makeText(this, "我明白是什么功能", Toast.LENGTH_SHORT).show();
//                break;
        }
    }


}
