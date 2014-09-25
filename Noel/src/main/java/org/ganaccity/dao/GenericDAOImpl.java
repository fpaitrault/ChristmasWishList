package org.ganaccity.dao;

import java.util.List;

import org.ganaccity.interfaces.dao.GenericDAO;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.zkoss.zkplus.hibernate.HibernateUtil;

public class GenericDAOImpl<T extends Object> implements GenericDAO<T> {
    //protected static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    protected static Session currentSession = null;
    private Class<T> type;
    public final Class<T> getType() {
        return type;
    }
    public GenericDAOImpl(final Class<T> type) {
        this.type = type;
    }
    
    public final Session getSession() {
        if (currentSession == null) {
            currentSession = HibernateUtil.getSessionFactory().openSession();
        }
        return currentSession;
    }
    public final int create(final T o) {
        int result = 0;
        synchronized (getSession()) {
            Transaction transact = getSession().beginTransaction();
            result = ((Integer) getSession().save(o)).intValue();
            transact.commit();
        }

        return result;
    }
    @SuppressWarnings("unchecked")
    public T readById(final int id) {
        T res = null;
        synchronized (getSession()) {
            res = (T) getSession().get(type.getName(), Integer.valueOf(id));
        }
        return res;
    }
    @SuppressWarnings("unchecked")
    public List<T> readAll() {
        List<T> res = null;
        synchronized (getSession()) {
            Query query = getSession().createQuery("from " + type.getName());
            res = query.list();
        }
        return res;
    }
    
    public final void update(final T o) {
        synchronized (getSession()) {
            Transaction transact = getSession().beginTransaction();
            getSession().saveOrUpdate(o);
            transact.commit();
        }
    }
    public final void delete(final T o) {
        synchronized (getSession()) {
            Transaction transact = getSession().beginTransaction();
            getSession().delete(o);
            transact.commit();
        }
    }

}
