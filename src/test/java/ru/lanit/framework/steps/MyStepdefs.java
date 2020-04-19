package ru.lanit.framework.steps;

import Helpers.ConfigReader;
import PageObjects.PageAuthForm;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.ru.*;
import io.cucumber.testng.CucumberOptions;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import ru.lanit.framework.webdriver.WebDriverManager;
import org.openqa.selenium.WebElement;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;
//@CucumberOptions(
//        strict = true,
//        features = {"src/test/resources"},
//        //tags = "@AuthorizationValid",
//        glue = {"ru.lanit.framework.steps"}
//)

public class MyStepdefs {
    private WebDriver driver;
    private WebDriverWait waiter;
    private PageAuthForm page = new PageAuthForm();
    private final Logger log = LogManager.getLogger(getClass());

   // protected final Wait<Webdriver> wait = new

//    public void addScreenshot(String title) {
//        try {
//            Allure.addAttachment(title, "image/png", FileUtils.openInputStream(page.getScreenshot()), ".png");
//            log.debug("Сделан скриншот на шаге {}", title);
//        } catch (IOException e) {
//            log.error("Ошибка записи скриншота: {}", e.getMessage());
//        }
//    }

    @Before
    public void beforeScenario(Scenario scenario)
    {

        //Reporter.log("Запуск сценария " + scenario.getName());
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
        Allure.addAttachment("Start page is opened", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    @И("я нажал на кнопку вызова формы авторизации")
    //public void clickLoginFormOpenButton(String name) {
    public void clickLoginFormOpenButton() {
        try {
            waiter.until(elementToBeClickable(page.authFormOpenButton));
            page.authFormOpenButton.click();
        } catch (TimeoutException e) {
            Assert.fail("Authorization button is not available " + e.getMessage());
        }
        Allure.addAttachment("Login button", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    @Когда("появилось окно Авторизации")
    public void loginFormDisplayed() {
        try {
            waiter.until(ExpectedConditions.visibilityOf(page.authFormTitle));
        } catch (TimeoutException e) {
            Assert.fail("Authorization window is not displayed " + e.getMessage());
        }
        Allure.addAttachment("Authorization window", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
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
        Allure.addAttachment("Login input", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
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
        Allure.addAttachment("Password input", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    @И("я нажал кнопку Входа")
    public void clickLoginSubmitButton() {
        try {
            waiter.until(elementToBeClickable(page.authFormSubmitButton));
            page.authFormSubmitButton.click();
        } catch  (TimeoutException e) {
            Assert.fail("Authorization button is not clickable " + e.getMessage());
        }
        Allure.addAttachment("Authorization", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    @Если("отобразилась пиктограмма активного пользователя")
    public void activeUserAvatarDisplayed() {
        try {
            waiter.until(visibilityOf(page.userAccountFormOpenLink));
        } catch  (TimeoutException e) {
            Assert.fail("Active user avatar is not displayed " + e.getMessage());
        }
        Allure.addAttachment("Checking user avatar", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    @И("я нажал на пиктограмму активного пользователя")
    public void clickActiveUserAvatar() {
        try {
            waiter.until(elementToBeClickable(page.userAccountFormOpenLink));
            page.userAccountFormOpenLink.click();
        } catch  (TimeoutException e) {
            Assert.fail("Active user avatar is not clickable" + e.getMessage());
        }
        Allure.addAttachment("Clicking user avatar", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    @И("в заголовке личного кабинета отобразилось имя {string}")
    //@И("в заголовке личного кабинета отобразилось имя \"([^\"]*)\"$")
    public void activeUserTitleDisplayed(String username) {
        try {
            waiter.until(textToBePresentInElement(page.userAccountFormTitle,username));
        } catch  (TimeoutException e) {
            Assert.fail("Login of active user is not displayed in the profile title" + e.getMessage());
        }
        Allure.addAttachment("Login display", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
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
        Allure.addAttachment("Exit profile", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    //тут реализация шагов сценария HelloWorld

    @Пусть("я открыл браузер и ввел адрес сайта")
    public void openedBrowserAndEnteredUrl() throws IOException{
        page.openPage(ConfigReader.getStringSystemProperty("url"));
    }

    // в этом шаге проблемный метод get
//    @И("я ввел поисковый запрос в \"(.*)\"$")
//    public void enteredText(String string) throws InterruptedException{
//        WebElement webElement = page.get(string);
//        waiter.until(ExpectedConditions.visibilityOf(webElement));
//        webElement.click();
//        try {
//            webElement.sendKeys(ConfigReader.getStringSystemProperty("searchWord"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    @И("я нажал кнопку \"(.*)\"$")
    public void pressedButton() {
    }
}
