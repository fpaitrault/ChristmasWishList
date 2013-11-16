package org.ganaccity.mdl;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="sessions")
public class Session implements Serializable {

    private static final long serialVersionUID = -3621224994226066716L;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int index;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
	private String uuid;

    public Session() {

    }
    public Session(final String uuid, final User user) {
        this.uuid = uuid;
        this.user = user;
    }

    public final String getUUID() {
        return uuid;
    }
    public final User getUser() {
        return user;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

}
