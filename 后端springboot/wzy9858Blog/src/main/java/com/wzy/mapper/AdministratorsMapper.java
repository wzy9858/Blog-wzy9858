package com.wzy.mapper;

import com.wzy.pojo.Administrators;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Sonder
* @description 针对表【administrators】的数据库操作Mapper
* @createDate 2024-12-17 09:00:08
* @Entity com.wzy.pojo.Administrators
*/
public interface AdministratorsMapper extends BaseMapper<Administrators> {



    Administrators selectSuperInfo();
}




