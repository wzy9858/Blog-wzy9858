import { http } from ".";
import axios from "axios";
// 拿到信息的方法
export function getAiContent() {
    return http.get('/ai/getinfo').then(
        s => { return s }
    ).catch(
        e => { throw e }
    )
}


// 设置信息的方法

export function saveAiContent(article:any) {  
    return axios.put('/api/ai/save',article,{ withCredentials: true }).then(
        s => { return s }
    ).catch(
        e => { throw e }
    )
}


// 拿到所有文章信息
export function getAIAllArticles() {  
    return http.get('/ai/getArticles').then(
        s => { return s }
    ).catch(
        e => { throw e }
    )
}


