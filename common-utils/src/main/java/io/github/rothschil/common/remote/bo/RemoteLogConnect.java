package io.github.rothschil.common.remote.bo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 远程服务器文件连接记录
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021-10-23
 * @since 1.0.0 
 */
public class RemoteLogConnect implements Serializable {

	private static final long serialVersionUID = 417784064800161131L;
	/**
	 * 连接记录标识
	 */
	private Long id;

	/**
	 * 流水标识，YYYYMMDDHH24MISS+8位随机数字
	 */
	private String transId;

	/**
	 * 请求连接参数
	 */
	private String req;

	/**
	 * 连接结果状态
	 */
	private Integer result;

	/**
	 * 开始连接时间
	 */
	private LocalDateTime connTime;

	/**
	 * 连接响应时间
	 */
	private LocalDateTime reponTime;

	/**
	 * FTP配置表标识
	 */
	private Long confId;

	/**
	 * 文件下载状态
	 */
	private Integer downStatus;

	/**
	 * 文件下载状态描述
	 */
	private String statusDetails;


	/**
	 * 连接下载文件数量
	 */
	private Integer amount;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getReq() {
		return req;
	}

	public void setReq(String req) {
		this.req = req;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public LocalDateTime getConnTime() {
		return connTime;
	}

	public void setConnTime(LocalDateTime connTime) {
		this.connTime = connTime;
	}

	public LocalDateTime getReponTime() {
		return reponTime;
	}

	public void setReponTime(LocalDateTime reponTime) {
		this.reponTime = reponTime;
	}

	public Long getConfId() {
		return confId;
	}

	public void setConfId(Long confId) {
		this.confId = confId;
	}

	public Integer getDownStatus() {
		return downStatus;
	}

	public void setDownStatus(Integer downStatus) {
		this.downStatus = downStatus;
	}

	public String getStatusDetails() {
		return statusDetails;
	}

	public void setStatusDetails(String statusDetails) {
		this.statusDetails = statusDetails;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
}
