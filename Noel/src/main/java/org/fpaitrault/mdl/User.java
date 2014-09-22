package org.fpaitrault.mdl;

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
	private String name;
    @Column
	private String hash;
    @Column
    private String email;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "users_link", joinColumns = { @JoinColumn(name = "left", nullable = false, updatable = false) }, 
            inverseJoinColumns = { @JoinColumn(name = "right", nullable = false, updatable = false) })
    private List<User> friends;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "users_link", joinColumns = { @JoinColumn(name = "right", nullable = false, updatable = false) }, 
            inverseJoinColumns = { @JoinColumn(name = "left", nullable = false, updatable = false) })
    private List<User> friendsInv;

    @SuppressWarnings("unchecked")
    public List<User> getFriends() {
        return ListUtils.union(friends, friendsInv);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User() {
        
    }
    
	public User(String name, String hash, int index) {
		this.name = name;
		this.hash = hash;
		this.index = index;
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
    
    public String getPassword() {
        return null;
    }
    
	public int getIndex() {
		return index;
	}

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object obj) {
        if((User) obj == null)
            return false;
        else
            return this.getIndex() == ((User) obj).getIndex();
    }

}
