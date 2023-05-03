package com.tickets.api.model;

import com.tickets.api.entity.ExtraEntity;
import com.tickets.api.enums.ExtraType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data

public class ExtraRequest {
	String name;
	BigDecimal price;
	ExtraType type;

	public static ExtraEntity toEntity(ExtraRequest extraRequest, String tenantId) {
		if (extraRequest == null) {
			return null;
		}

		return ExtraEntity.builder()
				.name(extraRequest.getName())
				.price(extraRequest.getPrice())
				.type(extraRequest.getType())
				.tenantId(tenantId)
				.build();
	}
}
