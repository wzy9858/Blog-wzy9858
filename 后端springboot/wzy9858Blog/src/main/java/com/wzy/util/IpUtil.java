package com.wzy.util;

import jakarta.servlet.http.HttpServletRequest;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.util.FileCopyUtils;

import java.io.*;

public class IpUtil {
//    static final String dbPath = new String("src/main/resources/ip2region/ip2region.xdb");
    static Searcher searcher = null;

    static {
        try {
            InputStream ris = IpUtil.class.getResourceAsStream("/ip2region/ip2region.xdb");
            byte[] dbBinStr = FileCopyUtils.copyToByteArray(ris);
            searcher = Searcher.newWithBuffer(dbBinStr);
            //注意：不能使用文件类型，打成jar包后，会找不到文件
//            logger.debug("缓存成功！！！！");
        } catch (IOException e) {
//            logger.error("解析ip地址失败,无法创建搜索器: {}", e);
            throw new RuntimeException(e);
        }
    }

    public static String getIpInfo(String ip) throws IOException {

        String cityInfo = null;
        try {
            return searcher.search(ip);
        } catch (Exception e) {
//            logger.error("搜索:{} 失败: {}",ipAddress, e);
        }
        return null;

//        try {
//            searcher = Searcher.newWithFileOnly(dbPath);
//        } catch (IOException e) {
//            System.out.printf("failed to create searcher with `%s`: %s\n", dbPath, e);
//            return null;
//        }
//        try {
//            long sTime = System.nanoTime();
//            String region = searcher.search(ip);
//            return region;
////            long cost = TimeUnit.NANOSECONDS.toMicros((long) (System.nanoTime() - sTime));
////            System.out.printf("{region: %s, ioCount: %d, took: %d μs}\n", region, searcher.getIOCount(), cost);
//        } catch (Exception e) {
//            System.out.printf("failed to search(%s): %s\n", ip, e);
//        }
//        // 3、关闭资源
//        searcher.close();
//        return null;

    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

}