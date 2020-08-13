package com.jinke.project.system.ocr.service;

import com.jinke.project.system.ocr.domain.Ocr;

import java.util.List;

/**
 * ocr 服务层
 *
 * @author jinke
 * @date 2019-09-09
 */
public interface IOcrService {
    /**
     * 查询ocr信息
     *
     * @param id ocrID
     * @return ocr信息
     */
    public Ocr selectOcrById(Integer id);

    /**
     * 查询ocr列表
     *
     * @param ocr ocr信息
     * @return ocr集合
     */
    public List<Ocr> selectOcrList(Ocr ocr);

    /**
     * 新增ocr
     *
     * @param ocr ocr信息
     * @return 结果
     */
    public int insertOcr(Ocr ocr);

    /**
     * 修改ocr
     *
     * @param ocr ocr信息
     * @return 结果
     */
    public int updateOcr(Ocr ocr);

    /**
     * 删除ocr信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOcrByIds(String ids);

}
