package com.jinke.project.system.supplierIdcardinfo.service;

import com.jinke.project.system.supplierIdcardinfo.domain.SupplierIdcardinfo;

import java.util.List;

/**
 * 供应商身份证 服务层
 *
 * @author jinke
 * @date 2019-08-20
 */
public interface ISupplierIdcardinfoService {
    /**
     * 查询供应商身份证信息
     *
     * @param id 供应商身份证ID
     * @return 供应商身份证信息
     */
    public SupplierIdcardinfo selectSupplierIdcardinfoById(Long id);

    /**
     * 查询供应商身份证列表
     *
     * @param supplierIdcardinfo 供应商身份证信息
     * @return 供应商身份证集合
     */
    public List<SupplierIdcardinfo> selectSupplierIdcardinfoList(SupplierIdcardinfo supplierIdcardinfo);

    /**
     * 新增供应商身份证
     *
     * @param supplierIdcardinfo 供应商身份证信息
     * @return 结果
     */
    public int insertSupplierIdcardinfo(SupplierIdcardinfo supplierIdcardinfo);

    /**
     * 修改供应商身份证
     *
     * @param supplierIdcardinfo 供应商身份证信息
     * @return 结果
     */
    public int updateSupplierIdcardinfo(SupplierIdcardinfo supplierIdcardinfo);

    /**
     * 删除供应商身份证信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSupplierIdcardinfoByIds(String ids);

    public int updateSupplierIdcardinfoAll(SupplierIdcardinfo supplierIdcardinfo);
}
