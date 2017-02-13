package ueditor.com.controller;


import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import ueditor.com.common.JsonResult;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by 钟锐锋 on 2017/2/10.
 */
@Controller
@RequestMapping("/wangEditor")
public class WangEditorController extends BaseController{
    private static final long serialVersionUID = -2712624630388027149L;

    @ResponseBody
    @RequestMapping("upload")
    public void upload(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        if(file!=null){
            String path="";//路径
            String type="";//类型
            String fileName=file.getOriginalFilename();//文件原名
            System.out.println("上传的文件原名称:"+fileName);
//            判断文件类型
            type=fileName.indexOf(".")!=-1?fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()):null;
            if(type!=null){
                if ("GIF".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase())||"BMP".equals(type.toUpperCase())){
                    //用户自身文件夹
                    String userName="xiaoming";
                    // 项目在容器中实际发布运行的根路径
                    String realPath=request.getSession().getServletContext().getRealPath("\\assets")+"\\upload\\"+userName+"\\";
                    // 自定义的文件名称
                    String trueFileName=String.valueOf(System.currentTimeMillis())+fileName;
                    // 设置存放图片文件的路径
                    path=realPath+/*System.getProperty("file.separator")+*/trueFileName;
                    File dir = new File(realPath);
                    if (!dir.exists())   //如果目录不存在就创建目录
                        dir.mkdirs();
                    System.out.println("存放图片文件的路径:"+path);
                    // 转存文件到指定的路径
                    try {
                        file.transferTo(new File(path));
                        System.out.println("文件成功上传到指定目录下");
                        // 返回图片的URL地址
                        response.setContentType("text/text;charset=utf-8");
                        response.getWriter().write(path.substring(path.indexOf("assets")-1));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    System.out.println("不是我们想要的文件类型,请按要求重新上传");
                }
            }else{
                System.out.println("文件类型为空");
            }
        }else{
            System.out.println("没有找到相对应的文件");
        }
    }
    @ResponseBody
    @RequestMapping("deleteEditor")
    public JsonResult deleteEditor(HttpServletRequest request){
        JsonResult jsonResult=null;
        try {
            Map<String,Object> params=super.getParamMap();
            String[] src=params.get("src").toString().split("\\\\");
            for(int i=0;i<src.length;i++){
                System.out.println("src"+i+" "+src[i]);
            }
            //文件路径
            String filedir = request.getSession().getServletContext().getRealPath("\\assets")+"\\upload\\"+src[3]+"\\"+src[4];
            System.out.println("文件路径"+filedir);
            //文件夹
            String directorydir =request.getSession().getServletContext().getRealPath("\\assets")+"\\upload\\"+src[3];
            //保存文件 文件夹路径
            String rootPath = request.getSession().getServletContext().getRealPath("\\assets")+"\\upload\\";
            File file = new File(filedir);
            if (!file.exists()) {
                //"删除文件失败："+fName+"文件不存在";
                jsonResult=new JsonResult(false,"删除文件失败："+src[4]+"文件不存在");
            }else{
                //删除所有空文件夹
                List<File> list = getAllNullDirectorys(new File(rootPath));
                removeNullFile(list, rootPath);
                if(file.isFile()){
//                    return deleteFile(filedir);  //删除单个文件
                    jsonResult=new JsonResult(deleteFile(filedir));
                }else{
//                    return deleteDirectory(directorydir);  //删除目录（文件夹）以及目录下的文件
                    jsonResult=new JsonResult(deleteDirectory(directorydir));
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            jsonResult=new JsonResult(false,"删除失败");
        }
        return jsonResult;
    }
    /**
     * 递归列出某文件夹下的最深层的空文件夹绝对路径，储存至list
     *
     * @param root
     * @return
     */
    public static List<File> getAllNullDirectorys(File root) {
        List<File> list = new ArrayList<File>();
        File[] dirs = root.listFiles();
        if (dirs != null) {
            for (int i = 0; i < dirs.length; i++) {
                if (dirs[i].isDirectory() && dirs[i].listFiles().length == 0) {
                    //System.out.println("name:" + dirs[i].getPath());
                    list.add(dirs[i]);
                }
                if(dirs[i].isFile()){
                    //System.out.println("文件:"+dirs[i].getPath());
                }
                list.addAll(getAllNullDirectorys(dirs[i]));
            }
        }
        return list;
    }
    /**
     * 由最深一层的空文件，向上（父文件夹）递归，删除空文件夹
     * @param list
     * @param rootPath
     */
    public static void removeNullFile(List<File> list, String rootPath) {
        if (list==null||list.size()==0) {
            return;
        }
        List<File> plist = new ArrayList<File>();
        for (int i = 0; i < list.size(); i++) {
            File temp = list.get(i);
            if (temp.isDirectory()  && temp.listFiles().length <= 0  ) {
                temp.delete();
                //System.out.println("parent:" + temp.getParentFile().getPath());
                File pFile = temp.getParentFile();
                if (pFile.getPath().equals(rootPath)) {
                    continue;
                }
                if (!plist.contains(pFile)) {//父目录去重添加
                    plist.add(pFile);
                }
            }
        }
        removeNullFile(plist, rootPath);
    }
    /**
     * 删除单个文件
     * @param   fileName    被删除文件的文件名
     * @return 单个文件删除成功返回true,否则返回false
     */
    public static boolean deleteFile(String fileName){
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            file.delete();//"删除单个文件"+name+"成功！"
            return true;
        }//"删除单个文件"+name+"失败！"
        return false;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     * @param directorydir 被删除目录的文件路径
     * @return  目录删除成功返回true,否则返回false
     */
    public static boolean  deleteDirectory(String directorydir){
        //如果dir不以文件分隔符结尾，自动添加文件分隔符
        if(!directorydir.endsWith(File.separator)){
            directorydir = directorydir+File.separator;
        }
        File dirFile = new File(directorydir);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if(!dirFile.exists() || !dirFile.isDirectory()){
            //"删除目录失败"+name+"目录不存在！"
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for(int i=0;i<files.length;i++){
            //删除子文件
            if(files[i].isFile()){
                flag = deleteFile(files[i].getAbsolutePath());
                if(!flag){
                    break;
                }
            }
            //删除子目录
            else{
                flag = deleteDirectory(files[i].getAbsolutePath());
                if(!flag){
                    break;
                }
            }
        }

        if(!flag){
            //System.out.println("删除目录失败");
            return false;
        }

        //删除当前目录
        if(dirFile.delete()){
            //System.out.println("删除目录"+directorydir+"成功！");
            return true;
        }else{
            //System.out.println("删除目录"+directorydir+"失败！");
            return false;
        }
    }
}
