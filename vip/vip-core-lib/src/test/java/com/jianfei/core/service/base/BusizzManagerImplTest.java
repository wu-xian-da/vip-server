package com.jianfei.core.service.base;

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
import com.jianfei.core.dto.UserProvince;
import com.jianfei.core.service.base.impl.BusizzManagerImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
@Transactional
public class BusizzManagerImplTest {
	@Autowired
	private BusizzManagerImpl busizzManagerImpl;
	
	@Test
	public void testa(){
		List<UserProvince> list = busizzManagerImpl.getProvinceIdByUserId(71);
		for(UserProvince a :list){
			System.out.println(a);
		}
	}

}
