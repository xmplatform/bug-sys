/**
 * Copyright &copy; 2015-2020 <a href="http://www.bug.org/">Bug</a> All rights reserved.
 */
package cn.gx.modules.bug.entity;

import cn.gx.common.persistence.ActEntity;
import cn.gx.modules.bug.entity.BugVersion;
import cn.gx.modules.bug.entity.BugProject;
import cn.gx.modules.bug.util.BugStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import cn.gx.common.persistence.DataEntity;
import cn.gx.common.utils.excel.annotation.ExcelField;

/**
 * 缺陷Entity
 * @author xinguan
 * @version 2016-05-04
 */
public class Bug extends ActEntity<Bug> {
	
	private static final long serialVersionUID = 1L;
	private BugVersion bugVersion;		// 项目版本主键
	private BugProject bugProject;		// 项目主键


	private String name;		// 名称

	private String bugPlatform; // 平台
	private String bugSystemAndVersion; // 操作系统以及版本

	private String bugType;		 	// 类型（0：BUG;1:改进；2：任务；3：需求）
	private String bugLevel;		// 优先级（0：低；1：普通；2：高；3：紧急）
	private String bugSerious;		// 严重（0:提示,1：一般；2：严重；3：致命）
	private String bugFrequency;    // 发生频率（0:低,1:中,2:高）

	private String summary;		// 简介


	private String step2Reproduce;	// 问题重现步骤
	private String behavior;	// 实际行为
	private String expected;	// 期望结果

	private String solution; // 解决办法

	private String file;		// 缺陷文件
	private String image;		// 缺陷图片

	private String bugStatus;		// 状态（0：新建；1：进行中；2：重开；3：已解决；4：暂缓；5：不解决；6：已关闭）


	private String assign;//分派


	private String testerLeadText;		// 测试主管意见
	private String developerLeadText;		// 开发主管意见
	private String projectManagerText;		// 项目经理意见


	private boolean isSelf;		// 是否只查询自己的项目

	private String bugStatusPhrase;
	
	public Bug() {
		super();
	}

	public Bug(String id){
		super(id);
	}

	@JsonIgnore
	public BugVersion getBugVersion() {
		return bugVersion;
	}
	@ExcelField(title="项目版本", align=2, sort=2)
	public String getBugVersionName() {
		return bugVersion.getVersion()+":build"+bugVersion.getBuild();
	}

	public void setBugVersion(BugVersion bugVersion) {
		this.bugVersion = bugVersion;
	}

	@JsonIgnore

	public BugProject getBugProject() {
		return bugProject;
	}

	@ExcelField(title="项目", value="", align=2, sort=1)
	public String getBugProjectName() {
		return bugProject.getName();
	}




	@Length(min=0, max=64, message="名称长度必须介于 0 和 64 之间")
	@ExcelField(title="名称", align=2, sort=3)
	public String getName() {
		return name;
	}


	public void setBugProject(BugProject bugProject) {
		this.bugProject = bugProject;
	}
	
	@Length(min=1, max=64, message="缺陷类型（0：BUG;1:改进；2：任务；3：需求）长度必须介于 1 和 64 之间")
	@ExcelField(title="缺陷类型", dictType="bug_type", align=2, sort=4)
	public String getBugType() {
		return bugType;
	}

	public void setBugType(String bugType) {
		this.bugType = bugType;
	}
	
	@Length(min=1, max=64, message="缺陷状态长度必须介于 1 和 64 之间")
	@ExcelField(title="状态", dictType="bug_status", align=2, sort=5)
	public String getBugStatus() {
		return bugStatus;
	}

	public void setBugStatus(String bugStatus) {
		this.bugStatus = bugStatus;
	}
	
	@Length(min=1, max=64, message="缺陷优先级长度必须介于 1 和 64 之间")
	@ExcelField(title="优先级", dictType="bug_level", align=2, sort=6)
	public String getBugLevel() {
		return bugLevel;
	}

	@ExcelField(title="发生频率", dictType="bug_frequency", align=2, sort=7)
	public String getBugFrequency() {
		return bugFrequency;
	}

	public void setBugLevel(String bugLevel) {
		this.bugLevel = bugLevel;
	}
	

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="简介长度必须介于 0 和 255 之间")
	@ExcelField(title="简介", align=2, sort=8)
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	


	@Length(min=0, max=255, message="缺陷文件长度必须介于 0 和 255 之间")
	@ExcelField(title="缺陷文件", align=2, sort=15)
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
	@Length(min=0, max=255, message="缺陷图片长度必须介于 0 和 255 之间")
	@ExcelField(title="缺陷图片", align=2, sort=16)
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}



	public void setBugFrequency(String bugFrequency) {
		this.bugFrequency = bugFrequency;
	}

	@ExcelField(title="解决办法", align=2, sort=12)
	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public void setProjectManagerText(String projectManagerText) {
		this.projectManagerText = projectManagerText;
	}

	@Length(min=0, max=255, message="测试主管意见长度必须介于 0 和 255 之间")
	public String getTesterLeadText() {
		return testerLeadText;
	}

	public void setTesterLeadText(String testerLeadText) {
		this.testerLeadText = testerLeadText;
	}

	@Length(min=0, max=255, message="开发主管意见长度必须介于 0 和 255 之间")
	public String getDeveloperLeadText() {
		return developerLeadText;
	}

	public void setDeveloperLeadText(String developerLeadText) {
		this.developerLeadText = developerLeadText;
	}
	
	@Length(min=0, max=255, message="项目经理意见长度必须介于 0 和 255 之间")
	public String getProjectManagerText() {
		return projectManagerText;
	}

	public void setProjectManager(String projectManagerText) {
		this.projectManagerText = projectManagerText;
	}


	public String getBugStatusPhrase() {

		return BugStatus.statusPhraseOf(this.bugStatus);
	}

	public void setBugStatusPhrase(String bugStatusPhrase) {

		this.bugStatusPhrase = bugStatusPhrase;
	}


	public String getBugPlatform() {

		return bugPlatform;
	}

	public void setBugPlatform(String bugPlatform) {
		this.bugPlatform = bugPlatform;
	}

	public String getBugSystemAndVersion() {
		return bugSystemAndVersion;
	}

	public void setBugSystemAndVersion(String bugSystemAndVersion) {
		this.bugSystemAndVersion = bugSystemAndVersion;
	}

	public String getBugSerious() {
		return bugSerious;
	}

	public void setBugSerious(String bugSerious) {
		this.bugSerious = bugSerious;
	}

	@ExcelField(title="重现步骤", align=2, sort=9)
	public String getStep2Reproduce() {
		return step2Reproduce;
	}

	public void setStep2Reproduce(String step2Reproduce) {
		this.step2Reproduce = step2Reproduce;
	}
	@ExcelField(title="实际行为", align=2, sort=10)
	public String getBehavior() {
		return behavior;
	}

	public void setBehavior(String behavior) {
		this.behavior = behavior;
	}
	@ExcelField(title="期望结果", align=2, sort=11)
	public String getExpected() {
		return expected;
	}

	public void setExpected(String expected) {
		this.expected = expected;
	}

	public String getAssign() {
		return assign;
	}

	public void setAssign(String assign) {


		this.assign = assign;
	}


	public boolean isSelf() {
		return isSelf;
	}

	public void setSelf(boolean self) {
		isSelf = self;
	}
}