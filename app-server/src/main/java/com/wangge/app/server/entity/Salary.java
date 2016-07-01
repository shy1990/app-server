package com.wangge.app.server.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 工资表
 * Created by 神盾局 on 2016/6/12.
 */
@Entity
@Table(name = "SYS_SALARY")
public class Salary {
    @Id
    @GenericGenerator(name="idgen",strategy="increment")
    @GeneratedValue(generator="idgen")   //自定义生成主键策略
    @Column(name = "SALARY_ID")
    private Long id;
    @Column(name = "SALARY_NAME")
    private String name;
    @Column(name = "SALARY_SALARY")
    private Float salary;
    @Temporal(TemporalType.DATE)
    @Column(name = "SALARY_CREATETIME")
    private Date createTime;
    @Column(name = "SALARY_TEL")
    private String tel;
    @Column(name = "SALARY_MESSAGE")
    private String message;
    @Column(name = "MONTHS")
    private String months;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getMonths() {
		return months;
	}

	public void setMonths(String months) {
		this.months = months;
	}

	@Override
    public String toString() {
        return "Salary{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", createTime=" + createTime +
                ", tel='" + tel + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
