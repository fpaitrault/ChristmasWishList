package org.fpaitrault.mdl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.fpaitrault.interfaces.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Entity(name="wishes")
public class Wish {
	
    @Autowired @Qualifier("authService")
    private transient AuthenticationService authService = null;

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
    
    @Column(name = "updated")
    private Date updated;
	
    public Wish() {
        
    }
    
	public Wish(User author, String descr, String comment, User dest, User reservedBy) {
		this.descr = descr;
		this.comment = comment;
		this.author = author;
		this.dest = dest;
		this.reservedBy = reservedBy;
		this.updated = new Date();
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
        this.updated = new Date();
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
		this.updated = new Date();
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public User getReservedBy() {
		return reservedBy;
	}
	public void setReservedBy(User reservedBy) {
		this.reservedBy = reservedBy;
        this.updated = new Date();
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

    public Date getUpdated() {
        return updated;
    }
}
