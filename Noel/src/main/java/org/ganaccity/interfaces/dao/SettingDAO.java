package org.ganaccity.interfaces.dao;

import org.ganaccity.mdl.Setting;

public interface SettingDAO extends GenericDAO<Setting> {
    String get(String key);
}
