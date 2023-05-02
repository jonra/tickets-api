package com.tickets.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CityEntity extends BaseEntity {

	@Column
	String name;

	@Column
	String timezone;

	@Column
	Double latitude;

	@Column
	Double longitude;

	@ManyToOne
	private CountryEntity country;
}
