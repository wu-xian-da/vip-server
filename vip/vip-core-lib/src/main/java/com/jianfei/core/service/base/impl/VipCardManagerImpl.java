package com.jianfei.core.service.base.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppCustomer;
import com.jianfei.core.bean.AppVipcard;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.mapper.AppVipcardMapper;
import com.jianfei.core.service.base.VipCardManager;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Vip卡管理
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/20 10:45
 */
@Service
public class VipCardManagerImpl implements VipCardManager {
	@Autowired
	private AppVipcardMapper appVipcardMapper;

	/**
	 * 添加VipCard
	 *
	 * @param vipcard
	 * @return
	 */
	@Override
	public boolean addVipCard(AppVipcard vipcard) {
		return false;
	}

	/**
	 * 更新VipCard
	 *
	 * @param vipcard
	 * @return
	 */
	@Override
	public boolean updateVipCard(AppVipcard vipcard) {
		return false;
	}

	/**
	 * 添加VipCard使用记录
	 *
	 * @param customer
	 * @return
	 */
	@Override
	public boolean addVipUseInfo(AppCustomer customer) {
		return false;
	}

	/**
	 * 分页获取用户使用记录
	 *
	 * @param pageNo
	 *            页数
	 * @param pageSize
	 *            每页大小
	 * @param vipCardNo
	 *            Vip卡号
	 * @return
	 */
	@Override
	public PageInfo<AppCustomer> pageVipUseInfo(int pageNo, int pageSize, String vipCardNo) {
		return null;
	}

	/**
	 * 根据vip卡No获取所有使用记录
	 *
	 * @param vipCardNo
	 *            vipCardNo
	 * @return List<AppCustomer>
	 */
	@Override
	public List<AppCustomer> listAllVipUse(String vipCardNo) {
		return null;
	}

	/**
	 * 分页显示vip卡列表信息
	 */
	@Override
	public PageInfo<AppVipcard> showCardListPage(int pageNo, int pageSize, Map<String, Object> params) {
		// 显示第几页
		PageHelper.startPage(pageNo, pageSize);
		// 查询条件
		List<AppVipcard> list = appVipcardMapper.pageList(params);
		PageInfo<AppVipcard> pageInfo = new PageInfo(list);
		return pageInfo;
	}

	/**
	 * 将vip卡列表信息转换成Grid格式 bindVipCardGridData
	 * 
	 * @param pageInfo
	 * @return Grid
	 * @version 1.0.0
	 */
	public Grid bindVipCardGridData(PageInfo<AppVipcard> pageInfo) {
		List<AppVipcard> list = pageInfo.getList();
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		for (AppVipcard vipcard : list) {
			Map<String, Object> map = MapUtils.<AppVipcard> entityInitMap(vipcard);
			maps.add(map);
		}
		Grid grid = new Grid();
		grid.setRows(maps);
		grid.setTotal(pageInfo.getTotal());
		return grid;
	}

	/**
	 * 根据vip卡号逻辑删除vip卡信息
	 */
	@Override
	public int deleteVipCardByCardNo(String cardNo) {
		// TODO Auto-generated method stub
		return appVipcardMapper.delVipCard(cardNo);
	}

	/**
	 * 将excel表格数据导入到数据表中 importExcelToDB
	 * 
	 * @param filePath
	 *            void
	 * @version 1.0.0
	 */
	public int importExcelData(String filePath) {
		try {
			// 创建对Excel工作簿文件的引用
			XSSFWorkbook wookbook = new XSSFWorkbook(new FileInputStream(filePath));
			// 在Excel文档中，第一张工作表的缺省索引是0
			// 其语句为：HSSFSheet sheet = workbook.getSheetAt(0);
			XSSFSheet sheet = wookbook.getSheet("Sheet1");
			// 获取到Excel文件中的所有行数
			int rows = sheet.getPhysicalNumberOfRows();
			// 遍历行
			for (int i = 1; i < rows; i++) {
				// 读取左上端单元格
				XSSFRow row = sheet.getRow(i);
				// 行不为空
				if (row != null) {
					// 获取到Excel文件中的所有的列
					int cells = row.getPhysicalNumberOfCells();
					String value = "";
					// 遍历列
					for (int j = 0; j < cells; j++) {
						// 获取到列的值
						XSSFCell cell = row.getCell(j);
						if (cell != null) {
							switch (cell.getCellType()) {
							case HSSFCell.CELL_TYPE_FORMULA:
								break;
							case HSSFCell.CELL_TYPE_NUMERIC:
								value += cell.getNumericCellValue() + ",";
								break;
							case HSSFCell.CELL_TYPE_STRING:
								value += cell.getStringCellValue() + ",";
								break;
							default:
								value += "0";
								break;
							}
						}
					}
					// 将数据插入到mysql数据库中
					String[] val = value.split(",");
					AppVipcard vipCard = new AppVipcard();
					vipCard.setCardNo(val[1]);
					vipCard.setNfcId(val[2]);
					vipCard.setImportTime(new Date());
					vipCard.setDtflag(0);
					vipCard.setCardState(0);// 未激活
					System.out.println(vipCard);

					// 想数据表中插入一条数据
					appVipcardMapper.importExcelToDB(vipCard);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception

		}
		return 0;

	}

	

	/**
	 * 导出数据到excel表格中
	 */
	@Override
	public int exportDataToExcel(String fileName) {
		try {
			WritableWorkbook wwb = null;
			// 创建可写入的Excel工作簿
			//filename=d://
			
			System.out.println("filenma="+fileName);
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			// 以fileName为文件名来创建一个Workbook
			wwb = Workbook.createWorkbook(file);
			// 创建工作表
			WritableSheet ws = wwb.createSheet("Sheet1", 0);
			//设置表头
			Label labelCradNo = new Label(0, 0, "卡号(cardNo)");
			Label labelNfcId = new Label(1, 0, "nfc号(nfcId)");
			Label labelOrderState = new Label(2, 0, "状态(cardState)");
			ws.addCell(labelCradNo);
			ws.addCell(labelNfcId);
			ws.addCell(labelOrderState);
			
			// 查询数据库中所有的数据
			List<AppVipcard> list=appVipcardMapper.selAllVipCard();
			for (int i = 0; i < list.size(); i++) {
				String stateName = "";//激活状态
				Label labelId_i = new Label(0, i + 1, list.get(i).getCardNo());
				Label labelName_i = new Label(1, i + 1, list.get(i).getNfcId());
				if(list.get(i).getCardState() == 0){
					stateName = "未激活";
				}else if(list.get(i).getCardState() == 1){
					stateName = "激活";
				}else if(list.get(i).getCardState() == 2){
					stateName = "退卡";
				}
				Label labelSex_i = new Label(2, i + 1, stateName);
				ws.addCell(labelId_i);
				ws.addCell(labelName_i);
				ws.addCell(labelSex_i);
			}
			
			// 写进文档
			wwb.write();
			// 关闭Excel工作簿对象
			wwb.close();
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return 0;
	}

}
