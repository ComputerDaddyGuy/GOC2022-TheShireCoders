package lu.goc2022.trafficfaker.step;

import lu.goc2022.trafficfaker.annotation.LazyAutowired;
import lu.goc2022.trafficfaker.annotation.LazyComponent;
import lu.goc2022.trafficfaker.page.LoginPage;
import org.springframework.beans.factory.annotation.Value;

@LazyComponent
public class LoginSteps {
    @Value("${browser}")
    private String browser;

    @LazyAutowired
    LoginPage loginPage;


    public LoginSteps doALogin(String url, String userName, String password) {
        loginPage
                .goToLoginPage(url, userName)
                .doALogin(password)
                .closeBrowser();
        return this;
    }

}