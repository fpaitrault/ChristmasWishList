package org.ganaccity.interfaces;

import java.util.UUID;

public interface SessionService {
    void register(int userId, UUID uuid);
    void unregister();
    UUID getUUID();
    Integer getUserId();
            
}
