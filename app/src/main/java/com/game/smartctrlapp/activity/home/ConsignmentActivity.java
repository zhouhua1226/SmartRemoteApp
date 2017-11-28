package com.game.smartctrlapp.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.game.smartctrlapp.R;
import com.game.smartctrlapp.base.BaseActivity;
import com.game.smartctrlapp.bean.VideoBackBean;
import com.game.smartctrlapp.utils.UrlUtils;
import com.game.smartctrlapp.utils.UserUtils;
import com.game.smartctrlapp.utils.Utils;
import com.game.smartctrlapp.view.GlideCircleTransform;
import com.game.smartctrlapp.view.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongxiu on 2017/10/18.
 */
public class ConsignmentActivity extends BaseActivity {
    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.consignment_rl)
    RelativeLayout consignmentRl;
    @BindView(R.id.shipping_button)
    Button shippingButton;
    @BindView(R.id.title_img)
    ImageView titleImg;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.times_tv)
    TextView timesTv;
    @BindView(R.id.information_tv)
    TextView informationTv;
    @BindView(R.id.text_tv)
    TextView textTv;
    @BindView(R.id.remark_et)
    EditText remarkEt;

    private String ATG="ConsignmentActivity--";
    private VideoBackBean videoBackBean;
    private String information="";

    @Override
    protected int getLayoutId() {
        //申请发货页面
        return R.layout.activity_consignment;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData(){
        videoBackBean=(VideoBackBean) getIntent().getExtras().getSerializable("sqfh");
        nameTv.setText(videoBackBean.getDOLLNAME());
        timesTv.setText(Utils.getTime(videoBackBean.getCREATETIME()));
        if(!Utils.isEmpty(UserUtils.UserAddress)) {
            informationTv.setText(UserUtils.UserAddress);
        }else {
            informationTv.setText("");
        }
        Glide.with(this)
                .load(UrlUtils.PICTUREURL+videoBackBean.getDOLL_URL())
                .dontAnimate()
                .transform(new GlideCircleTransform(this))
                .into(titleImg);

    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        initData();
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

    @OnClick({R.id.image_back, R.id.consignment_rl, R.id.shipping_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                this.finish();
                break;
            case R.id.consignment_rl:
                //新增地址
                startActivity(new Intent(this, NewAddressActivity.class));

                break;
            case R.id.shipping_button:
                information=informationTv.getText().toString();
                String remark=remarkEt.getText().toString();
                if(Utils.isEmpty(information)){
                    MyToast.getToast(this, "请设置收货信息！").show();
                }else {
                    MyToast.getToast(this, "发货成功，请耐心等待！").show();
                    finish();
                }



                break;
        }
    }



}
