package com.jianfei.core.service.stat.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppOrderArchive;
import com.jianfei.core.common.cache.JedisUtils;
import com.jianfei.core.common.utils.PageDto;
import com.jianfei.core.dto.CharData;
import com.jianfei.core.dto.GraphDto;
import com.jianfei.core.dto.OrderAppDetailInfo;
import com.jianfei.core.dto.OrderPageDto;
import com.jianfei.core.dto.ReturnCardDto;
import com.jianfei.core.dto.SalesRankingDto;
import com.jianfei.core.dto.UserProvince;
import com.jianfei.core.mapper.AppOrderArchiveMapper;
import com.jianfei.core.mapper.AppOrdersMapper;
import com.jianfei.core.service.stat.StatManager;

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
	@Autowired
	private AppOrdersMapper ordersMapper;

	/**
	 * 分页获取个人每日开卡数据
	 *
	 * @param pageDto 分页数据
	 * @param uno     用户工号
	 * @return
	 */
	@Override
	public PageInfo<GraphDto> pageOrderStatByUserUno(PageDto pageDto, String uno) {
		PageHelper.startPage(pageDto.getPageNo(), pageDto.getPageSize());
		List<GraphDto> list = appOrderArchiveMapper.selectByUserUno(uno);
		PageInfo<GraphDto> pageInfo = new PageInfo(list);
		return pageInfo;
	}

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
     * x轴：场站名称  y轴：该场站在所选时间段内所有的开卡总数
     * @throws ParseException 
     */
	public List<Map<String, Object>> getSticCardData(List<Map<String,Object>> proIdApIdList, String begin,String end) throws ParseException {
		// TODO Auto-generated method stub
		int days = returnDays(begin,end);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		//场站列表
		for(Map<String,Object> proIdApIdMap:proIdApIdList){
			Map<String,Object> mapItem = new HashMap<String,Object>();
			//该场站在选择时间内所有的开卡总数
			int sum =0;
			int backTotal = 0;
			for(int index =0;index <= days; index ++){
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(sf.parse(begin));
				calendar.add(Calendar.DATE, index);
				String date = sf.format(calendar.getTime());
				Object obj = JedisUtils.getObject(date+"$"+proIdApIdMap.get("pid")+"$"+proIdApIdMap.get("airportId"));
				if(obj == null){
					sum +=0;
					backTotal +=0;
				}else{
					CharData charData = JSON.parseObject(obj.toString(), CharData.class);
					sum += Float.parseFloat(charData.getTotal() == null ? "0" : charData.getTotal());
					backTotal += Float.parseFloat(charData.getBack_order_total() == null ? "0" : charData.getBack_order_total());
				}
			}
			//场站名称
			mapItem.put("airPortName", proIdApIdMap.get("anames"));
			//该场站在选择时间内所有的开卡数量
			mapItem.put("total", sum);
			mapItem.put("backTotal", backTotal);
			list.add(mapItem);
		}
		
		return list;
	}
	
	/**
     * 销售榜单-详细图表接口
     * x轴：日期  y轴：该场站在所选时间段内所有的开卡总数
     * @throws ParseException 
     */
	public Map<String,Object> returnCardNumByDate(List<Map<String,Object>> proIdApIdList, 
			String begin,String end) throws ParseException {
		// TODO Auto-generated method stub
		int days = returnDays(begin,end);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		
		//1、返回数据----x轴：日期  y轴：该场站在所选时间段内所有的开卡总数
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		//2、返回数据----所选期间所有的开卡总数和退卡总数
		Map<String,Object> totalMap = new HashMap<String,Object>();
		
		//在所选期间所有的开卡总数
		int sumAllDay = 0;
		//在所选期间所有的退卡总数
		int backTotalAllDay = 0;
		
		//间隔天数
		for(int index =0;index <= days; index ++){
			Map<String,Object> mapItem = new HashMap<String,Object>();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sf.parse(begin));
			calendar.add(Calendar.DATE, index);
			String date = sf.format(calendar.getTime());
			//每天的开卡总数
			float sum = 0;
			//每天的退卡总数
			float backTotal = 0;
			//业务人员数量
			int agentNum = 0;
			//场站列表
			for(Map<String,Object> proIdApIdMap:proIdApIdList){
				Object obj = null;
				String test = date+"$"+proIdApIdMap.get("pid")+"$"+proIdApIdMap.get("airportId");
				try {
					obj = JedisUtils.getObject(test);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
				if(obj == null){
					sum +=0;
					backTotal +=0;
				}else{
					CharData charData = JSON.parseObject(obj.toString(), CharData.class);
					sum += Float.parseFloat(charData.getTotal() == null ? "0" :charData.getTotal());
					backTotal += Float.parseFloat(charData.getBack_order_total() ==null ? "0" : charData.getBack_order_total());
					agentNum += Integer.parseInt(charData.getPcount() == null ? "0" : charData.getPcount());
				}
			}
			mapItem.put("date", date);
			mapItem.put("total", sum);
			mapItem.put("back_total", backTotal);
			mapItem.put("avgNum",agentNum== 0 ? 0 : formatNum( (double)sum/agentNum) );
			mapItem.put("avgNum_back",agentNum== 0 ? 0 : formatNum( (double)backTotal/agentNum) );
			list.add(mapItem);
			
			sumAllDay += sum;
			backTotalAllDay += backTotal;
		}
		
		totalMap.put("saleCardNumTotal", sumAllDay);
		totalMap.put("backCardNumTotal", backTotalAllDay);
		
		Map<String,Object> resList = new HashMap<String,Object>();
		resList.put("cardNumList", list);
		resList.put("total", totalMap);
		
		return resList;
	}
		
	/** 所属省份平均开卡数
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
			float sum_back = 0;
			int agentNum = 0;
			//计算多个省份某天的平均值
			if(UserProvinceList !=null && UserProvinceList.size()>=1){
				for(int i =0 ;i <UserProvinceList.size(); i ++){
					Object obj = JedisUtils.getObject(date+"$"+UserProvinceList.get(i).getProvinceId());
					if(obj == null){
						sum +=0;
						sum_back +=0;
					}else{
						//将json字符串转换为CharDate对象
						CharData charData = JSON.parseObject(obj.toString(), CharData.class);
						sum += Float.parseFloat(charData.getAvgNum()==null ? "0.00": charData.getTotal());
						sum_back += Float.parseFloat(charData.getAvgNum_back() ==null ? "0.00" : charData.getBack_order_total());
						agentNum += Integer.parseInt(charData.getPcount() == null ? "0" : charData.getPcount());

					}
				}
				//平均开卡数
				mapItem.put("avgNum",agentNum==0 ? 0 : formatNum(sum/agentNum));
				//平均退卡数
				mapItem.put("avgNum_back",agentNum == 0 ? 0 : formatNum(sum_back/agentNum));
			}else{
				mapItem.put("avgNum", "0.00");
				mapItem.put("avgNum_back", "0.00");
			}
			
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
	 * 保留两位小数
	 * 
	 * @param num
	 */
	public String formatNum(double num) {
		java.text.DecimalFormat   df   =new   java.text.DecimalFormat("0.00");
		return df.format(num);

	}
	public static void main(String[] args){
		
		StatManagerImpl a = new StatManagerImpl();
		System.out.println(a.formatNum(Float.parseFloat("0.2500")));
	}
	
	/**
	 *  根据业务人员工号查询某个时间段内的销售情况
	 */
	@Override
	public List<CharData> selectCharDataByUserId(Map<String, Object> map) {
		//1、每天的开卡列表
		List<CharData> orderNumList = appOrderArchiveMapper.selectOrderNumByUserId(map);
		//2、每天的退卡列表
		List<CharData> backNUmList = appOrderArchiveMapper.selectBackCardByUserId(map);
		//3、按查询时间合并两个列表
		for(CharData orderNum : orderNumList){
			int flag = 0;//--0代表没有相同的时间
			Date orderFileTime = orderNum.getDate();
			
			for(CharData backNum : backNUmList){
				//如果两个归档时间相同，则合并到一条记录中
				if(orderFileTime.compareTo(backNum.getDate())==0){
					backNum.setTotal(orderNum.getTotal());
					flag = 1;
					break;
				}
			}
			
			if(flag == 0){
				backNUmList.add(orderNum);
			}
		}
		return backNUmList;
	}
	
	@Override
	public List<SalesRankingDto> salesRanking(String uno, String pid,
			String airportId, String begin, String end, int pageNo, int pageSize) {
		Map<String,Object> param = new HashMap<String, Object>();
		
		param.put("pageStart", (pageNo - 1) * pageSize);
		param.put("pageSize", pageSize);
		param.put("startTime", begin);
		param.put("endTime", end);
		if (airportId != null)
			if (!airportId.equals(""))
				param.put("airport_id", StringUtils.split(airportId, ','));
		if (pid != null)
			if (!pid.equals(""))
				param.put("pid", pid);
		
		return appOrderArchiveMapper.salesRanking(param);
	}

	@Override
	public List<Map<String, Object>> getSaleCurveByUserId(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 根据工号获取业务员需要处理的数据
	 *
	 * @param uno 工号
	 * @return
	 */
	@Override
	public List<GraphDto> getSaleToDoData(String uno) {
		return ordersMapper.getSaleToDoData(uno);
	}

	/**
	 * 分页查询订单相关状态
	 *
	 * @param uno 销售员工号
	 * @param state 订单状态
	 * @return
	 */
	@Override
	public PageInfo<OrderPageDto> pageOrderInfoBySale(String uno,String state,int pageNo,int pageSize,String key) {
		// state 1 全部 2 代付款 3 未激活 4 退款中 5 已退卡
		PageHelper.startPage(pageNo, pageSize);
		//订单状态
		List<Integer> orderStates=null;
		List<Integer> cardStates=null;
		if ("2".equals(state)){
			orderStates=new ArrayList<>();
			orderStates.add(0);
		}else if ("3".equals(state)){
			orderStates=new ArrayList<>();
			cardStates=new ArrayList<>();
			orderStates.add(1);
			cardStates.add(3);
			cardStates.add(4);
		}else if ("4".equals(state)){
			orderStates=new ArrayList<>();
			orderStates.add(2);
			orderStates.add(3);
		}else if ("5".equals(state)){
			orderStates=new ArrayList<>();
			orderStates.add(4);
		}
		List<OrderPageDto> list=ordersMapper.orderListBySale(uno,orderStates,cardStates,key);
		PageInfo<OrderPageDto> pageDtoPageInfo=new PageInfo<>(list);
		return pageDtoPageInfo;
	}
}
