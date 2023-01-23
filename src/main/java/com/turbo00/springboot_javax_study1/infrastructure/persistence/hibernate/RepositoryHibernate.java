package com.turbo00.springboot_javax_study1.infrastructure.persistence.hibernate;

import com.turbo00.springboot_javax_study1.domain.Base;
import com.turbo00.springboot_javax_study1.domain.Repository;
import com.turbo00.springboot_javax_study1.domain.TimeObject;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public abstract class RepositoryHibernate<T, PK extends Serializable>
        implements Repository<T, PK> {

    @Autowired
    protected SessionFactory sessionFactory;

    protected Class<T> type;

    public RepositoryHibernate(Class<T> type) {
        this.type = type;
    }

    @Override
    @Transactional
    public T delete(T t)  {
    	Session session = sessionFactory.getCurrentSession();
    	T _t = (T)session.merge(t);
        session.delete(_t);
    	return _t;
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            //在多线程环境中,会抛出这个异常,所以需要用openSession
            session = sessionFactory.openSession();
        }
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(type);
        Root e = cq.from(type);
        cq.select(e);
        Query query = session.createQuery(cq);
        try {
            return query.getResultList();
        } catch (NoResultException exception) {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public T findById(PK id) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            //在多线程环境中,会抛出这个异常,所以需要用openSession
            session = sessionFactory.openSession();
        }
        Object obj = session.get(type, id);
        return type.cast(obj);
    }

    @Override
    @Transactional
    public T store(T t) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            //在多线程环境中,会抛出这个异常,所以需要用openSession
            session = sessionFactory.openSession();
        }
        t = (T) session.merge(t);
        if (t instanceof TimeObject) {
            TimeObject t_ = (TimeObject) t;

            if (t_.getCreateDate() == null) {
                t_.setCreateDate((new Date()));
            }
            t_.setUpdateDate(new Date());
        }

        //刚创建时
//        if (t instanceof Base) {
//            Base b_ = (Base) t;
//            if (b_.getCreateUser() == null) {
//                b_.setCreateUser(AppUtils.getCurrentUser());
//            }
//        }

        session.saveOrUpdate(t);
        return t;
    }

    /**
     * 参见hibernate的批量处理:https://docs.jboss.org/hibernate/orm/3.5/reference/zh-CN/html/batch.html
     * @param tList
     */
    @Override
    public void batchStore(List<T> tList) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            //在多线程环境中,会抛出这个异常,所以需要用openSession
            session = sessionFactory.openSession();
        }

        int i = 0;
        Transaction tx = session.beginTransaction();
        for (T t : tList) {
            i++;
            session.saveOrUpdate(t);
            if (i % 30 == 0) {
                session.flush();
                session.clear();
            }
        }
        tx.commit();
    }

    @Override
    @Transactional(readOnly = true)
    public T reload(T t) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            //在多线程环境中,会抛出这个异常,所以需要用openSession
            session = sessionFactory.openSession();
        }
        if (t != null) {
            session.refresh(t);
        }
        return t;
    }

    @Override
    @Transactional(readOnly = true)
    public Long count(JpaCriteriaHolder jpaCriteriaHolder) {
        jpaCriteriaHolder.getCriteriaQuery().select(jpaCriteriaHolder.getCriteriaBuilder().count(jpaCriteriaHolder.getRoot()));
        Query query = jpaCriteriaHolder.getSession().createQuery(jpaCriteriaHolder.getCriteriaQuery());
        try {
            return (Long) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> listPart(Integer start, Integer count, JpaCriteriaHolder jpaCriteriaHolder) {
        Query query = jpaCriteriaHolder.getSession().createQuery(jpaCriteriaHolder.getCriteriaQuery());
        try {
            return query.setFirstResult(start).setMaxResults(count).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<T> listAll(JpaCriteriaHolder jpaCriteriaHolder) {
        Query query = jpaCriteriaHolder.getSession().createQuery(jpaCriteriaHolder.getCriteriaQuery());

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Session getSession() {
        Session session;
        try {
            session =  sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        return session;
    }

    @Override
    public void evict(T t) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            //在多线程环境中,会抛出这个异常,所以需要用openSession
            session = sessionFactory.openSession();
        }
        session.evict(t);
    }

    @Override
    public void clear() {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            //在多线程环境中,会抛出这个异常,所以需要用openSession
            session = sessionFactory.openSession();
        }
        session.clear();
    }

    @Override
    public void flush() {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            //在多线程环境中,会抛出这个异常,所以需要用openSession
            session = sessionFactory.openSession();
        }
        session.flush();
    }
}