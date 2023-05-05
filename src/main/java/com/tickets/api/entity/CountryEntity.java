package com.tickets.api.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CountryEntity extends BaseEntity {

	@Column
	String name;

	@Column
	String isoAlpha2Code;

	@Column
	String timezone;

	@Column
	String phoneCode;

	@OneToMany(mappedBy = "country", orphanRemoval = true, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	List<CityEntity> cities = new ArrayList<>();

}
