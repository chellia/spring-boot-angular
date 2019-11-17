package com.dev.tech.skills.app.resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED, reason = "More than one firstname exists")
public class DuplicateFirstNameException extends RuntimeException{
}
