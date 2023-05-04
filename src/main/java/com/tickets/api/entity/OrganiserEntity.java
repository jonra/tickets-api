package com.tickets.api.entity;

import com.tickets.api.enums.OrganiserType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrganiserEntity extends BaseEntity {
	@Column
	@NotNull String tenantId;
	@Column
	@NotNull String name;
	@Column
	String description;
	@Column
	String location;
	@Column
	Date startDate;
	@Column
	Date endDate;
	@Enumerated(EnumType.STRING)
	@NotNull OrganiserType type;

	@OneToMany(mappedBy = "organiser")
	List<EventEntity> events = new ArrayList<>();

	@OneToMany(mappedBy = "organiser", cascade = {jakarta.persistence.CascadeType.PERSIST, jakarta.persistence.CascadeType.MERGE})
	List<UserEntity> users = new ArrayList<>();

	@OneToMany(mappedBy = "organiser")
	List<ExtraEntity> extras = new ArrayList<>();


}
