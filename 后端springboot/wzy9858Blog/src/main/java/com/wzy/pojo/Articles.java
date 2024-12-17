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
     * 文章的id 自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 文章的标题
     */
    private String articleTitle;

    /**
     * 文章的内容
     */
    private String articleContent;

    /**
     * 文章的创建时间
     */
    private Date createdAt;

    /**
     * 文章的更新时间
     */
    private Date updatedAt;

    /**
     * 是否加密 默认为0
     */
    private Integer isEncrypted;

    /**
     * 标签
     */
    private String tags;

    /**
     * 热度
     */
    private Integer popularity;

    /**
     * 首页图片的展示地址
     */
    private String homeDisplayImageUrl;

    /**
     * 是否置顶  默认为0
     */
    private Integer isPinned;

    /**
     * 文章所属人  作者
     */
    private String owner;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}