package com.wzy.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wzy.pojo.Articles;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Sonder
* @description 针对表【articles】的数据库操作Mapper
* @createDate 2024-12-17 08:59:40
* @Entity com.wzy.pojo.Articles
*/
public interface ArticlesMapper extends BaseMapper<Articles> {


    IPage<Articles> selectPageVo(IPage<?> page, Integer id);
}




