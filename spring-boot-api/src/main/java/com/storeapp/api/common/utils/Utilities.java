package com.storeapp.api.common.utils;

import com.storeapp.api.common.constant.AppConstant;
import com.storeapp.api.common.constant.Constant;
import com.storeapp.api.common.logging.AppLogger;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * CommonUtils
 *
 * @author khal
 * @since 2020/09/29
 */
public class Utilities {
    /**
     * Get string encoder with MD5 Algorithm
     *
     * @param value
     * @return result
     */
    public static String getMD5Encoder(String value) {
        String result = AppConstant.EMPTRY_STR;
        try {
            MessageDigest msgDigest = MessageDigest.getInstance(AppConstant.MD5_ENCODE);
            byte[] hashBytes = msgDigest.digest(value.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(2*hashBytes.length);
            for(byte hashByte : hashBytes){
                sb.append(String.format("%02x", hashByte&0xff));
            }
            result = sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            AppLogger.errorLog(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Upper first character in characters sequence
     * Ex: abcdef -> Abcdef
     *     abcdef GHijkl -> Abcdef GHijkl
     *
     * @param value
     * @return result
     */
    public static String upperFirstChar(String value) {
        String result = AppConstant.EMPTRY_STR;
        if(StringUtils.isEmpty(value)) {
            return result;
        }

        result = value.substring(0, 1).toUpperCase() + value.substring(1);
        return result;
     }

    /**
     * Get current datetime
     * Format: yyyyMMddHHmmss
     *
     * @return result
     */
    public static String getInitDatetime() {
        String result = LocalDateTime.now().format(DateTimeFormatter.ofPattern(AppConstant.DATETIME_PATTERN_1));
        return result;
    }

    /**
     * Get current datetime by datetime pattern
     * Format: pattern
     *
     * @return result
     */
    public static String getInitDatetime(String pattern) {
        String result = LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
        return result;
    }

    /**
     * Get format datetime
     * Format: srcPattern -> destPattern
     *
     * @return result
     */
    public static String getFDatetime(String value, String srcPattern, String destPattern) {
        LocalDateTime datetime = LocalDateTime.parse(value, DateTimeFormatter.ofPattern(srcPattern));
        String result = datetime.format(DateTimeFormatter.ofPattern(destPattern));
        return result;
    }

    /**
     * Convert string
     * Ex: This is my code -> this-is-my-code
     *
     * @param str
     * @return
     */
    public static String convertStr(String str) {
        // TODO: Uncheck vietnamese unicode
        String result = "";
        if (!StringUtils.isEmpty(str) && str.contains(" ")) {
            String[] strArr = str.trim().replaceAll("\\s+", " ").toLowerCase().split(" ");
            result = Arrays.asList(strArr).stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining("-"));
        }

        return result;
    }

    /**
     * Check if it is a static resources
     *
     * @param uri
     */
    public static boolean isStaticResouces(String uri) {
        return uri.startsWith("/dist/")
                || uri.endsWith(".js")
                || uri.endsWith(".css")
                || uri.endsWith(".png")
                || uri.endsWith(".jpg")
                || uri.endsWith(".jpeg")
                || uri.endsWith(".svg")
                || uri.endsWith(".ico")
                || uri.endsWith(".woff")
                || uri.endsWith(".woff2")
                || uri.endsWith(".ttf")
                || uri.endsWith(".map")
                || uri.endsWith(".webmanifest")
                || uri.endsWith("manifest.json")
                || uri.endsWith("favicon.ico");
    }

    /**
     * Format price
     *
     * @param price BigDecimal
     * @return result
     */
    public static String formatPrice(BigDecimal price) {
        String result = AppConstant.SINGLE_DASH;
        if (Objects.nonNull(price)) {
            String strUnit = AppConstant.EMPTRY_STR;
            BigDecimal unit = new BigDecimal( 1);

            String strPrice = price.toString();
            strPrice = strPrice.endsWith(".00") ? strPrice.replace(".00", AppConstant.EMPTRY_STR) : strPrice;
            if (strPrice.length() > 9) { // billion
                unit = new BigDecimal(AppConstant.BILLION_VALUE);
                strUnit = AppConstant.BILLION_TEXT;
            } else if (strPrice.length() > 6 && strPrice.length() <= 9) {
                unit = new BigDecimal(AppConstant.MILLION_VALUE);
                strUnit = AppConstant.MILLION_TEXT;
            }

            // convert price to string following correct formatter
            result = new BigDecimal(strPrice).divide(unit).toString()  + AppConstant.WHITE_SPACE + strUnit;
        }

        return result;
    }

    /**
     * Calculate price
     *
     * @param price
     * @return double
     */
    public static double calculatePrice(BigDecimal price) {
        BigDecimal result = new BigDecimal(0);
        if (Objects.nonNull(price)) {
            String strPrice = price.toString();
            strPrice = strPrice.endsWith(".00") ? strPrice.replace(".00", AppConstant.EMPTRY_STR) : strPrice;
            if (strPrice.length() > 9) { // billion
                BigDecimal unit = new BigDecimal(AppConstant.BILLION_VALUE);
                result = price.divide(unit);
            } else if (strPrice.length() > 6 && strPrice.length() <= 9) {
                BigDecimal unit = new BigDecimal(AppConstant.MILLION_VALUE);
                result = price.divide(unit);
            }
        }

        return result.doubleValue();
    }

    /**
     * Get all files in folder by id
     *
     * @param constant
     * @param dir
     * @param id
     * @return List<String>
     */
    public static List<String> getStaticFileById(Constant constant, String dir, String id) {
        String rootFolderDir = constant.getRootDir() + dir + File.separator + id + File.separator;
        File[] fileArr = new File(rootFolderDir).listFiles(f -> f.exists() && f.isFile() &&
                        (  f.getName().endsWith(AppConstant.EXT_JPEG)
                        || f.getName().endsWith(AppConstant.EXT_JPG)
                        || f.getName().endsWith(AppConstant.EXT_PNG))
        );

        List<String> results = new ArrayList<>();
        if (Objects.nonNull(fileArr) && fileArr.length != 0) {
            results = Optional.ofNullable(Arrays.asList(fileArr)).orElse(Collections.emptyList())
                    .stream()
                    .map(file -> dir + File.separator + id + File.separator + file.getName())
                    .collect(Collectors.toList());
        }

        return results;
    }
}
