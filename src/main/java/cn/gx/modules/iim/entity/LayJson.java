package cn.gx.modules.iim.entity;

import java.util.ArrayList;
import java.util.List;

public class LayJson {
	
	private int status;
	private String msg;
	private List data = new ArrayList();


	public void setData(List data) {
		this.data = data;
	}
	public List getData() {
		return data;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMsg() {
		return msg;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getStatus() {
		return status;
	}

}
