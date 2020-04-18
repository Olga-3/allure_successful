package PageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.lanit.framework.webdriver.WebDriverManager;

public class PageAuthForm {
    private final Logger log = LogManager.getLogger(getClass());
    private WebDriver driver;

    public PageAuthForm() {
        driver = WebDriverManager.getDriver();
        PageFactory.initElements(driver, this);
    }

    public void openPage(String url) {
        driver.get(url);
        log.trace("Выполнен вход на страницу: " + url);
        //System.out.println("Выполнен вход на страницу: " + url);
    }

    //FindBy нужна вместо обращения к вебдрайверу для поиска веб элемента
    @FindBy(xpath = "//button[text()='Войти']")
    public WebElement authFormOpenButton;

    @FindBy(xpath = "//h4[text()='Войти']")
    public WebElement authFormTitle;

    @FindBy(xpath = "//input[@id='id_username']")
    public WebElement authFormUsernameField;

    @FindBy(xpath = "//input[@id='id_password']")
    public WebElement authFormPasswordField;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement authFormSubmitButton;

//    @FindBy(xpath = "//a[contains(.,'Забыли пароль')]")
//    public WebElement authFormForgottenPswdLink;

//    @FindBy(xpath = "//p[text()='Логин или пароль неправильны.']")
//    public WebElement authFormIncorrectCredentialsAlert ;

//    @FindBy(xpath = "//p[text()='Заполните оба поля.']")
//    public WebElement authFormEmptyCredentialsAlert;

    @FindBy(xpath = "//li[@class='dropdown']/a[@role='button']")
    public WebElement userAccountFormOpenLink;

    @FindBy(xpath = "//li[@class='dropdown-header']/strong")
    public WebElement userAccountFormTitle;

    @FindBy(xpath = "//button[text()='Выход']")
    public WebElement userAccountExitButton;
}