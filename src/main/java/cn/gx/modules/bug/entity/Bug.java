/**
 * Copyright &copy; 2015-2020 <a href="http://www.bug.org/">JeePlus</a> All rights reserved.
 */
package cn.gx.modules.bug.entity;

import cn.gx.common.persistence.ActEntity;
import cn.gx.modules.bug.entity.BugVersion;
import cn.gx.modules.bug.entity.BugProject;
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
	private String bugType;		// 缺陷类型（0：BUG;1:改进；2：任务；3：需求）
	private String bugStatus;		// 缺陷状态（0：新建；1：进行中；2：重开；3：已解决；4：暂缓；5：不解决；6：已关闭）
	private String bugLevel;		// 缺陷优先级（0：低；1：普通；2：高；3：紧急）
	private String name;		// 名称
	private String summary;		// 简介
	private String description;		// 内容详情
	private String file;		// 缺陷文件
	private String image;		// 缺陷图片
	private String testerLeadText;		// 测试主管意见
	private String developerLeadText;		// 开发主管意见
	private String projectManagerText;		// 项目经理意见
	
	public Bug() {
		super();
	}

	public Bug(String id){
		super(id);
	}

	@ExcelField(title="项目版本主键", fieldType=BugVersion.class, value="", align=2, sort=1)
	public BugVersion getBugVersion() {
		return bugVersion;
	}

	public void setBugVersion(BugVersion bugVersion) {
		this.bugVersion = bugVersion;
	}
	
	@ExcelField(title="项目主键", fieldType=BugProject.class, value="", align=2, sort=2)
	public BugProject getBugProject() {
		return bugProject;
	}

	public void setBugProject(BugProject bugProject) {
		this.bugProject = bugProject;
	}
	
	@Length(min=1, max=64, message="缺陷类型（0：BUG;1:改进；2：任务；3：需求）长度必须介于 1 和 64 之间")
	@ExcelField(title="缺陷类型（0：BUG;1:改进；2：任务；3：需求）", dictType="bug_type", align=2, sort=3)
	public String getBugType() {
		return bugType;
	}

	public void setBugType(String bugType) {
		this.bugType = bugType;
	}
	
	@Length(min=1, max=64, message="缺陷状态（0：新建；1：进行中；2：重开；3：已解决；4：暂缓；5：不解决；6：已关闭）长度必须介于 1 和 64 之间")
	@ExcelField(title="缺陷状态（0：新建；1：进行中；2：重开；3：已解决；4：暂缓；5：不解决；6：已关闭）", dictType="bug_status", align=2, sort=4)
	public String getBugStatus() {
		return bugStatus;
	}

	public void setBugStatus(String bugStatus) {
		this.bugStatus = bugStatus;
	}
	
	@Length(min=1, max=64, message="缺陷优先级（0：低；1：普通；2：高；3：紧急）长度必须介于 1 和 64 之间")
	@ExcelField(title="缺陷优先级（0：低；1：普通；2：高；3：紧急）", dictType="bug_level", align=2, sort=5)
	public String getBugLevel() {
		return bugLevel;
	}

	public void setBugLevel(String bugLevel) {
		this.bugLevel = bugLevel;
	}
	
	@Length(min=0, max=64, message="名称长度必须介于 0 和 64 之间")
	@ExcelField(title="名称", align=2, sort=6)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="简介长度必须介于 0 和 255 之间")
	@ExcelField(title="简介", align=2, sort=7)
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@ExcelField(title="内容详情", align=2, sort=8)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	@Length(min=0, max=255, message="测试主管意见长度必须介于 0 和 255 之间")
	@ExcelField(title="测试主管意见", align=2, sort=17)
	public String getTesterLeadText() {
		return testerLeadText;
	}

	public void setTesterLeadText(String testerLeadText) {
		this.testerLeadText = testerLeadText;
	}

	@Length(min=0, max=255, message="开发主管意见长度必须介于 0 和 255 之间")
	@ExcelField(title="开发主管意见", align=2, sort=18)
	public String getDeveloperLeadText() {
		return developerLeadText;
	}

	public void setDeveloperLeadText(String developerLeadText) {
		this.developerLeadText = developerLeadText;
	}
	
	@Length(min=0, max=255, message="项目经理意见长度必须介于 0 和 255 之间")
	@ExcelField(title="项目经理意见", align=2, sort=19)
	public String getProjectManagerText() {
		return projectManagerText;
	}

	public void setProjectManager(String projectManagerText) {
		this.projectManagerText = projectManagerText;
	}
	
}