package com.jianfei.core.service.stat.impl;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import com.jianfei.core.bean.AppAirportArchive;
import com.jianfei.core.bean.AppOrderArchive;
import com.jianfei.core.common.cache.JedisUtils;
import com.jianfei.core.common.utils.PageDto;
import com.jianfei.core.dto.CharData;
import com.jianfei.core.dto.OrderAppDetailInfo;
import com.jianfei.core.dto.OrderShowInfoDto;
import com.jianfei.core.dto.ReturnCardDto;
import com.jianfei.core.dto.UserProvince;
import com.jianfei.core.mapper.AppOrderArchiveMapper;
import com.jianfei.core.mapper.ArchiveMapper;
import com.jianfei.core.service.stat.StatManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/20 16:11
 */
@Service
public class StatManagerImpl implements StatManager {
    @Autowired
    private AppOrderArchiveMapper appOrderArchiveMapper;

    /**
     * 分页获取个人每日开卡数据
     *
     * @param pageDto 分页数据
     * @param userId  用户唯一标示
     * @return
     */
    @Override
    public PageInfo<AppOrderArchive> pageOrderStatByUserId(PageDto pageDto, String userId) {
        PageHelper.startPage(pageDto.getPageNo(), pageDto.getPageSize());
        List<AppOrderArchive> list = appOrderArchiveMapper.selectByUserId(userId);
        PageInfo<AppOrderArchive> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    /**
     * 获取某天某人的开卡记录
     *
     * @param userId
     * @param date
     * @return
     */
    @Override
    public List<OrderAppDetailInfo> listOrderByUserId(String userId, String date) {
        return appOrderArchiveMapper.listOrderByUserId(userId, date);
    }

    /**
     * 分页获取某个业务员退卡数量
     *
     * @param pageDto 分页数据
     * @param userId  用户唯一标示
     * @return
     */
    @Override
    public PageInfo<ReturnCardDto> pageReturnVipCardsByUserId(PageDto pageDto, String userId) {
        PageHelper.startPage(pageDto.getPageNo(), pageDto.getPageSize());
        List<ReturnCardDto> list = appOrderArchiveMapper.selectReturnCardsByUserId(userId);
        PageInfo<ReturnCardDto> pageInfo = new PageInfo(list);
        return pageInfo;
    }
    
    /**
     * 销售榜单-详细图表接口
     * @throws ParseException 
     */
	public List<Map<String, Object>> getSticCardData(List<Map<String,Object>> proIdApIdList, String begin,String end) throws ParseException {
		// TODO Auto-generated method stub
		int days = returnDays(begin,end);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(int index =0;index <= days; index ++){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sf.parse(begin));
			calendar.add(Calendar.DATE, index);
			String date = sf.format(calendar.getTime());
			Map<String,Object> mapItem = new HashMap<String,Object>();
			mapItem.put("date", date);
			
			for(int i =0 ;i <proIdApIdList.size(); i ++){
				Object obj = JedisUtils.getObject(date+"$"+proIdApIdList.get(i).get("pid")+"$"+proIdApIdList.get(i).get("airportId"));
				if(obj == null){
					mapItem.put("cardNum",0);
				}else{
					CharData charData = JSON.parseObject(obj.toString(), CharData.class);
					mapItem.put("total", charData.getTotal());
				}
				
			}
			list.add(mapItem);
		}
		return list;
	}
	
	/**
	 * 个人中心销售榜单获取接口-key:日期+省份
	 * @throws ParseException 
	 */
	public List<Map<String, Object>> getSaleCurveByUserId(List<UserProvince> UserProvinceList, String begin,String end) throws ParseException {
		// TODO Auto-generated method stub
		int days = returnDays(begin,end);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(int index =0;index <= days; index ++){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sf.parse(begin));
			calendar.add(Calendar.DATE, index);
			String date = sf.format(calendar.getTime());
			Map<String,Object> mapItem = new HashMap<String,Object>();
			mapItem.put("date", date);
			
			float sum=0;
			//计算多个省份某天的平均值
			for(int i =0 ;i <UserProvinceList.size(); i ++){
				Object obj = JedisUtils.getObject(date+"$"+UserProvinceList.get(i).getProvinceId());
				if(obj == null){
					sum +=0;
				}else{
					//将json字符串转换为CharDate对象
					CharData charData = JSON.parseObject(obj.toString(), CharData.class);
					sum += Float.parseFloat(charData.getAvgNum());
				}
			}
			//多个省份的平均开卡数
			mapItem.put("avgNum", sum/UserProvinceList.size());
			list.add(mapItem);
		}
		return list;
	}
	
	
	/**
	 * 计算两个日期相差的天数
	 * @param begin
	 * @param end
	 * @return
	 */
	public int returnDays(String begin,String end){
		//天数
        int days = 0;
		try {
            //时间转换类
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(begin);
            Date date2 = sdf.parse(end);
            //将转换的两个时间对象转换成Calendard对象
            Calendar can1 = Calendar.getInstance();
            can1.setTime(date1);
            Calendar can2 = Calendar.getInstance();
            can2.setTime(date2);
            //拿出两个年份
            int year1 = can1.get(Calendar.YEAR);
            int year2 = can2.get(Calendar.YEAR);
            
            Calendar can = null;
            //如果can1 < can2
            //减去小的时间在这一年已经过了的天数
            //加上大的时间已过的天数
            if(can1.before(can2)){
                days -= can1.get(Calendar.DAY_OF_YEAR);
                days += can2.get(Calendar.DAY_OF_YEAR);
                can = can1;
            }else{
                days -= can2.get(Calendar.DAY_OF_YEAR);
                days += can1.get(Calendar.DAY_OF_YEAR);
                can = can2;
            }
            for (int i = 0; i < Math.abs(year2-year1); i++) {
                //获取小的时间当前年的总天数
                days += can.getActualMaximum(Calendar.DAY_OF_YEAR);
                //再计算下一年。
                can.add(Calendar.YEAR, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return days;
	}
	
	/**
	 *  根据业务人员id号查询某个时间段内的销售情况
	 */
	@Override
	public List<AppOrderArchive> selectCharDataByUserId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return appOrderArchiveMapper.selectCharDataByUserId(map);
	}
	
}
