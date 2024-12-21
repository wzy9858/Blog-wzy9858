package com.wzy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzy.mapper.VisitorsMapper;
import com.wzy.pojo.Visitors;
import com.wzy.util.JwtHelper;
import com.wzy.util.R;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 访客的Controller
 */

@CrossOrigin

@RestController
@RequestMapping("/visitor")
public class VisitorController {

    @Autowired
    JwtHelper jwtHelper;

    @Autowired
    VisitorsMapper visitorsMapper;

    /**
     * 获取访客列表 需要验证jwt令牌
     */
    @GetMapping("/getVisitorList")
    public R getVisitorList(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return R.error().message("未携带cookie");
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accountToken")) {
                    Boolean b = jwtHelper.parseJwt(cookie.getValue());
                    if (b) {//解析成功
//                        visitorsMapper.select
                        QueryWrapper queryWrapper = new QueryWrapper();
                        queryWrapper.orderByDesc("last_visit_time");//根据上一次登录时间降序排序
                        List list = visitorsMapper.selectList(queryWrapper);
                        return R.ok().data("list", list);
                    } else {
                        return R.error().message("jwt令牌解析失败");
                    }
                }
            }
            return R.error().message("未携带目标cookie");
        }
    }

    @DeleteMapping("deleteByIds")
    public R deleteVisitor(@RequestParam("id") List<Integer> ids, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return R.error().message("未携带cookie");
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accountToken")) {
                    Boolean b = jwtHelper.parseJwt(cookie.getValue());
                    if (b) {
                        int count = 0;
                        for (Integer id : ids) {
                            visitorsMapper.deleteById(id);
                            count++;
                        }
                        if (count == ids.size())
                            return R.ok();
                        else
                            return R.error().message("删除数据不完全");

                    } else {
                        return R.error().message("jwt令牌解析失败");
                    }
                }
            }
            return R.error().message("未携带目标cookie");
        }
    }

    /**
     * 保存访客备注的按钮
     */
    @PostMapping("saveVisitorNote")
    public R saveVisitorNote(@RequestBody Visitors visitors,HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return R.error().message("未携带cookie");
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accountToken")) {
                    Boolean b = jwtHelper.parseJwt(cookie.getValue());
                    if (b) {

                        int i = visitorsMapper.updateById(visitors);
                        System.out.println("-------");
                        System.out.println(i);

                        if(i==0){
                            return R.error().message("保存出错");
                        }else {
                            return R.ok();
                        }
                    } else {
                        return R.error().message("jwt令牌解析失败");
                    }
                }
            }
            return R.error().message("未携带目标cookie");
        }
    }

}
