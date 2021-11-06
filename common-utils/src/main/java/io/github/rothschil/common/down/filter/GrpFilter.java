package io.github.rothschil.common.down.filter;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;

/**
 * FTP文件下载的过滤器，可指定需要过滤名称
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/23 - 17:04
 * @since 1.0.0
 */
public class GrpFilter implements FTPFileFilter {

    private String contains;

    public GrpFilter() {
    }

    public GrpFilter(String contains) {
        this.contains = contains;
    }

    @Override
    public boolean accept(FTPFile ftpFile) {
        String fileName = ftpFile.getName().toUpperCase();
        return fileName.contains(contains.toUpperCase());
    }
}
