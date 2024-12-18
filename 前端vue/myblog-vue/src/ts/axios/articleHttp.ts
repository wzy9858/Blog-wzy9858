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

// 归档页面 或许文章的一些提示信息

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

