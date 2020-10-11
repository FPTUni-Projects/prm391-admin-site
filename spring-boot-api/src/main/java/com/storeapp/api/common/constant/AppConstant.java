package com.storeapp.api.common.constant;

/**
 * CommonConstant
 *
 * @author khal
 * @since 2020/09/29
 */
public interface AppConstant {

    /**
     * Datetime pattern formatter
     */
     String DATETIME_PATTERN_1 = "yyyyMMddHHmmss";
     String DATETIME_PATTERN_2 = "yyyy/MM/dd HH:mm:ss";
     String DATETIME_PATTERN_3 = "dd/MM/yyyy";
     String DATETIME_PATTERN_4 = "yyyyMMdd";
     String DATETIME_PATTERN_5 = "ddMMyyyy";

    /**
     * Encrypt, decrypt algorithm
     */
    String BCRYPT_ENCODE = "BCRYPT";
    String MD5_ENCODE = "MD5";

    /**
     * String Utils
     */
    String EMPTRY_STR = "";
    String SINGLE_DASH = "-";
    double DEFAULT_VALUE = 1.0;
    double MILLION_VALUE = 1000000.0;
    String MILLION_TEXT = "Triệu";
    double BILLION_VALUE = 1000000000.0;
    String BILLION_TEXT = "Tỷ";
    String WHITE_SPACE = " ";
    String DS_STORE_FILE = ".DS_Store";

    /**
     * Image Extension
     */
    String EXT_PNG = "png";
    String EXT_JPG = "jpg";
    String EXT_JPEG = "jpeg";
    String EXT_PDF = "pdf";
    String EXT_DOC = "doc";
    String EXT_DOCX = "docx";
    String EXT_XLSX = "xlsx";
    String EXT_XLSM = "xlsm";
    String EXT_XLS = "xls";
    String EXT_TXT = "txt";

}
