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
@NamedEntityGraph(name = "salesman.graph", attributeNodes = @NamedAttributeNode(value = "salesman", subgraph = "region.graph"), subgraphs = {
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
	//tal15m1  上月提货量>=15&&<20的店铺的数量
	private int tal15m1;
	//tal15m3  三月内平均提货量>=15&&<20的店铺的数量
	private int tal15m3;
	private int tal10m1;
	private int tal10m3;
	private int tal7m1;
	private int tal7m3;
	//上月月提货量<=4的店铺的数量
	private int tal4m1;
	private int tal4m3;
	//  上月月提货量>20的店铺的数量
	private int tal20m1;
	private int tal20m3;
	//上月统计拜访次数为15的店铺数量
	private int visitCount15;
	private int visitCount10;
	private int visitCount7;
	private int visitCount4;
	private int visitCount20;
	//系统建议拜访次数为15的店铺数量
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

	public int gettal15m1() {
		return tal15m1;
	}

	public void settal15m1(int tAL15M1) {
		tal15m1 = tAL15M1;
	}

	public int gettal15m3() {
		return tal15m3;
	}

	public void settal15m3(int tAL15M3) {
		tal15m3 = tAL15M3;
	}

	public int gettal10m1() {
		return tal10m1;
	}

	public void settal10m1(int tAL10M1) {
		tal10m1 = tAL10M1;
	}

	public int gettal10m3() {
		return tal10m3;
	}

	public void settal10m3(int tAL10M3) {
		tal10m3 = tAL10M3;
	}

	public int gettal7m1() {
		return tal7m1;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public void settal7m1(int tAL7M1) {
		tal7m1 = tAL7M1;
	}

	public int gettal7m3() {
		return tal7m3;
	}

	public void settal7m3(int tAL7M3) {
		tal7m3 = tAL7M3;
	}

	public int gettal4m1() {
		return tal4m1;
	}

	public void settal4m1(int tAL4M1) {
		tal4m1 = tAL4M1;
	}

	public int gettal4m3() {
		return tal4m3;
	}

	public void settal4m3(int tAL4M3) {
		tal4m3 = tAL4M3;
	}

	public int gettal20m1() {
		return tal20m1;
	}

	public void settal20m1(int tAL20M1) {
		tal20m1 = tAL20M1;
	}

	public int gettal20m3() {
		return tal20m3;
	}

	public void settal20m3(int tAL20M3) {
		tal20m3 = tAL20M3;
	}

	public int getTal15m1() {
		return tal15m1;
	}

	public void setTal15m1(int tal15m1) {
		this.tal15m1 = tal15m1;
	}

	public int getTal15m3() {
		return tal15m3;
	}

	public void setTal15m3(int tal15m3) {
		this.tal15m3 = tal15m3;
	}

	public int getTal10m1() {
		return tal10m1;
	}

	public void setTal10m1(int tal10m1) {
		this.tal10m1 = tal10m1;
	}

	public int getTal10m3() {
		return tal10m3;
	}

	public void setTal10m3(int tal10m3) {
		this.tal10m3 = tal10m3;
	}

	public int getTal7m1() {
		return tal7m1;
	}

	public void setTal7m1(int tal7m1) {
		this.tal7m1 = tal7m1;
	}

	public int getTal7m3() {
		return tal7m3;
	}

	public void setTal7m3(int tal7m3) {
		this.tal7m3 = tal7m3;
	}

	public int getTal4m1() {
		return tal4m1;
	}

	public void setTal4m1(int tal4m1) {
		this.tal4m1 = tal4m1;
	}

	public int getTal4m3() {
		return tal4m3;
	}

	public void setTal4m3(int tal4m3) {
		this.tal4m3 = tal4m3;
	}

	public int getTal20m1() {
		return tal20m1;
	}

	public void setTal20m1(int tal20m1) {
		this.tal20m1 = tal20m1;
	}

	public int getTal20m3() {
		return tal20m3;
	}

	public void setTal20m3(int tal20m3) {
		this.tal20m3 = tal20m3;
	}

	public int getVisitCount15() {
		return visitCount15;
	}

	public void setVisitCount15(int visitCount15) {
		this.visitCount15 = visitCount15;
	}

	public int getVisitCount10() {
		return visitCount10;
	}

	public void setVisitCount10(int visitCount10) {
		this.visitCount10 = visitCount10;
	}

	public int getVisitCount7() {
		return visitCount7;
	}

	public void setVisitCount7(int visitCount7) {
		this.visitCount7 = visitCount7;
	}

	public int getVisitCount4() {
		return visitCount4;
	}

	public void setVisitCount4(int visitCount4) {
		this.visitCount4 = visitCount4;
	}

	public int getVisitCount20() {
		return visitCount20;
	}

	public void setVisitCount20(int visitCount20) {
		this.visitCount20 = visitCount20;
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

	public void setMonth(String month) {
		this.month = month;
	}

	public MonthOdersData() {
		super();
	}

	public MonthOdersData(String regionId, int tAL15M1, int tAL15M3, int tAL10M1, int tAL10M3, int tAL7M1, int tAL7M3,
			int tAL4M1, int tAL4M3, int tAL20M1, int tAL20M3, String month) {
		super();
		this.regionId = regionId;
		tal15m1 = tAL15M1;
		tal15m3 = tAL15M3;
		tal10m1 = tAL10M1;
		tal10m3 = tAL10M3;
		tal7m1 = tAL7M1;
		tal7m3 = tAL7M3;
		tal4m1 = tAL4M1;
		tal4m3 = tAL4M3;
		tal20m1 = tAL20M1;
		tal20m3 = tAL20M3;
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
		this.tal15m1 = tAL15M1;
		this.tal15m3 = tAL15M3;
		this.tal10m1 = tAL10M1;
		this.tal10m3 = tAL10M3;
		this.tal7m1 = tAL7M1;
		this.tal7m3 = tAL7M3;
		this.tal4m1 = tAL4M1;
		this.tal4m3 = tAL4M3;
		this.tal20m1 = tAL20M1;
		this.tal20m3 = tAL20M3;
		this.visitCount15 = visitCount15;
		this.visitCount10 = visitCount10;
		this.visitCount7 = visitCount7;
		this.visitCount4 = visitCount4;
		this.visitCount20 = visitCount20;
		this.sysgive15 = sysgive15;
		this.sysgive10 = sysgive10;
		this.sysgive7 = sysgive7;
		this.sysgive4 = sysgive4;
		this.sysgive20 = sysgive20;
		this.salesman = salesman;
	}

}
