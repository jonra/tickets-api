package com.tickets.api.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data

public class OrganiserUserRequest {
	String userId;
	String organiserId;
}
