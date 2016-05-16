
package com.jianfei.frame.doc;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author libinsong1204@gmail.com
 *
 */
public abstract class ClassUtils {
	private static final List<String> primitiveWrapperList = new ArrayList<String>();
	
	static {
		primitiveWrapperList.add("boolean");
		primitiveWrapperList.add(Boolean.class.getName());
		primitiveWrapperList.add("byte");
		primitiveWrapperList.add(Byte.class.getName());
		primitiveWrapperList.add("char");
		primitiveWrapperList.add(Character.class.getName());
		primitiveWrapperList.add("double");
		primitiveWrapperList.add(Double.class.getName());
		primitiveWrapperList.add("float");
		primitiveWrapperList.add(Float.class.getName());
		primitiveWrapperList.add("int");
		primitiveWrapperList.add(Integer.class.getName());
		primitiveWrapperList.add("long");
		primitiveWrapperList.add(Long.class.getName());
		primitiveWrapperList.add("short");
		primitiveWrapperList.add(Short.class.getName());
		primitiveWrapperList.add(String.class.getName());
	}
	
	public static boolean isPrimitiveOrWrapper(String typeName) {
		Assert.notNull(typeName, "typeName must not be null");
		return primitiveWrapperList.contains(typeName);
	}
}
