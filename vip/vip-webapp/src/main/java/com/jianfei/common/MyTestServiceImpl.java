package com.jianfei.common;

import org.springframework.stereotype.Component;

@Component
public class MyTestServiceImpl implements IMyTestService {
	@Override
	public void myTest() {
		System.out.println("进入测试");
	}
}