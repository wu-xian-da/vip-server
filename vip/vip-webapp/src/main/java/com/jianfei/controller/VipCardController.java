package com.jianfei.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppVipcard;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.service.base.impl.VipCardManagerImpl;


/**
 * 
 *
 * @Description: TODO
 * @author: guo.jian@jianfeitech.com
 * @date: 2016年5月24日 上午6:51:25
 * 
 * @version 1.0.0
 *
 */
@Controller
@RequestMapping("vipCard")
public class VipCardController extends BaseController {
	@Autowired
	private VipCardManagerImpl vipCardManagerImpl;

	/**
	 * 跳转到vip卡管理页面 goVipCardManageView
	 * 
	 * @return String
	 * @version 1.0.0
	 */
	@RequestMapping(value = "goVipCardManageView")
	public String goVipCardManageView() {
		return "vipcard/vipCardManagement";
	}

	/**
	 * 分页显示卡列表信息 showVipCardList
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return Grid
	 * @version 1.0.0
	 */
	@RequestMapping(value = "showVipCardList", method = RequestMethod.POST)
	@ResponseBody
	public Grid showVipCardList(@RequestParam(value = "page", defaultValue = "1") Integer pageNo,
			@RequestParam(value = "rows", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
		System.out.println("");
		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, "_");
		if (searchParams != null) {
			if (searchParams.get("cardState") == null || searchParams.get("cardState").equals("")) {
				searchParams.remove("cardState");
			}
		}
		PageInfo<AppVipcard> pageInfo = vipCardManagerImpl.showCardListPage(pageNo, pageSize, searchParams);
		intiWebContentEnv();
		return vipCardManagerImpl.bindVipCardGridData(pageInfo);

	}

	/**
	 * 逻辑删除vip卡信息 delVipCard
	 * 
	 * @param vipCard
	 * @return MessageDto<AppVipcard>
	 * @version 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "delVipCard", method = RequestMethod.POST)
	public MessageDto<AppVipcard> delVipCard(AppVipcard vipCard) {
		vipCardManagerImpl.deleteVipCardByCardNo(vipCard.getCardNo());
		return new MessageDto<AppVipcard>().setOk(true).setMsgBody(MessageDto.MsgFlag.SUCCESS);

	}

	/**
	 * 将excel表格数据导入到数据库
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	public Map<String,Object> importExcel(@RequestParam("filename") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String,Object> respMap = new HashMap<String,Object>();
		int result =1;
		try {
			InputStream in = file.getInputStream();
			vipCardManagerImpl.importExcelData(in);
		} catch (Exception e) {
			// TODO: handle exception
			result = 0;
		}
		respMap.put("result", result);
		return respMap;
	}

	/**
	 * 导出excle表格
	 * @param request
	 * @param response
	 */
	@RequestMapping("exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
		// 生成提示信息，
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		try {
			// 进行转码，使其支持中文文件名
			codedFileName = java.net.URLEncoder.encode("vip卡信息", "UTF-8");
			response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
			// 产生工作簿对象
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 产生工作表对象
			HSSFSheet sheet = workbook.createSheet();
			//设置表头
			HSSFRow head = sheet.createRow((int) 0);
			HSSFCell idCell = head.createCell((int) 0);
			idCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			idCell.setCellValue("序号");
			
			HSSFCell cardCell = head.createCell((int) 1);
			cardCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cardCell.setCellValue("vip卡号");
			
			HSSFCell nfcCell = head.createCell((int) 2);
			nfcCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			nfcCell.setCellValue("nfc号");
			
			HSSFCell activeStateCell = head.createCell((int) 3);
			activeStateCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			activeStateCell.setCellValue("激活状态");
			
			//返回表中所有的数据
			List<AppVipcard> list = vipCardManagerImpl.getAllAppVipcardInfo();
			int index =1;
			for(AppVipcard vipCard : list){
				// 创建一行
				HSSFRow row = sheet.createRow((int) index);
				
				HSSFCell id = row.createCell((int) 0);
				id.setCellType(HSSFCell.CELL_TYPE_STRING);
				id.setCellValue(index);
				
				HSSFCell cardNo = row.createCell((int) 1);
				cardNo.setCellType(HSSFCell.CELL_TYPE_STRING);
				cardNo.setCellValue(vipCard.getCardNo());
				
				HSSFCell nfcNo = row.createCell((int) 2);
				nfcNo.setCellType(HSSFCell.CELL_TYPE_STRING);
				nfcNo.setCellValue(vipCard.getNfcId());
				
				HSSFCell activeState = row.createCell((int) 3);
				activeState.setCellType(HSSFCell.CELL_TYPE_STRING);
				if(vipCard.getCardState() == 0){
					activeState.setCellValue("未激活");
				}else if(vipCard.getCardState() == 1){
					activeState.setCellValue("激活");
				}else if(vipCard.getCardState() == 2){
					activeState.setCellValue("退卡");
				}
				index ++;
				
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

	/**
	 * vip卡权益 vipCardRightsView
	 * 
	 * @return String
	 * @version 1.0.0
	 */
	@RequestMapping("vipCardRightsView")
	public String vipCardRightsView() {
		return "vipcard/vipCardRights";
	}

}
