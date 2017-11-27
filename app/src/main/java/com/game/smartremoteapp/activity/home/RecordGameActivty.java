package com.game.smartremoteapp.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.game.smartremoteapp.R;
import com.game.smartremoteapp.base.BaseActivity;
import com.game.smartremoteapp.bean.VideoBackBean;
import com.game.smartremoteapp.utils.UrlUtils;
import com.game.smartremoteapp.utils.Utils;
import com.game.smartremoteapp.view.GlideCircleTransform;
import com.game.smartremoteapp.view.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongxiu on 2017/10/18.
 */
public class RecordGameActivty extends BaseActivity {
    //未申请发货
    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.image_service)
    ImageButton imageService;
    @BindView(R.id.gamemoney_button)
    Button gamemoneyButton;
    @BindView(R.id.shipments_button)
    Button shipmentsButton;
    @BindView(R.id.title_img)
    ImageView titleImg;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.times_tv)
    TextView timesTv;
    @BindView(R.id.results_tv)
    TextView resultsTv;
    @BindView(R.id.mydoll_num_tv)
    TextView mydollNumTv;
    @BindView(R.id.mydoll_id_tv)
    TextView mydollIdTv;
    @BindView(R.id.mydoll_state_tv)
    TextView mydollStateTv;
    @BindView(R.id.mydoll_exchangenum_tv)
    TextView mydollExchangenumTv;
    @BindView(R.id.text_tv)
    TextView textTv;

    private VideoBackBean videoBackBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recordgame;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
    }


    private void initData(){
        videoBackBean=(VideoBackBean) getIntent().getExtras().getSerializable("record");
        nameTv.setText(videoBackBean.getDOLLNAME());
        timesTv.setText(Utils.getTime(videoBackBean.getCREATETIME()));
        mydollNumTv.setText(videoBackBean.getDOLLTOTAL()+"");
        mydollIdTv.setText(videoBackBean.getID());
        if(videoBackBean.getCONVERSIONGOLD()==0){
            mydollExchangenumTv.setText("100");
        }else {
            mydollExchangenumTv.setText(videoBackBean.getCONVERSIONGOLD()+"");
        }
        if (videoBackBean.getPOSTSTATE().equals("0")){
            mydollStateTv.setText("寄存中");
        }else {
            mydollStateTv.setText("已发货");
        }
        Glide.with(this)
                .load(UrlUtils.PICTUREURL+videoBackBean.getDOLL_URL())
                .dontAnimate()
                .transform(new GlideCircleTransform(this))
                .into(titleImg);


    }

    @Override
    protected void onResume() {
        super.onResume();
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

    @OnClick({R.id.image_back, R.id.image_service, R.id.gamemoney_button, R.id.shipments_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                this.finish();
                break;
            case R.id.image_service:
                MyToast.getToast(this, "我是客服按钮").show();
                break;
            case R.id.gamemoney_button:
                //兑换游戏币
                MyToast.getToast(this, "兑换游戏币").show();
                break;
            case R.id.shipments_button:
                //申请发货
                Intent intent=new Intent(this, ConsignmentActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("sqfh",videoBackBean);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }
}
