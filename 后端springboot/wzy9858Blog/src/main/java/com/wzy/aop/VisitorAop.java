package com.wzy.aop;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzy.mapper.VisitorsMapper;
import com.wzy.pojo.Visitors;
import com.wzy.util.IpUtil;
import com.wzy.util.JwtHelper;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static java.rmi.server.LogStream.log;




/**
 * 记录访客的信息 当有来访的时候获取ip及其一些列信息存进数据库
 */
@Aspect
// @Component注解保证这个切面类能够放入IOC容器
@Component

public class VisitorAop {

    @Autowired
    VisitorsMapper visitorsMapper;

    @Autowired
    JwtHelper jwtHelper;

    /**
     * 如果来了一个访客,先看是否能够查询到 查不到就插入数据
     * 查询到了就更新ip地址等一系列信息
     */
    @Before(value = "execution(public * com.wzy.controller.*.*(..)) && args(.., request)")
    public void visitorApi(JoinPoint joinPoint, HttpServletRequest request) throws IOException {
        String url = request.getRequestURL().toString();//访问路径

        String ipAddr = IpUtil.getIpAddr(request);//ip地址

        //获取浏览器信息
        String ua = request.getHeader("User-Agent");
        //转成UserAgent对象
        UserAgent userAgent = UserAgent.parseUserAgentString(ua);
        //获取浏览器信息
        Browser browser = userAgent.getBrowser();
        //获取系统信息
        OperatingSystem os = userAgent.getOperatingSystem();
        //系统名称
        String system = os.getName();
        //浏览器名称
        String browserName = browser.getName();


        LocalDateTime now = LocalDateTime.now();
        // 定义时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 格式化当前日期和时间
        String formattedDateTime = now.format(formatter);

        Visitors visitor = new Visitors();
        visitor.setUserAgent(ua);//浏览器整个标识字符串
        visitor.setIpAddress(ipAddr);//ip地址
        visitor.setGeoLocation(IpUtil.getIpInfo(ipAddr));//地理位置
        visitor.setDeviceInfo(system + "-" + browserName);//设置信息
        visitor.setAccessPath(formattedDateTime + " : " + url + "\n");//访问路径

//        如果有cookie就设置备注为超级管理员  这样设置无法保存对访客的备注
//        Cookie[] cookies = request.getCookies();
//        if(cookies != null){
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("accountToken")) {
//                    Boolean b = jwtHelper.parseJwt(cookie.getValue());
//                    if (b) {
//                        visitor.setVisitorNotes("超级管理员");
//                    }
//                }
//            }
//        }

//        先查询 查询失败就插入
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("ip_address", ipAddr);
        Visitors visitors2 = visitorsMapper.selectOne(queryWrapper);


        if (visitors2 == null) {//没查到
            visitor.setFirstVisitTime(new Date());
            visitor.setVisitFrequency(1);
            visitorsMapper.insert(visitor);
        } else {
            //查到了 就更新
            System.out.println(visitors2);
//            访问路径不能太长啊 超过十条就清空
            if (visitors2.getAccessPath().split("\n").length >= 10) {
                visitor.setAccessPath(formattedDateTime + " : " + url);
            } else {
                visitor.setAccessPath(visitors2.getAccessPath() + "\n" + formattedDateTime + " : " + url);
            }

            visitor.setVisitFrequency(visitors2.getVisitFrequency() + 1);
            visitor.setVisitorId(visitors2.getVisitorId());
            visitorsMapper.updateById(visitor);
        }
    }
}
