package voa.learning.english.special.adapter;

import java.io.Serializable;

public class CategoryAdapter implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String link;
	String icon;
	String title;
	int drawerIcon;
	public int getDrawerIcon() {
		return drawerIcon;
	}
	public void setDrawerIcon(int drawerIcon) {
		this.drawerIcon = drawerIcon;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
