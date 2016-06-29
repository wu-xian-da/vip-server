package com.jianfei.core.service.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.crypto.hash.SimpleHash;
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
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MessageDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
@Transactional
public class AriPortManagerTest {
	@Autowired
	private AriPortManager<AriPort> ariPortManager;

	@Autowired
	private AppPictureManager appPictureManager;

	@Test
	public void test1() {
		PageHelper.startPage(1, 10);
		appPictureManager.get(new MapUtils.Builder().build());
	}

	@Test
	public void test3() {
		@SuppressWarnings("unused")
		Page<AriPort> ariPorts = PageHelper.startPage(1, 10);
		MessageDto<List<AriPort>> messageDto = ariPortManager
				.get(new MapUtils.Builder().setKeyValue("name", "")
						.setKeyValue("dtflag", GloabConfig.OPEN).build());
		if (messageDto.isOk()) {
			PageInfo pageInfo = new PageInfo(messageDto.getData());
			System.out.println(pageInfo.getTotal());
		}
	}

	@Test
	public void test5() {
		List<Map<String, Object>> maps = ariPortManager
				.selectCityById(new MapUtils.Builder().setKeyValue("pid", "0")
						.build());
		System.out.println(JSONObject.toJSONString(maps));
	}

	@Test
	public void testValidateExist() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "北京站");
		boolean result = ariPortManager.validateAirPortExist(map);
		System.out.println(result);
	}
}
