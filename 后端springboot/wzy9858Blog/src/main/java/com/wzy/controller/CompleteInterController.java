package com.wzy.controller;


//比赛扩展接口 饼图 等

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzy.mapper.AdministratorsMapper;
import com.wzy.mapper.ArticlesMapper;
import com.wzy.mapper.VisitorsMapper;
import com.wzy.pojo.Administrators;
import com.wzy.pojo.Articles;
import com.wzy.pojo.Visitors;
import com.wzy.util.IpUtil;
import com.wzy.util.JwtHelper;
import com.wzy.util.SendmailUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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


    //    文章热度统计仪表盘 获取热度最高的几组数据
    @GetMapping("/panelPopularityRankings")
    public List<Map<String, Object>>  getPopularityRankings(){
// SELECT article_title,popularity FROM articles ORDER BY popularity DESC LIMIT 10;

        QueryWrapper<Articles> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("article_title as title", "popularity as popularition")  // 指定查询字段
                .orderByDesc("popularity")              // 按 popularity 降序
                .last("LIMIT 10");                      // 限制 10 条


        System.out.println("-----------");

        List<Map<String, Object>> list = articlesMapper.selectMaps(queryWrapper);
//        List<Map<String, Object>> list = visitorsMapper.selectMaps(queryWrapper);
//        System.out.println(list);
        System.out.println("------");


        return list;

    }

//    访问这个接口就返回ip及对应的地址
    @GetMapping("getIpAndAdress")
    public String getIpAndAdress( HttpServletRequest request) throws IOException {
        String ipAddr = IpUtil.getIpAddr(request);//ip地址
        String ipInfo = IpUtil.getIpInfo(ipAddr);//得到城市信息
        return ipAddr+"-"+ipInfo;
    }
    @Autowired
    SendmailUtil sendmailUtil;

    @Autowired
    JwtHelper jwtHelper;

    @Autowired
    AdministratorsMapper administratorsMapper;

//    注册功能的实现
    @Value("${test.url}")
    String url;

    @PostMapping("/register")
    public void register(@RequestBody Administrators administrators){//传过来的有账号和密码
        String account = administrators.getAccount();//账号
        String password = administrators.getPassword();//密码

        if (account != null && password!= null){
            //如何把账号加密
            		String jwt = jwtHelper.createJwt(administrators.getAccount()+"-"+administrators.getPassword());//创建一个jwt
//            		String s = jwtHelper.praseAccount(jwt);//解析

            sendmailUtil.sendSimpleEmail("985891315@qq.com","博客新用户注册","新用户注册了一个新账号,点击链接同意或拒绝。\n" +
                    url+jwt);
        }
    }
        // 注册发送邮件后 是否同意或者否定请求

    @GetMapping("/newAccount/{jwt}")// 访问这个接口就是同意了
    public void agreeAndNot(@PathVariable String jwt){
        String str = jwtHelper.praseAccount(jwt);//解析
        String account = str.split("-")[0];//账号
        String passwd = str.split("-")[1];// 密码
//        System.out.println("jwt = "+jwt);
//        System.out.println(str);

//        像数据库中插入用户
        System.out.println("account="+account);
        System.out.println("passwd="+passwd);

        Administrators administrators = new Administrators();
        administrators.setEmail(account);
        administrators.setAccount(account);
        administrators.setPassword(passwd);
        administrators.setIsSuperAdmin(0);//普通用户
        administrators.setNickname(account);
        administratorsMapper.insert(administrators);
        sendmailUtil.sendSimpleEmail(account,"您的注册申请已经通过","现在您可以成功登录账号,账号为:"+account);
    }

    // 返回仪表盘的一些信息
    // 文章总数   访客总数 用户总数
    @GetMapping("/getPanelSomeInfo")
    public String getPanelSomeInfo(){

        Long l = articlesMapper.selectCount(null);  // 文章总数

        Long l1 = visitorsMapper.selectCount(null);//访客总数

        Long l2 = administratorsMapper.selectCount(null);//用户总数

        return l+"-"+l1+"-"+l2;

    }


    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/img/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("文件为空");
        }

        try {
            // 确保上传目录存在
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 保存文件到服务器
            Path filePath = Paths.get(UPLOAD_DIR, file.getOriginalFilename());
            Files.write(filePath, file.getBytes());

            System.out.println("-------------");
            System.out.println(filePath);

            // 构建文件的访问URL
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(file.getOriginalFilename())
                    .toUriString();

            return ResponseEntity.ok().body(new UploadResponse(fileDownloadUri));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("文件上传失败");
        }
    }
    public static class UploadResponse {
        private String url;

        public UploadResponse(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }



}

