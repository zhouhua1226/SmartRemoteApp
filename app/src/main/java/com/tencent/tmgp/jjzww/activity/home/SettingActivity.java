package com.tencent.tmgp.jjzww.activity.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.utils.SPUtils;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongxiu on 2017/9/25.
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.image_kf)
    ImageButton imageKf;
    @BindView(R.id.money_rl)
    RelativeLayout moneyRl;
    @BindView(R.id.record_rl)
    RelativeLayout recordRl;
    @BindView(R.id.invitation_rl)
    RelativeLayout invitationRl;
    @BindView(R.id.feedback_rl)
    RelativeLayout feedbackRl;
    @BindView(R.id.gywm_rl)
    RelativeLayout gywmRl;
    @BindView(R.id.bt_out)
    Button btOut;
    @BindView(R.id.vibrator_control_layout)
    RelativeLayout vibratorControlLayout;
    @BindView(R.id.vibrator_control_imag)
    ImageView vibratorControlImag;

    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        setIsVibrator();
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

    @OnClick({R.id.image_back, R.id.image_kf, R.id.money_rl, R.id.record_rl, R.id.invitation_rl, R.id.feedback_rl, R.id.gywm_rl, R.id.bt_out,R.id.vibrator_control_layout,R.id.vibrator_control_imag})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.image_kf:
                Toast.makeText(this, "我是客服", Toast.LENGTH_SHORT).show();
                break;
            case R.id.money_rl:
                //我的游戏币
                startActivity(new Intent(this, GameCurrencyActivity.class));
                break;
            case R.id.record_rl:
                //我的主娃娃记录
                startActivity(new Intent(this, RecordActivity.class));
                break;
            case R.id.invitation_rl:
                //邀请码
                startActivity(new Intent(this, LnvitationCodeActivity.class));
                break;
            case R.id.feedback_rl:
                //问题反馈
                startActivity(new Intent(this, FeedBackActivity.class));
                break;
            case R.id.gywm_rl:
                //关于我们
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
            case R.id.bt_out:
                Toast.makeText(this, "退出登录", Toast.LENGTH_SHORT).show();
                SPUtils.remove(this, UserUtils.SP_TAG_LOGIN);
                UserUtils.UserPhone = "";
                Log.e("<<<<<<<<<<", "退出成功！");
                break;
            case R.id.vibrator_control_layout:
            case R.id.vibrator_control_imag:
                Utils.isVibrator = !Utils.isVibrator;
                editor.putBoolean("isVibrator", Utils.isVibrator);
                editor.commit();
                setBtnText(vibratorControlImag, Utils.isVibrator);
                break;

        }
    }

    private void setIsVibrator(){
        settings = getSharedPreferences("app_user", 0);
        editor = settings.edit();
        if (settings.contains("isVibrator")) {
            Utils.isVibrator = settings.getBoolean("isVibrator", true);
        }
        if (!Utils.isVibrator)
            vibratorControlImag.setSelected(false);
        else
            vibratorControlImag.setSelected(true);

    }

    private void setBtnText(ImageView btn, boolean isOpen) {
        if (isOpen)
            btn.setSelected(true);
        else
            btn.setSelected(false);
    }

}
