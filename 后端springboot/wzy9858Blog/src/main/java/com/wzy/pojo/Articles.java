package com.wzy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName articles
 */
@TableName(value ="articles")
@Data
public class Articles implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String articleTitle;

    /**
     * 
     */
    private String articleContent;

    /**
     * 
     */
    private Date createdAt;

    /**
     * 
     */
    private Date updatedAt;

    /**
     * 
     */
    private Integer isEncrypted;

    /**
     * 
     */
    private String tags;

    /**
     * 
     */
    private Integer popularity;

    /**
     * 
     */
    private String homeDisplayImageUrl;

    /**
     * 
     */
    private Integer isPinned;

    /**
     * 
     */
    private String owner;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}