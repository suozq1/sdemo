package com.suo.sdemo.buss.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Map;

/**
 * <p>
 * 
 * </p>
 *
 * @author suozq
 * @since 2022-06-14
 */
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    private String email;

    private String password;

    private String nickname;

    private Integer deleted;
    
    @TableField(exist = false)
    private Map<String,SysResource> userResources; //前端使用权限
    
    @TableField(exist = false)
    private Serializable sessionId;

	/**
	 * shiro-redis组件需要存储对象有getId方法
	 * @return
	 */
	public Integer getId() {
		return getUserId();
	}
	
    public Serializable getSessionId() {
		return sessionId;
	}

	public void setSessionId(Serializable sessionId) {
		this.sessionId = sessionId;
	}

	public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
	public Map<String, SysResource> getUserResources() {
		return userResources;
	}

	public void setUserResources(Map<String, SysResource> userResources) {
		this.userResources = userResources;
	}
	
}
