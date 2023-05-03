package com.tickets.api.entity;

import com.tickets.api.enums.EventType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity extends BaseEntity {
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
	@NotNull EventType type;

	@OneToMany(mappedBy = "event")
	List<TicketEntity> tickets = new ArrayList<>();

//	@OneToMany(mappedBy = "event")
//	List<ExtraEntity> extras = new ArrayList<>();
	@ManyToOne
	private OrganiserEntity organiser;
}
