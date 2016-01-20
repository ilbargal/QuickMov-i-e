package com.finalandroidproject.quickmovie.UsefulClasses;

import java.util.Hashtable;

// This class contains global variable - useful for all activities
public class IntentHelper {
    private static IntentHelper _instance;
    private Hashtable<String, Object> _hash;

    private IntentHelper()
    {
        _hash = new Hashtable<String, Object>();
    }

    private static IntentHelper getInstance() {
        if(_instance==null) {
            _instance = new IntentHelper();
        }
        return _instance;
    }

    public static void addObjectForKey(String key, Object object) {
        getInstance()._hash.put(key, object);
    }

    public static Object getObjectForKey(String key) {
        IntentHelper helper = getInstance();
        Object data = helper._hash.get(key);
        helper._hash.remove(key);
        helper = null;
        return data;
    }
}
