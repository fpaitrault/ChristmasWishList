package org.ganaccity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import winstone.Launcher;

public class MainServer {

    /**
     * @param args
     * @throws IOException 
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        Map<String,String> params = new HashMap<String,String>();
        params.put("webroot", "src/main/webapp"); // or any other command line args, eg port
        Launcher.initLogger(params);
        
        new Launcher(params); // spawns threads, so your application doesn't block

        while(true) {
            Thread.sleep(10);
        } 
    }

}
