package com.game.smartremoteapp.activity.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.game.smartremoteapp.R;
import com.game.smartremoteapp.base.BaseActivity;
import com.game.smartremoteapp.bean.Result;
import com.game.smartremoteapp.model.http.HttpManager;
import com.game.smartremoteapp.model.http.RequestSubscriber;
import com.game.smartremoteapp.utils.UserUtils;
import com.game.smartremoteapp.utils.Utils;
import com.game.smartremoteapp.view.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongxiu on 2017/9/26.
 */
public class ChangNicknameAvtivity extends BaseActivity {
    private static final String TAG="ChangNicknameAvtivity-";
    @BindView(R.id.back_image_bt)
    ImageButton backImageBt;
    @BindView(R.id.nickname_et)
    EditText nicknameEt;
    @BindView(R.id.changen_image)
    ImageView changenImage;
    @BindView(R.id.save_bt)
    Button saveBt;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_changenickname;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        nicknameEt.setText(UserUtils.UserNickName);
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

    @OnClick({R.id.back_image_bt, R.id.save_bt,R.id.changen_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_image_bt:
                finish();
                break;
            case R.id.save_bt:
                break;
            case R.id.changen_image:
                    nicknameEt.setText("");
                break;
            default:break;
        }
    }

}
