package com.tickets.api.entity;

import com.tickets.api.enums.ExtraType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ExtraEntity extends BaseEntity {

	@Column
	String name;
	@Column
	BigDecimal price;
	@Column
	ExtraType type;
	@Column
	String tenantId;

	@ManyToOne
	private EventEntity event;

	@ManyToOne
	private OrganiserEntity organiser;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "ticket_extra",
			joinColumns = @JoinColumn(name = "extra_id"),
			inverseJoinColumns = @JoinColumn(name = "ticket_id")
	)
	private Set<TicketEntity> tickets = new HashSet<>();
}
