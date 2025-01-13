<template>
  <div>
    <quill-editor  ref="myQuillEditor"
                   v-model:content="content"
                   contentType="html"
                   :options="editorOption"
                   @change="onContentChange($event)"
                   class="my-quill-editor"></quill-editor>
<!--    <el-button @click="dayin">dayin</el-button>-->
  </div>
</template>
<script>
export default {
  props:{
    now:String
  },
  data() {
    return {
      content:this.now,
      editorOption: {
        // 占位配置
        placeholder: '请输入......',
        height: '300px',
        modules: {
          // 配置工具栏
          toolbar: [
            ['bold', 'italic', 'underline', 'strike'], // 加粗 斜体 下划线 删除线
            ['blockquote', 'code-block'], // 引用  代码块
            [{ header: 1 }, { header: 2 }], // 1、2 级标题
            [{ list: 'ordered' }, { list: 'bullet' }], // 有序、无序列表
            [{ script: 'sub' }, { script: 'super' }], // 上标/下标
            [{ indent: '-1' }, { indent: '+1' }], // 缩进
            [{ direction: 'rtl' }], // 文本方向
            ['link', 'image', 'video'], // 链接、图片、视频
            [{ align: [] }], // 添加居中按钮
            [{ color: [] }], // 文字颜色按钮
          ],

        }
      },
    }
  },
  methods:{
    getContent(){
      return this.content;
    },
    onContentChange ({ html }) {
      this.content = html
    },
  },
  watch: {
    now(newVal) {
      this.content = newVal;
    }
  }
}
</script>
<style>
/* 编辑器高度及背景色 */
::v-deep .ql-editor {
  min-height: 300px;
  background-color: #ffffff;
}
.my-quill-editor{
  height: 300px;  margin-bottom: 60px;
  z-index: 100000 !important;
}
.my-quill-editor .ql-editor{
  z-index:-1;
}
.ql-picker {
  z-index: 1000;
}

</style>
