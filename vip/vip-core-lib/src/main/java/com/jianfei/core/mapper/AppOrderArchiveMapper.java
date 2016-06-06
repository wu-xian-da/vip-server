package com.jianfei.core.mapper;

import com.jianfei.core.bean.AppOrderArchive;
import com.jianfei.core.common.persistence.MyBatisDao;
import com.jianfei.core.dto.OrderAppDetailInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@MyBatisDao
public interface AppOrderArchiveMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_order_archive
     *
     * @mbggenerated Fri May 20 13:59:51 CST 2016
     */
    int deleteByPrimaryKey(Integer fileId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_order_archive
     *
     * @mbggenerated Fri May 20 13:59:51 CST 2016
     */
    int insert(AppOrderArchive record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_order_archive
     *
     * @mbggenerated Fri May 20 13:59:51 CST 2016
     */
    int insertSelective(AppOrderArchive record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_order_archive
     *
     * @mbggenerated Fri May 20 13:59:51 CST 2016
     */
    AppOrderArchive selectByPrimaryKey(Integer fileId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_order_archive
     *
     * @mbggenerated Fri May 20 13:59:51 CST 2016
     */
    int updateByPrimaryKeySelective(AppOrderArchive record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_order_archive
     *
     * @mbggenerated Fri May 20 13:59:51 CST 2016
     */
    int updateByPrimaryKey(AppOrderArchive record);

    List<AppOrderArchive> selectByUserId(@Param("userId")String userId);

    /**
     * 获取某天某人的开卡记录
     * @param userId
     * @param date
     * @return
     */
    List<OrderAppDetailInfo> listOrderByUserId(@Param("userId")String userId,@Param("date") String date);
}