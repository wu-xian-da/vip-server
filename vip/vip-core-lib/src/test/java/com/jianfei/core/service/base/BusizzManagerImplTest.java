package com.jianfei.core.service.base;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AriPort;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.dto.UserProvince;
import com.jianfei.core.service.base.impl.BusizzManagerImpl;
import com.jianfei.core.service.sys.UserManaer;

/**
 * 业务人员
 * 
 * @Description: TODO
 * @author guo.jian
 * @Title: BusizzManagerImplTest.java
 * @date 2016年6月8日 下午6:05:51
 * @Version 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
@Transactional
public class BusizzManagerImplTest {
	@Autowired
	private BusizzManagerImpl busizzManagerImpl;
	@Autowired
	private UserManaer<User> userManager;

	@Test
	public void testa() {
		MessageDto<List<User>> dto = userManager.get(new MapUtils.Builder()
				.setKeyValue("uno", "1111").build());
		System.out.println(JSONObject.toJSONString(dto));
	}

	@Test
	public void testab() {
		List<Map<String, Object>> maps = busizzManagerImpl
				.selectMap(new MapUtils.Builder().setKeyValue("state", "0")
						.build());
		System.out.println(JSONObject.toJSONString(maps));
	}
}
