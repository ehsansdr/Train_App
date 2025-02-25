package com.example.trainproject.base.Controller;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;

@SecurityScheme(type = SecuritySchemeType.APIKEY, name = "api-key", in = SecuritySchemeIn.HEADER)
public class baseController {
}
