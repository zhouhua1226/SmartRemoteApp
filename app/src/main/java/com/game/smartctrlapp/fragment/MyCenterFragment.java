package com.game.smartctrlapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.bumptech.glide.Glide;
import com.game.smartctrlapp.R;
import com.game.smartctrlapp.activity.home.InformationActivity;
import com.game.smartctrlapp.activity.home.RecordGameActivty;
import com.game.smartctrlapp.activity.home.RecordGameTwoActivity;
import com.game.smartctrlapp.activity.home.SelectRechargeTypeActiivty;
import com.game.smartctrlapp.activity.home.SettingActivity;
import com.game.smartctrlapp.activity.wechat.WeChatPayActivity;
import com.game.smartctrlapp.adapter.MyCenterAdapter;
import com.game.smartctrlapp.base.BaseFragment;
import com.game.smartctrlapp.bean.LoginInfo;
import com.game.smartctrlapp.bean.Result;
import com.game.smartctrlapp.bean.VideoBackBean;
import com.game.smartctrlapp.model.http.HttpManager;
import com.game.smartctrlapp.model.http.RequestSubscriber;
import com.game.smartctrlapp.utils.UserUtils;
import com.game.smartctrlapp.utils.Utils;
import com.game.smartctrlapp.view.FillingCurrencyDialog;
import com.game.smartctrlapp.view.GlideCircleTransform;
import com.game.smartctrlapp.view.MyToast;
import com.game.smartctrlapp.view.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by hongxiu on 2017/9/25.
 */
public class MyCenterFragment extends BaseFragment {

    private static final String TAG = "MyCenterFragment";
    Unbinder unbinder;
    @BindView(R.id.image_kefu)
    ImageButton imageKefu;
    @BindView(R.id.image_setting)
    ImageButton imageSetting;
    @BindView(R.id.user_image)
    ImageView userImage;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_number)
    TextView userNumber;
    @BindView(R.id.user_filling)
    TextView userFilling;
    @BindView(R.id.mycenter_recyclerview)
    RecyclerView mycenterRecyclerview;
    @BindView(R.id.header)
    RecyclerViewHeader header;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    private FillingCurrencyDialog fillingCurrencyDialog;
    private MyCenterAdapter myCenterAdapter;
    private List<String> list;
    private List<VideoBackBean> videoList=new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_center;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        Glide.get(getContext()).clearMemory();

        initlist();
        initData();
        onClick();
    }

    private void onClick() {
        myCenterAdapter.setOnItemClickListener(new MyCenterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getContext(), RecordGameActivty.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("record",videoList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                startActivity(new Intent(getContext(), RecordGameTwoActivity.class));
            }
        });

//        下拉刷新
        swiperefresh.setColorSchemeResources(R.color.pink);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swiperefresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getUserImageAndName();
                        initlist();
                        myCenterAdapter.notifyDataSetChanged();
                        swiperefresh.setRefreshing(false);
                    }
                }, 2000);
            }
        });

    }

    private void initData() {
        myCenterAdapter = new MyCenterAdapter(getContext(), videoList);
        mycenterRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mycenterRecyclerview.addItemDecoration(new SpaceItemDecoration(15));
        mycenterRecyclerview.setAdapter(myCenterAdapter);
        header.attachTo(mycenterRecyclerview);

    }

    private void initlist() {
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + "");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserImageAndName();
        getVideoBackList(userName.getText().toString());
    }

    private void getUserImageAndName(){
        if(!Utils.isEmpty(UserUtils.UserPhone)) {
            if (!UserUtils.UserName.equals("")) {
                userName.setText(UserUtils.UserName);
            } else {
                userName.setText(UserUtils.UserPhone);
            }
            Glide.with(getContext())
                    .load(UserUtils.UserImage)
                    .dontAnimate()
                    .transform(new GlideCircleTransform(getContext()))
                    .into(userImage);
        }else {
            userName.setText("请登录");
            userImage.setImageResource(R.drawable.round);
        }
    }

    @OnClick({R.id.image_kefu, R.id.image_setting, R.id.user_image, R.id.user_filling,R.id.user_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_kefu:
                MyToast.getToast(getContext(), "我是客服").show();
                break;
            case R.id.image_setting:
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            case R.id.user_image:
                startActivity(new Intent(getContext(), InformationActivity.class));
                break;
            case R.id.user_filling:
                startActivity(new Intent(getContext(), SelectRechargeTypeActiivty.class));
//                getMoney();
                break;
            case R.id.user_name:
                //此处添加登录dialog
                break;
            default:
                break;
        }
    }

    private void getMoney() {
        fillingCurrencyDialog = new FillingCurrencyDialog(getContext(), R.style.easy_dialog_style);
        fillingCurrencyDialog.show();
        fillingCurrencyDialog.setDialogClickListener(myDialogClick);
    }

    private FillingCurrencyDialog.MyDialogClick myDialogClick = new FillingCurrencyDialog.MyDialogClick() {
        @Override
        public void getMoney10(String money) {
            Intent intent = new Intent(getContext(), WeChatPayActivity.class);
            intent.putExtra("money", money);
            getContext().startActivity(intent);
        }

        @Override
        public void getMoney20(String money) {
            Intent intent = new Intent(getContext(), WeChatPayActivity.class);
            intent.putExtra("money", money);
            getContext().startActivity(intent);
        }

        @Override
        public void getMoney50(String money) {
            Intent intent = new Intent(getContext(), WeChatPayActivity.class);
            intent.putExtra("money", money);
            getContext().startActivity(intent);
        }

        @Override
        public void getMoney100(String money) {
            Intent intent = new Intent(getContext(), WeChatPayActivity.class);
            intent.putExtra("money", money);
            getContext().startActivity(intent);
        }
    };

    private void getVideoBackList(String userName) {
        HttpManager.getInstance().getVideoBackList(userName, new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> result) {
                videoList= result.getData().getPlayback();
                Utils.showLogE("视频列表", "list=" + result.getMsg()+"="+videoList.size());
                userNumber.setText("累积抓中"+videoList.size()+"次");
                if(videoList.size()!=0) {
                    myCenterAdapter.notify(videoList);
                }else {
                    mycenterRecyclerview.setVisibility(View.GONE);

                }
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }


}