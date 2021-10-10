package xyz.wongs.drunkard.war.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.wongs.drunkard.common.text.Convert;
import xyz.wongs.drunkard.war.core.domain.SysLogininfor;
import xyz.wongs.drunkard.war.core.mapper.SysLogininforMapper;
import xyz.wongs.drunkard.war.core.service.ISysLogininforService;

import java.util.List;

/**
 * 系统访问日志情况信息
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/10 - 0:03
 * @since 1.0.0
 */
@Service
public class SysLogininforServiceImpl implements ISysLogininforService {

    @Autowired
    private SysLogininforMapper logininforMapper;

    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    @Override
    public void insertLogininfor(SysLogininfor logininfor) {
        logininforMapper.insertLogininfor(logininfor);
    }

    /**
     * 查询系统登录日志集合
     *
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public List<SysLogininfor> selectLogininforList(SysLogininfor logininfor) {
        return logininforMapper.selectLogininforList(logininfor);
    }

    /**
     * 批量删除系统登录日志
     *
     * @param ids 需要删除的数据
     * @return int
     */
    @Override
    public int deleteLogininforByIds(String ids) {
        return logininforMapper.deleteLogininforByIds(Convert.toStrArray(ids));
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLogininfor() {
        logininforMapper.cleanLogininfor();
    }
}
