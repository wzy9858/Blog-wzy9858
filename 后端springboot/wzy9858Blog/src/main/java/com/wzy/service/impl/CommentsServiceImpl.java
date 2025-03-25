package com.wzy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.pojo.Comments;
import com.wzy.service.CommentsService;
import com.wzy.mapper.CommentsMapper;
import org.springframework.stereotype.Service;

/**
* @author Sonder
* @description 针对表【comments】的数据库操作Service实现
* @createDate 2025-03-22 11:02:11
*/
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments>
    implements CommentsService{

}




