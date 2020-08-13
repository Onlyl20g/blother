package com.jinke.project.system.upload.service;

import com.jinke.project.system.filepath.domain.Filepath;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 上传文件服务层
 * Created by user on 2019/7/10.
 */
public interface UploadFileService {

    /**
     * 上传文件
     *
     * @param files
     * @return
     * @throws Exception
     */
    public String UploadFilesToServer(MultipartFile files) throws Exception;

    /**
     * 删除文件
     *
     * @param url
     * @return
     */
    public Integer deleteFile(String url);

    /**
     * 上传文件到fastdfs图片服务器
     *
     * @param uploadFiles
     * @return
     */
    //List<UploadFile> UploadFilesToServer(List<UploadFile> uploadFiles);

    /**
     * 从fastdfs服务器下载文件
     *
     * @param filesPath
     */
    public void downloadFile(String filesPath, String name, HttpServletResponse response) throws IOException;

    /**
     * 从fastdfs服务器下载文件
     *
     * @param filesPath
     */
    public void downStaticFile(String filesPath, String name, HttpServletResponse response) throws IOException;

    /**
     * 保存文件
     *
     * @param uuid
     * @param url
     */
    public void save(String uuid, String url);


    /**
     * 查询文件列表
     *
     * @param uuid
     */
    public List<Filepath> selectList(String uuid);

    /**
     * 加载图片流
     *
     * @param filesPath
     * @param response
     */
    public void loadImg(String filesPath, HttpServletResponse response);
}
