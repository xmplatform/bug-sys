/**
 * Copyright &copy; 2015-2020 <a href="http://www.bug.org/">JeePlus</a> All rights reserved.
 */
package cn.gx.modules.bug.entity;

import org.hibernate.validator.constraints.Length;

import cn.gx.common.persistence.DataEntity;
import cn.gx.common.utils.excel.annotation.ExcelField;

/**
 * 缺陷所属的项目Entity
 * @author xinguan
 * @version 2016-04-30
 */
public class BugVersion extends DataEntity<BugVersion> {
	
	private static final long serialVersionUID = 1L;
	private String version;		// 版本,限10个字符
	private String build;		// build,限10个字符
	private BugProject bugProject;		// 关联项目外键 父类
	
	public BugVersion() {
		super();
	}

	public BugVersion(String id){
		super(id);
	}

	public BugVersion(BugProject bugProject){
		this.bugProject = bugProject;
	}

	@Length(min=1, max=11, message="版本,限10个字符长度必须介于 1 和 11 之间")
	@ExcelField(title="版本,限10个字符", align=2, sort=1)
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	@Length(min=1, max=11, message="build,限10个字符长度必须介于 1 和 11 之间")
	@ExcelField(title="build,限10个字符", align=2, sort=2)
	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}
	
	@Length(min=0, max=64, message="关联项目外键长度必须介于 0 和 64 之间")
	public BugProject getBugProject() {
		return bugProject;
	}

	public void setBugProject(BugProject bugProject) {
		this.bugProject = bugProject;
	}
	
}