package com.wzy.wzy9858blog;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzy.controller.CommentsController;
import com.wzy.mapper.AdministratorsMapper;
import com.wzy.mapper.ArticlesMapper;
import com.wzy.pojo.Articles;
import com.wzy.util.IpUtil;
import com.wzy.util.JwtHelper;
import com.wzy.util.SendmailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@SpringBootTest
class Wzy9858BlogApplicationTests {
	@Autowired
	ArticlesMapper articlesMapper;

	@Autowired
	AdministratorsMapper administratorsMapper;


	@Autowired
	JwtHelper jwtHelper;


	@Autowired
	SendmailUtil sendmailUtil;

	@Autowired
	CommentsController commentsController;

	@Test
	void testMail(){
		commentsController.getComment(1);
	}






	@Test
	void contextLoads() throws IOException {
		String ipInfo = IpUtil.getIpInfo("171.10.178.203");
		System.out.println(ipInfo);
//		LocalDateTime now = LocalDateTime.now();
//
//		// 定义时间格式
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//		// 格式化当前日期和时间
//		String formattedDateTime = now.format(formatter);
//
//		// 输出当前日期和时间
//		System.out.println("当前时间: " + formattedDateTime);

	}


	@Test
	public void getList(){

		//设置分页参数  第几页 页容量
		Page<Articles> page = new Page<>(1, 5);
		articlesMapper.selectPageVo(page, 300);
		//获取分页数据
		List<Articles> list = page.getRecords();
		list.forEach(System.out::println);
		System.out.println("当前页："+page.getCurrent());
		System.out.println("每页显示的条数："+page.getSize());
		System.out.println("总记录数："+page.getTotal());
		System.out.println("总页数："+page.getPages());
		System.out.println("是否有上一页："+page.hasPrevious());
		System.out.println("是否有下一页："+page.hasNext());

	}
}
