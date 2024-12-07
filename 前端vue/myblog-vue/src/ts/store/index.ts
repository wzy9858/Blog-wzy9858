import { defineStore } from 'pinia'
export const articleList = defineStore('main', {
    state: () => {
        return {
            info: "pinia 可以使用"
        }
    },
    getters: {},
    actions: {}
})
