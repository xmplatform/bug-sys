/**
 * Copyright &copy; 2015-2020 <a href="http://www.bug.org/">Bug</a> All rights reserved.
 */
package cn.gx.modules.iim.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gx.modules.bug.entity.BugProject;
import cn.gx.modules.bug.service.BugProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.gx.common.config.Global;
import cn.gx.common.utils.IdGen;
import cn.gx.common.web.BaseController;
import cn.gx.modules.iim.entity.Friend;
import cn.gx.modules.iim.entity.Group;
import cn.gx.modules.iim.entity.LayJson;
import cn.gx.modules.sys.dao.UserDao;
import cn.gx.modules.sys.entity.Office;
import cn.gx.modules.sys.entity.User;
import cn.gx.modules.sys.service.OfficeService;
import cn.gx.modules.sys.service.SystemService;
import cn.gx.modules.sys.utils.UserUtils;

/**
 * 联系人Controller
 * @author bug
 * @version 2015-11-09
 */
@Controller
@RequestMapping(value = "${adminPath}/iim/contact")
public class ContactController extends BaseController {

	@Autowired
	private SystemService systemService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private OfficeService officeService;

	@Autowired
	private BugProjectService bugProjectService;
	
	
	/**
	 * 打开通讯录
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"index", ""})
	public String index(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<User> list = systemService.findUser(user);
        model.addAttribute("list", list);
		return "modules/iim/contacts";
	}

	
	/**
	 * 查找
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "searchUsers")
	public String searchUsers(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
			List<User> friends = userDao.searchUsers(user);
	        model.addAttribute("list", friends);
		
		return "modules/iim/search_user";
	}
	
	
	/**
	 * 添加好友--->常用联系人
	 */
	@RequestMapping(value = "addFriend")
	public String addFriend(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		User currentUser =  UserUtils.getUser();
		for(String id : idArray){
			 if(userDao.findFriend(currentUser.getId(), id) == null){
			  
				 userDao.insertFriend(IdGen.uuid(), currentUser.getId(), id);//添加对方为好友
				// userDao.insertFriend(IdGen.uuid(), id, currentUser.getId());//同时把自己添加为对方的好友
			 }
		}
		addMessage(redirectAttributes, "添加好友成功");
		return "redirect:"+Global.getAdminPath()+"/iim/contact/myFriends/?repage";
	}

	
	/**
	 * 删除好友
	 */
	@RequestMapping(value = "delFriend")
	public String delFriend(String id, RedirectAttributes redirectAttributes) {
		  User friend =   UserUtils.get(id);
		  User currentUser =  UserUtils.getUser();
		  if(friend != null &&  userDao.findFriend(currentUser.getId(), friend.getId()) != null){
			  userDao.deleteFriend(currentUser.getId(), friend.getId());//删除好友
			//  userDao.deleteFriend(friend.getId(), currentUser.getId());//同时把自己从对方的好友列表删除
		  }
		
		return "redirect:"+Global.getAdminPath()+"/iim/contact/myFriends/?repage";
	}

	/**
	 * 打开我的好友列表--->常用联系人列表
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "myFriends")
	public String myFriends(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		User currentUser =  UserUtils.getUser();
		List<User>  friends = userDao.findFriends(currentUser);
        model.addAttribute("list", friends);
		return "modules/iim/myFriends";
	}

	
	
	
	
	/**
	 * 打开聊天窗口
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "layerIM")
	public String layerIM(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<User> list = systemService.findUser(user);
        model.addAttribute("list", list);
		return "modules/iim/layerIM";
	}
	
	@RequestMapping(value="friend")
	@ResponseBody
	public LayJson getFriend(User user, HttpServletRequest request, HttpServletResponse response, Model model){
		LayJson j = new LayJson();
		j.setStatus(1);
		//我的好友--->常用联系人
		User currentUser =  UserUtils.getUser();
		List<User>  friends = userDao.findFriends(currentUser);
		Group friendGroup = new Group();
		friendGroup.setName("我的好友");
		friendGroup.setId(1);
		friendGroup.setNums(friends.size());
		for(User u : friends){
			Friend friend = new Friend();
			friend.setId(u.getLoginName());
			friend.setName(u.getName());
			friend.setFace(u.getPhoto());
			friendGroup.getItem().add(friend);
		}
		j.getData().add(friendGroup);
		
		
		List<Office> officeList = officeService.findList(true);
		int index=1;
		for(Office office : officeList){// 获取每个部门,即 分组
			user.setOffice(office);
			List<User> users = userDao.findListByOffice(user);
			Group group = new Group();
			group.setName(office.getName());
			group.setNums(users.size());
			group.setId(++index);
			for(User u : users){
				Friend friend = new Friend();
				friend.setId(u.getLoginName());
				friend.setName(u.getName());
				friend.setFace(u.getPhoto());
				group.getItem().add(friend);
			}
			j.getData().add(group);
		}
		user.setOffice(null);
		List<User> users = userDao.findListByOffice(user);
		Group group = new Group();
		group.setName("未分组");
		group.setNums(users.size());
		group.setId(++index);
		for(User u : users){
			Friend friend = new Friend();
			friend.setId(u.getLoginName());
			friend.setName(u.getName());
			friend.setFace(u.getPhoto());
			group.getItem().add(friend);
		}
		j.getData().add(group);
		
		return j;
	}



	/**
	 * 群组列表接口 预留待开发
	 * @return
	 */
	@RequestMapping(value="group")
	@ResponseBody
	public LayJson getGroup(User user){
		LayJson j = new LayJson();
		j.setStatus(1);

		User currentUser=UserUtils.getUser();

		BugProject bugProject=new BugProject();
		if (!currentUser.isAdmin()){
			bugProject.setSelf(true);
		}

		//项目组
		List<BugProject> projectList = bugProjectService.findList(bugProject);
		int index=1;
		for(BugProject project : projectList){// 获取每个项目,即 分组
			user.setBugProject(project);
			List<User> users = bugProjectService.getProjectPeople(project.getId());//项目下的用户

			Group group = new Group();
			group.setName(project.getName());
			group.setNums(users.size());
			group.setId(++index);
			for(User u : users){
				Friend friend = new Friend();
				friend.setId(u.getLoginName());
				friend.setName(u.getName());
				friend.setFace(u.getPhoto());
				group.getItem().add(friend);
			}
			j.getData().add(group);
		}
		//
		return j;
	}
	/**
	 * 最近联系人接口 预留
	 * @return
	 */
	@RequestMapping(value="chatLog")
	@ResponseBody
	public LayJson getChatLog(){
		LayJson j = new LayJson();
		j.setStatus(1);
		return j;
	}
	/**
	 * 群组成员接口 预留
	 *  @return
	 */
	@RequestMapping(value="groups")
	@ResponseBody
	public LayJson getGroups(BugProject project){
		LayJson j = new LayJson();
		j.setStatus(1);


		List<User> users = bugProjectService.getProjectPeople(project.getId());//项目下的用户
		List<Friend> friends=new ArrayList<Friend>();
		for(User u : users){
			Friend friend = new Friend();
			friend.setId(u.getLoginName());
			friend.setName(u.getName());
			friend.setFace(u.getPhoto());
			friends.add(friend);
		}
		j.setData(friends);
		return j;
	}
	
}