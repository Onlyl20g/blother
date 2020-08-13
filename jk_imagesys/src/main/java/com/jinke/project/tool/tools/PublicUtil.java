package com.jinke.project.tool.tools;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PublicUtil {

    public static File MultipartFileToFile(MultipartFile mFile) {
        File file = null;
        try {
            InputStream ins = mFile.getInputStream();
            file = new File(mFile.getOriginalFilename());
            inputStreamToFile(ins, file);
            ins.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toFile(MultipartFile multipartFile) {
        File file = null;
        try {
            file = File.createTempFile("tmp", null);
            multipartFile.transferTo(file);
            file.deleteOnExit();
            return file.getPath();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[1024 * 100];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 输入一个字符串，对其进行编码后返回
     *
     * @param string
     * @return
     */
    public static String encodeString(String string) {
        try {
            return new String(string.getBytes("utf-8"), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 将windows路径标识符替换为当前系统的
     *
     * @param string
     * @return
     */
    public static String reBuildString(String string) {
        return string.replace("\\", new String(System.getProperty("file.separator")));
    }

    /**
     * 获取当前时间字符串yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getNowString() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }

    /**
     * 把日期对象转换为字符串
     * @param date
     * @return
     */
    public static String formatTime(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        return df.format(date);
    }
}
