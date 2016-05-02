/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.bug.entity;

import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 缺陷所属的项目Entity
 * @author xinguan
 * @version 2016-04-30
 */
public class BugProject extends DataEntity<BugProject> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 项目名称
	private String summary;		// 项目简介
	private List<BugVersion> bugVersionList = Lists.newArrayList();		// 子表列表
	
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

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public List<BugVersion> getBugVersionList() {
		return bugVersionList;
	}

	public void setBugVersionList(List<BugVersion> bugVersionList) {
		this.bugVersionList = bugVersionList;
	}
}