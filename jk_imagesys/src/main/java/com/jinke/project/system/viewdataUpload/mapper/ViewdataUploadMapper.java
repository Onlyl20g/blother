package com.jinke.project.system.viewdataUpload.mapper;

import com.jinke.project.system.viewdataUpload.domain.ViewdataUpload;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 影像上传 数据层
 *
 * @author jinke
 * @date 2019-09-09
 */
@Component
public interface ViewdataUploadMapper {
    /**
     * 查询影像上传信息
     *
     * @param id 影像上传ID
     * @return 影像上传信息
     */
    public ViewdataUpload selectViewdataUploadById(Integer id);

    /**
     * 查询影像上传列表
     *
     * @param viewdataUpload 影像上传信息
     * @return 影像上传集合
     */
    public List<ViewdataUpload> selectViewdataUploadList(ViewdataUpload viewdataUpload);

    /**
     * 新增影像上传
     *
     * @param viewdataUpload 影像上传信息
     * @return 结果
     */
    public int insertViewdataUpload(ViewdataUpload viewdataUpload);

    /**
     * 修改影像上传
     *
     * @param viewdataUpload 影像上传信息
     * @return 结果
     */
    public int updateViewdataUpload(ViewdataUpload viewdataUpload);

    /**
     * 删除影像上传
     *
     * @param id 影像上传ID
     * @return 结果
     */
    public int deleteViewdataUploadById(Integer id);

    /**
     * 批量删除影像上传
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteViewdataUploadByIds(String[] ids);

}