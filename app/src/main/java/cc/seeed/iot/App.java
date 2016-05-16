package cc.seeed.iot;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.UnknownHostException;

import cc.seeed.iot.logic.UserLogic;
import cc.seeed.iot.util.CommonUrl;
import cc.seeed.iot.util.Constant;
import cc.seeed.iot.webapi.IotApi;

/**
 * Created by tenwong on 15/7/9.
 */
public class App extends com.activeandroid.app.Application {
    public static App sApp;
    private static SharedPreferences sp;
    private String ota_server_url;
    //    private String exchange_server_url;
    private String ota_server_ip;
    //    private String exchange_server_ip;
    private static String OLD_OTA_CHINA_URL = "https://cn.iot.seeed.cc";
    private static String OLD_OTA_INTERNATIONAL_URL = "https://iot.seeed.cc";

    /**
     * into smartconfig state
     */
    private Boolean configState;

    /**
     * login state
     */
    private Boolean loginState;

    private Boolean firstUseState;


    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        sp = this.getSharedPreferences("IOT", Context.MODE_PRIVATE);
        //   user.email = sp.getString("userName", "awong1900@163.com");
        //   user.user_key = sp.getString("userToken", "sBoKhjQNdtT8oTjukEeg98Ui3fuF3416zh-1Qm5Nkm0");
        ota_server_url = sp.getString("ota_server_url", CommonUrl.OTA_SERVER_URL); //https://iot.seeed.cc/v1 //https://cn.iot.seeed.cc/v1
        ota_server_ip = sp.getString("ota_server_ip", CommonUrl.OTA_SERVER_IP);
        configState = sp.getBoolean("configState", false);
        loginState = sp.getBoolean("loginState", false);
        firstUseState = sp.getBoolean("firstUseState", true);

        init();
        getIpAddress();
    }

    /**
     * 根据域名解析ip
     */
    public void getIpAddress() {
        if (ota_server_url.equals(CommonUrl.OTA_SERVER_URL)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    InetAddress address = null;
                    try {
                        address = InetAddress.getByName(CommonUrl.OTA_SERVER_URL);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    if (address != null) {
                        getSp().edit().putString("ota_server_ip", address.getHostAddress()).commit();
                    }
                }
            }).start();
        }
    }

    private void init() {
        int firstStart = getSp().getInt(Constant.APP_FIRST_START, 0);
        if (firstStart == 0) {
            if (OLD_OTA_CHINA_URL.equals(ota_server_url) || OLD_OTA_INTERNATIONAL_URL.equals(ota_server_url)){
                UserLogic.getInstance().logOut();
                getSp().edit().putString("ota_server_ip", CommonUrl.OTA_SERVER_URL).commit();
                ota_server_url = CommonUrl.OTA_SERVER_URL;
            }
        }
        IotApi.SetServerUrl(ota_server_url);
    }

    public static App getApp() {
        return sApp;
    }

    public static SharedPreferences getSp() {
        return sp;
    }

    public Boolean getLoginState() {
        return loginState;
    }

    public void setLoginState(Boolean loginState) {
        this.loginState = loginState;
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("loginState", loginState);
        editor.apply();
    }

    public static void showToastShrot(String str) {
        Toast toast = Toast.makeText(sApp, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToastLong(String str) {
        Toast toast = Toast.makeText(sApp, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public Boolean getFirstUseState() {
        return firstUseState;
    }

    public void setFirstUseState(Boolean firstUseState) {
        this.firstUseState = firstUseState;
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("firstUseState", firstUseState);
        editor.apply();
    }

  /*  public User getUser() {
        return user;
    }*/

  /*  public void setUser(User user) {
        this.user = user;
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("userName", user.email);
        editor.putString("userToken", user.user_key);
        editor.apply();
    }*/

    public String getOtaServerUrl() {
        return ota_server_url;
    }

    public void setOtaServerUrl(String ota_server_url) {
        this.ota_server_url = ota_server_url;
        IotApi.SetServerUrl(ota_server_url);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("ota_server_url", ota_server_url);
        editor.apply();
    }
/*

    public String getExchangeServerUrl() {
        return exchange_server_url;
    }

    public void setExchangeServerUrl(String exchange_server_url) {
        this.exchange_server_url = exchange_server_url;
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("exchange_server_url", exchange_server_url);
        editor.apply();
    }
*/

    public String getOtaServerIP() {
        return ota_server_ip;
    }

    public void setOtaServerIP(String ota_server_ip) {
        this.ota_server_ip = ota_server_ip;
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("ota_server_ip", ota_server_ip);
        editor.apply();
    }

    public void setConfigState(Boolean configState) {
        this.configState = configState;
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("configState", configState);
        editor.apply();
    }

    public boolean isDefaultServer() {
        if (CommonUrl.OTA_SERVER_URL.equals(ota_server_url)) {
            return true;
        } else {
            return false;
        }
    }

}