package com.tickets.api.model;

import com.tickets.api.entity.ExtraEntity;
import com.tickets.api.enums.ExtraType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data

public class ExtraRequest {
	private String name;
	private BigDecimal price;
	private ExtraType type;
	private Integer stock;
	private Integer maxSelect;

	public static ExtraEntity toEntity(ExtraRequest extraRequest, String tenantId) {
		if (extraRequest == null) {
			return null;
		}

		return ExtraEntity.builder()
				.name(extraRequest.getName())
				.price(extraRequest.getPrice())
				.type(extraRequest.getType())
				.tenantId(tenantId)
				.stock(extraRequest.getStock())
				.maxSelect(extraRequest.getMaxSelect())
				.price(extraRequest.getPrice())
				.build();
	}
}
