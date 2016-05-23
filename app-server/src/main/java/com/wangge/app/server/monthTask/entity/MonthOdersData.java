package com.wangge.app.server.monthTask.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wangge.app.server.entity.Salesman;

@Entity
@Table(name = "sys_month_Task_basicdata")
@NamedEntityGraph(name = "salesman.graph", 
attributeNodes = @NamedAttributeNode(value = "salesman", subgraph = "registData.graph"),subgraphs = {
		@NamedSubgraph(name = "region.graph", attributeNodes = @NamedAttributeNode("region")) })
public class MonthOdersData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 101L;
	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator = "idgen")
	private long id;
	private String regionId;
	private String month;
	private int TAL15M1;
	private int TAL15M3;
	private int TAL10M1;
	private int TAL10M3;
	private int TAL7M1;
	private int TAL7M3;
	private int TAL4M1;
	private int TAL4M3;
	private int TAL20M1;
	private int TAL20M3;
	private int VisitCount15;
	private int VisitCount10;
	private int VisitCount7;
	private int VisitCount4;
	private int VisitCount20;
	private int sysgive15;
	private int sysgive10;
	private int sysgive7;
	private int sysgive4;
	private int sysgive20;
	// 相应计划是否已生成
	private int used;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "salesman_ID")
	private Salesman salesman;

	public int getTAL15M1() {
		return TAL15M1;
	}

	public void setTAL15M1(int tAL15M1) {
		TAL15M1 = tAL15M1;
	}

	public int getTAL15M3() {
		return TAL15M3;
	}

	public void setTAL15M3(int tAL15M3) {
		TAL15M3 = tAL15M3;
	}

	public int getTAL10M1() {
		return TAL10M1;
	}

	public void setTAL10M1(int tAL10M1) {
		TAL10M1 = tAL10M1;
	}

	public int getTAL10M3() {
		return TAL10M3;
	}

	public void setTAL10M3(int tAL10M3) {
		TAL10M3 = tAL10M3;
	}

	public int getTAL7M1() {
		return TAL7M1;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public void setTAL7M1(int tAL7M1) {
		TAL7M1 = tAL7M1;
	}

	public int getTAL7M3() {
		return TAL7M3;
	}

	public void setTAL7M3(int tAL7M3) {
		TAL7M3 = tAL7M3;
	}

	public int getTAL4M1() {
		return TAL4M1;
	}

	public void setTAL4M1(int tAL4M1) {
		TAL4M1 = tAL4M1;
	}

	public int getTAL4M3() {
		return TAL4M3;
	}

	public void setTAL4M3(int tAL4M3) {
		TAL4M3 = tAL4M3;
	}

	public int getTAL20M1() {
		return TAL20M1;
	}

	public void setTAL20M1(int tAL20M1) {
		TAL20M1 = tAL20M1;
	}

	public int getTAL20M3() {
		return TAL20M3;
	}

	public void setTAL20M3(int tAL20M3) {
		TAL20M3 = tAL20M3;
	}

	public MonthOdersData() {
		super();
	}

	public MonthOdersData(String regionId, int tAL15M1, int tAL15M3, int tAL10M1, int tAL10M3, int tAL7M1, int tAL7M3,
			int tAL4M1, int tAL4M3, int tAL20M1, int tAL20M3, String month) {
		super();
		this.regionId = regionId;
		TAL15M1 = tAL15M1;
		TAL15M3 = tAL15M3;
		TAL10M1 = tAL10M1;
		TAL10M3 = tAL10M3;
		TAL7M1 = tAL7M1;
		TAL7M3 = tAL7M3;
		TAL4M1 = tAL4M1;
		TAL4M3 = tAL4M3;
		TAL20M1 = tAL20M1;
		TAL20M3 = tAL20M3;
		this.month = month;
	}

	public int getUsed() {
		return used;
	}

	public void setUsed(int used) {
		this.used = used;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getVisitCount15() {
		return VisitCount15;
	}

	public void setVisitCount15(int visitCount15) {
		VisitCount15 = visitCount15;
	}

	public int getVisitCount10() {
		return VisitCount10;
	}

	public void setVisitCount10(int visitCount10) {
		VisitCount10 = visitCount10;
	}

	public int getVisitCount7() {
		return VisitCount7;
	}

	public void setVisitCount7(int visitCount7) {
		VisitCount7 = visitCount7;
	}

	public int getVisitCount4() {
		return VisitCount4;
	}

	public void setVisitCount4(int visitCount4) {
		VisitCount4 = visitCount4;
	}

	public int getVisitCount20() {
		return VisitCount20;
	}

	public void setVisitCount20(int visitCount20) {
		VisitCount20 = visitCount20;
	}

	public int getSysgive15() {
		return sysgive15;
	}

	public void setSysgive15(int sysgive15) {
		this.sysgive15 = sysgive15;
	}

	public int getSysgive10() {
		return sysgive10;
	}

	public void setSysgive10(int sysgive10) {
		this.sysgive10 = sysgive10;
	}

	public int getSysgive7() {
		return sysgive7;
	}

	public void setSysgive7(int sysgive7) {
		this.sysgive7 = sysgive7;
	}

	public int getSysgive4() {
		return sysgive4;
	}

	public void setSysgive4(int sysgive4) {
		this.sysgive4 = sysgive4;
	}

	public int getSysgive20() {
		return sysgive20;
	}

	public void setSysgive20(int sysgive20) {
		this.sysgive20 = sysgive20;
	}

	public Salesman getSalesman() {
		return salesman;
	}

	public void setSalesman(Salesman salesman) {
		this.salesman = salesman;
	}

	public MonthOdersData(String regionId, String month, int tAL15M1, int tAL15M3, int tAL10M1, int tAL10M3, int tAL7M1,
			int tAL7M3, int tAL4M1, int tAL4M3, int tAL20M1, int tAL20M3, int visitCount15, int visitCount10,
			int visitCount7, int visitCount4, int visitCount20, int sysgive15, int sysgive10, int sysgive7,
			int sysgive4, int sysgive20, Salesman salesman) {
		super();
		this.regionId = regionId;
		this.month = month;
		TAL15M1 = tAL15M1;
		TAL15M3 = tAL15M3;
		TAL10M1 = tAL10M1;
		TAL10M3 = tAL10M3;
		TAL7M1 = tAL7M1;
		TAL7M3 = tAL7M3;
		TAL4M1 = tAL4M1;
		TAL4M3 = tAL4M3;
		TAL20M1 = tAL20M1;
		TAL20M3 = tAL20M3;
		VisitCount15 = visitCount15;
		VisitCount10 = visitCount10;
		VisitCount7 = visitCount7;
		VisitCount4 = visitCount4;
		VisitCount20 = visitCount20;
		this.sysgive15 = sysgive15;
		this.sysgive10 = sysgive10;
		this.sysgive7 = sysgive7;
		this.sysgive4 = sysgive4;
		this.sysgive20 = sysgive20;
		this.salesman = salesman;
	}

}
