package org.nolhtaced.core.utilities;

public class NolhtacedRegex {
    public static final String ENTITY_NAME_PATTERN = "^[0-9A-Za-z]{5,32}$";
    public static final String PASSWORD_REQUIREMENTS_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
}
