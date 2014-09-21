package org.fpaitrault.dao;

import java.util.List;

import org.fpaitrault.interfaces.dao.SettingDAO;
import org.fpaitrault.mdl.Setting;
import org.springframework.stereotype.Service;

@Service("settingDAO")
public class SettingDAOImpl extends GenericDAOImpl<Setting> implements SettingDAO {
    private List<Setting> settingCache;

    public SettingDAOImpl() {
    	super(Setting.class);
    }
    
    @Override
    public String get(String key) {
        synchronized (getSession()) {
            if(settingCache == null) {
                settingCache = super.readAll();
            }
            for (Setting setting : settingCache) {
                if (setting.getKey().equalsIgnoreCase(key)) {
                    return setting.getValue();
                }
            }
            return null;
        }
    }
}
