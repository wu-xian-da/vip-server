/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月19日-下午5:07:45
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.base.impl;

import java.util.Map;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.dto.OrderShowInfoDto;

/**
 *
 * @Description: TODO
 * @author: guo.jian@jianfeitech.com 
 * @date: 2016年5月19日 下午5:07:45 
 * 
 * @version 1.0.0
 *
 */
@Service
public class VipRoomManager implements com.jianfei.core.service.base.VipRoomManager {
	@Override
	public PageInfo<OrderShowInfoDto> simplePage(int pageNo, int pageSize, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.jianfei.core.service.base.VipRoomManager#addVipRoom()
	 */
	@Override
	public void addVipRoom() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.jianfei.core.service.base.VipRoomManager#updateVipRoom()
	 */
	@Override
	public void updateVipRoom() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.jianfei.core.service.base.VipRoomManager#delVipRoom()
	 */
	@Override
	public void delVipRoom() {
		// TODO Auto-generated method stub

	}

}
