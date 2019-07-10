package com.acs.app_update.uCommon.Setting_EncryptAndDescrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/*******************************************************************************
 *    Description  : PDA Android EncryptAndDescrypt
 *    Usage        : EncryptAndDescrypt ..
 *                  AES 암호화 key = acscorp!mescorp! 자리 이다.
 *                  WCF에서도 같은 암호화를 사용하여
 *                  암호화 하여 web서비스를 통해 던져주고
 *                  wcf 에서는 복호화 하여 db에 파라미터를 던져준다.
 *    사용 xml     :
 *******************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.05.22		JH_KIM                            CREATE
 *
 *******************************************************************************/

public class EncryptAndDescrypt {

    //todo   고정 키값 입력
    private final String KEYNAME = "acscorp!mescorp!";
    private final String ALGORITHM = "AES";
    public static final String AES_ECB_NOPADDING = "AES/ECB/NoPadding";

    public String encrypt(final String source) throws Exception {
        byte[] eArr = null;
        SecretKeySpec skeySpec = new SecretKeySpec(KEYNAME.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_ECB_NOPADDING);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        eArr = cipher.doFinal(this.addPadding(source.getBytes()));
        return fromHex(eArr);
    }

    public byte[] encryptToByteArray(final String source) throws Exception {
        byte[] eArr = null;
        SecretKeySpec skeySpec = new SecretKeySpec(KEYNAME.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_ECB_NOPADDING);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        eArr = cipher.doFinal(this.addPadding(source.getBytes()));
        return eArr;
    }

    public String GetEncryptString(String source) throws Exception {
        StringBuilder sbResult = new StringBuilder();
        byte[] eArr = null;
        SecretKeySpec skeySpec = new SecretKeySpec(KEYNAME.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_ECB_NOPADDING);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        eArr = cipher.doFinal(this.addPadding(source.getBytes()));
        for (byte b : eArr) {
            sbResult.append(String.format("%02x", b));
        }
        return sbResult.toString();
    }

    public String decrypt(final String source) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ECB_NOPADDING);
        SecretKeySpec skeySpec = new SecretKeySpec(KEYNAME.getBytes(), ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] eArr = this.removePadding(cipher.doFinal(this.toBytes(source)));
        return new String(eArr);
    }

    private byte[] removePadding(final byte[] pBytes) {
        int pCount = pBytes.length;
        int index = 0;
        boolean loop = true;
        while (loop) {
            if (index == pCount || pBytes[index] == 0x00) {
                loop = false;
                index--;
            }
            index++;
        }
        byte[] tBytes = new byte[index];
        System.arraycopy(pBytes, 0, tBytes, 0, index);
        return tBytes;
    }

    private byte[] toBytes(final String pSource) {
        StringBuffer buff = new StringBuffer(pSource);
        int bCount = buff.length() / 2;
        byte[] bArr = new byte[bCount];
        for (int bIndex = 0; bIndex < bCount; bIndex++) {
            bArr[bIndex] = (byte) Long.parseLong(buff.substring(2 * bIndex, (2 * bIndex) + 2), 16);
        }
        return bArr;
    }

    private byte[] addPadding(final byte[] pBytes) {
        int pCount = pBytes.length;
        int tCount = pCount + (16 - (pCount % 16));
        byte[] tBytes = new byte[tCount];
        System.arraycopy(pBytes, 0, tBytes, 0, pCount);
        for (int rIndex = pCount; rIndex < tCount; rIndex++) {
            tBytes[rIndex] = 0x00;
        }
        return tBytes;
    }

    public String fromHex(byte[] pBytes) {
        int pCount = pBytes.length;
        StringBuffer buff = new StringBuffer(pCount * 2);
        for (int pIndex = 0; pIndex < pCount; pIndex++) {
            if (((int) pBytes[pIndex] & 0xff) < 0x10) {
                buff.append(0);
            }
            buff.append(Long.toString((int) pBytes[pIndex] & 0xff, 16));
        }
        return buff.toString();
    }


}
