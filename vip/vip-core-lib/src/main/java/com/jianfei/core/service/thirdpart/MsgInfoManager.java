package com.jianfei.core.service.thirdpart;

/**
 *  短信相关接口
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 17:12
 */
public interface MsgInfoManager {


    /**
     * 发送短信
     * @param phone 短信号码
     * @param content 短信内容
     * @return
     */
    boolean sendMsgInfo(String phone,String content);


}
