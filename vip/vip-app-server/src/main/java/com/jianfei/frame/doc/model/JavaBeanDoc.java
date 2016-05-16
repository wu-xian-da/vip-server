
package com.jianfei.frame.doc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Create on @2014年7月16日 @下午6:55:47 
 * @author libinsong1204@gmail.com
 */
public class JavaBeanDoc {
	private String name;
	private String description;
	private List<FieldDoc> fieldDocs = new ArrayList<FieldDoc>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<FieldDoc> getFieldDocs() {
		return fieldDocs;
	}

	public void setFieldDocs(List<FieldDoc> fieldDocs) {
		this.fieldDocs = fieldDocs;
	}

	public static class FieldDoc {
		private String name;
		private String dataType;
		private boolean required = false;
		private String exampleData = "";
		private String description;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDataType() {
			return dataType;
		}
		public void setDataType(String dataType) {
			this.dataType = dataType;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public boolean isRequired() {
			return required;
		}
		public void setRequired(boolean required) {
			this.required = required;
		}
		public String getExampleData() {
			return exampleData;
		}
		public void setExampleData(String exampleData) {
			this.exampleData = exampleData;
		}
	}

}
