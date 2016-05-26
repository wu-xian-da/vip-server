package com.jianfei.core.service.base;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppCustomer;
import com.jianfei.core.bean.AppVipcard;
import com.jianfei.core.dto.OrderShowInfoDto;

import java.util.List;
import java.util.Map;

/**
 * Vip使用记录
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/20 10:05
 */
public interface VipCardManager {
    /**
     * 添加VipCard
     * @param vipcard
     * @return
     */
    boolean addVipCard(AppVipcard vipcard);

    /**
     * 更新VipCard
     * @param vipcard
     * @return
     */
    boolean updateVipCard(AppVipcard vipcard);

    /**
     * 添加VipCard使用记录
     * @return
     */
    boolean addVipUseInfo(AppCustomer  customer);

    /**
     * 分页获取用户使用记录
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @param vipCardNo Vip卡号
     * @return
     */
    PageInfo<AppCustomer> pageVipUseInfo(int pageNo, int pageSize, String vipCardNo );

    /**
     * 根据vip卡No获取所有使用记录
     * @param vipCardNo vipCardNo
     * @return  List<AppCustomer>
     */
    List<AppCustomer> listAllVipUse( String vipCardNo );
    
    
    /**
     * 分页显示vip卡列表信息
     * showCardListPage
     * @param pageNo
     * @param pageSize
     * @param params
     * @return
     *PageInfo<OrderShowInfoDto>
     * @version  1.0.0
     */
    PageInfo<AppVipcard> showCardListPage(int pageNo, int pageSize,
            Map<String, Object> params);
    
    /**
     * 根据vip卡逻辑删除vip卡信息
     * deleteVipCardByCardNo
     * @param cardNo
     * @return
     * int
     * @version  1.0.0
     */
    int deleteVipCardByCardNo(String cardNo);
    
    /**
     * 导入excel表格数据到数据表中
     * importExcelData
     * @param appVipcard
     * @return
     * int
     * @version  1.0.0
     */
    int importExcelData(String filePath);
    
    /**
     * 导出数据到excel表格中
     * exportDataToExcel
     * @return
     *List<AppVipcard>
     * @version  1.0.0
     */
    int exportDataToExcel(String filePath);
}
