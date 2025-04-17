package com.wzy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName custom_ai_info
 */
@TableName(value ="custom_ai_info")
@Data
public class CustomAiInfo implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String customAiStatement;

    private String referenceData;

    private String announcement;

    private String conversationData;

    private String spareData;

    private static final long serialVersionUID = 1L;

}