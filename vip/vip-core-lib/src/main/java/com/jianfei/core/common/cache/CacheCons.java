/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月25日-下午12:47:27
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.common.cache;

/**
 *
 * @Description: 缓存常量
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月25日 下午12:47:27
 * 
 * @version 1.0.0
 *
 */
public class CacheCons {

	public interface Sys {
		public final static String SYS_USER_LIST = "sys_user_list";
		public static final String SYS_ROLE_LIST = "sys_role_list";
		public static final String SYS_RESOURCE_LIST = "sys_resource_list";

		/**
		 * last_1_month:TODO（按照省统计上个月开卡数，月底清空）
		 *
		 * @version 1.0.0
		 */
		public static final String LAST_1_MONTH = "last_1_month";
		/**
		 * LAST_2_MONTH:TODO（按照省统计上上个月开卡数，月底清空）
		 *
		 * @version 1.0.0
		 */
		public static final String LAST_2_MONTH = "last_2_month";
		/**
		 * LAST_3_MONTH:TODO（按照省统计上上上个月开卡数，月底清空）
		 *
		 * @version 1.0.0
		 */
		public static final String LAST_3_MONTH = "last_3_month";
		/**
		 * LAST_MONTH_TOP:TODO（全国前三个省份，售卡统计,月底清空）
		 *
		 * @version 1.0.0
		 */
		public static final String LAST_MONTH_TOP3 = "last_month_top3";
		/**
		 * LAST_MONTH_TOTAL_ZHUGUAN:TODO(主管历史开卡总数，月底清除)
		 *
		 * @version 1.0.0
		 */
		public static final String LAST_MONTH_TOTAL_ZHUGUAN = "last_month_total_zhuguan";

	}

}
