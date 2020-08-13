package com.jinke.project.system.upload.service;


import com.jinke.project.system.folder.domain.Folder;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
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
     * 外部上传文件
     *
     * @param files
     * @return
     * @throws Exception
     */
    public String doUpload(MultipartFile files) throws Exception;

    /**
     * 外部下载文件
     *
     * @param url
     * @return
     * @throws Exception
     */
    public byte[] doDownload(String url) throws Exception;

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
    public void downloadFile(String filesPath, String name, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 从fastdfs服务器下载压缩文件
     *
     * @param filesPath
     */
    public void downloadMiFile(String filesPath, String name, HttpServletResponse response) throws IOException;

    /**
     * 保存文件
     *
     * @param uuid
     * @param url
     */
    public void save(String uuid, String url);

    /**
     * 打包下载
     */
    public void downloadAll(HttpServletResponse response, File[] files);

    /**
     * 加载文件
     *
     * @param filesPath
     * @param name
     * @param response
     */
    public void loadFile(String filesPath, String name, HttpServletResponse response) throws IOException;

    public void loadFile2(String filesPath, String name, HttpServletResponse response) throws IOException;

    public void downloadAllZip(List<Folder> folders, List<com.jinke.project.system.file.domain.File> files, String fileType, String pFolderName, HttpServletResponse response);

    public void downloadAllZips(List<Folder> folderList, List<com.jinke.project.system.file.domain.File> fileList, HttpServletResponse response);
}
