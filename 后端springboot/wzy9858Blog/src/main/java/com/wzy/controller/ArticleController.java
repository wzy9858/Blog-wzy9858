package com.wzy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzy.mapper.ArticlesMapper;
import com.wzy.pojo.Articles;
import com.wzy.service.ArticlesService;
import com.wzy.util.JwtHelper;
import com.wzy.util.R;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文章的Controller
 * <p>
 * 所有请求均根据项目文档的请求接口来定义
 */

@CrossOrigin
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticlesMapper articlesMapper;

    @Autowired
    JwtHelper jwtHelper;

    /**
     * 拿到文章列表  在首页的请求
     * 不应该返回文章的内容 应为有加密内容
     * 一页显示10个
     * <p>
     * 返回的内容
     * 当前页 是否有下一页
     * id 标题 创建时间 更新时间  是否加密 标签 热度 主页图片url 置顶数值 作者
     * 按照置顶数值进行排序
     */

    @GetMapping("getList/{id}") //此方法在主页调用 根据页数查找
    public R getList(@PathVariable Integer id) {//传过来页数
        //设置分页参数  第几页 页容量
        Page<Articles> page = new Page<>(id, 10);
        articlesMapper.selectPageVo(page, null);//这个id什么用处
        //获取分页数据
        List<Articles> list = page.getRecords();
        R ok = R.ok();
        ok.data("hasNext", page.hasNext());
        ok.data("total", page.getTotal());
        ok.data("list", list);
        System.out.println(list);
        return ok;
    }

    @GetMapping("/getAllInfo")
    public R getAllInfo() {//此页面为归档页面调用  返回所有信息
        List<Articles> allInfo = articlesMapper.getAllInfo();
        R ok = R.ok();
        ok.data("total", allInfo.size());
        ok.data("list", allInfo);
        return ok;

    }


    /***
     * 根据id查询一篇文章
     * 注意： 这里需要进行用户鉴权,因为有加密的文章
     */
    @GetMapping("/getArticleById/{id}")
    public R getOneArticle(@PathVariable Integer id) {

        Articles one = articlesMapper.selectById(id);
        R ok = R.ok();
        ok.data("article", one);


        return ok;
    }

    /**
     * 创建文章 需要鉴权 jwt
     */
    @PostMapping("/create")
    public R createArticle(@RequestBody Articles articles, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        System.out.println(cookies);
        if (cookies == null) {
            return R.error().message("未携带cookie");
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accountToken")) {
                    System.out.println(cookie.getValue());
                    Boolean b = jwtHelper.parseJwt(cookie.getValue());
                    if (b) {
                        articles.setArticleContent("默认文章内容");
                                int i = articlesMapper.insert(articles);
                        return R.ok();
                    } else {
                        return R.error().message("jwt令牌解析失败");
                    }
                }
            }
            return R.error().message("未携带目标cookie");
        }
    }

}
