package com.wangge.app.server.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "BIZ_APP_VERSION")
public class AppVersion implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator = "idgen")
	@Column(name = "ID")
	private Long id;

	private String versionNum;//版本号

	private String  versionUrl ;//下载url

	private String versionContent;//升级内容
	private String updateTime;//升级时间

	private String updateFlag;//0:不强制升级　１:	强制升级

	private String versionName;//版本名称

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}

	public String getVersionUrl() {
		return versionUrl;
	}

	public void setVersionUrl(String versionUrl) {
		this.versionUrl = versionUrl;
	}

	public String getVersionContent() {
		return versionContent;
	}

	public void setVersionContent(String versionContent) {
		this.versionContent = versionContent;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
}
