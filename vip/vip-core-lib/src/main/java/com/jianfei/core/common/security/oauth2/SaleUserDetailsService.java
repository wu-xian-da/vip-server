package com.jianfei.core.common.security.oauth2;

import com.jianfei.core.bean.SysUsers;
import com.jianfei.core.bean.User;
import com.jianfei.core.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * TODO
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/6/23 17:38
 */
@Service("saleUserDetailsService")
public class SaleUserDetailsService implements UserDetailsService {
    /**
     * 用户查询
     */
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        try {
            List<User> users=userMapper.getUserByUno(userName);
            User appUser=users.get(0);
            return new OauthUser(appUser.getCode(),appUser.getExtraPasswd());
        } catch (Exception e) {
            throw new UsernameNotFoundException("user select fail");
        }
    }
}
