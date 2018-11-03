package voa.learning.english.special.model;

import java.io.Serializable;

public class VideoSchema implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getIdYoutube() {
		return idYoutube;
	}
	public void setIdYoutube(String idYoutube) {
		this.idYoutube = idYoutube;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	String img;
	String path;
	String idYoutube;
	String title;
	String content;
	String listen;
	String idcat;
	String mp3;
	String linkmp3;
	String thumbnaix;
	String id;	
	String idSqlite;	
		
	public String getIdSqlite() {
		return idSqlite;
	}
	public void setIdSqlite(String idSqlite) {
		this.idSqlite = idSqlite;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getListen() {
		return listen;
	}
	public void setListen(String listen) {
		this.listen = listen;
	}
	public String getIdcat() {
		return idcat;
	}
	public void setIdcat(String idcat) {
		this.idcat = idcat;
	}
	public String getMp3() {
		return mp3;
	}
	public void setMp3(String mp3) {
		this.mp3 = mp3;
	}
	public String getLinkmp3() {
		return linkmp3;
	}
	public void setLinkmp3(String linkmp3) {
		this.linkmp3 = linkmp3;
	}
	public String getThumbnaix() {
		return thumbnaix;
	}
	public void setThumbnaix(String thumbnaix) {
		this.thumbnaix = thumbnaix;
	}			

}
