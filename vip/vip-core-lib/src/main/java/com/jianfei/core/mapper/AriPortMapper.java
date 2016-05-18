/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月17日-下午5:35:29
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.mapper;

import com.jianfei.core.bean.AriPort;
import com.jianfei.core.common.persistence.MyBatisDao;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月17日 下午5:35:29
 * 
 * @version 1.0.0
 *
 */
@MyBatisDao
public interface AriPortMapper extends BaseMapper<AriPort> {

	public void delete(String id);

}