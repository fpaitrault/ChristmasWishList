package org.ganaccity.tests;

import java.util.LinkedList;
import java.util.List;

import org.ganaccity.mdl.Session;
import org.ganaccity.mdl.Setting;
import org.ganaccity.mdl.User;
import org.ganaccity.mdl.Wish;

public class MdlFactory {
    private static String PREFIX_VALUE = "VALUE-";
    private static String PREFIX_KEY = "SETTING-";
    private static String PREFIX_UUID = "UUID-";
    private static String PREFIX_USER = "USER-";

    public static List<User> createUsers() {
        List<User> users = new LinkedList<User>();
        users.add(new User("Florent", "toto",0));
        users.add(new User("Maeva", "test",1));
        users.add(new User("Howard", "titi",2));
        for(User user : users) {
            user.setFriends(users);
        }
        return users;
    }

    public static List<Wish> createWishes(List<User> users) {
        List<Wish> wishes = new LinkedList<Wish>();
        for(User author : users) {
            for(User dest : users) {
                for(User resv : users) {
                    Wish wish = createWish(author, dest, resv);
                    wishes.add(wish);
                }
                Wish wish = createWish(author, dest, null);
                wishes.add(wish);
            }
        }
        return wishes;
    }


    private static Wish createWish(User author, User dest, User resv) {
        Wish wish = new Wish(
                author, 
                "Present for "+dest.toString(), 
                "Comment from "+author.toString(),dest,resv);
        return wish;
    }

    public static List<Session> createSessions() {
        List<Session> result = new LinkedList<Session>();
        for(int i = 0 ; i < 10 ; i++) {
            result.add(new Session(PREFIX_UUID+i, new User(PREFIX_USER+i, "", i)));
        }
        return result;
    }
    
    public static List<Setting> createSettings() {
        List<Setting> result = new LinkedList<Setting>();
        for(int i = 0 ; i < 10 ; i++) {
            result.add(new Setting(PREFIX_KEY+i, PREFIX_VALUE+i));
        }
        return result;
    }
}
