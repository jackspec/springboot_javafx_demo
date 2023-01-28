package com.turbo00.springboot_javax_study1.interfaces.javafx;

import com.turbo00.springboot_javax_study1.infrastructure.persistence.hibernate.JpaCriteriaHolder;
import javafx.beans.property.SimpleIntegerProperty;
import lombok.Getter;

import java.util.List;

/**
 * https://www.jianshu.com/p/21193fcc05f1
 */
public abstract class Page<T> {
    private SimpleIntegerProperty totalRecord; // total record number in source data
    private SimpleIntegerProperty pageSize; // the number of data in per page
    private SimpleIntegerProperty totalPage; // total page number
    @Getter
    private int currentPage;

    protected JpaCriteriaHolder jpaCriteriaHolder;
    protected JpaCriteriaHolder jpaCriteriaHolderCount;

    // setter
    public void setPageSize(int pageSize) {
        this.pageSize.set(pageSize);
    }

    // getter
    public int getTotalRecord() {
        return totalRecord.get();
    }

    public SimpleIntegerProperty totalRecordProperty() {
        return totalRecord;
    }

    public int getPageSize() {
        return pageSize.get();
    }

    public SimpleIntegerProperty pageSizeProperty() {
        return pageSize;
    }

    public int getTotalPage() {
        return totalPage.get();
    }

    public SimpleIntegerProperty totalPageProperty() {
        return totalPage;
    }


    /**
     * @param pageSize    the number of data in per page
     */
    public Page(JpaCriteriaHolder jpaCriteriaHolder, JpaCriteriaHolder jpaCriteriaHolderCount, int pageSize) {
        this.totalRecord = new SimpleIntegerProperty();
        this.totalPage = new SimpleIntegerProperty();
        this.pageSize = new SimpleIntegerProperty(pageSize);
        currentPage = 0;
        this.jpaCriteriaHolder = jpaCriteriaHolder;
        this.jpaCriteriaHolderCount = jpaCriteriaHolderCount;
        initialize();
    }

    public void initialize() {
        totalRecord.set(rowCount());

        // calculate the number of total pages
        totalPage.set(
                totalRecord.get() % pageSize.get() == 0 ?
                        totalRecord.get() / pageSize.get() :
                        totalRecord.get() / pageSize.get() + 1);

        // add listener: the number of total pages need to be change if the page size changed
        pageSize.addListener((observable, oldVal, newVal) ->
                totalPage.set(
                        totalRecord.get() % pageSize.get() == 0 ?
                                totalRecord.get() / pageSize.get() :
                                totalRecord.get() / pageSize.get() + 1)
        );
    }

    /**
     * current page number(0-based system)
     *
     * @param currentPage current page number
     * @return
     */
    public List<T> getCurrentPageDataList(int currentPage) {
        this.currentPage = currentPage;
        return listPartRow(currentPage * pageSize.get(), pageSize.get());
    }

    protected abstract int rowCount();
    protected abstract List<T> listPartRow(int itemStartNumber, int pageSize);
}
