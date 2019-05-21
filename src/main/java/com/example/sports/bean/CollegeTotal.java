package com.example.sports.bean;
/**
  * 学院表所对应实体类
 * @author Misaki
 *
 */
public class CollegeTotal {
	private Integer id;
	private Integer collegeId;
	private Double total;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(Integer collegeId) {
		this.collegeId = collegeId;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
}
