define(['bootstrap', 'wangEditor'], function() {
    console.log("abd");
    var editor = new wangEditor('div1');
    // 上传图片
    editor.config.uploadImgUrl = '/upload';
    editor.create();

    $('#btn1').click(function () {
        // 获取编辑器区域完整html代码
        var html = editor.$txt.html();
        alert(html);
        // 获取编辑器纯文本内容
        var text = editor.$txt.text();

        // 获取格式化后的纯文本
        var formatText = editor.$txt.formatText();
    });
});