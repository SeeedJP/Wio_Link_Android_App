package cc.seeed.iot.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.umeng.analytics.MobclickAgent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cc.seeed.iot.App;
import cc.seeed.iot.R;
import cc.seeed.iot.entity.ServerBean;
import cc.seeed.iot.logic.SystemLogic;
import cc.seeed.iot.util.CommonUrl;
import cc.seeed.iot.util.Constant;
import cc.seeed.iot.util.DialogUtils;
import cc.seeed.iot.util.NetworkUtils;
import cc.seeed.iot.util.RegularUtils;
import cc.seeed.iot.view.FontEditView;
import cc.seeed.iot.view.FontTextView;

/**
 * author: Jerry on 2016/6/2 17:29.
 * description:
 */
public class SelectServerActivity extends BaseActivity implements TextWatcher {
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.mRbGlobalServer)
    RadioButton mRbGlobalServer;
    @InjectView(R.id.mLlGlobalServer)
    LinearLayout mLlGlobalServer;
    @InjectView(R.id.mRbChineseServer)
    RadioButton mRbChineseServer;
    @InjectView(R.id.mLlChineseServer)
    LinearLayout mLlChineseServer;
    @InjectView(R.id.mRbJapanServer)
    RadioButton mRbJapanServer;
    @InjectView(R.id.mLlJapanServer)
    LinearLayout mLlJapanServer;
    @InjectView(R.id.mRbCustomizeServer)
    RadioButton mRbCustomizeServer;
    @InjectView(R.id.mLlCustomizeServer)
    LinearLayout mLlCustomizeServer;
    @InjectView(R.id.mEtCustomServer)
    FontEditView mEtCustomServer;
    @InjectView(R.id.mLLSave)
    LinearLayout mLLSave;
    @InjectView(R.id.mTvSave)
    FontTextView mTvSave;

    private String changeServer = "";
    private String serverUrl;
    ProgressDialog dialog;
    ServerBean serverBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_server);
        ButterKnife.inject(this);

        initView();
        initData();
    }

    private void initView() {
        mEtCustomServer.addTextChangedListener(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.select_server_activity_title);
    }

    private void initData() {
        serverUrl = App.getApp().getOtaServerUrl();
        serverBean = SystemLogic.getInstance().getServerBean();
        changeServer = serverUrl;
        if (CommonUrl.OTA_INTERNATIONAL_URL.equals(serverUrl)) {
            mRbGlobalServer.setChecked(true);
            mRbChineseServer.setChecked(false);
            mRbJapanServer.setChecked(false);
            mRbCustomizeServer.setChecked(false);
            mEtCustomServer.setEnabled(false);
        } else if (CommonUrl.OTA_CHINA_URL.equals(serverUrl)) {
            mRbGlobalServer.setChecked(false);
            mRbChineseServer.setChecked(true);
            mRbJapanServer.setChecked(false);
            mRbCustomizeServer.setChecked(false);
            mEtCustomServer.setEnabled(false);
        } else if (CommonUrl.OTA_JAPAN_URL.equals(serverUrl)) {
            mRbGlobalServer.setChecked(false);
            mRbChineseServer.setChecked(false);
            mRbJapanServer.setChecked(true);
            mRbCustomizeServer.setChecked(false);
            mEtCustomServer.setEnabled(false);
        } else {
            mRbGlobalServer.setChecked(false);
            mRbChineseServer.setChecked(false);
            mRbJapanServer.setChecked(false);
            mRbCustomizeServer.setChecked(true);
            mEtCustomServer.setEnabled(true);
            if (TextUtils.isEmpty(serverUrl)) {
                mEtCustomServer.setText(App.getSp().getString(Constant.SP_HISTORY_IP, ""));
            } else {
                mEtCustomServer.setText(serverUrl);
            }
            mEtCustomServer.setSelection(serverUrl.length());
        }
    }

    @OnClick({R.id.mLlGlobalServer, R.id.mLlChineseServer, R.id.mLlJapanServer, R.id.mLlCustomizeServer, R.id.mLLSave,
            R.id.mRbGlobalServer, R.id.mRbChineseServer, R.id.mRbJapanServer, R.id.mRbCustomizeServer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mRbGlobalServer:
            case R.id.mLlGlobalServer:
                mRbGlobalServer.setChecked(true);
                mRbChineseServer.setChecked(false);
                mRbJapanServer.setChecked(false);
                mRbCustomizeServer.setChecked(false);
                mEtCustomServer.setEnabled(false);
                hideKeyboard(mEtCustomServer);
                changeServer = CommonUrl.OTA_INTERNATIONAL_URL;
                isChangServer();
                saveIpAdress();
                break;
            case R.id.mRbChineseServer:
            case R.id.mLlChineseServer:
                mRbGlobalServer.setChecked(false);
                mRbChineseServer.setChecked(true);
                mRbJapanServer.setChecked(false);
                mRbCustomizeServer.setChecked(false);
                mEtCustomServer.setEnabled(false);
                hideKeyboard(mEtCustomServer);
                changeServer = CommonUrl.OTA_CHINA_URL;
                isChangServer();
                saveIpAdress();
                break;
            case R.id.mRbJapanServer:
            case R.id.mLlJapanServer:
                mRbGlobalServer.setChecked(false);
                mRbChineseServer.setChecked(false);
                mRbJapanServer.setChecked(true);
                mRbCustomizeServer.setChecked(false);
                mEtCustomServer.setEnabled(false);
                hideKeyboard(mEtCustomServer);
                changeServer = CommonUrl.OTA_JAPAN_URL;
                isChangServer();
                saveIpAdress();
                break;
            case R.id.mRbCustomizeServer:
            case R.id.mLlCustomizeServer:
                if (CommonUrl.OTA_INTERNATIONAL_URL.equals(serverUrl) || CommonUrl.OTA_CHINA_URL.equals(serverUrl) || CommonUrl.OTA_JAPAN_URL.equals(serverUrl)) {
                    mEtCustomServer.setText(App.getSp().getString(Constant.SP_HISTORY_IP, ""));
                }
                mRbGlobalServer.setChecked(false);
                mRbChineseServer.setChecked(false);
                mRbJapanServer.setChecked(false);
                mRbCustomizeServer.setChecked(true);
                mEtCustomServer.setEnabled(true);
                changeServer = mEtCustomServer.getText().toString().trim();
                isChangServer();
                break;
            case R.id.mLLSave:
                hideKeyboard(mEtCustomServer);
                if (isChangServer()) {
                    saveIpAdress();
                    setTextSize(false);
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().length() == 0) {
            setTextSize(true);
        } else {
            setTextSize(false);
            changeServer = s.toString();
        }
        setTextColor(RegularUtils.isWebsite(s.toString()));
        isChangServer();
    }


    public void setTextSize(boolean isSmall) {
        if (isSmall) {
            mEtCustomServer.setTextSize(13);
        } else {
            mEtCustomServer.setTextSize(14);
        }
    }

    public void setTextColor(boolean isOk) {
        if (isOk) {
            mEtCustomServer.setTextColor(Color.parseColor("#333333"));
        } else {
            mEtCustomServer.setTextColor(Color.parseColor("#d0021b"));
        }
    }

    public boolean isChangServer() {
        if (serverUrl.equals(changeServer)) {
            mTvSave.setTextColor(Color.parseColor("#ADB0C3"));
            return false;
        } else {
            mTvSave.setTextColor(Color.parseColor("#ffffff"));
            return true;
        }
    }

    public void saveIpAdress() {
        if (CommonUrl.OTA_INTERNATIONAL_URL.equals(changeServer)) {
            saveUrlAndIp(CommonUrl.OTA_INTERNATIONAL_URL, CommonUrl.OTA_INTERNATIONAL_IP);
            finish();
        } else if (CommonUrl.OTA_CHINA_URL.equals(changeServer)) {
            saveUrlAndIp(CommonUrl.OTA_CHINA_URL, CommonUrl.OTA_CHINA_IP);
            finish();
        } else {
            if (!RegularUtils.isWebsite(changeServer)) {
                App.showToastShrot(getString(R.string.website_format_error));
                return;
            }
            getIpAddress(this, changeServer);
        }
    }

    public void getIpAddress(final Activity context, final String url) {
        final Dialog progressDialog = DialogUtils.showProgressDialog(context, context.getResources().getString(R.string.hint_get_host_address));
        NetworkUtils.getIpAddress(context, NetworkUtils.getDomainName(url), new NetworkUtils.OnIpCallback() {
            @Override
            public void okCallback(String ip) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                    saveUrlAndIp(changeServer, ip);
                    if (!CommonUrl.OTA_JAPAN_URL.equals(changeServer)) {
                        App.getSp().edit().putString(Constant.SP_HISTORY_IP, changeServer).commit();
                    }
                    finish();
                }
            }

            @Override
            public void failCallback(String error) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                    DialogUtils.showErrorDialog(context, "Error", "OK", "", error, new DialogUtils.OnErrorButtonClickListenter() {
                        @Override
                        public void okClick() {

                        }

                        @Override
                        public void cancelClick() {

                        }
                    });
                }
            }
        });
    }

    private void saveUrlAndIp(String url, String ip) {
        App.getApp().saveUrlAndIp(url, ip);
    }

    private boolean isOverdue() {
        long nowTime = System.currentTimeMillis();
        long targetTime = stringToLong();
        if (nowTime <= targetTime) {

            return true;
        } else {
            return false;
        }
    }

    public long stringToLong() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = formatter.parse("2016-09-01 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
            date = null;
        }
        return date.getTime();
    }


    @Override
    public String[] monitorEvents() {
        return new String[]{Cmd_Stop_server};
    }

    @Override
    public void onEvent(String event, boolean ret, String errInfo, Object[] data) {
        if (Cmd_Stop_server.equals(event)) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            if (ret) {
                serverBean = SystemLogic.getInstance().getServerBean();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
