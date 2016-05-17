
package com.jianfei.oauth2;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Create on @2014年7月30日 @上午10:39:19 
 * @author libinsong1204@gmail.com
 */
public class User implements UserDetails, CredentialsContainer {
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private String password;
    private final String username;
    private List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

    public User(String username, String password) {
    	this.username = username;
        this.password = password;
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void eraseCredentials() {
        password = null;
    }

    @Override
    public boolean equals(Object rhs) {
        if (rhs instanceof User) {
            return username.equals(((User) rhs).username);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(": ");
        sb.append("Username: ").append(this.username).append("; ");
        sb.append("Password: [PROTECTED]; ");
        return sb.toString();
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
