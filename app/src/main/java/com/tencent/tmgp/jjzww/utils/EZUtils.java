package com.tencent.tmgp.jjzww.utils;

import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.openapi.bean.EZDeviceInfo;


public class EZUtils {

    public static String APP_KEY = "06fb051f38cb43d8b3d18397e006d13d";//"081e953ec0c8413c9c218936062de1dc";//"27155a2b15004f4f92667c899e28bc12";
    public static String SECRET = "d094f908d5048ae2c6aebc60e5039916";//"940e15b92e800a84ed3f35a8992e7fd9";
    //设置设备状态
    public static int EZ_OFFLINE = 2;
    public static int EZ_ONLINE = 1;
    /**
     * 通过ezdevice 得到其中通道信息
     * @param deviceInfo
     * @return
     */
    public static EZCameraInfo getCameraInfoFromDevice(EZDeviceInfo deviceInfo, int camera_index) {
        if (deviceInfo == null) {
            return null;
        }
        if (deviceInfo.getCameraNum() > 0 && deviceInfo.getCameraInfoList() != null && deviceInfo.getCameraInfoList().size() > camera_index) {
            return deviceInfo.getCameraInfoList().get(camera_index);
        }
        return null;
    }
}
