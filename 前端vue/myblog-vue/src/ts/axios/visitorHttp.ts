import { http } from ".";
import axios from "axios";

/**
 * 查询访客列表 需要进行jwt令牌验证
 * 
 */

export function getVisitorList() {
    return axios.get("/api/visitor/getVisitorList",
        { withCredentials: true }
    ).then(
        s => {
            return s
        }

    ).catch(
        e => {
            throw e
        }
    )
}

/**
 * 删除访客记录  需要进行jwt令牌验证
 */

export function deleteVisitor(ids:[]) {
    let strPath = ''
    ids.forEach(item => {
        strPath = strPath + "id=" + item + "&"
    })
    strPath = strPath.substring(0, strPath.length - 1)
    // console.log(strPath);
    
    return axios.delete(`/api/visitor/deleteByIds?${strPath}`,{ withCredentials: true }).then(
        s => { return s }
    ).catch(
        e => { throw e }
    )
}


//备注按钮
export function saveVisitorNote(visitor:any){
    return axios.post('/api/visitor/saveVisitorNote',visitor,
        { withCredentials: true }).then(
        s => { return s }
    ).catch(
        e => { throw e }
    )
}


// ----------------------------------
// 比赛专用接口


// 仪表盘 上方的访客数量和日期   http://localhost:8080/complete/panelVisitornumAndDate
export function getVisitorDateAndNum(){
    return axios.get('/api/complete/panelVisitornumAndDate',
        { withCredentials: true }).then(
        s => { return s }
    ).catch(
        e => { throw e }
    )
}

export function getPanelPopularityRankings(){
    //http://localhost:8080/complete/panelPopularityRankings
    return axios.get('/api/complete/panelPopularityRankings',
        { withCredentials: true }).then(
        s => { return s }
    ).catch(
        e => { throw e }
    )
}

//http://localhost:8080/complete/getPanelSomeInfo 仪表盘上方的一些信息
export function getPanelSomeInfo(){
    return axios.get('/api/complete/getPanelSomeInfo',
        { withCredentials: true }).then(
        s => { return s }
    ).catch(
        e => { throw e }
    )
}

//http://localhost:8080/complete/getIpAndAdress
// 拿到用户的ip及其相关城市信息
export function getIpAndAdress(){
    return axios.get('/api/complete/getIpAndAdress',
        { withCredentials: true }).then(
        s => { return s }
    ).catch(
        e => { throw e }
    )
}



// 发送邮件反馈的方法
export function sendFeedback(information:any){// 格式user-message
    return axios.get(`/api/complete/feedback?info=${information}`,{ withCredentials: true }).then(
        s => { return s }
    ).catch(
        e => { throw e }
    )
}
// 搜索功能的api
export function search(keywords:any){// 格式user-message
    return axios.get(`/api/complete/search?keyWords=${keywords}`,{ withCredentials: true }).then(
        s => { return s }
    ).catch(
        e => { throw e }
    )
}

//  触发彩蛋的方法
export function caidan(information:any){// 格式user-message
    return axios.get(`/api/complete/caidan?info=${information}`,{ withCredentials: true }).then(
        s => { return s }
    ).catch(
        e => { throw e }
    )
}

//  获取所有文章的分类
export function getAllCategory(){// 格式user-message
    return axios.get(`/api/complete/getTags`,{ withCredentials: true }).then(
        s => { return s }
    ).catch(
        e => { throw e }
    )
}


// 根据tag获取所有文章信息
// http://localhost:8080/complete/getArticlesByList?tag=tag1
export function getAllArticlesByTag(information:any){// 格式user-message
    return axios.get(`/api/complete/getArticlesByList?tag=${information}`,{ withCredentials: true }).then(
        s => { return s }
    ).catch(
        e => { throw e }
    )
}



//  获取所有评论

export function getCommentsList() {
    return http.get("/comments/getAllComment"
    ).then(
        s => {
            return s
        }

    ).catch(
        e => {
            throw e
        }
    )
}

// 删除评论
export function deleteComments(ids:[]) {
    let strPath = ''
    ids.forEach(item => {
        strPath = strPath + "id=" + item + "&"
    })
    strPath = strPath.substring(0, strPath.length - 1)
    // console.log(strPath);
    
    return http.delete(`/comments/deleteByIds?${strPath}`).then(
        s => { return s }
    ).catch(
        e => { throw e }
    )
}