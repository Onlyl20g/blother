package com.jinke.project.system.upload.service;

import com.alibaba.fastjson.JSONObject;
import com.jinke.project.system.fileTemp.domain.FileTemp;
import com.jinke.project.system.fileTemp.domain.FileTempStatus;
import com.jinke.project.system.fileTemp.mapper.FileTempMapper;
import com.jinke.project.system.fileTemp.service.FileTempServiceImpl;
import com.jinke.project.system.filepath.domain.Filepath;
import com.jinke.project.system.filepath.mapper.FilepathMapper;
import com.jinke.project.system.ncInteractivity.controller.YXInteractiveControllor;
import com.jinke.project.tool.fastdfs.FastDFSClient;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

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
    private FileTempServiceImpl fileTempServiceImpl;

    @Autowired
    private FileTempMapper fileTempMapper;

    @Autowired
    private FilepathMapper filepathMapper;

    @Autowired
    private YXInteractiveControllor yxInteractiveControllor;

    /**
     * 文件上传
     *
     * @param file
     * @return
     * @throws Exception
     */
    @Override
    public String UploadFilesToServer(MultipartFile file) throws Exception {
        //Map<String,Object> resultMap = new HashMap<>();
       /* String url = null;
        url = fdfsClient.uploadFile(file);*/

        String path = yxInteractiveControllor.doUpload(file);
        String url = "";
        JSONObject jsonObject = JSONObject.parseObject(path);
        if (path != null && !"".equals(path)) {
            url = jsonObject.getString("path");
            FileTemp fileTemp = new FileTemp();
            fileTemp.setUrl(url);
            fileTemp.setCreatetime(new Date());
            fileTemp.setStatus(FileTempStatus.INV.getValue());
            fileTemp.setName(file.getResource().getFilename());
            Integer id = fileTempServiceImpl.insertFileTemp(fileTemp);
            if (id == 0) {
                return "";
            }
        }
        return url;
    }


    /**
     * @param filesPath
     * @param response
     * @throws Exception
     */
    @Override
    public void downloadFile(String filesPath, String name, HttpServletResponse response) throws IOException {

        byte[] data = null;
//        data = fdfsClient.download(filesPath);
        data = yxInteractiveControllor.doDownload(filesPath);
        //设置响应头格式
        String downName = name;
        String filename = java.net.URLEncoder.encode(downName, "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" + filename);
        // 写出
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.write(data, outputStream);
    }

    /**
     * @param filesPath
     * @param response
     * @throws Exception
     */
    @Override
    public void downStaticFile(String filesPath, String name, HttpServletResponse response) throws IOException {
        //设置响应头格式
        String filename = URLEncoder.encode(name, "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        byte[] data;
        File file = new File(filesPath);
        data = File2byte(file);
        // 写出
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.write(data, outputStream);
    }

    /**
     * 将文件转换成byte数组
     *
     * @param tradeFile
     * @return
     */
    public static byte[] File2byte(File tradeFile) {
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
     * 文件流加载
     *
     * @param filesPath
     * @param response
     * @throws Exception
     */
    @Override
    public void loadImg(String filesPath, HttpServletResponse response) {

        byte[] data = null;
        data = yxInteractiveControllor.doDownload(filesPath);
        // 写出
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            IOUtils.write(data, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

       /* ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            BufferedOutputStream bot = new BufferedOutputStream(outputStream);
            bot.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    /**
     * 保存文件并修改临时表对应的状态
     *
     * @param uuid
     * @param url
     */
    @Override
    public void save(String uuid, String url) {
        Filepath filepath = new Filepath();
        FileTemp fileTempid = new FileTemp();
        fileTempid.setUrl(url);
        List<FileTemp> fileTemps = fileTempMapper.selectFileTempList(fileTempid);
        if (fileTemps.size() > 0) {
            FileTemp fileTemp = fileTemps.get(0);
            fileTempid.setUrl(url);
            fileTemp.setStatus(FileTempStatus.EFF.getValue());

            filepath.setFilepathId(uuid);
            filepath.setUrlBillid(url);
            filepath.setFormName(fileTemp.getName());//file.getResource().getFilename()
            filepath.setCreateTime(new Date());
            int row = filepathMapper.insertFilepath(filepath);

            fileTempMapper.updateFileTemp(fileTemp);
        }
    }

    @Override
    public List<Filepath> selectList(String uuid) {
        Filepath filepath = new Filepath();
        filepath.setFilepathId(uuid);
        List<Filepath> filepaths = filepathMapper.selectFilepathList(filepath);
        return filepaths;
    }

    /**
     * 删除文件
     *
     * @param url
     * @return 0==false;1==true
     */
    @Override
    public Integer deleteFile(String url) {
        try {
            fdfsClient.deleteFile(url);
        } catch (Exception e) {
            log.debug("文件不存在");
        }

        //根据路径删除记录
        Integer row = fileTempServiceImpl.deleteFileTempByUrl(url);
        return row;
    }


}
