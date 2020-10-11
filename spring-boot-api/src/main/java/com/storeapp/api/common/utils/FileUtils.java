package com.storeapp.api.common.utils;

import com.storeapp.api.common.constant.AppConstant;
import com.storeapp.api.common.constant.MimeType;
import com.storeapp.api.common.exception.ServerErrorException;
import com.storeapp.api.common.logging.AppLogger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * FileUtils
 *
 * @author khal
 * @since 2020/09/29
 */
public class FileUtils<T> {

    /**
     * Write list objects to file
     * @param name file name
     * @param dir file directory
     * @param ext file extension
     * @param delimiter delimiter between field value
     * @param objs list object
     */
    public static <T> void writeListObject(String name, String dir, String ext, String delimiter, List<T> objs) {
        if (objs == null || objs.size() == 0)
            return;

        String output = objs.stream()
                .map(obj -> getLineObjectValue(delimiter, obj))
                .collect(Collectors.joining("\n"));

        File folder = new File(dir);
        if (folder == null || !folder.exists() || !folder.exists())
            folder.mkdirs();

        File file = new File(dir + File.separator + name + "." + ext);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(output);
            fileWriter.flush();
        } catch (IOException e) {
            AppLogger.errorLog(e.getMessage(), e);
        } finally {
            try {
                if (fileWriter != null)
                    fileWriter.close();
            } catch (IOException e) {
                AppLogger.errorLog(e.getMessage(), e);
            }
        }
    }

    /**
     * Get file as response entity byte array
     *
     * @param dir
     * @return
     */
    public static ResponseEntity<byte[]> download(String dir) {
        MediaType contentType;
        byte[] binArr = null;
        InputStream inputStream = null;

        File file = new File(dir);
        String fileName = AppConstant.EMPTRY_STR;
        if (file != null && file.exists()) {
            String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            String mimeType = getMimeTypeByExtension(ext);
            contentType = MediaType.valueOf(mimeType);
            
            try {
                fileName = file.getName();
                binArr = new byte[(int) file.length()];
                inputStream = new FileInputStream(file);
                inputStream.read(binArr);

            } catch (IOException e) {
                AppLogger.errorLog(e.getMessage(), e);
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            throw new ServerErrorException();
        }


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(contentType);
        headers.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(binArr, headers, HttpStatus.CREATED);
    }

    /**
     * Combine value of object into one line string
     *
     * @param delimiter delimiter between field value
     * @param obj
     */
    public static <T> String getLineObjectValue(String delimiter, T obj) {
        if (obj == null)
            return null;

        Field[] fields = obj.getClass().getDeclaredFields();
        return Optional.ofNullable(Arrays.asList(fields))
                .orElse(Collections.emptyList())
                .stream()
                .map(field -> {
                    String value = null;
                    try {
                        field.setAccessible(true);
                        value = field.get(obj).toString();
                    } catch (IllegalAccessException e) {
                        AppLogger.errorLog(e.getMessage(), e);
                    }
                    return value;
                })
                .collect(Collectors.joining(delimiter));
    }

    /**
     * Clean directory
     *
     * @param dir
     */
    public static void cleanDir(String dir) {
        File folder = new File(dir);
        if (folder != null && folder.exists() && folder.isDirectory()) {
            try {
                org.apache.commons.io.FileUtils.cleanDirectory(folder);
            } catch (IOException e) {
                AppLogger.errorLog(e.getMessage(), e);
                throw new ServerErrorException(e.getMessage());
            }
        }
    }

    /**
     * Save multipart file into physical file
     *
     * @param dir
     * @param fileName
     * @param multipartFile
     */
    public static void saveOnce(String dir, String fileName, MultipartFile multipartFile) {
        if (StringUtils.isEmpty(dir) || multipartFile == null)
            return;

        OutputStream fos = null;
        OutputStream stream = null;
        try {
            File folder = new File(dir);
            if (folder == null | !folder.exists() || !folder.isDirectory()) {
                folder.mkdirs();
            }

            File file = new File(dir + File.separator + fileName);
            fos = new FileOutputStream(file);
            stream = new BufferedOutputStream(fos);
            stream.write(multipartFile.getBytes());
        } catch (IOException e) {
            AppLogger.errorLog(e.getMessage(), e);
            throw new ServerErrorException(e.getMessage());
        } finally {
            try {
                if (stream != null) stream.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                AppLogger.errorLog(e.getMessage(), e);
                throw new ServerErrorException(e.getMessage());
            }
        }
    }
    /**
     * Save list multipart file into physical file
     *
     * @param dir
     * @param multipartFiles
     */
    public static void saveMultiple(String dir, MultipartFile[] multipartFiles) {
        Arrays.asList(multipartFiles)
                .stream()
                .forEach(multipartFile -> {
                    saveOnce(dir, multipartFile.getOriginalFilename(), multipartFile);
                });
    }

    /**
     * Read multiple file type is image as base64 url
     *
     * @param dir
     * @return List<String>
     */
    public static String readFileAsBase64Url(String dir) {
        String base64Url = AppConstant.EMPTRY_STR;

        File file = new File(dir);
        if (file != null && file.exists() && file.isFile()) {
            String filePath = file.getAbsolutePath();
            byte[] byteArr = Base64.getEncoder().encode(readFileAsByte(filePath));
            String ext = filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();
            String mimeType = getMimeTypeByExtension(ext);
            String base64Encode = new String(byteArr);

            base64Url = "data:" + mimeType + ";base64," + base64Encode;
        }

        return base64Url;
    }

    /**
     * Read file to byte array
     *
     * @param fileDir
     * @return byte[]
     */
    public static byte[] readFileAsByte(String fileDir) {
        byte[] bytes = null;
        InputStream inputStream = null;

        File file = new File(fileDir);
        if (file.exists()) {
            try {
                bytes = new byte[(int) file.length()];
                inputStream = new FileInputStream(file);
                inputStream.read(bytes);
            } catch (IOException e) {
                AppLogger.errorLog(e.getMessage(), e);
                throw new ServerErrorException(e.getMessage());
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return bytes;
    }

    /**
     * Delete file
     *
     * @param dir
     */
    public static void deleteFile(String dir) {
        File file = new File(dir);
        if (file != null && file.exists() && file.isFile()) {
            file.delete();
        }
    }

    /**
     * Get mime type of file by extension
     * 
     * @param extension
     * @return mimeType
     */
    public static String getMimeTypeByExtension(String extension) {
        String mimeType;
        switch (extension) {
            case AppConstant.EXT_DOC: mimeType = MimeType.MIME_TYPE_DOC; break;
            case AppConstant.EXT_DOCX: mimeType = MimeType.MIME_TYPE_DOCX; break;
            case AppConstant.EXT_PDF: mimeType = MimeType.MIME_TYPE_PDF; break;
            case AppConstant.EXT_XLS: mimeType = MimeType.MIME_TYPE_XLS; break;
            case AppConstant.EXT_XLSX: mimeType = MimeType.MIME_TYPE_XLSX; break;
            case AppConstant.EXT_XLSM: mimeType = MimeType.MIME_TYPE_XLSM; break;
            case AppConstant.EXT_TXT: mimeType = MimeType.MIME_TYPE_TXT; break;
            default: mimeType = MimeType.MIME_TYPE_OCTET_STREAM; break;
        }

        return mimeType;
    }
}
