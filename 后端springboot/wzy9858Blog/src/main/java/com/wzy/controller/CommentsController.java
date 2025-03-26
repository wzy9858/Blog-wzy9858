package com.wzy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzy.mapper.CommentsMapper;
import com.wzy.pojo.Comments;
import com.wzy.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Transactional
@CrossOrigin
@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    CommentsMapper commentsMapper;

    @PostMapping("/create")
    public int create(@RequestBody Comments comments) {
        System.out.println(comments);
        comments.setCreateTime(new Date());
        int insert = commentsMapper.insert(comments);//插入一条评论
        System.out.println(comments);
        return insert;
    }


    //    根据文章id获取文章的评论
    @GetMapping("/get/{id}")
    public List<Comments> getComment(@PathVariable Integer id) {
//        Comments c1 = new Comments();
//        c1.setId(5l);
//        c1.setArticleId(1l);
//        c1.setContent("这篇文章太好啦!");
//        c1.setUserAccount("ac1");
//        c1.setUserAvatar("https://tse1-mm.cn.bing.net/th/id/OIP-C.g5M-iZUiocFCi9YAzojtRAAAAA?rs=1&pid=ImgDetMain");
//        c1.setIsParent(0);// 是否有父评论
//        c1.setParentCommentId(null);
//        c1.setCreateTime(new Date());
//
//        Comments c2 = new Comments();
//        c2.setArticleId(1l);
//        c2.setContent("我也觉得好棒!");
//        c2.setUserAccount("ac2");
//        c2.setUserAvatar("https://tse3-mm.cn.bing.net/th/id/OIP-C.UyaBji0AU_6M3VDA2F1RvgAAAA?rs=1&pid=ImgDetMain");
//        c2.setIsParent(1);
//        c2.setParentCommentId(5l);
//        c2.setCreateTime(new Date());
//
//
//        int insert = commentsMapper.insert(c1);
//        int insert1 = commentsMapper.insert(c2);
//
//        System.out.println("a = "+insert+" b="+insert1);

//        先根据id选出来父评论
//        commentsMapper
//        List<Comments> allList = new ArrayList<>();//封装的数据都放在这里面


        QueryWrapper q1 = new QueryWrapper();
        q1.eq("article_id", id);//查询所有评论
        List<Comments> l1 = commentsMapper.selectList(q1);


        for (Comments comments : l1) {//遍历所有评论
            if (comments.getParentCommentId() != null) {//如果有父评论
                for (Comments a : l1) {
                    if (a.getId() == comments.getParentCommentId()) {
                        a.getReplies().add(comments);
                    }
                }
            }
        }

        for (int i = l1.size() - 1; i >= 0; i--) {
            Comments comments = l1.get(i);
            if (comments.getIsParent() != 0) {
                l1.remove(i); // 直接删除当前索引元素
            }
        }
        return l1;
    }

    //    获取评论的接口
    @GetMapping("/getAllComment")
    public List<Comments> getAllComment() {
        QueryWrapper q1 = new QueryWrapper();
        q1.orderByDesc("create_time");
        List<Comments> list = commentsMapper.selectList(q1);

        return list;
    }

    //    删除评论
    @DeleteMapping("deleteByIds")
    public R deleteVisitor(@RequestParam("id") List<Integer> ids) {
        int count = 0;
        for (Integer id : ids) {
            commentsMapper.deleteById(id);
            count++;
        }

        if (count == ids.size())
            return R.ok();
        else
            return R.error().message("删除数据不完全");

    }


}
