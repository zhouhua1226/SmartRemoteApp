package com.tencent.tmgp.jjzww.wxapi;

import android.os.Bundle;
import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import butterknife.ButterKnife;

public class WXEntryActivity extends BaseActivity {


	private String TAG="WXEntryActivity";

	@Override
	protected int getLayoutId() {
		return R.layout.wxback_result;
	}

	@Override
	protected void afterCreate(Bundle savedInstanceState) {
		initView();


	}

	@Override
	protected void initView() {
		ButterKnife.bind(this);
	}


}