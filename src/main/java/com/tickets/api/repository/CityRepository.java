package com.tickets.api.repository;

import com.tickets.api.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CityRepository extends JpaRepository<CityEntity, UUID> {
	List<CityEntity> findAllByCountryId(UUID countryId);
}
