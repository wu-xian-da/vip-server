package com.jianfei.core.common.utils.export;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.jianfei.core.common.myAnnotation.ExcelAnnotation;

/**
 * 
 * @ClassName: ExcelUtils
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author guojian
 * @date 2017年3月8日 下午2:27:18
 *
 */
public class ExcelUtils {

	/**
	 * 生成excel表格
	 * 
	 * @param list
	 * @param response
	 * @param clazz
	 */
	public static <T> void fillDataToExcel(List<T> list, HttpServletResponse response, Class<T> clazz) {

		// 生成提示信息，
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		try {
			// 下载文件名
			String downLoadFileName = clazz.getAnnotation(ExcelAnnotation.class).name();
			codedFileName = java.net.URLEncoder.encode(downLoadFileName, "UTF-8");
			response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
			// 产生工作簿对象
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 产生工作表对象
			HSSFSheet sheet = workbook.createSheet();
			// 设置表头
			Field[] fields = clazz.getDeclaredFields();
			int fieldNum = fields.length;
			HSSFRow head = sheet.createRow(0);
			for (int index = 0; index < fieldNum; index++) {
				HSSFCell idCell = head.createCell(index);
				idCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				idCell.setCellValue(fields[index].getAnnotation(ExcelAnnotation.class).name());
			}
			// 填充表格数据
			for (int row = 1; row < list.size(); row++) {
				// 创建一行
				HSSFRow contentRow = sheet.createRow(row);
				for (int index = 0; index < fieldNum; index++) {
					HSSFCell id = contentRow.createCell(index);
					id.setCellType(HSSFCell.CELL_TYPE_STRING);
					id.setCellValue(BeanUtils.getProperty(list.get(row), fields[index].getName()));
				}

			}

			fOut = response.getOutputStream();
			workbook.write(fOut);
		} catch (UnsupportedEncodingException e1) {
		} catch (Exception e) {
		} finally {
			try {
				fOut.flush();
				fOut.close();
			} catch (IOException e) {
			}

		}
	}
}
