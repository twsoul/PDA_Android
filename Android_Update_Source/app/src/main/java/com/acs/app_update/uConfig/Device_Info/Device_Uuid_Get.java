package com.acs.app_update.uConfig.Device_Info;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/*******************************************************************************
 *    Description  : PDA Android divice uuid get
 *    Usage        : Act_Splash_Main을 실행할때 가지고 오는 uuid 정보
 *
 *    사용 xml     :
 *    *****************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.06.03		JH_KIM                            CREATE
 *
 *******************************************************************************/

public class Device_Uuid_Get {

    protected static final String PREFS_FILE = "device_id.xml";
    protected static final String PREFS_DEVICE_ID = "device_id";
    protected volatile static UUID uuid;

    public Device_Uuid_Get(Context context) {
        if (uuid == null) {
            synchronized (Device_Uuid_Get.class) {
                if (uuid == null) {
                    final SharedPreferences prefs = context
                            .getSharedPreferences(PREFS_FILE, 0);
                    final String id = prefs.getString(PREFS_DEVICE_ID, null);
                    if (id != null) {
                        // Use the ids previously computed and stored in the
                        // prefs file
                        uuid = UUID.fromString(id);
                    } else {
                        final String androidId = Secure.getString(
                                context.getContentResolver(), Secure.ANDROID_ID);
                        // Use the Android ID unless it's broken, in which case
                        // fallback on deviceId,
                        // unless it's not available, then fallback on a random
                        // number which we store to a prefs file
                        if (!"9774d56d682e549c".equals(androidId)) {
                            uuid = UUID.nameUUIDFromBytes(androidId
                                    .getBytes(StandardCharsets.UTF_8));
                        } else {
                            @SuppressLint("MissingPermission") final String deviceId = (
                                    (TelephonyManager) context
                                            .getSystemService(Context.TELEPHONY_SERVICE))
                                    .getDeviceId();
                            uuid = deviceId != null ? UUID
                                    .nameUUIDFromBytes(deviceId
                                            .getBytes(StandardCharsets.UTF_8)) : UUID
                                    .randomUUID();
                        }
                        // Write the value out to the prefs file
                        prefs.edit()
                                .putString(PREFS_DEVICE_ID, uuid.toString())
                                .commit();
                    }
                }
            }
        }
    }

    public UUID getDeviceUuid() {
        return uuid;
    }
}


