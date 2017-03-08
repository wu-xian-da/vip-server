package com.jianfei.controller.export;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jianfei.core.service.order.OrderManager;

/**
 * 数据导出
 * 
 * @ClassName: DataExportController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author guojian
 * @date 2017年3月8日 下午4:39:03
 *
 */
@Controller
@RequestMapping("/exportData")
public class DataExportController {
	@Autowired
	private OrderManager orderManager;

	@RequestMapping("/cosumeInfoOfVipCard")
	public void exportConsumeInfoOfVipCard(HttpServletResponse response) {
		orderManager.exportcCounsumeInfoOfVipCard(response);
	}
}
