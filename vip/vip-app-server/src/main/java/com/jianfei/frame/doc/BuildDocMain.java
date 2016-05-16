
package com.jianfei.frame.doc;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.jianfei.frame.utils.EnvUtil;
import com.jianfei.frame.utils.SystemPropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static java.io.File.separator;

/**
 * Create on @2014年7月16日 @下午10:03:11 
 * @author libinsong1204@gmail.com
 */
public class BuildDocMain {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceDocBuilder.class);
	
	public static void main(String[] args) throws Exception {
		ServiceDocBuilder docBuilder = new ServiceDocBuilder();
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		File jsonFile = getJsonFile();
		JsonGenerator jsonGenerator = mapper.getFactory().createGenerator(jsonFile, JsonEncoding.UTF8);
		jsonGenerator.writeObject(docBuilder.buildDoc());
		
		LOGGER.info("服务API文档信息：" + jsonFile.getPath());
	}
	
	private static File getJsonFile() {
		String docPath = null;
		if(EnvUtil.isDevelopment()) {
			docPath = EnvUtil.getProjectBaseDir() + separator +
					"src" + separator + "main" + separator + "resources" + separator
					+ EnvUtil.getProjectName() + ".json";
		} else {
			docPath = SystemPropertyUtil.get("BASE_HOME") + separator + "conf" + separator
					+ EnvUtil.getProjectName() + ".json";
		}
		
		return new File(docPath);
	}
}
