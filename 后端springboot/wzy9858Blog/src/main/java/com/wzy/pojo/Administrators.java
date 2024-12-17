package com.wzy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName administrators
 */
@TableName(value ="administrators")
@Data
public class Administrators implements Serializable {
    /**
     * 
     */
    @TableId
    private String account;

    /**
     * 
     */
    private String nickname;

    /**
     * 
     */
    private String password;

    /**
     * 
     */
    private String bio;

    /**
     * 
     */
    private String avatarUrl;

    /**
     * 
     */
    private String ipAddress;

    /**
     * 
     */
    private Date creationTime;

    /**
     * 
     */
    private Date lastLoginTime;

    /**
     * 
     */
    private String email;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;




}