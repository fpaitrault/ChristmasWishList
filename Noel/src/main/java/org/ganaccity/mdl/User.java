package org.ganaccity.mdl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.codec.digest.DigestUtils;

@Entity(name="users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private int index;
    @Column
	private String name;
    @Column
	private String hash;

    public User() {
        
    }
    
	public User(String name, String hash) {
		this.name = name;
		this.hash = hash;
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
