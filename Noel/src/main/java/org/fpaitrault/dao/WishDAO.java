package org.fpaitrault.dao;

import java.util.LinkedList;
import java.util.List;

import org.fpaitrault.AuthenticationService;
import org.fpaitrault.mdl.User;
import org.fpaitrault.mdl.Wish;
import org.hibernate.Query;

public class WishDAO extends GenericDAO<Wish> {

	public WishDAO() {
		super(Wish.class);
	}
	
    @SuppressWarnings("unchecked")
    public List<Wish> readAll() {
        List<Wish> res = null;
        List<Wish> toBeRemoved = new LinkedList<Wish>();
        synchronized (getSession()) {
            Query query = getSession().createQuery("from " + Wish.class.getName());
            res = query.list();
        }
        
        //Remove wishes that shall not be visible !!!!!!!
        User user = AuthenticationService.instance().getUserCredential();
        for(Wish wish : res) {
            if(wish.getDest().equals(user) && !wish.getAuthor().equals(user))
                toBeRemoved.add(wish);
        }
        res.removeAll(toBeRemoved);
        return res;
    }


}
