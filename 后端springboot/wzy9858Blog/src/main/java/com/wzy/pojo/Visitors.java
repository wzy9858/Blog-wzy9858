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
 * @TableName visitors
 */
@TableName(value ="visitors")
@Data
public class Visitors implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer visitorId;

    /**
     * 
     */
    private String ipAddress;

    /**
     * 
     */
    private String geoLocation;

    /**
     * 
     */
    private String userAgent;

    /**
     * 
     */
    private String deviceInfo;

    /**
     * 
     */
    private Date firstVisitTime;

    /**
     * 
     */
    private Date lastVisitTime;

    /**
     * 
     */
    private Integer visitDuration;

    /**
     * 
     */
    private Integer visitFrequency;

    /**
     * 
     */
    private String visitorNotes;

    /**
     * 
     */
    private Integer isBlacklisted;

    private String accessPath;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}