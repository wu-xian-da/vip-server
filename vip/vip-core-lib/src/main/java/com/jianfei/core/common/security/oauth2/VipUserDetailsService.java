package com.jianfei.core.common.security.oauth2;

import com.jianfei.core.bean.User;
import com.jianfei.core.common.enu.MsgType;
import com.jianfei.core.service.base.ValidateCodeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/6/23 18:15
 */
@Service("vipUserDetailsService")
public class VipUserDetailsService implements UserDetailsService {
    @Autowired
    private ValidateCodeManager validateCodeManager;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        try {
            String password=validateCodeManager.getSendValidateCode(userName, MsgType.LOGIN);
            return new OauthUser(userName,password);
        } catch (Exception e) {
            throw new UsernameNotFoundException("user select fail");
        }
    }
}
