package com.wzy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzy.mapper.AdministratorsMapper;
import com.wzy.pojo.Administrators;
import com.wzy.util.JwtHelper;
import com.wzy.util.R;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;

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
     * sql语句应该通过是否超级远管理员进行筛选
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
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody Administrators administrators,HttpServletRequest request, HttpServletResponse response) {
        String email = administrators.getEmail();
        String password = administrators.getPassword();
        QueryWrapper<Administrators> queryWrapper = new QueryWrapper();
        queryWrapper.eq("email", email);
        queryWrapper.eq("password", password);

        Administrators administrators1 = administratorsMapper.selectOne(queryWrapper);

        if (administrators1 != null) {//登录成功 返回头像信息
            String jwt = jwtHelper.createJwt(administrators1.getEmail());
            return R.ok().data("avatarUrl",administrators1.getAvatarUrl()).data("token",jwt);
        }else {
            return R.error();
        }
    }

}
