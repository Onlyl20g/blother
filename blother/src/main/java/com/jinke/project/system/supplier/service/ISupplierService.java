package com.jinke.project.system.supplier.service;

import com.jinke.project.system.supplier.domain.Supplier;
import com.jinke.project.system.supplier.domain.SupplierDto;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * 供应商 服务层
 *
 * @author jinke
 * @date 2019-07-24
 */
public interface ISupplierService {
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
     * selectSupplierListInfo
     *
     * @param supplier 供应商信息
     * @return 供应商集合
     */
    public List<SupplierDto> selectSupplierListInfo(Supplier supplier);

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
    public int updateSupplier(Supplier supplier) throws Exception;


    /**
     * 修改供应商
     *
     * @param supplier 供应商信息
     * @return 结果
     */
    public int updateSupplierEdit(Supplier supplier) throws Exception;

    /**
     * 删除供应商信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSupplierByIds(String ids);


    /**
     * 供应商注册
     *
     * @param map
     * @return
     */
    public String register(@RequestBody Map<String, String> map);

    /**
     * 修改状态
     *
     * @param supplier
     * @return
     */
    public int changeStatus(Supplier supplier);

    /**
     * 根据登陆名查询供应商
     *
     * @param username
     * @return
     */
    public Supplier selectSupplierByUserName(String username);

    /**
     * 修改密码
     *
     * @param supplier
     * @return
     */
    public int updateSupplierNewPassWord(Supplier supplier);

}
