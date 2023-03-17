package cc.seeed.iot.util;

/**
 * Created by tenwong on 15/10/20.
 */
public class CommonUrl {
    public static String Server_In_Prefix = "http://192.168.1.40/seeedcc-develop/api/index.php?";
    public static String Server_Out_Prefix = "http://bazaar.seeed.cc/api/index.php?";
    public static String OTA_INTERNATIONAL_URL = "https://us.wio.seeed.io";
    public static String OTA_INTERNATIONAL_IP = "54.186.73.152";
    public static String OTA_CHINA_URL = "https://cn.wio.seeed.io";
    public static String OTA_CHINA_IP= "120.25.216.117";
    public static String OTA_SERVER_URL = OTA_INTERNATIONAL_URL;


    public static String Hinge_User_Login = "r=common/user/login";
    public static String Hinge_User_Register = "r=common/user/register";
    public static String Hinge_User_ForgetPwd = "r=common/user/forget-password";
    public static String Hinge_User_ResetPwd = "r=common/user/rest-password";
    public static String Hinge_User_ChangePwd = "r=common/user/modify-password";
    public static String Hinge_User_ChangeUserInfoUrl = "r=common/user/modify-user-message";
    public static String Hinge_User_OtherLoginUrl = "r=common/user/other-login";
    public static String Hinge_Get_NewVersion = "r=makermap/version/get-new-version-message";
    public static String Hinge_UpdateImgUrl = "r=makermap/upload/avater";
    public static String Hinge_StopServer = "r=wiolink/version/choose-server";
    public static String Hinge_User_Bind_Email = "r=wiolink/version/choose-server";
    public static String Hinge_UserBindPlatformUrl = "r=common/user/bind-platform";

    public static String Hinge_Set_Token = "/v1/ext_users";


    public static SmartValue<String> Server_Prefix = new SmartValue<>(Server_Out_Prefix, Server_In_Prefix);
    public static SmartValue<String> Image_Prefix = new SmartValue<>("http://bazaar.seeed.cc/","http://192.168.1.40/seeedcc-develop/");
    public static SmartValue<String> Update_Img_Url = new SmartValue<>(Server_Out_Prefix+Hinge_UpdateImgUrl,Server_In_Prefix+Hinge_UpdateImgUrl);
    public static SmartValue<String> Submmit_Feedback_Url = new SmartValue<>(Server_Out_Prefix+"r=makermap/feedback/add",Server_In_Prefix+"r=makermap/feedback/add");
  /*  public static String OTA_SERVER_URL = new SmartValue<String>(Server_Out_Ota_Url_Prefix, Server_In_Ota_Url_Prefix).getVal();
    public static String OTA_SERVER_IP = new SmartValue<String>(Server_Out_Ota_Ip_Prefix, Server_In_Ota_Ip_Prefix).getVal();*/

}
