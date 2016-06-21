package com.jianfei.user;

import com.jianfei.core.bean.AppVipcard;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.enu.VipCardState;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.common.utils.UUIDUtils;
import com.jianfei.core.dto.BaseMsgInfo;
import com.jianfei.core.service.base.VipCardManager;
import com.jianfei.core.service.base.impl.VipCardManagerImpl;
import com.jianfei.core.service.user.impl.SaleUserManagerImpl;
import com.jianfei.dto.VipTestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 销售人员Controller
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/17 15:53
 */
@Controller
@RequestMapping(value = "saler")
public class SalerUserController {

    @Autowired
    private SaleUserManagerImpl saleUserManager;
	@Autowired
	private VipCardManager vipCardManager;

    /**
     * 用户登录
     * @return
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public BaseMsgInfo vipRoomInfo(@RequestParam(value = "uno", required = true) String uno,
                                   @RequestParam(value = "password", required = true) String password
    ) {
        boolean validate= saleUserManager.validatePassword(uno,password);
        if (validate){

            VipTestVo vipTestVo=new VipTestVo("4219a91f-45d5-4a07-9e8e-3acbadd0c23e","d41df9fd-3d36-4a20-b0b7-1a1883c7439d",
                    "read write trust","bearer",43199);
            return BaseMsgInfo.success(vipTestVo);
        }else {
            return new BaseMsgInfo().setCode(-1).setMsg("账号或密码错误");
        }
    }

    /**
     * 获取用户信息
     * @return
     */
    @RequestMapping(value = "/getUser")
    @ResponseBody
    public BaseMsgInfo getUser(@RequestParam(value = "uno", required = true) String uno
    ) {
        User saleUser= saleUserManager.getSaleUserDetail(uno);
        return BaseMsgInfo.success(saleUser);
    }

    /**
     * 修改密码
     * @return
     */
    @RequestMapping(value = "changePassword")
    @ResponseBody
    public BaseMsgInfo update(@RequestParam(value = "uno", required = false) String uno,
                             @RequestParam(value = "password", required = false) String password,
                             @RequestParam(value = "newPassword", required = false) String newPassword) {
        return saleUserManager.updatePassword(uno, password, newPassword);
    }

	/**
	 * 验证卡片信息
	 * @return
	 */
	@RequestMapping(value = "/validateCard")
	@ResponseBody
	public BaseMsgInfo validateCard(@RequestParam(value = "cardNo", required = true) String cardNo
	) {
		AppVipcard vipCard = vipCardManager.getVipCardByNo(cardNo);
		if (vipCard == null || StringUtils.isBlank(vipCard.getCardNo())) {
			return BaseMsgInfo.msgFail("卡号有误或系统暂未录入此卡信息，请联系相关人员添加此卡信息！");
		}else if (!VipCardState.ACTIVE.getName().equals(vipCard.getCardState())){
			return BaseMsgInfo.success(true);
		}else {
			return BaseMsgInfo.msgFail("卡号有误，此卡已激活");
		}
	}

	/**
	 * 修改头像
	 * @return
	 */
	@RequestMapping(value = "/photoPath")
	@ResponseBody
	public BaseMsgInfo updatePhotoPath(@RequestParam(value = "uno", required = true) String uno,
							  @RequestParam(value = "photoPath", required = true) String photoPath) {
		boolean flag = saleUserManager.updatePhotoPath(uno,photoPath);
		return BaseMsgInfo.success(flag);
	}



    
    @RequestMapping(value = "/photoUpdate")
    @ResponseBody
    public BaseMsgInfo salePhotoUpdate(@RequestParam(value = "file", required = false) MultipartFile file) {
    	 if (!file.isEmpty()) {
    		 String path = GloabConfig.getInstance().getConfig("upload.home.dir") + "/salesPhoto";
    		 String fileName = file.getOriginalFilename();
    		 String newFileName = UUIDUtils.returnNewFileName(fileName);
 			 File targetFile = new File(path, newFileName);
 			 if (!targetFile.exists()) {
 				 targetFile.mkdirs();
 			 }

        	 try {
				file.transferTo(targetFile);
			 } catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }

             return BaseMsgInfo.success( "/salesPhoto/" + newFileName);

         } else {
             return  BaseMsgInfo.fail("失败",false);
         }
    }
    

}
