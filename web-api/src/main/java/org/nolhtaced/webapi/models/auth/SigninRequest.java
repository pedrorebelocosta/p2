package org.nolhtaced.webapi.models.auth;

public record SigninRequest(
        String username,
        String password
) { }
