package ru.lanit.framework.steps;

import PageObjects.PageAuthForm;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.ru.*;
import io.cucumber.testng.CucumberOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.lanit.framework.webdriver.WebDriverManager;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;
@CucumberOptions(
        strict = true,
        features = {"src/test/resources"},
        //tags = "@AuthorizationValid",
        glue = {"ru.lanit.framework.steps"}
)

public class MyStepdefs {
    private WebDriver driver;
    private WebDriverWait waiter;
    private PageAuthForm page = new PageAuthForm();
    private final Logger log = LogManager.getLogger(getClass());

    @Before
    public void beforeScenario(Scenario scenario)
    {
        log.info("Запуск сценария " + scenario.getName());
        System.out.println("Запуск сценария " + scenario.getName());
        driver = WebDriverManager.getDriver();
        waiter = new WebDriverWait(driver, 10);
    }
    @After
    public void afterScenario(Scenario scenario) {
        log.info("Завершено выполнение сценария " + scenario.getName());
        WebDriverManager.quit();
        waiter = null;
    }

    @Пусть("я открыл браузер и ввел url {string}")
    public void openBrowserAndFollowLink(String url) {
        page.openPage(url);
    }

    @И("я нажал на {string}")
    public void clickLoginFormOpenButton(String name) {
        try {
            waiter.until(elementToBeClickable(page.authFormOpenButton));
            page.authFormOpenButton.click();
        } catch (TimeoutException e) {
            Assert.fail("Authorization button is not available " + e.getMessage());
        }
    }

    @Когда("появилось окно Авторизации")
    public void loginFormDisplayed() {
        try {
            waiter.until(ExpectedConditions.visibilityOf(page.authFormTitle));
        } catch (TimeoutException e) {
            Assert.fail("Authorization window is not displayed " + e.getMessage());
        }
    }

    @То("я ввел логин {string}")
    public void typeUsername(String username) {
        try {
            waiter.until(elementToBeClickable(page.authFormUsernameField));
            page.authFormUsernameField.click();
            page.authFormUsernameField.sendKeys(username);
        } catch  (TimeoutException e) {
            Assert.fail("Login field is not available for input " + e.getMessage());
        }
    }

    @И("я ввел пароль {string}")
    public void typePassword(String password) {
        try {
            waiter.until(elementToBeClickable(page.authFormPasswordField));
            page.authFormPasswordField.click();
            page.authFormPasswordField.sendKeys(password);
        } catch  (TimeoutException e) {
            Assert.fail("Password field is not available for input " + e.getMessage());
        }
    }

    @И("я нажал кнопку Входа")
    public void clickLoginSubmitButton() {
        try {
            waiter.until(elementToBeClickable(page.authFormSubmitButton));
            page.authFormSubmitButton.click();
        } catch  (TimeoutException e) {
            Assert.fail("Authorization button is not clickable " + e.getMessage());
        }
    }

    @Если("отобразилась пиктограмма активного пользователя")
    public void activeUserAvatarDisplayed() {
        try {
            waiter.until(visibilityOf(page.userAccountFormOpenLink));
        } catch  (TimeoutException e) {
            Assert.fail("Active user avatar is not displayed " + e.getMessage());
        }
    }

    @И("я нажал на пиктограмму активного пользователя")
    public void clickActiveUserAvatar() {
        try {
            waiter.until(elementToBeClickable(page.userAccountFormOpenLink));
            page.userAccountFormOpenLink.click();
        } catch  (TimeoutException e) {
            Assert.fail("Active user avatar is not clickable" + e.getMessage());
        }
    }

    @И("в заголовке личного кабинета отобразилось имя {string}")
    //@И("в заголовке личного кабинета отобразилось имя \"([^\"]*)\"$")
    public void activeUserTitleDisplayed(String username) {
        try {
            waiter.until(textToBePresentInElement(page.userAccountFormTitle,username));
        } catch  (TimeoutException e) {
            Assert.fail("Login of active user is not displayed in the profile title" + e.getMessage());
        }
    }

    @Тогда("я нажал на кнопку Выхода")
    public void exitActiveUserAccount() {
        try {
            waiter.until(elementToBeClickable(page.userAccountExitButton));
            page.userAccountExitButton.click();
        } catch  (TimeoutException e) {
            Assert.fail("Exit button it not clickable" + e.getMessage());
        }

        try {
            driver.switchTo().alert().accept();
        } catch (NoAlertPresentException e) {
            log.error("No expected Alert",e);
        }
    }

}
