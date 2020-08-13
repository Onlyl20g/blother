package com.jinke.project.system.supplierIdcardinfo.service;

import com.jinke.common.utils.text.Convert;
import com.jinke.project.system.supplierIdcardinfo.domain.SupplierIdcardinfo;
import com.jinke.project.system.supplierIdcardinfo.mapper.SupplierIdcardinfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 供应商身份证 服务层实现
 *
 * @author jinke
 * @date 2019-08-20
 */
@Service
public class SupplierIdcardinfoServiceImpl implements ISupplierIdcardinfoService {
    @Autowired
    private SupplierIdcardinfoMapper supplierIdcardinfoMapper;

    /**
     * 查询供应商身份证信息
     *
     * @param id 供应商身份证ID
     * @return 供应商身份证信息
     */
    @Override
    public SupplierIdcardinfo selectSupplierIdcardinfoById(Long id) {
        return supplierIdcardinfoMapper.selectSupplierIdcardinfoById(id);
    }

    /**
     * 查询供应商身份证列表
     *
     * @param supplierIdcardinfo 供应商身份证信息
     * @return 供应商身份证集合
     */
    @Override
    public List<SupplierIdcardinfo> selectSupplierIdcardinfoList(SupplierIdcardinfo supplierIdcardinfo) {
        return supplierIdcardinfoMapper.selectSupplierIdcardinfoList(supplierIdcardinfo);
    }

    /**
     * 新增供应商身份证
     *
     * @param supplierIdcardinfo 供应商身份证信息
     * @return 结果
     */
    @Override
    public int insertSupplierIdcardinfo(SupplierIdcardinfo supplierIdcardinfo) {
        return supplierIdcardinfoMapper.insertSupplierIdcardinfo(supplierIdcardinfo);
    }

    /**
     * 修改供应商身份证
     *
     * @param supplierIdcardinfo 供应商身份证信息
     * @return 结果
     */
    @Override
    public int updateSupplierIdcardinfo(SupplierIdcardinfo supplierIdcardinfo) {
        return supplierIdcardinfoMapper.updateSupplierIdcardinfo(supplierIdcardinfo);
    }

    /**
     * 删除供应商身份证对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSupplierIdcardinfoByIds(String ids) {
        return supplierIdcardinfoMapper.deleteSupplierIdcardinfoByIds(Convert.toStrArray(ids));
    }

    public int updateSupplierIdcardinfoAll(SupplierIdcardinfo supplierIdcardinfo) {
        return supplierIdcardinfoMapper.updateSupplierIdcardinfoAll(supplierIdcardinfo);
    }
}
