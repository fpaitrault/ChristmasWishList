package org.fpaitrault.dao;

import java.util.List;

import org.fpaitrault.interfaces.dao.SettingDAO;
import org.fpaitrault.mdl.Setting;
import org.springframework.stereotype.Service;

@Service("settingDAO")
public class SettingDAOImpl extends GenericDAOImpl<Setting> implements SettingDAO {

    public SettingDAOImpl() {
    	super(Setting.class);
    }
    
    @Override
    public String get(String key) {
        synchronized (getSession()) {
            for (Setting setting : super.readAll()) {
                if (setting.getKey().equalsIgnoreCase(key)) {
                    if(setting.getValue() == null)
                        return "";
                    return setting.getValue();
                }
            }
            return "";
        }
    }

    @Override
    public void set(String key, String value) {
        synchronized (getSession()) {
            for (Setting setting : super.readAll()) {
                if (setting.getKey().equalsIgnoreCase(key)) {
                    setting.setValue(value);
                    this.update(setting);
                }
            }
            return;
        }
    }
}
