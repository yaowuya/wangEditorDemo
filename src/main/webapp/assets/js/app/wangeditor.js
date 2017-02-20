/**
 * Created by zhongrf on 2017/2/20.
 */
define(['ajaxPackage','wangeditor'],
 function (wangAjax,wangeditor) {
    var $add=$("#add"),
        $update=$("#update"),
        $submitBtn=$("#submitBtn"),
        $modal=$("#myModal").modal({show:false}),
        editor;

    $add.on('click', function(event) {
        event.preventDefault();
        /* Act on the event */
        $("#myModalLabel").text("录入");
        $modal.modal("show");
        editor=wangeditor.Editor.initEditor("WangEditor","<p>请输入...</p>");
    });

    $modal.on('hide.bs.modal',function(){
        editor.destroy();
    });
})
