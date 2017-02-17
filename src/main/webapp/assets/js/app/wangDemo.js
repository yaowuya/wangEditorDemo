define(['ajaxPackage', 'plupload', 'wangEditor'], function (wangAjax, plupload) {
    // 封装console.log
    function printLog(title, info) {
        window.console && console.log(title, info);
    }

    // ------- 配置上传的初始化事件 -------
    function uploadInit() {
        // this 即 editor 对象
        var editor = this;
        // 编辑器中，触发选择图片的按钮的id
        var btnId = editor.customUploadBtnId;
        // 编辑器中，触发选择图片的按钮的父元素的id
        var containerId = editor.customUploadContainerId;

        //实例化一个上传对象
        var uploader = new plupload.Uploader({
            browse_button: btnId, // 选择文件的按钮的id
            url: '/wangEditor/upload', // 服务器端的上传地址
            flash_swf_url: 'lib/plupload/plupload/Moxie.swf',
            sliverlight_xap_url: 'lib/plupload/plupload/Moxie.xap',
            file_data_name: "file",
            filters: {
                mime_types: [
                    //只允许上传图片文件 （注意，extensions中，逗号后面不要加空格）
                    {
                        title: "图片文件",
                        extensions: "jpg,gif,png,bmp"
                    }
                ]
            }
        });

        //存储所有图片的url地址
        var urls = [];

        //初始化
        uploader.init();

        //绑定文件添加到队列的事件
        uploader.bind('FilesAdded', function (uploader, files) {
            //显示添加进来的文件名
            $.each(files, function (key, value) {
                printLog('添加文件' + value.name);
            });

            // 文件添加之后，开始执行上传
            uploader.start();
        });

        //单个文件上传之后
        uploader.bind('FileUploaded', function (uploader, file, responseObject) {
            //注意，要从服务器返回图片的url地址，否则上传的图片无法显示在编辑器中
            console.log(responseObject);
            var url = responseObject.response;

            console.log("response",url);
            url=JSON.parse(url);
            //先将url地址存储来，待所有图片都上传完了，再统一处理
            urls.push(url);

            printLog('一个图片上传完成，返回的url是' + url);
        });

        //全部文件上传时候
        uploader.bind('UploadComplete', function (uploader, files) {
            printLog('所有图片上传完成');

            // 用 try catch 兼容IE低版本的异常情况
            try {
                //打印出所有图片的url地址
                $.each(urls, function (key, value) {
                    printLog('即将插入图片' + value);

                    // 插入到编辑器中
                    editor.command(null, 'insertHtml', '<img src="' + value + '" style="max-width:100%;"/>');
                });
            } catch (ex) {
                // 此处可不写代码
            } finally {
                //清空url数组
                urls = [];

                // 隐藏进度条
                editor.hideUploadProgress();
            }
        });

        // 上传进度条
        uploader.bind('UploadProgress', function (uploader, file) {
            // 显示进度条
            editor.showUploadProgress(file.percent);
        });
    }

    // ------- 创建编辑器 -------
    var editor = new wangEditor('div1');
    editor.config.customUpload = true; // 配置自定义上传的开关
    editor.config.customUploadInit = uploadInit; // 配置上传事件，uploadInit方法已经在上面定义了
    // 阻止输出log
    // wangEditor.config.printLog = false;
    // 上传图片
    // editor.config.uploadImgUrl = '/upload';
    editor.config.menus = $.map(wangEditor.config.menus, function (item, index) {
        if (item === "emotion" || item === "video" || item === "location") {
            return null;
        }
        return item;
    });
    // 只粘贴纯文本
    // （注意，如果你在上面设置了 editor.config.pasteFilter = false 那么这个粘贴纯文本的设置将失效）
    // editor.config.pasteText = true;

    var imgs = [];
    // 配置 onchange 事件
    editor.onchange = function () {
        // 编辑区域内容变化时，实时打印出当前内容
        var changeImgs = editor.$txt.find('img');
        console.log("changeImgs", changeImgs);
        if (changeImgs.length < imgs.length) {
            $.each(imgs, function (index, val) {
                /* iterate through array or object */
                var flag = 0;
                $.each(changeImgs, function (key, value) {
                    /* iterate through array or object */
                    if (value == val) {
                        flag = 1;
                        return false;
                    }
                });
                if (flag == 0) {
                    console.log("删除的图片", val);
                    var src = $(this).attr('src');
                    wangAjax.Ajax.request({
                        url: "/wangEditor/deleteEditor",
                        data: {"src": src},
                        success: function (response) {
                            if (response.success) {
                                console.log("删除成功");
                            } else {
                                console.log("删除失败");
                            }
                        }
                    });
                }
            });
        }
        imgs = changeImgs;

    };
    /*菜单配置，字体配置，颜色配置，都要放在create之前*/
    editor.create();

    editor.$txt.html("<p>要初始化的内容</p>")
    $('#btn1').click(function () {
        // 获取编辑器区域完整html代码
        var html = editor.$txt.html();
        alert(html);
        // 获取编辑器纯文本内容
        var text = editor.$txt.text();

        // 获取格式化后的纯文本
        var formatText = editor.$txt.formatText();
    });
    // $('#btn1').click(function () {
    //     // 清空内容。
    //     // 不能传入空字符串，而必须传入如下参数
    //     editor.$txt.html('<p><br></p>');
    // });
    $("#btn2").click(function (event) {
        /* Act on the event */
        var html = $("#div1").html();
        alert(html);
    });
});