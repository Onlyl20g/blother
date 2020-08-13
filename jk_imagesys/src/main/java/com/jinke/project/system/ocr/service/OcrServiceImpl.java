package com.jinke.project.system.ocr.service;

import com.jinke.common.utils.text.Convert;
import com.jinke.project.system.ocr.domain.Ocr;
import com.jinke.project.system.ocr.mapper.OcrMapper;
import com.jinke.project.system.user.domain.User;
import com.jinke.project.system.user.mapper.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * ocr 服务层实现
 *
 * @author jinke
 * @date 2019-09-09
 */
@Service
public class OcrServiceImpl implements IOcrService {
    @Autowired
    private OcrMapper ocrMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询ocr信息
     *
     * @param id ocrID
     * @return ocr信息
     */
    @Override
    public Ocr selectOcrById(Integer id) {
        return ocrMapper.selectOcrById(id);
    }

    /**
     * 查询ocr列表
     *
     * @param ocr ocr信息
     * @return ocr集合
     */
    @Override
    public List<Ocr> selectOcrList(Ocr ocr) {
        return ocrMapper.selectOcrList(ocr);
    }

    /**
     * 新增ocr
     *
     * @param ocr ocr信息
     * @return 结果
     */
    @Override
    public int insertOcr(Ocr ocr) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        ocr.setUserBusinessId(user.getBusinessId());
        ocr.setBusinessId(UUID.randomUUID().toString().replaceAll("-", ""));
        ocr.setStatus("0");
        ocr.setCreateTime(new Date());
        return ocrMapper.insertOcr(ocr);
    }

    /**
     * 修改ocr
     *
     * @param ocr ocr信息
     * @return 结果
     */
    @Override
    public int updateOcr(Ocr ocr) {
        return ocrMapper.updateOcr(ocr);
    }

    /**
     * 删除ocr对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteOcrByIds(String ids) {
        return ocrMapper.deleteOcrByIds(Convert.toStrArray(ids));
    }

}
