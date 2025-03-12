package com.wzy.controller;


//比赛扩展接口 饼图 等

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzy.mapper.ArticlesMapper;
import com.wzy.mapper.VisitorsMapper;
import com.wzy.pojo.Visitors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/complete")
public class CompleteInterController {


    @Autowired
    ArticlesMapper articlesMapper;

    @Autowired
    VisitorsMapper visitorsMapper;

    //    文章种类 饼图 首页右下方的饼图数据 文章种类
    @GetMapping("/homePanelData")
    public List getArticleType() {
        //SELECT tags, COUNT(*) AS num
        //FROM articles
        //GROUP BY tags
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("tags name", "COUNT(*) AS value");
        queryWrapper.groupBy("tags");
        List list1 = articlesMapper.selectMaps(queryWrapper);
        return list1;
    }

    //    仪表盘中 上方的图
    @GetMapping("panelVisitornumAndDate")
    public List<Map<String, Object>> getVisitorNumAndDate() {
        QueryWrapper<Visitors> queryWrapper = new QueryWrapper<>();

        // 获取当前日期时间
        LocalDateTime now = LocalDateTime.now();
        // 计算十天前的日期时间
        LocalDateTime tenDaysAgo = now.minusDays(10);

        // 添加查询条件
        queryWrapper.ge("last_visit_time", tenDaysAgo);
        queryWrapper.groupBy("DATE(last_visit_time)");
        queryWrapper.orderByAsc("DATE(last_visit_time)");

        // 只选择需要的列
        queryWrapper.select("DATE(last_visit_time) AS date", "COUNT(*) AS num");

        // 执行查询
        List<Map<String, Object>> list = visitorsMapper.selectMaps(queryWrapper);

        System.out.println("--------");
        for (Map<String, Object> map : list) {
            System.out.println("访问日期: " + map.get("访问日期") + ", 访问量: " + map.get("访问量"));
        }

        return list;
    }


}
