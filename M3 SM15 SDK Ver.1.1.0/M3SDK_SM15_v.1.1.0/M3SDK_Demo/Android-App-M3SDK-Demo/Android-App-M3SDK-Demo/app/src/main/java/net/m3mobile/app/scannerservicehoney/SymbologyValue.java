package net.m3mobile.app.scannerservicehoney;

public class SymbologyValue {

    private SymbologyValue() {
    }

    public final class SymbologyFlags {
        public static final int SYMBOLOGY_ENABLE = 1;
        public static final int SYMBOLOGY_CHECK_ENABLE = 2;
        public static final int SYMBOLOGY_CHECK_TRANSMIT = 4;
        public static final int SYMBOLOGY_START_STOP_XMIT = 8;
        /** @deprecated */
        public static final int SYMBOLOGY_ENABLE_APPEND_MODE = 16;
        public static final int SYMBOLOGY_ENABLE_FULLASCII = 32;
        public static final int SYMBOLOGY_NUM_SYS_TRANSMIT = 64;
        public static final int SYMBOLOGY_2_DIGIT_ADDENDA = 128;
        public static final int SYMBOLOGY_5_DIGIT_ADDENDA = 256;
        public static final int SYMBOLOGY_ADDENDA_REQUIRED = 512;
        public static final int SYMBOLOGY_ADDENDA_SEPARATOR = 1024;
        public static final int SYMBOLOGY_EXPANDED_UPCE = 2048;
        public static final int SYMBOLOGY_UPCE1_ENABLE = 4096;
        public static final int SYMBOLOGY_COMPOSITE_UPC = 8192;
        public static final int SYMBOLOGY_AUSTRALIAN_BAR_WIDTH = 65536;
        /** @deprecated */
        public static final int SYMBOLOGY_128_APPEND = 524288;
        public static final int SYMBOLOGY_RSE_ENABLE = 8388608;
        public static final int SYMBOLOGY_RSL_ENABLE = 16777216;
        public static final int SYMBOLOGY_RSS_ENABLE = 33554432;
        public static final int SYMBOLOGY_RSX_ENABLE_MASK = 58720256;
        public static final int SYMBOLOGY_TELEPEN_OLD_STYLE = 67108864;
        /** @deprecated */
        public static final int SYMBOLOGY_POSICODE_LIMITED_1 = 134217728;
        /** @deprecated */
        public static final int SYMBOLOGY_POSICODE_LIMITED_2 = 268435456;
        public static final int SYMBOLOGY_CODABAR_CONCATENATE = 536870912;
        public static final int SYMBOLOGY_AUS_POST_NUMERIC_N_TABLE = 1048576;
        public static final int SYMBOLOGY_AUS_POST_ALPHANUMERIC_C_TABLE = 2097152;
        public static final int SYMBOLOGY_AUS_POST_COMBINATION_N_AND_C_TABLES = 4194304;
        public static final int SYM_MASK_FLAGS = 1;
        public static final int SYM_MASK_MIN_LEN = 2;
        public static final int SYM_MASK_MAX_LEN = 4;
        public static final int SYM_MASK_ALL = 7;

        private SymbologyFlags() {
        }
    }

    public static final class SymbologyID {
        public static final int SYM_AZTEC = 0;
        public static final int SYM_CODABAR = 1;
        public static final int SYM_CODE11 = 2;
        public static final int SYM_CODE128 = 3;
        public static final int SYM_CODE39 = 4;
        /** @deprecated */
        public static final int SYM_CODE49 = 5;
        public static final int SYM_CODE93 = 6;
        public static final int SYM_COMPOSITE = 7;
        public static final int SYM_DATAMATRIX = 8;
        public static final int SYM_EAN8 = 9;
        public static final int SYM_EAN13 = 10;
        public static final int SYM_INT25 = 11;
        public static final int SYM_MAXICODE = 12;
        public static final int SYM_MICROPDF = 13;
        /** @deprecated */
        public static final int SYM_OCR = 14;
        public static final int SYM_PDF417 = 15;
        /** @deprecated */
        public static final int SYM_POSTNET = 16;
        public static final int SYM_QR = 17;
        public static final int SYM_RSS = 18;
        public static final int SYM_UPCA = 19;
        public static final int SYM_UPCE0 = 20;
        public static final int SYM_UPCE1 = 21;
        public static final int SYM_ISBT = 22;
        public static final int SYM_BPO = 23;
        public static final int SYM_CANPOST = 24;
        public static final int SYM_AUSPOST = 25;
        public static final int SYM_IATA25 = 26;
        public static final int SYM_CODABLOCK = 27;
        public static final int SYM_JAPOST = 28;
        public static final int SYM_PLANET = 29;
        public static final int SYM_DUTCHPOST = 30;
        public static final int SYM_MSI = 31;
        public static final int SYM_TLCODE39 = 32;
        public static final int SYM_TRIOPTIC = 33;
        public static final int SYM_CODE32 = 34;
        public static final int SYM_STRT25 = 35;
        public static final int SYM_MATRIX25 = 36;
        /** @deprecated */
        public static final int SYM_PLESSEY = 37;
        public static final int SYM_CHINAPOST = 38;
        public static final int SYM_KOREAPOST = 39;
        public static final int SYM_TELEPEN = 40;
        /** @deprecated */
        public static final int SYM_CODE16K = 41;
        /** @deprecated */
        public static final int SYM_POSICODE = 42;
        public static final int SYM_COUPONCODE = 43;
        public static final int SYM_USPS4CB = 44;
        public static final int SYM_IDTAG = 45;
        /** @deprecated */
        public static final int SYM_LABEL = 46;
        public static final int SYM_GS1_128 = 47;
        public static final int SYM_HANXIN = 48;
        /** @deprecated */
        public static final int SYM_GRIDMATRIX = 49;
        public static final int SYM_POSTALS = 50;
        public static final int SYM_US_POSTALS1 = 51;
        public static final int SYMBOLOGIES = 52;
        public static final int SYM_ALL = 100;

        private SymbologyID() {
        }
    }

    public static final int  AUS_POST = 1;
    public static final int  JAPAN_POST = 3;
    public static final int  KIX = 4;
    public static final int  PLANETCODE = 5;
    public static final int  POSTNET = 6;
    public static final int  ROYAL_MAIL = 7;
    public static final int  UPU_4_STATE = 9;
    public static final int  USPS_4_STATE = 10;
    public static final int  US_POSTALS = 29;
    public static final int  CANADIAN = 30;
}
