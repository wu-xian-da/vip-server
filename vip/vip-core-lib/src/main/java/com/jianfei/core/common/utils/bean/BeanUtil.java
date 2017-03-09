package com.jianfei.core.common.utils.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.jianfei.core.dto.VipCardConsumeDto;

/**
 * bean操作的一些公用方法
 * 
 * @ClassName: BeanUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author guojian
 * @date 2017年3月9日 上午11:09:34
 *
 */
public class BeanUtil {
	/**
	 * 将map对象转化为javabean
	 * 
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 */
	public static <T> Object covertMap(Class<T> type, Map<Object, Object> map) throws IntrospectionException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		Object obj = type.newInstance();// 构建一个对象
		// 给javabean对象属性赋值
		PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor propertyDescriptor : properties) {
			String propertyName = propertyDescriptor.getName();
			if (map.containsKey(propertyName)) {
				Object value = map.get(propertyName);
				Object[] args = new Object[1];
				args[0] = value;

				propertyDescriptor.getWriteMethod().invoke(obj, args);
			}
		}

		return obj;
	}

	public static void main(String[] args) {
		Map<Object, Object> map = new HashMap<>();
		map.put("agentName", "guojian");
		map.put("customerName", "用戶姓名");
		map.put("orderState", 1);
		try {
			System.out.println(covertMap(VipCardConsumeDto.class, map));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
