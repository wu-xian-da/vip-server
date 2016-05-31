package com.jianfei.core.service.base.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppCustomer;
import com.jianfei.core.bean.AppVipcard;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.mapper.AppVipcardMapper;
import com.jianfei.core.service.base.VipCardManager;

import java.io.InputStream;
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
	public int importExcelData(InputStream in) {
		try {
			// 创建对Excel工作簿文件的引用
			//XSSFWorkbook wookbook = new XSSFWorkbook(new FileInputStream(filePath));
			XSSFWorkbook wookbook = new XSSFWorkbook(in);
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
	 * 获取数据表中所有的数据，用于导出excel表格
	 * @return
	 */
	public List<AppVipcard> getAllAppVipcardInfo(){
		return appVipcardMapper.selAllVipCard();
	}
	
	/**
	 * 根据vip card no 获取VIP卡片信息
	 *
	 * @param vipCardNo VIP卡号
	 * @return
	 */
	@Override
	public AppVipcard getVipCardByNo(String vipCardNo) {
		return appVipcardMapper.selectByPrimaryKey(vipCardNo);
	}
	
	
}
