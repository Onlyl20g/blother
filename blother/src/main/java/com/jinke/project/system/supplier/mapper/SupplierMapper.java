package com.jinke.project.system.supplier.mapper;

import com.jinke.project.system.supplier.domain.Supplier;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 供应商 数据层
 *
 * @author jinke
 * @date 2019-07-24
 */
@Mapper
@Component
public interface SupplierMapper {
    /**
     * 查询供应商信息
     *
     * @param id 供应商ID
     * @return 供应商信息
     */
    public Supplier selectSupplierById(Long id);

    /**
     * 查询供应商列表
     *
     * @param supplier 供应商信息
     * @return 供应商集合
     */
    public List<Supplier> selectSupplierList(Supplier supplier);


    /**
     * 查询供应商列表详细信息
     *
     * @param supplier 供应商信息
     * @return 供应商集合
     */
    public List<Supplier> selectSupplierListInfo(Supplier supplier);

    /**
     * 新增供应商
     *
     * @param supplier 供应商信息
     * @return 结果
     */
    public int insertSupplier(Supplier supplier);

    /**
     * 修改供应商
     *
     * @param supplier 供应商信息
     * @return 结果
     */
    public int updateSupplier(Supplier supplier);


    /**
     * 根据多id修改供应商
     *
     * @param ids
     * @return 结果
     */
    public int updateSupplierByIds(String[] ids);

    /**
     * 删除供应商
     *
     * @param id 供应商ID
     * @return 结果
     */
    public int deleteSupplierById(Long id);

    /**
     * 批量删除供应商
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSupplierByIds(String[] ids);

    /**
     * 校验第一次登录用户
     *
     * @param loginName 登录，名
     * @return
     */
    public Supplier checkLoginNameFirst(String loginName);

    /**
     * 根据用户名称查询用户信息
     *
     * @param userName
     * @return
     */
    public Supplier selectSupplierByUserName(String userName);

    public int updateSupplierEdit(Supplier supplier);
}