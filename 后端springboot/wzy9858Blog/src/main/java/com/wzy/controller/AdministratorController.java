package com.wzy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzy.mapper.AdministratorsMapper;
import com.wzy.pojo.Administrators;
import com.wzy.util.JwtHelper;
import com.wzy.util.R;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;

/**
 * 管理员的Controller
 */

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdministratorController {

    @Autowired
    AdministratorsMapper administratorsMapper;

    @Autowired
    JwtHelper jwtHelper;

    /**
     * 这个是默认接口  返回超级管理员的相关信息
     * <p>
     * 包括  昵称 简介 头像地址  电子邮箱
     * <p>
     * sql语句应该通过是否超级远管理员进行筛选  ==1的
     */
    @GetMapping("/getSuperInfo")
    public R getSUperInfo() {
        Administrators administrators = administratorsMapper.selectSuperInfo();
        administrators.setPassword("admin");
        R ok = R.ok();
        ok.data("admin", administrators);
        return ok;
    }

//    处理登录请求 并设置相应的jwt信息

    /**
     * @param administrators
     * @param response
     * @return 账号是主键 应该根据账号和密码进行登录
     * 如果是用邮箱注册的，账号应该与邮箱一致
     */
    @PostMapping("/login")
    public R login(@RequestBody Administrators administrators, HttpServletRequest request, HttpServletResponse response) {
        String account = administrators.getAccount();
        String password = administrators.getPassword();
        QueryWrapper<Administrators> queryWrapper = new QueryWrapper();
        queryWrapper.eq("account", account);
        queryWrapper.eq("password", password);

        Administrators administrators1 = administratorsMapper.selectOne(queryWrapper);

        if (administrators1 != null) {//登录成功 返回头像信息
            String jwt = jwtHelper.createJwt(administrators1.getAccount());
            return R.ok().data("avatarUrl", administrators1.getAvatarUrl()).data("token", jwt);
        } else {
            return R.error();
        }
    }

    /***
     * 拿到账号列表 不返回密码
     */
    @GetMapping("/getAccountList")
    public R getAccountList(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accountToken")) {
                    String account = jwtHelper.praseAccount(cookie.getValue());
                    QueryWrapper queryWrapper = new QueryWrapper();
                    System.out.println("--------");
                    System.out.println(account);
                    queryWrapper.eq("account", account);//账号和解析出来的相同q
                    queryWrapper.eq("is_super_admin", 1);//是超级管理员
                    if (administratorsMapper.exists(queryWrapper)) {
                        QueryWrapper<Administrators> q = new QueryWrapper();
                        q.orderByDesc("is_super_admin");
                        List list = administratorsMapper.selectList(q);
                        R ok = R.ok();
                        ok.data("list", list);
                        return ok;
                    } else {
                        return R.error().message("解析失败");
                    }
                }
            }
            return R.error().message("token名称错误 ");
        } else {
            return R.error().message("未携带cookie");
        }
    }


    /**
     * 删除账号 根据传过来的ids
     *
     */
    @DeleteMapping("deleteByIds")
    public R deleteByIds(@RequestParam("id") List<String> ids, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accountToken")) {
                    String account = jwtHelper.praseAccount(cookie.getValue());
                    QueryWrapper queryWrapper = new QueryWrapper();
                    System.out.println("--------");
                    System.out.println(account);
                    queryWrapper.eq("account", account);//账号和解析出来的相同q
                    queryWrapper.eq("is_super_admin", 1);//是超级管理员
                    if (administratorsMapper.exists(queryWrapper)) {
                        for (String id : ids) {
                            administratorsMapper.deleteById(id);
                        }
                        return R.ok();
                    } else {
                        return R.error().message("解析失败");
                    }
                }
            }
            return R.error().message("token名称错误 ");
        } else {
            return R.error().message("未携带cookie");
        }

    }

    /***
     * 更新账号信息
     */
    @PostMapping("/updateAccount")
    public R updateAccount(@RequestBody Administrators administrators, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accountToken")) {
                    String account = jwtHelper.praseAccount(cookie.getValue());
                    QueryWrapper queryWrapper = new QueryWrapper();
                    System.out.println("--------");
                    System.out.println(account);
                    queryWrapper.eq("account", account);//账号和解析出来的相同
                    queryWrapper.eq("is_super_admin", 1);//是超级管理员
                    if (administratorsMapper.exists(queryWrapper)) {
                        System.out.println("------");
                        System.out.println(administrators);
                        administratorsMapper.updateById(administrators);
                        return R.ok();
                    } else {
                        return R.error().message("解析失败");
                    }
                }
            }
            return R.error().message("token名称错误 ");
        } else {
            return R.error().message("未携带cookie");
        }
    }


    /**
     * 根据account获取关于我
     */
    @GetMapping("/getAboutMe")
    public R getAboutMe(@RequestParam("account") String a) {
//        只返回关于的内容
        Administrators administrators = administratorsMapper.selectById(a);
//        更新

        String bio = administrators.getBio();
        return R.ok().data("content", bio);

    }


}
