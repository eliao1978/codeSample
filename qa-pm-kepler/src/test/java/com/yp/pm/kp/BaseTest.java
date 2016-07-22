package com.yp.pm.kp;

import com.yp.enums.other.UserEnum;
import com.yp.pm.kp.security.AuthSecurityUser;
import com.yp.pm.kp.security.RoleEnum;
import com.yp.util.DBUtil;
import com.yp.util.LogUtil;
import org.apache.log4j.Logger;
import org.junit.*;
import org.junit.rules.TestName;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import static org.junit.Assert.*;

public class BaseTest {

    protected static Properties config = new Properties(System.getProperties());
    protected Logger logger = new LogUtil().getLogger();
    protected final long agencyId = 142;
    protected final long budgetAmount = 100;
    protected final String deliveryMethod = "STANDARD";

    @Rule
    public TestName testName = new TestName();

    @BeforeClass
    public static void setup() throws IOException {
        String env = (System.getProperty("env") == null) ? "qa1" : System.getProperty("env");
        String os = (System.getProperty("os") == null) ? "mac" : System.getProperty("os");
        config.load(new FileInputStream("src/test/resources/config/".concat(env).concat(".properties")));

        // selenium driver
        if (System.getProperty("driver") != null) {
            config.setProperty("selenium.driver", System.getProperty("driver"));
        }

        // selenium page load, implicitly, and explicitly wait time
        if (System.getProperty("pageLoad") != null) {
            config.setProperty("selenium.page.load.time", System.getProperty("pageLoad"));
        }

        if (System.getProperty("implicitly") != null) {
            config.setProperty("selenium.implicitly.wait.time", System.getProperty("implicitly"));
        }

        if (System.getProperty("explicitly") != null) {
            config.setProperty("selenium.explicitly.wait.time", System.getProperty("explicitly"));
        }

        // selenium remote
        if (System.getProperty("mode") != null && System.getProperty("mode").equalsIgnoreCase("remote")) {
            config.setProperty("selenium.remote", "true");

            if (os.equalsIgnoreCase("windows")) {
                config.setProperty("selenium.remote.host", "XXX");
            }

            if (os.equalsIgnoreCase("mac")) {
                config.setProperty("selenium.remote.host", "XXX");
            }
        }
        System.setProperties(config);

        setSecurityContextUsername();
    }

    @AfterClass
    public static void tearDown() {
        config.clear();
    }

    @Before
    public void startTest() throws Exception {
        // for console log
        logger.info("===== EXECUTION START ========");
        logger.info(this.getClass().getName() + "." + testName.getMethodName());
        logger.info("");
    }

    @After
    public void endTest() throws Exception {
        // for console log
        logger.info("===== EXECUTION COMPLETE =====");
        logger.info("");
    }

    protected void assertObjectNull(Object object) {
        assertNull("Object should be null", object);
    }

    protected void assertObjectNotNull(Object object) {
        assertNotNull("Object shouldn't be null", object);
    }

    protected void assertObjectEqual(boolean actual, boolean expect) {
        assertTrue("Expect [" + expect + "] but received [" + actual + "]", expect == actual);
    }

    protected void assertObjectEqual(String actual, String expect) {
        assertTrue("Expect [" + expect + "] but received [" + actual + "]", expect.equalsIgnoreCase(actual));
    }

    protected void assertObjectEqual(long actual, long expect) {
        assertTrue("Expect [" + expect + "] but received [" + actual + "]", expect == actual);
    }

    protected void assertObjectEqual(Double actual, Double expect) {
        assertTrue("Expect [" + expect + "] but received [" + actual + "]", expect.equals(actual));
    }

    protected void assertConditionTrue(boolean condition) {
        assertConditionTrue("Condition is not true", condition);
    }

    protected void assertConditionTrue(String message, boolean condition) {
        assertTrue(message, condition);
    }

    protected void assertConditionFalse(boolean condition) {
        assertFalse(condition);
    }

    private static void setSecurityContextUsername() {
        AuthSecurityUser user = new AuthSecurityUser();
        user.setName(UserEnum.KP_SUPER_ADMIN.getValue());
        user.setRequestId(UUID.randomUUID().toString());
        user.addAuthority(RoleEnum.ROLE_SUPER_ADMIN.name());

        PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(user, null, user.getAuthorities());
        token.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(token);
    }
}