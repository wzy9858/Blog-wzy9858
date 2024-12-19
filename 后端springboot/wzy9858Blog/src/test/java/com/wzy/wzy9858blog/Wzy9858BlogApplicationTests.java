package com.wzy.wzy9858blog;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzy.mapper.AdministratorsMapper;
import com.wzy.mapper.ArticlesMapper;
import com.wzy.pojo.Administrators;
import com.wzy.pojo.Articles;
import com.wzy.util.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@SpringBootTest
class Wzy9858BlogApplicationTests {
	@Autowired
	ArticlesMapper articlesMapper;

	@Autowired
	AdministratorsMapper administratorsMapper;


	@Autowired
	JwtHelper jwtHelper;
	@Test
	void contextLoads() {
//		String jwt = jwtHelper.createJwt(666);
//		jwtHelper.parseJwt(jwt);
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
