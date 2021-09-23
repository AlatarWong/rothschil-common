package xyz.wongs;

import java.io.File;

public class TestResource {

    public static void main(String[] args) throws Exception{
//        File folder =  new File(Thread.currentThread().getContextClassLoader().getResource("static/images").getPath());
//        File folder = ResourceUtils.getFile("static/images");
        File folder =  new File("D:/Alipay/Images");
        File[] list = folder.listFiles();
        System.out.println("文件内容为空"+folder.getAbsolutePath());
        if(list==null || list.length==0){
            return;
        }
        for (File file : list) {
            System.out.println(file.getName());
        }


    }
}
