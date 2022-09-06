package com.web.curation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.controller.dto.HealthCheckResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = { "*" })
@RequestMapping("/health")
@RestController
public class HealthCheckController {
	
	@GetMapping("/check")
	@ApiOperation(value = "헬스체크")
	public ResponseEntity<Object> healthCheck(){
		log.info("[Get] /health/check");
		
		return new ResponseEntity<>(
				HealthCheckResponseDto.builder()
				.health("정상~")
				.count(1)
				.build()
				, HttpStatus.OK);
	}
}
