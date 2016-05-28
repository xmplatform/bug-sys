/**
 * Copyright &copy; 2015-2020 <a href="http://www.bug.org/">Bug</a> All rights reserved.
 */
package cn.gx.modules.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.gx.common.persistence.CrudDao;
import cn.gx.common.persistence.annotation.MyBatisDao;
import cn.gx.modules.sys.entity.User;

/**
 * 用户DAO接口
 * @author bug
 * @version 2014-05-16
 */
@MyBatisDao
public interface
UserDao extends CrudDao<User> {
	
	/**
	 * 根据登录名称查询用户
	 * @param loginName
	 * @return
	 */
	public User getByLoginName(User user);

	/**
	 * 通过OfficeId获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	public List<User> findUserByOfficeId(User user);


	/**
	 * 通过 bugProjectId 获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	public List<User> findUserByBugProjectId(User user);


	
	/**
	 * 查询全部用户数目
	 * @return
	 */
	public long findAllCount(User user);
	
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	public int updatePasswordById(User user);
	
	/**
	 * 更新登录信息，如：登录IP、登录时间
	 * @param user
	 * @return
	 */
	public int updateLoginInfo(User user);

	/**
	 * 删除用户角色关联数据
	 * @param user
	 * @return
	 */
	public int deleteUserRole(User user);
	
	/**
	 * 插入用户角色关联数据
	 * @param user
	 * @return
	 */
	public int insertUserRole(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);
	
	/**
	 * 插入好友
	 */
	public int insertFriend(@Param("id")String id, @Param("userId")String userId, @Param("friendId")String friendId);
	
	/**
	 * 查找好友
	 */
	public User findFriend(@Param("userId")String userId, @Param("friendId")String friendId);
	/**
	 * 删除好友
	 */
	public void deleteFriend(@Param("userId")String userId, @Param("friendId")String friendId);
	
	/**
	 * 
	 * 获取我的好友列表
	 * 
	 */
	public List<User> findFriends(User currentUser);
	
	/**
	 * 
	 * 查询用户-->用来添加到常用联系人
	 * 
	 */
	public List<User> searchUsers(User user);
	
	/**
	 * 
	 */
	
	public List<User>  findListByOffice(User user);


	int outAllUserInBugProject(String projectId);

	/**
	 * 插入用户项目关联数据
	 * @return
	 */
	int insertUserBugProject(@Param("userId")String userId,@Param("projectId")String projectId);

	/**
	 * 项目中是否已经存在用户
	 * @param map
	 * @return
     */
	boolean isExistUserInProject(@Param("userId")String userId,@Param("projectId")String projectId);

	void outUserInBugProject(@Param("userId")String userId, @Param("projectId")String projectId);

	String getUserName(String userId);

	List<User> findUserListByRoleEnname(@Param("projectId") String projectId, @Param("enname") String enname);


	/**
	 * 修改是否进行邮件通知
	 * @param id
	 * @param openEmail
     */
	public void updateOpenEmail(@Param("id") String id,@Param("openEmail")String openEmail);

	/**
	 * 修改是否进行站内通知
	 * @param id
	 * @param openNotify
     */
	public void updateOpenNotify(@Param("id") String id,@Param("openNotify")String openNotify);
}
