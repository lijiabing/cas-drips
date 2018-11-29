package com.drips.cas.cache;

import org.springframework.stereotype.Component;
import org.springframework.util.ConcurrentReferenceHashMap;

@Component
public class SessionCache {

    private final ConcurrentReferenceHashMap<String,String> concurrentReferenceHashMap=new ConcurrentReferenceHashMap<>();

    public void recodeSession(String ticket,String sessionId){
        concurrentReferenceHashMap.put(sessionId,ticket);
    }

    public void removeSession(String sessionId){
        concurrentReferenceHashMap.remove(sessionId);
    }

    public boolean hasKey(String sessionId){
        return concurrentReferenceHashMap.containsKey(sessionId);
    }
}
