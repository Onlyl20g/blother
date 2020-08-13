package com.jinke.project.system.viewdataUpload.service;

import com.jinke.common.utils.text.Convert;
import com.jinke.project.system.user.domain.User;
import com.jinke.project.system.viewdataUpload.domain.ViewdataUpload;
import com.jinke.project.system.viewdataUpload.domain.ViewdataUploadSource;
import com.jinke.project.system.viewdataUpload.mapper.ViewdataUploadMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 影像上传 服务层实现
 *
 * @author jinke
 * @date 2019-09-09
 */
@Service
public class ViewdataUploadServiceImpl implements IViewdataUploadService {
    @Autowired
    private ViewdataUploadMapper viewdataUploadMapper;

    /**
     * 查询影像上传信息
     *
     * @param id 影像上传ID
     * @return 影像上传信息
     */
    @Override
    public ViewdataUpload selectViewdataUploadById(Integer id) {
        return viewdataUploadMapper.selectViewdataUploadById(id);
    }

    /**
     * 查询影像上传列表
     *
     * @param viewdataUpload 影像上传信息
     * @return 影像上传集合
     */
    @Override
    public List<ViewdataUpload> selectViewdataUploadList(ViewdataUpload viewdataUpload) {
        return viewdataUploadMapper.selectViewdataUploadList(viewdataUpload);
    }

    /**
     * 新增影像上传
     *
     * @param viewdataUpload 影像上传信息
     * @return 结果
     */
    @Override
    public int insertViewdataUpload(ViewdataUpload viewdataUpload) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();//获取当前用户
        String userBusinessId = user.getBusinessId();//当前用户id
        String businessId = UUID.randomUUID().toString().replaceAll("-", "");//业务id
        Date date = new Date();//获取当前时间
        viewdataUpload.setDataSource(ViewdataUploadSource.ITU.getValue());
        viewdataUpload.setCreateTime(date);
        viewdataUpload.setBusinessId(businessId);
        viewdataUpload.setUserBusinessId(userBusinessId);
        viewdataUpload.setStatus("0");//状态默认0
        return viewdataUploadMapper.insertViewdataUpload(viewdataUpload);
    }

    /**
     * 新增影像上传
     *
     * @param viewdataUpload 影像上传信息
     * @return 结果
     */
    @Override
    public int insertViewdataUploadScan(ViewdataUpload viewdataUpload) {
        String businessId = UUID.randomUUID().toString().replaceAll("-", "");//业务id
        Date date = new Date();//获取当前时间
        viewdataUpload.setDataSource(ViewdataUploadSource.ITU.getValue());
        viewdataUpload.setCreateTime(date);
        viewdataUpload.setBusinessId(businessId);
        viewdataUpload.setStatus("0");//状态默认0
        return viewdataUploadMapper.insertViewdataUpload(viewdataUpload);
    }

    /**
     * 新增保存影像上传
     *
     * @param viewdataUpload 影像上传信息
     * @return 结果
     */
    @Override
    public int addViewdataUpload(ViewdataUpload viewdataUpload) {
        String businessId = UUID.randomUUID().toString().replaceAll("-", "");//业务id
        Date date = new Date();//获取当前时间
        viewdataUpload.setCreateTime(date);
        viewdataUpload.setBusinessId(businessId);
        viewdataUpload.setStatus("0");//状态默认0
        viewdataUpload.setDelFlag("0");
        return viewdataUploadMapper.insertViewdataUpload(viewdataUpload);
    }

    /**
     * 修改影像上传
     *
     * @param viewdataUpload 影像上传信息
     * @return 结果
     */
    @Override
    public int updateViewdataUpload(ViewdataUpload viewdataUpload) {
        return viewdataUploadMapper.updateViewdataUpload(viewdataUpload);
    }

    /**
     * 删除影像上传对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteViewdataUploadByIds(String ids) {
        return viewdataUploadMapper.deleteViewdataUploadByIds(Convert.toStrArray(ids));
    }

}
