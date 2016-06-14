package com.jianfei.core.service.base;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jianfei.core.service.base.impl.AriPortManagerImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
@Transactional
public class AriPortManagerImplTest {
	@Autowired
	private AriPortManagerImpl ariPortManagerImpl;
	
	@Test
	public void testSelAirportInfo(){
		ariPortManagerImpl.selectAirPortInfoById("0465798272992435");
	}
}
