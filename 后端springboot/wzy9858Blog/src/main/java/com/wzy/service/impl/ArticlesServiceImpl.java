package com.wzy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.pojo.Articles;
import com.wzy.service.ArticlesService;
import com.wzy.mapper.ArticlesMapper;
import org.springframework.stereotype.Service;

/**
* @author Sonder
* @description 针对表【articles】的数据库操作Service实现
* @createDate 2024-12-17 08:59:40
*/
@Service
public class ArticlesServiceImpl extends ServiceImpl<ArticlesMapper, Articles>
    implements ArticlesService{

}




