package org.fpaitrault.mdl;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="settings")
public class Setting implements Serializable {

    private static final long serialVersionUID = -3621224994226066716L;

    @Id  @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private String index;

    @Column
    private String key;

    @Column
    private String value;

    public Setting() {

    }

    public Setting(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
