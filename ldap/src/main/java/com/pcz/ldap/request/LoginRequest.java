package com.pcz.ldap.request;

import lombok.Builder;
import lombok.Data;

/**
 * @author picongzhi
 */
@Data
@Builder
public class LoginRequest {
    private String username;
    private String password;
}
