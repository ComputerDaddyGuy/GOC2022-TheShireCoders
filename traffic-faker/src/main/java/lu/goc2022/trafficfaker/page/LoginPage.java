package lu.goc2022.trafficfaker.page;

import lu.goc2022.trafficfaker.annotation.ElapsedTime;
import lu.goc2022.trafficfaker.annotation.LazyComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static org.junit.jupiter.api.Assertions.assertEquals;

@LazyComponent
public class LoginPage extends BasePage {
    //********* Web Elements by using Page Factory *********
    @FindBy(how = How.ID, using = "email")
    public WebElement userName;
    @FindBy(how = How.ID, using = "password")
    public WebElement password;
    @FindBy(how = How.ID, using = "msg")
    public WebElement msg;
    //********* Web Elements by using By Class *********
    By loginButtonBy = By.id("submit-btn");

    By errorMessageUsernameBy = By.id("msg");

    //*********Page Methods*********

    public LoginPage goToLoginPage(String baseURL, String userName) {
        driver.get(baseURL + userName);
        return this;
    }

    @ElapsedTime
    public LoginPage doALogin(String password) {
        writeText(this.password, password);
        jsClick(loginButtonBy);
        return this;
    }



    @ElapsedTime
    public LoginPage verifyLoginUserNameErrorMessage(String expectedText) {
        try {
            wait(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(expectedText, readText(errorMessageUsernameBy));
        return this;
    }

    @ElapsedTime
    public LoginPage closeBrowser() {
        driver.quit();
        return this;
    }

    @Override public boolean isAt() {
        return this.wait.until((d) -> this.userName.isDisplayed());
    }
}