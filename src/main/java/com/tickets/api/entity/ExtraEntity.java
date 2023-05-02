package com.tickets.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ExtraEntity extends BaseEntity {

	@Column
	String name;


//	@ManyToOne
//	private TicketEntity organiser;

//	@ManyToMany(cascade = CascadeType.ALL)
//	@JoinTable(
//			name = "ticket_extra",
//			joinColumns = @JoinColumn(name = "extra_id"),
//			inverseJoinColumns = @JoinColumn(name = "ticket_id")
//	)
//	private Set<TicketEntity> tickets = new HashSet<>();
}
