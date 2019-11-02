package org.algorithm.example.json;

public class Activity {
	private ActivityMetadata metadata;
	private String name;
	private Double distance;
	private Integer moving_time;
	private Integer elapsed_time;
	private Double total_elevation_gain;
	private String type;
	private Long id;

	public ActivityMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(ActivityMetadata metadata) {
		this.metadata = metadata;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Integer getMoving_time() {
		return moving_time;
	}

	public void setMoving_time(Integer moving_time) {
		this.moving_time = moving_time;
	}

	public Integer getElapsed_time() {
		return elapsed_time;
	}

	public void setElapsed_time(Integer elapsed_time) {
		this.elapsed_time = elapsed_time;
	}

	public Double getTotal_elevation_gain() {
		return total_elevation_gain;
	}

	public void setTotal_elevation_gain(Double total_elevation_gain) {
		this.total_elevation_gain = total_elevation_gain;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
