<template>
    <div class="edit-container">
        <!-- 编辑文章的相关信息 标题 图片连接 作者等信息 -->

        <div class="edit-header">


            <div>
                <el-input v-model="inputTitle" style="width: 240px;" placeholder="请输入文章标题" />
            </div>

            <div style="margin-top: 1rem;">
                <el-input v-model="inputLabel" style="width: 240px" placeholder="请输入文章标签" />
            </div>

            <div class="btn-row" style="margin-top: 1rem; margin-bottom: 1rem;">
                <el-button type="danger" size="small" @click="handleClick">
                    保存文章
                </el-button>
            </div>
        </div>

        <!-- 编辑区域 -->
        <div style="width: 100%;height: 100%;">
            <MdEditor v-model="text" />
        </div>

    </div>

</template>

<script setup>
import { ref } from 'vue';
import { MdEditor } from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import { ElMessage } from 'element-plus'

const inputTitle = ref('')
const inputLabel = ref('')
const text = ref('# Hello Editor');

function handleClick(){
    if(inputTitle.value==''){
        ElMessage.error("标题不能为空")
    }else if(inputLabel.value == ''){
        ElMessage.error("标签不能为空")
    }else{
        ElMessage.success("保存成功")
    }
}


</script>
<style scoped>
.edit-container {
    height: 100%;
    margin-top: 70px;
}

.edit-header {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;

}
</style>