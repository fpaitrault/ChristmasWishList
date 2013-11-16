package org.fpaitrault.mdl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

public class DatabaseStub {
	private List<User> users;
	private List<Wish> wishes;
	
	private static DatabaseStub instance = new DatabaseStub();
	
	public static DatabaseStub instance() {
		return instance;
	}
	
	private DatabaseStub() {
		users = new LinkedList<User>();
		wishes = new LinkedList<Wish>();
		
		User florent = new User("Florent", DigestUtils.md5Hex("tigrou"));
		User maeva = new User("Maeva", DigestUtils.md5Hex("tigrou"));
		users.add(florent);
		users.add(maeva);
		
		wishes.add(new Wish(florent, "Jeu <b>3DS</b>", "Commentaires divers", florent, maeva));
		wishes.add(new Wish(florent, "Je ne sais pas", "Commentaires divers2", maeva, null));
		wishes.add(new Wish(maeva, "Je ne sais pas", "Commentaires divers2", maeva, null));
		
	}

	public List<User> getUsers() {
		return users;
	}

	public List<Wish> getWishes() {
		return wishes;
	}
}
