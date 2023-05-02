package com.tickets.api.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(AdminController.PATH)
@Slf4j
@Tag(name = "Admin API", description = "The Admin API manages configuration and settings.")
public class AdminController {

	public static final String PATH = "v1/{tenant}/admin/";


}
