<template>
    <div class="edit-container">
        <div style="width: 100%;height: 46rem;">
            <MdEditor @onUploadImg="onUploadImg" style="height: 100%;" v-model="text" @onSave="handleSave" :preview="false" />
        </div>

    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { MdEditor } from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import { ElMessage } from 'element-plus'
import { onMounted } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';
let route = useRoute();
const text = ref('# Hello Editor');
// 上传图片的方法
const onUploadImg = async (files: (string | Blob)[], callback: (arg0: any[]) => void) => {
  const res = await Promise.all(
    files.map((file: string | Blob) => {
      return new Promise((rev, rej) => {
        const form = new FormData();
        form.append('file', file);

        axios
          .post('/api/complete/img/upload', form, {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
          })
          .then((res: { data: { url: string } }) => rev(res))
          .catch((error) => rej(error));
      });
    })
  );
  callback(res.map((item:any) => item.data.url));
};
let a = ref({
    account: '',
    bio: 'aboutMe'
})
import { getAboutMe } from '../ts/axios/adminHttp';
onMounted(() => {
    if (typeof route.query.id === 'string') {
        a.value.account = route.query.id;
        getAboutMe(a.value.account).then(
            s=>{     
                text.value = s.data.data.content
            }
        ).catch(
            e=>{

            }
        )

        // 发送请求获取文章

        
    } else {
        // 处理非字符串情况，例如设置默认值或者抛出错误
        a.value.account = 'default';
        console.log('ID is not a string');
    }
});
import { updateAccountInfo } from '../ts/axios/adminHttp';


function handleSave() {
    a.value.bio = text.value
    // console.log(a.value);
    
    updateAccountInfo(a.value).then(
        s=>{
            if(s.data.code == 200){
                ElMessage.success("保存成功")
            }else{
                ElMessage.success("出错啦")
            }
        }  
    ).catch(
        e=>{
            ElMessage.success("出错啦")
        }
    )
}



</script>
<style scoped>
.edit-container {
    height: 100%;
    margin-top: 70px;
}
</style>