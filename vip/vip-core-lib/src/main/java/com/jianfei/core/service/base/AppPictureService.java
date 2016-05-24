/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月23日-下午11:22:41
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.base;

import java.util.List;
import java.util.Map;

import com.jianfei.core.bean.AppPicture;
import com.jianfei.core.common.utils.MessageDto;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月23日 下午11:22:41
 * 
 * @version 1.0.0
 *
 */
public interface AppPictureService {

	MessageDto<String> save(AppPicture appPicture);

	MessageDto<String> updateByPrimaryKeySelective(AppPicture appPicture);

	MessageDto<String> deleteByPrimaryKey(Integer id);

	MessageDto<List<AppPicture>> get(Map<String, Object> map);

	MessageDto<AppPicture> selectByPrimaryKey(Integer pictureId);

}
