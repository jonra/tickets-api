package com.tickets.api.entity;

import com.tickets.api.enums.TicketType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
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
public class TicketEntity extends BaseEntity {
	@Column
	@NotNull private String tenantId;
	private String name;
	@Column
	private String description;
	@Column
	private TicketType type;

	@ManyToOne
	private EventEntity event;


	@ManyToMany(mappedBy = "tickets", cascade = CascadeType.ALL)
	private List<ExtraEntity> extras = new ArrayList<>();

}
