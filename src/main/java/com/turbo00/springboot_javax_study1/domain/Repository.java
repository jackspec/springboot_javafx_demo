package com.turbo00.springboot_javax_study1.domain;

import com.turbo00.springboot_javax_study1.infrastructure.persistence.hibernate.JpaCriteriaHolder;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

/**
 * 使用了模板类的仓库类
 *
 * @param <T>
 * @param <PK>
 * @author Administrator
 */
public interface Repository<T, PK extends Serializable> {
    T store(T t);

    void batchStore(List<T> tList);

    T findById(PK id);

    List<T> findAll();

    T delete(T t);

    T reload(T t);

    Long count(JpaCriteriaHolder jpaCriteriaHolder);

    public List<T> listPart(Integer start, Integer count, JpaCriteriaHolder jpaCriteriaHolder);

    public List<T> listAll(JpaCriteriaHolder jpaCriteriaHolder);

    Session getSession();

    public void evict(T t);

    /**
     * clear hibernate cache
     */
    public void clear();

    public void flush();
}