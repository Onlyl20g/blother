package com.jinke.project.system.upload.service;


import com.jinke.project.system.file.service.FileServiceImpl;
import com.jinke.project.system.fileTemp.domain.FileTemp;
import com.jinke.project.system.fileTemp.domain.FileTempStatus;
import com.jinke.project.system.folder.domain.Folder;
import com.jinke.project.system.folder.service.FolderServiceImpl;
import com.jinke.project.system.upload.controller.UploadFileController;
import com.jinke.project.system.user.domain.User;
import com.jinke.project.tool.fastdfs.FastDFSClient;
import com.jinke.project.tool.tools.ConfigEntity;
import com.jinke.project.tool.tools.PublicUtil;
import com.jinke.project.tool.tools.ZipUtil;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 上传文件服务层实现
 * Created by user on 2019/7/10.
 */
@Service
public class UploadFileServiceImpl implements UploadFileService {
    private static final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);
    @Autowired
    private FastDFSClient fdfsClient;
    @Autowired
    private UploadFileController uploadFilecontroller;
    @Autowired
    private FolderServiceImpl folderService;
    @Autowired
    private FileServiceImpl fileService;

    @Override
    public String UploadFilesToServer(MultipartFile file) throws Exception {
        String url = null;
        url = fdfsClient.uploadFile(file);

        FileTemp fileTemp = new FileTemp();
        fileTemp.setUrl(url);
        fileTemp.setCreatetime(new Date());
        fileTemp.setStatus(FileTempStatus.INV.getValue());
        fileTemp.setName(file.getResource().getFilename());
        /*Integer id = fileTempServiceImpl.insertFileTemp(fileTemp);*/
        return url;
    }

    @Override
    public String doUpload(MultipartFile file) throws Exception {
        return fdfsClient.uploadFile(file);
    }

    @Override
    public byte[] doDownload(String url) throws Exception {
        return fdfsClient.download(url);
    }

    @Override
    public Integer deleteFile(String url) {
        return null;
    }

    /**
     * 文件下载
     *
     * @param filesPath
     * @param response
     * @throws Exception
     */
    @Override
    public void downloadFile(String filesPath, String name, HttpServletRequest request, HttpServletResponse response) throws IOException {

        byte[] data = null;
        try {
            data = fdfsClient.download(filesPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (data == null || data.length == 0) {
            response.setStatus(200);
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().print("<html><body><script type='text/javascript'>alert('找不到文件');window.close();</script></body></html>");
            response.getWriter().close();
            return;
        }
        //设置响应头格式
        String filename = java.net.URLEncoder.encode(name, "UTF-8");
        if (request != null && request.getHeader("Range") != null && !request.getHeader("Range").isEmpty()) {
            String range = request.getHeader("Range").substring(6);
            range = range.substring(0, range.indexOf("-"));
            if (data.length < Integer.valueOf(range)) {
                return;
            }
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Range", "bytes " + range + "-" + (data.length - 1) + "/" + data.length);
            response.setStatus(206);
            System.arraycopy(data, Integer.valueOf(range), data, 0, data.length - Integer.valueOf(range));
        }
        response.setHeader("Content-Length", data.length + "");
        response.setHeader("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" + filename);
        // 写出
        ServletOutputStream outputStream = response.getOutputStream();
        try {
            IOUtils.write(data, outputStream);
        } catch (IOException e) {
            outputStream.close();
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    /**
     * 压缩文件下载
     *
     * @param filesPath
     * @param response
     * @throws Exception
     */
    @Override
    public void downloadMiFile(String filesPath, String name, HttpServletResponse response) throws IOException {
        byte[] data = null;
        data = fdfsClient.download(filesPath);
        ByteArrayInputStream intputStream = new ByteArrayInputStream(data);
        Builder<? extends InputStream> builder = Thumbnails.of(intputStream).scale(0.1);
        BufferedImage bufferedImage = builder.asBufferedImage();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        byte[] byteArray = baos.toByteArray();
        String filename = java.net.URLEncoder.encode(name, "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" + filename);
        // 写出
        ServletOutputStream outputStream = response.getOutputStream();
        try {
            IOUtils.write(byteArray, outputStream);
        } catch (IOException e) {
            outputStream.close();
        }
    }

    /**
     * 文件流加载
     *
     * @param filesPath
     * @param response
     * @throws Exception
     */
    @Override
    public void loadFile(String filesPath, String name, HttpServletResponse response) throws IOException {

        byte[] data = null;
        data = fdfsClient.download(filesPath);
        // 写出
        ServletOutputStream outputStream = response.getOutputStream();
        BufferedOutputStream bot = new BufferedOutputStream(outputStream);
        bot.write(data);
        bot.flush();
        bot.close();
        outputStream.flush();
        outputStream.close();
    }

    public String downloadFileBase(String filesPath) throws IOException {
        byte[] data = null;
        data = fdfsClient.download(filesPath);
        return Base64.encodeBase64String(data);
    }

    @Override
    public void save(String uuid, String url) {

    }

    /**
     * 打包下载
     */
    @Override
    public void downloadAll(HttpServletResponse response, File[] files) {
        try {
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", "attachment; filename=" + getZipFilename());
            ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
            zipFile(files, "", zos);
            zos.flush();
            zos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 打包下载
     *
     * @param subs
     * @param baseName
     * @param zos
     * @throws IOException
     */
    public static void zipFile(File[] subs, String baseName, ZipOutputStream zos)
            throws IOException {
        for (int i = 0; i < subs.length; i++) {
            File f = subs[i];

            zos.putNextEntry(new ZipEntry(baseName + f.getName()));
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            int r = 0;
            while ((r = fis.read(buffer)) != -1) {
                zos.write(buffer, 0, r);
            }
            fis.close();
            f.delete();
        }
    }

    /**
     * 功能描述:生成压缩文件名称
     *
     * @return
     */
    public static String getZipFilename() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String time = dateFormat.format(date); //可以把日期转换转指定格式的字符串
        return time + ".zip";
    }

    /**
     * 文件流加载
     *
     * @param filesPath
     * @param response
     * @throws Exception
     */
    @Override
    public void loadFile2(String filesPath, String name, HttpServletResponse response) throws IOException {

        byte[] data = null;
        data = fdfsClient.download(filesPath);
        String filename = name;
        File dataFile = null;
        try {
            dataFile = uploadFilecontroller.ByteToFile(filename, data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 写出
        ServletOutputStream outputStream = response.getOutputStream();
        BufferedOutputStream bot = new BufferedOutputStream(outputStream);
        bot.write(fileToByte(compressPictureByQality(dataFile, 0.1F)));
    }

    public static File compressPictureByQality(File file, float qality) throws IOException {
        BufferedImage src = null;
        FileOutputStream out = null;
        ImageWriter imgWrier;
        ImageWriteParam imgWriteParams;
        // 指定写图片的方式为 jpg
        imgWrier = ImageIO.getImageWritersByFormatName("jpg").next();
        imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(
                null);
        // 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
        imgWriteParams.setCompressionMode(imgWriteParams.MODE_EXPLICIT);
        // 这里指定压缩的程度，参数qality是取值0~1范围内，
        imgWriteParams.setCompressionQuality(qality);
        imgWriteParams.setProgressiveMode(imgWriteParams.MODE_DISABLED);
        ColorModel colorModel = ImageIO.read(file).getColorModel();// ColorModel.getRGBdefault();
        imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(
                colorModel, colorModel.createCompatibleSampleModel(32, 32)));
        if (!file.exists()) {
            throw new FileNotFoundException("Not Found Img File,文件不存在");
        } else {
            src = ImageIO.read(file);
            out = new FileOutputStream(file);
            imgWrier.reset();
            // 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何
            // OutputStream构造
            imgWrier.setOutput(ImageIO.createImageOutputStream(out));
            // 调用write方法，就可以向输入流写图片
            imgWrier.write(null, new IIOImage(src, null, null),
                    imgWriteParams);
            out.flush();
            out.close();
            return file;
        }
    }

    /**
     * 将文件转换成byte数组
     */
    public static byte[] fileToByte(File tradeFile) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(tradeFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 对文件进行打包压缩下载
     *
     * @param folders
     * @param files
     * @param fileType
     * @param response
     */
    @Override
    public void downloadAllZip(List<Folder> folders, List<com.jinke.project.system.file.domain.File> files, String fileType, String pFolderName, HttpServletResponse response) {
        String basePath = ConfigEntity.getTempFilePath();
        if (basePath == null || "".equals(basePath)) {
            //如果没有配置，读取项目路径
            basePath = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        }
        basePath = PublicUtil.reBuildString(basePath + pFolderName + getFormatDate() + "\\");
        File tempFile = new File(basePath);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
        for (Folder f : folders) {
            Folder folder = new Folder();
            folder.setBusinessId(f.getBusinessPid());
            List<Folder> folders1 = folderService.selectFolderList(folder);
            int catSize = 0;
            if (folders1 != null && folders1.size() == 1) {
                catSize = folders1.get(0).getFolderPath().length();
            }
            createPath(f, basePath, fileType, catSize);
        }
        if (files != null && files.size() > 0) {
            for (com.jinke.project.system.file.domain.File file : files) {
                try {
                    OutputStream os = null;
                    byte[] bytes = fdfsClient.download(file.getPath());
                    file.setName(checkFileExists(file, basePath + "\\", 0));
                    os = new FileOutputStream(basePath + file.getName() + "." + file.getFileSuffix());
                    os.write(bytes, 0, bytes.length);
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        response.setContentType("APPLICATION/OCTET-STREAM");
        try {
            response.setHeader("Content-Disposition", "attachment; filename=" + PublicUtil.encodeString(pFolderName + "_" + getZipFilename()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ZipUtil.toZip(basePath, response.getOutputStream(), true);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clearLocalFile(tempFile);
    }

    /**
     * 对文件进行打包压缩下载
     *
     * @param folders
     * @param files
     * @param response
     */
    public void downloadAllZips(List<Folder> folders, List<com.jinke.project.system.file.domain.File> files, HttpServletResponse response) {
        String basePath = ConfigEntity.getTempFilePath();
        basePath = PublicUtil.reBuildString(basePath + getFormatDate() + "\\");
        File tempFile = new File(basePath);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
        for (Folder f : folders) {
            Folder folder = new Folder();
            folder.setBusinessId(f.getBusinessPid());
            List<Folder> folders1 = folderService.selectFolderList(folder);
            int catSize = 0;
            if (folders1 != null && folders1.size() == 1) {
                catSize = folders1.get(0).getFolderPath().length();
            }
            createPaths(f, basePath, catSize);
        }
        if (files != null && files.size() > 0) {
            for (com.jinke.project.system.file.domain.File file : files) {
                try {
                    OutputStream os = null;
                    byte[] bytes = fdfsClient.download(file.getPath());
                    file.setName(checkFileExists(file, basePath + "\\", 0));
                    os = new FileOutputStream(basePath + file.getName() + "." + file.getFileSuffix());
                    os.write(bytes, 0, bytes.length);
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=" + getZipFilename());
        try {
            ZipUtil.toZip(basePath, response.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        clearLocalFile(tempFile);

    }

    /**
     * 构建真实路径
     *
     * @param folder
     * @param basePath
     */
    public void createPath(Folder folder, String basePath, String fileType, int catSize) {
        User user = null;
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal instanceof User) {
            user = (User) principal;
        }

        File tempFile = new File(PublicUtil.reBuildString(basePath + folder.getFolderPath().substring(catSize + 1)));
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
        com.jinke.project.system.file.domain.File file = new com.jinke.project.system.file.domain.File();
        file.setFolderBusinessId(folder.getBusinessId());
        file.setDelFlag("0");
        if (fileType != null && !"".equals(fileType)) {
            file.setFileClass(fileType);
        }
        file.setUserBusinessId(user.getBusinessId());
        List<com.jinke.project.system.file.domain.File> files = fileService.selectFileList(file);
        if (files != null && files.size() > 0) {
            try {
                OutputStream os = null;
                for (com.jinke.project.system.file.domain.File file1 : files) {
                    Folder folder1 = new Folder();
                    folder1.setBusinessId(file1.getFolderBusinessId());
                    folder1 = folderService.selectFolderList(folder1).get(0);
                    file1.setName(checkFileExists(file1, PublicUtil.reBuildString(basePath + "\\" + folder1.getFolderPath() + "\\"), 0));
                    byte[] bytes = fdfsClient.download(file1.getPath());
                    os = new FileOutputStream(PublicUtil.reBuildString(basePath + folder.getFolderPath().substring(catSize + 1) + "\\" + file1.getName() + "." + file1.getFileSuffix()));
                    os.write(bytes, 0, bytes.length);
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Folder folder1 = new Folder();
        folder1.setBusinessPid(folder.getBusinessId());
        List<Folder> folders = folderService.selectFolderList(folder1);
        if (folders != null && folders.size() > 0) {
            for (Folder folder2 : folders) {
                File file3 = new File(PublicUtil.reBuildString(basePath + folder2.getFolderPath().substring(catSize + 1)));
                if (!file3.exists()) {
                    file3.mkdirs();
                }
                createPath(folder2, basePath, fileType, catSize);
            }
        }
    }

    /**
     * 构建真实路径
     *
     * @param folder
     * @param basePath
     */
    public void createPaths(Folder folder, String basePath, int catSize) {
        Object principal = SecurityUtils.getSubject().getPrincipal();

        File tempFile = new File(basePath + folder.getFolderPath().substring(catSize + 1));
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
        com.jinke.project.system.file.domain.File file = new com.jinke.project.system.file.domain.File();
        file.setFolderBusinessId(folder.getBusinessId());
        file.setDelFlag("0");
        List<com.jinke.project.system.file.domain.File> files = fileService.selectFileList(file);
        if (files != null && files.size() > 0) {
            try {
                OutputStream os = null;
                for (com.jinke.project.system.file.domain.File file1 : files) {
                    Folder folder1 = new Folder();
                    folder1.setBusinessId(file1.getFolderBusinessId());
                    folder1 = folderService.selectFolderList(folder1).get(0);
                    file1.setName(checkFileExists(file1, PublicUtil.reBuildString(basePath + "\\" + folder1.getFolderPath() + "\\"), 0));
                    byte[] bytes = fdfsClient.download(file1.getPath());
                    os = new FileOutputStream(basePath + folder.getFolderPath().substring(catSize + 1) + "\\" + file1.getName() + "." + file1.getFileSuffix());
                    os.write(bytes, 0, bytes.length);
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Folder folder1 = new Folder();
        folder1.setBusinessPid(folder.getBusinessId());
        List<Folder> folders = folderService.selectFolderList(folder1);
        if (folders != null && folders.size() > 0) {
            for (Folder folder2 : folders) {
                File file3 = new File(basePath + folder2.getFolderPath().substring(catSize + 1));
                if (!file3.exists()) {
                    file3.mkdirs();
                }
                createPaths(folder2, basePath, catSize);
            }
        }
    }

    /**
     * 传入一个java.io 的file 进行递归删除
     *
     * @param file
     * @return
     */
    public boolean clearLocalFile(File file) {
        if (!file.exists()) {
            return true;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                clearLocalFile(f);
            }
        }
        return file.delete();
    }

    public String checkFileExists(com.jinke.project.system.file.domain.File file, String basePath, int num) {
        File file1 = null;
        if (num > 0) {
            file1 = new File(basePath + file.getName() + "(" + num + ")." + file.getFileSuffix());
        } else {
            file1 = new File(basePath + file.getName() + "." + file.getFileSuffix());
        }
        if (file1.exists()) {
            return checkFileExists(file, basePath, ++num);
        } else {
            if (num > 0) {
                return file.getName() + "(" + num + ")";
            } else {
                return file.getName();
            }
        }
    }

    public String getFormatDate() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        return dateFormat.format(date);
    }
}
