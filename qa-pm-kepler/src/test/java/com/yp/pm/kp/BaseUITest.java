package com.yp.pm.kp;

import com.yp.pm.kp.ui.LoginPage;
import com.yp.pm.kp.ui.MyAgencyPage;

public class BaseUITest extends SeleniumBaseTest {
    public String phase = (System.getProperty("phase") == null) ? "1" : System.getProperty("phase");
    public String user = (System.getProperty("user") == null) ? "user" : System.getProperty("user");
    public String pass = (System.getProperty("pass") == null) ? "pass" : System.getProperty("pass");
    public String version = (System.getProperty("version") == null) ? "4.9.113.1" : System.getProperty("version");


    protected MyAgencyPage login() throws Exception {
        LoginPage loginPage = new LoginPage(webDriver);
        return loginPage.authenticate();
    }

    protected MyAgencyPage loginRootReadOnly() throws Exception {
        LoginPage loginPage = new LoginPage(webDriver);
        return loginPage.authenticateReadOnly();
    }

    protected YPSearch openYPSearchHome() throws Exception {
        LoginPage loginPage = new LoginPage(webDriver);
        return loginPage.openSearch(System.getProperty("ui.ypSearch"));
    }

    protected YPSearch openYPSearch(String search, String loc) throws Exception {
        LoginPage loginPage = new LoginPage(webDriver);
        if(loc == null) loc = "Glendale%2C+CA";
        //.concat("&flash_app_id=bento-qa")
        return loginPage.openSearch(System.getProperty("ui.ypSearch").concat("/search?search_terms=").concat(search).concat("&geo_location_terms=").concat(loc));
    }

    protected MyAgencyPage login(String user) throws Exception {
        LoginPage loginPage = new LoginPage(webDriver);
        return loginPage.authenticate(user);
    }

    protected MyAgencyPage loginNode(String server) throws Exception {
        LoginPage loginPage = new LoginPage(webDriver);
        return loginPage.authenticateNode(server);
    }
}