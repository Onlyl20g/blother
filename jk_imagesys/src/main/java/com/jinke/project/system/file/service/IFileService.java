package com.jinke.project.system.file.service;

import com.jinke.project.system.file.domain.File;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 文件 服务层
 *
 * @author jinke
 * @date 2019-09-09
 */
public interface IFileService {
    /**
     * 查询文件信息
     *
     * @param id 文件ID
     * @return 文件信息
     */
    public File selectFileById(Integer id);

    /**
     * 查询文件列表
     *
     * @param file 文件信息
     * @return 文件集合
     */
    public List<File> selectFileList(File file);

    /**
     * 查询文件列表
     *
     * @param file 文件信息
     * @return 文件集合
     */
    public List<File> selectFileLists(File file);

    /**
     * 模糊查询文件列表
     *
     * @param file 文件信息
     * @return 文件集合
     */
    public List<File> selectFileListByLabel(File file);

    /**
     * 新增文件
     *
     * @param file 文件信息
     * @return 结果
     */
    public int insertFile(HttpServletRequest request, File file);

    public int insertScanFile(HttpServletRequest request, File file);

    /**
     * 修改文件
     *
     * @param file 文件信息
     * @return 结果
     */
    public int updateFile(File file);

    /**
     * 删除文件信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFileByIds(String ids);

    /**
     * 找回文件
     */
    public int returnFile(File file);

    /**
     * 修改状态为删除
     *
     * @param ids
     * @return
     */
    public int deleteFiles(String ids);

    /**
     * 回收站修改状态为删除
     *
     * @param ids
     * @return
     */
    public int reallyRemove(String ids);

    public int insertSaveFile(String fileAddressId, String url, MultipartFile file);

    /**
     * 分享查询
     *
     * @param file
     * @return
     */
    public List<File> selectShareFile(File file);

    public int addFile(File file);

    public List<File> selectFileListGroupByPath(File file);

    public int moveFile(String pid, String files, String folders);

    public int moveMerger(String pid, String files, String folders);

    //清空回收站
    public int cleanAllFile(File file);
}
