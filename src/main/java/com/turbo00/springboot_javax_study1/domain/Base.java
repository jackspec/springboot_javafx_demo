package com.turbo00.springboot_javax_study1.domain;

import com.turbo00.springboot_javax_study1.util.MyToStringStyle;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import jakarta.persistence.*;

/**
 * 所有实体的基类
 */
@MappedSuperclass
public class Base extends TimeObject {

    private static final long serialVersionUID = -3917339154556885083L;

    private static final int MEMO_LENGTH = 500;

    protected Long id;
    protected String memo;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    /**
     * 备注
     *
     * @return
     */
    @Column(name = "memo", length = MEMO_LENGTH)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public boolean equals(Object obj) {
        Base b = (Base) obj;
        if (b == null) return false;
        return new EqualsBuilder().append(id, b.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, new MyToStringStyle());
    }
}
