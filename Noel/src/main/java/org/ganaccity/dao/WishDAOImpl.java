package org.ganaccity.dao;

import java.util.LinkedList;
import java.util.List;

import org.ganaccity.interfaces.AuthenticationService;
import org.ganaccity.interfaces.dao.WishDAO;
import org.ganaccity.mdl.User;
import org.ganaccity.mdl.Wish;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("wishDAO")
public class WishDAOImpl extends GenericDAOImpl<Wish> implements WishDAO {

    @Autowired @Qualifier("authService")
    private AuthenticationService authService = null;

    public WishDAOImpl() {
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
        User user = authService.getUserCredential();
        for(Wish wish : res) {
            if(wish.getDest().equals(user) && !wish.getAuthor().equals(user))
                toBeRemoved.add(wish);
        }
        res.removeAll(toBeRemoved);
        return res;
    }


}
