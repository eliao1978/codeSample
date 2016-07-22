package com.yp;

import com.yp.enums.other.UserEnum;
import com.yp.pm.kp.security.AuthSecurityUser;
import com.yp.pm.kp.security.RoleEnum;
import com.yp.util.LogUtil;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

public class BaseTest {

    protected static Properties config = new Properties(System.getProperties());
    protected Logger logger = new LogUtil().getLogger();

    // for Kepler
    protected long agencyId = 142;
    protected String timezone = "America/Los_Angeles";
    protected long budgetAmount = 100;
    protected String deliveryMethod = "standard";

    @BeforeClass
    public static void start() throws IOException {
        String env = (System.getProperty("env") == null) ? "qa1" : System.getProperty("env");
        config.load(new FileInputStream("src/test/resources/config/".concat(env).concat(".properties")));
        System.setProperties(config);

        setSecurityContextUsername();
    }

    @AfterClass
    public static void tearDown() {
        config.clear();
    }

    private static void setSecurityContextUsername() {
        AuthSecurityUser user = new AuthSecurityUser();
        user.setName(UserEnum.KP_SUPER_ADMIN.getValue());
        user.setRequestId(UUID.randomUUID().toString());
        user.addAuthority(RoleEnum.ROLE_SUPER_ADMIN.name());
        PreAuthenticatedAuthenticationToken authToken = new PreAuthenticatedAuthenticationToken(user, null, user.getAuthorities());
        authToken.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}