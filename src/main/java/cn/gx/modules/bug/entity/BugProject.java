/**
 * Copyright &copy; 2015-2020 <a href="http://www.bug.org/">Bug</a> All rights reserved.
 */
package cn.gx.modules.bug.entity;

import cn.gx.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.google.common.collect.Lists;

import cn.gx.common.persistence.DataEntity;
import cn.gx.common.utils.excel.annotation.ExcelField;

/**
 * 缺陷所属的项目Entity
 * @author xinguan
 * @version 2016-04-30
 */
public class BugProject extends DataEntity<BugProject> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 项目名称
	private String summary;		// 项目简介
	private String logo;//项目图片
	private String processKey; // 流程定义key
	private boolean active=false; // 项目启动

	private List<BugVersion> bugVersionList = Lists.newArrayList();		// 子表列表
	private List<User> userList=Lists.newArrayList();// 参与用户列表


	private boolean isSelf;		// 是否只查询自己的项目



	public BugProject() {
		super();
	}

	public BugProject(String id){
		super(id);
	}

	@Length(min=1, max=64, message="项目名称长度必须介于 1 和 64 之间")
	@ExcelField(title="项目名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="项目简介长度必须介于 0 和 255 之间")
	@ExcelField(title="项目简介", align=2, sort=2)
	public String getSummary() {
		return summary;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getProcessKey() {
		return processKey;
	}

	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public List<BugVersion> getBugVersionList() {
		return bugVersionList;
	}

	public void setBugVersionList(List<BugVersion> bugVersionList) {


		this.bugVersionList = bugVersionList;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {


		this.userList = userList;
	}

	public boolean isSelf() {
		return isSelf;
	}

	public void setSelf(boolean isSelf) {
		this.isSelf = isSelf;
	}

	public boolean isActive() {
		if(processKey!=null){
			return true;
		}
		return  false;

	}

	public void setActive(boolean active) {
		this.active = active;
	}
}