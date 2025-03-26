import { http } from ".";

import axios from "axios";


// 请求文章列表的的方法 传过来查询第几页
export function getArtilesList(num: number) {
    return http.get(`/article/getList/${num}`).then(
        s => { return s }
    ).catch(
        e => { throw e }
    )
}

// 根据id获取文章的内容  需要进行用户鉴权 因为有加密文章

export function getArticleById(id: number) {
    return http.get(`/article/getArticleById/${id}`).then(
        s => { return s }
    ).catch(
        e => { throw e }
    )
}

// 归档页面 文章的一些提示信息

export function getAllInfo() {
    return http.get('/article/getAllInfo').then(
        s => { return s }
    ).catch(
        e => { throw e }
    )
}

// 创建文章请求 需要携带cookie所以在前端解决一下跨域问题
export function createArticle(article: any) {
    return axios.post("/api/article/create", article,
        { withCredentials: true }).then(

            s => {
                return s
            }
        ).catch(
            e => {
                throw e
            }
        )
}

// 删除文章 传过去待删除id的集合
export function deleteArticles(ids:[]) {
    let strPath = ''
    ids.forEach(item => {
        strPath = strPath + "id=" + item + "&"
    })
    strPath = strPath.substring(0, strPath.length - 1)
    // console.log(strPath);
    
    return axios.delete(`/api/article/deleteByIds?${strPath}`,{ withCredentials: true }).then(
        s => { return s }
    ).catch(
        e => { throw e }
    )
}

// 保存文章 需要验证jwt
export function saveArticleContent(article:any) {  
    return axios.put('/api/article/saveArticleContent',article,{ withCredentials: true }).then(
        s => { return s }
    ).catch(
        e => { throw e }
    )
}




// 比赛特用接口 ---------------------------------------
// 首页饼图 数据获取

export function getHomePanelData(){
    return http.get('/complete/homePanelData').then(
        s => { return s }
    ).catch(
        e => { throw e }
    )
}


// 创建评论 
export function createComment(comments: any) {
    return http.post("/comments/create", comments).then(

            s => {
                return s
            }
        ).catch(
            e => {
                throw e
            }
        )
}

// 拿到评论信息   http://localhost:8080/comments/get/1
export function getComments(id:any){
    return http.get('/comments/get/'+id).then(
        s => { return s }
    ).catch(
        e => { throw e }
    )
}