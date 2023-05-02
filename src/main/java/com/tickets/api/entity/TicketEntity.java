package com.tickets.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TicketEntity extends BaseEntity {
	@Column
	@NotNull private String tenantId;
	private String name;
	@Column
	private String description;

	@ManyToOne
	private EventEntity event;

//
//	@ManyToMany(mappedBy = "tickets", cascade = CascadeType.ALL)
//	private Set<ExtraEntity> extras = new HashSet<>();

}
