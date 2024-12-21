import { http } from ".";


// 得到超级管理员的相关信息
export function getSuperInfo() {
    return http.get('/admin/getSuperInfo').then(
        s => {
            return s
        }
    ).catch(
        e => {
            throw e
        }
    )
}


// 用户登录  根据邮箱和密码进行登录
export function accountLogin(name: any, pass: any) {

    let account = {
        "account": name,
        "password": pass
    }

    return http.post('/admin/login', account).then(
        s => {
            return s
        }
    ).catch(
        e => {
            throw e
        }
    )

}
import axios from "axios";
// 拿到账号列表 需要验证token
export function getAccountList() {
    return axios.get("/api/admin/getAccountList", {
        withCredentials: true
    }).then(
        s => {
            return s
        }
    ).catch(
        e => {
            throw e
        }
    )
}

// 根据ids删除所选账号
export function deleteAccountByIds(ids: any) {
    let strPath = ''
    ids.forEach((item: string) => {
        strPath = strPath + "id=" + item + "&"
    })
    strPath = strPath.substring(0, strPath.length - 1)
    // console.log(strPath);

    return axios.delete(`/api/admin/deleteByIds?${strPath}`, { withCredentials: true }).then(
        s => { return s }
    ).catch(
        e => { throw e }
    )
}

// 更改管理员信息

export function updateAccountInfo(account: any) {
    // account.bio = null
    return axios.post('/api/admin/updateAccount', account, { withCredentials: true }).then(
        s => {
            return s
        }
    ).catch(
        e => {
            return e
        }
    )
}


// 根据id获取关于我的 不需要加密
export function getAboutMe(account: string) {
    return http.get(`/admin/getAboutMe?account=${account}`).then(
        s => {
            return s
        }
    ).catch(
        e => {
            return e
        }
    )
}
