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
export function accountLogin(name:any,pass:any) {

    let account = {
        "email":name,
        "password":pass
    }

    return http.post('/admin/login',account).then(
        s => {
            return s
        }
    ).catch(
        e => {
            throw e
        }
    )

}