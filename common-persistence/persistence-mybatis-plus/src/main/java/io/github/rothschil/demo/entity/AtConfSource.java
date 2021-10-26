package io.github.rothschil.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 配置表
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021-10-23
 * @since 1.0.0 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("at_conf_source")
public class AtConfSource extends Model<AtConfSource> {

	private static final long serialVersionUID = 8298320832128294283L;
	/**
	 * FTP配置表标识
	 */
	@TableId(type = IdType.AUTO)
 	@TableField("conf_id")
	private Long id;

	/**
	 * 接入系统标识
	 */
 	@TableField("system_id")
	private Long systemId;

	/**
	 * 接入系统标识
	 */
	@TableField(exist = false)
	private AtSystem atSystem;

	/**
	 * 远程Host地址
	 */
 	@TableField("host")
	private String host;

	/**
	 * 远程端口
	 */
 	@TableField("port")
	private String port;

	/**
	 * 远程目录
	 */
 	@TableField("remote_dir")
	private String remoteDir;

	/**
	 * 本地存储目录
	 */
 	@TableField("local_dic")
	private String localDic;

	/**
	 * 类型(FTP/SFTP)
	 */
 	@TableField("ftp_type")
	private Integer ftpType;

	/**
	 * 登录用户
	 */
 	@TableField("login_user")
	private String loginUser;

	/**
	 * 登录密码
	 */
 	@TableField("login_pass")
	private String loginPass;

	/**
	 * 修改时间
	 */
 	@TableField("mod_time")
	private LocalDateTime modTime;

	/**
	 * 最近执行时间
	 */
 	@TableField("last_excu_time")
	private LocalDateTime lastExcuTime;

	/**
	 * 状态
	 */
 	@TableField("status")
	private Integer status;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
