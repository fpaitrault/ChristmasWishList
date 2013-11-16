package org.fpaitrault.mdl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.fpaitrault.AuthenticationService;

@Entity(name="wishes")
public class Wish {
	
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private int index;
    @Column(name="description")
	private String descr;
    @Column(name="comments")
	private String comment;
    @ManyToOne
    @JoinColumn(name = "author")
	private User author;
    @ManyToOne
    @JoinColumn(name = "addressee")
	private User dest;
    @ManyToOne
    @JoinColumn(name = "reserved")
	private User reservedBy;
	
    public Wish() {
        
    }
    
	public Wish(User author, String descr, String comment, User dest, User reservedBy) {
		this.descr = descr;
		this.comment = comment;
		this.author = author;
		this.dest = dest;
		this.reservedBy = reservedBy;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getComment() {
	    User user = AuthenticationService.instance().getUserCredential();
	    if(user.equals(this.dest))
	        return "";
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public User getReservedBy() {
        User user = AuthenticationService.instance().getUserCredential();
        if(user.equals(this.dest))
            return null;
		return reservedBy;
	}
	public void setReservedBy(User reservedBy) {
		this.reservedBy = reservedBy;
	}
	public User getDest() {
		return dest;
	}
	public void setDest(User dest) {
		this.dest = dest;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
}
