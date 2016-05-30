package com.jianfei.core.service.base;

import java.util.List;

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

	@Test
	public void test1() {
		System.out.println("sdwede");
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

}
