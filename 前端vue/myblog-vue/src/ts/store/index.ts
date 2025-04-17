import { defineStore } from 'pinia'

export const accountPinia = defineStore('account', {
    state: () => {
        return {
            bio: ""
        }
    },
    getters: {},
    actions: {}
})

export const useTextStore = defineStore('textStore', {
  state: () => ({
    longText: '这是共享的长文本内容...',
  }),
  actions: {
    updateText(newText: string) {
      this.longText = newText;
    },
  },
});
