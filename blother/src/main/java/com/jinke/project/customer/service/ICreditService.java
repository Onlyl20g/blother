package com.jinke.project.customer.service;

import com.jinke.project.customer.domain.Credit;
import com.jinke.project.customer.domain.CreditDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 授信申请 服务层
 *
 * @author jinke
 * @date 2019-07-17
 */
public interface ICreditService {
    /**
     * 查询授信申请信息
     *
     * @param id 授信申请ID
     * @return 授信申请信息
     */
    public Credit selectCreditById(Long id);

    /**
     * 查询授信申请列表
     *
     * @param credit 授信申请信息
     * @return 授信申请集合
     */
    public List<CreditDto> selectCreditList(Credit credit);

    /**
     * 通过billId查询授信申请列表
     *
     * @return 授信申请集合
     */
    public List<CreditDto> selectCreditListByUsername(String billId);

    /**
     * 通过billId查询授信通过申请列表
     *
     * @return 授信申请集合
     */
    public List<CreditDto> selectSuccessCreditListByUsername(String billId);

    /**
     * 新增授信申请
     *
     * @param credit 授信申请信息
     * @return 结果
     */
    public int insertCredit(Credit credit);

    /**
     * 修改授信申请
     *
     * @param credit 授信申请信息
     * @return 结果
     */
    public int updateCredit(Credit credit);

    /**
     * 删除授信申请信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCreditByIds(String ids);


    /**
     * 保存授信申请
     *
     * @param
     * @return
     */
    public Map<String, Object> save(@RequestBody Map<String, String> map);

    /**
     * 保存授信申请附表
     *
     * @param
     * @return
     */
    Map<String, Object> saveCredits(List<Map<String, String>> map);

    /**
     * 资料补交
     */
    @PostMapping("/saveEdit")
    @ResponseBody
    public int saveEdit(@RequestBody Map<String, String> map);

    /**
     * 查询授信申请成功列表
     *
     * @param credit 授信申请信息
     * @return 授信申请集合
     */
    public List<Credit> selectCreditSucList(Credit credit);

}
