package org.fpaitrault.mdl;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.ListUtils;

@Entity(name="users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private int index;
    @Column
	private String username;
    @Column
    private String name;
    @Column
	private String hash;
    @Column
    private String email;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "users_link", joinColumns = { @JoinColumn(name = "leftId") }, 
            inverseJoinColumns = { @JoinColumn(name = "rightId") })
    private List<User> friends;

    @SuppressWarnings("unchecked")
    public List<User> getFriends() {
        return friends;
    }
    
    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User() {
        this.friends = new LinkedList<User>();
    }
    
	public User(String username, String hash, int index) {
		this.username = username;
		this.hash = hash;
		this.index = index;
        this.friends = new LinkedList<User>();
	}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public String getHash() {
		return hash;
	}

    public void setPassword(String password) {
        this.hash = DigestUtils.md5Hex(password);
    }
    
	public int getIndex() {
		return index;
	}

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !User.class.isAssignableFrom(obj.getClass()))
            return false;
        else
            return this.getIndex() == ((User) obj).getIndex();
    }

}
