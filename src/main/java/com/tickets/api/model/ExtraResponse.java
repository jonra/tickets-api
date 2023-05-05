package com.tickets.api.model;

import com.tickets.api.entity.ExtraEntity;
import com.tickets.api.enums.ExtraType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ExtraResponse {
	private String id;
	private String name;
	private BigDecimal price;
	private ExtraType type;
	private Integer stock;
	private Integer maxSelect;
	public static List<ExtraResponse> fromEntity(List<ExtraEntity> entities) {
		if (entities == null) {
			return null;
		}

		return entities.stream()
				.map(ExtraResponse::fromEntity)
				.collect(java.util.stream.Collectors.toList());

	}

	public static ExtraResponse fromEntity(ExtraEntity entity) {
		if (entity == null) {
			return null;
		}
		return ExtraResponse.builder()
				.id(String.valueOf(entity.getId()))
				.name(entity.getName())
				.type(entity.getType())
				.price(entity.getPrice())
				.stock(entity.getStock())
				.maxSelect(entity.getMaxSelect())
				.build();
	}
}
