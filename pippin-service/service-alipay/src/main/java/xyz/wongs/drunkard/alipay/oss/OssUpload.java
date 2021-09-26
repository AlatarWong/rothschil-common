package xyz.wongs.drunkard.alipay.oss;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.ServiceException;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.wongs.drunkard.alipay.pojo.OssBed;
import xyz.wongs.drunkard.base.constant.Constants;
import xyz.wongs.drunkard.base.utils.DateUtils;
import xyz.wongs.drunkard.base.utils.file.FileUtil;

import java.io.*;
import java.util.List;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * 
 * @date 2021/9/22 - 16:59
 * @version 1.0.0
 */
public class OssUpload {

    private static final Logger LOG = LoggerFactory.getLogger(OssUpload.class);

    private static String FOLDER = "pay/";

    /** 上传OSS服务器
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/9/23-9:31
     * @param ossBed 配置文件
     * @param local 本地路径
     * @return String   OSS资源地址
     **/
    public static String upload(OssBed ossBed, String local) {

        File file = new File(local);
        if(!file.exists()){
            throw new SecurityException("本地文件夹不存在");
        }
        OSS ossClient = OssClientFactory.INSTANCE.singletonInstance(ossBed);
        String responseUrl = null;
        if(!file.isDirectory()){
            responseUrl = putFile(ossBed.getBucketname(),ossClient,file);
        } else {
            throw new SecurityException("这是一个文件夹");
//            File[] files = new File(local).listFiles();
//            if (null == files || files.length == 0) {
//
//            }
//            for (File temp : files) {
//                putFile(ossClient, temp);
//            }
        }
        OssClientFactory.INSTANCE.shutDown();
        responseUrl = ossBed.getEndpoint()+"/"+responseUrl;
        return getRespUrl(ossBed.getBucketname(),responseUrl);
    }

    private static String putFile(String bucketName, OSS ossClient,File file){
        String filePath = FOLDER + DateUtils.getTransId() + FileUtil.suffix(file.getName());
        try {
            FileInputStream fis = new FileInputStream(file);
            PutObjectResult result = ossClient.putObject(bucketName, filePath, fis);
            LOG.info("RequestId {}", result.getRequestId());
            LOG.info("getETag {}", result.getETag());
        } catch (FileNotFoundException e) {
            LOG.error("{} File Not Exist", filePath);
        } catch (ClientException e) {
            // 客户端异常，例如网络异常等。
            LOG.error("{} Client Err", file);
        } catch (ServiceException e) {
            // 服务端异常。
            LOG.error("RequestId {}", e.getRequestId());
            LOG.error("ErrorCode {}", e.getErrorCode());
            LOG.error("HostId {}", e.getHostId());
        }
        return filePath;
    }

    private static String getRespUrl(String bucketName, String url){
        String place = url.replaceFirst(Constants.DOUL_SLASH,Constants.DOUL_SLASH+bucketName+ Constants.POINT);
        return place;
    }

    /**
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/9/23-14:39
     * @param ossBed 配置文件
     * @param local 文件存放路径
     * @param prefix    文件前缀，如 xx/xx等
     * @return
     **/
    public static void downLoad(OssBed ossBed, String local,String prefix) {

        OSS ossClient = OssClientFactory.INSTANCE.singletonInstance(ossBed);
        ObjectListing objectListing = ossClient.listObjects(ossBed.getBucketname(), prefix);
        List<OSSObjectSummary> summaryList = objectListing.getObjectSummaries();
        if (summaryList.isEmpty()) {
            return;
        }
        InputStream is = null;
        OSSObject ossObject = null;
        OutputStream outputStream = null;


        // 枚举文件夹
        for (OSSObjectSummary ossObjectSummary : summaryList) {
            ossObject = ossClient.getObject(ossBed.getBucketname(), ossObjectSummary.getKey());
            is = ossObject.getObjectContent();
            if (null == is) {
                continue;
            }
            try {
                String outDirectory = local + ossObjectSummary.getKey();
                FileUtil.existsFolder(outDirectory);
                outputStream = new FileOutputStream(outDirectory);
                byte[] read = new byte[1024];
                int len = 0;
                while ((len = is.read(read)) != -1) {
                    outputStream.write(read, 0, len);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            if (null != is) {
                is.close();
            }

            if (null != outputStream) {
                outputStream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        OssClientFactory.INSTANCE.shutDown();
    }

}
