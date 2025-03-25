package com.wzy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @TableName comments
 */
@TableName(value ="comments")
@Data
public class Comments implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long articleId;

    private String content;

    private String userAccount;

    private String userAvatar;

    private Date createTime;

    private Integer isParent;

    private Long parentCommentId;

    private String backupComment;

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private List<Comments> replies = new ArrayList<>();


}