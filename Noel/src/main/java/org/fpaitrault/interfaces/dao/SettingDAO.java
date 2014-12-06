package org.fpaitrault.interfaces.dao;

import org.fpaitrault.mdl.Setting;

public interface SettingDAO extends GenericDAO<Setting> {
    String get(String key);
    void set(String key, String value);
}
