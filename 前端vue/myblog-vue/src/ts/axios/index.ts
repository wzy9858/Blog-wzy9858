import axios from 'axios';
const http = axios.create({
    baseURL: 'http://127.0.0.1:8080', // 设置基础URL
    timeout: 5000, // 请求超时时间
});

//添加请求拦截
http.interceptors.request.use(
    // 设置请求头配置信息
    config => {
        config.headers.Accept = 'application/json, text/plain, text/html,*/*'
        return config
    },
    // 设置请求错误处理函数
    error => {
        // console.log("request error")
        return Promise.reject(error)
    }
)

// 添加响应拦截器
http.interceptors.response.use(
    // 设置响应正确时的处理函数
    response => {
        // console.log("after success response")
        // console.log(response)
        return response
    },
    // 设置响应异常时的处理函数
    error => {
        // console.log("after fail response")
        // console.log(error)
        return Promise.reject(error)
    }
)


export {
    http,
}
