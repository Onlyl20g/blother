package com.jinke.project.tool.tools;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by 18723 on 2019/10/30.
 */
public class OcrProcess {

    public static String ocrImg(File imageFile) {
        BufferedImage image = null;
        String result = "";
        try {
            image = ImageIO.read(imageFile);
            //对图片进行处理
            //image = convertImage(image);
            ITesseract instance = new Tesseract();//JNA Interface Mapping
            instance.setDatapath(ConfigEntity.getTess4j());
            instance.setLanguage("chi_sim");//使用中文字库
            result = instance.doOCR(image); //识别
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return result;
    }
}
