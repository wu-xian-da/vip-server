
package com.jianfei.frame.doc.model;


import com.jianfei.frame.utils.EnvUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author libinsong1204@gmail.com
 *
 */
public class ClassDoc {
	private String version;
	private String serviceName;
	private String serviceDesc;
	private List<MethodDoc> methodDocs = new ArrayList<MethodDoc>();
	private List<JavaBeanDoc> beanDocs = new ArrayList<JavaBeanDoc>();
	
	public ClassDoc() {
		version = EnvUtil.getBuildVersion();
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceDesc() {
		return serviceDesc;
	}
	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}
	public List<MethodDoc> getMethodDocs() {
		return methodDocs;
	}
	public void setMethodDocs(List<MethodDoc> methodDocs) {
		this.methodDocs = methodDocs;
	}
	public List<JavaBeanDoc> getBeanDocs() {
		return beanDocs;
	}
	public void setBeanDocs(List<JavaBeanDoc> beanDocs) {
		this.beanDocs = beanDocs;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}
