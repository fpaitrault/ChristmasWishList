package org.fpaitrault.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.fpaitrault.interfaces.AuthenticationService;
import org.fpaitrault.interfaces.dao.WishDAO;
import org.fpaitrault.mdl.User;
import org.fpaitrault.mdl.Wish;
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

        return cleanupWishes(res, toBeRemoved);
    }

    @SuppressWarnings("unchecked")
    public List<Wish> getHotWishes() {
        List<Wish> res = null;
        List<Wish> toBeRemoved = new LinkedList<Wish>();
        
        synchronized (getSession()) {
            Query query = getSession().createQuery("from " + Wish.class.getName() +
                    " where updated > :date").setDate("date", subtractDay(new Date()));
            res = query.list();
        }
        
        return cleanupWishes(res, toBeRemoved);
    }

    private Date subtractDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }
    private List<Wish> cleanupWishes(List<Wish> res, List<Wish> toBeRemoved) {
        //Remove wishes that shall not be visible !!!!!!!
        User user = authService.getUserCredential();
        for(Wish wish : res) {
            if(wish.getAuthor() != user) {
                if(wish.getDest() == user) {
                    toBeRemoved.add(wish);
                }
            }
        }
        res.removeAll(toBeRemoved);
        
        return res;
    }
}
