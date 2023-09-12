package regressionsuit.testngproject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.DataProvider;
import regressionsuit.pageobjectmodel.FunctionLibrary;
import regressionsuit.testngframework.ScreenShotUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NewslettersPage {
    WebDriver driver;
    FunctionLibrary functionLibrary;
    ScreenShotUtility screenShotUtility;

    public NewslettersPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
        functionLibrary=new FunctionLibrary(driver);
        screenShotUtility=new ScreenShotUtility();
    }

    @FindBy(linkText = "Create Newsletter")
    WebElement createNewsLetterLink;
    @FindBy(id = "email_subject")
    WebElement newsLetterSubjectField;
    @FindBy(id = "sender_name")
    WebElement senderNameField;
    @FindBy(id = "sender_email")
    WebElement senderEmailField;
    @FindBy (id = "template_id")
    WebElement templateField;
    @FindBy(linkText = "HTML Content")
    WebElement htmlContentLink;
    @FindBy(css = "iframe[aria-describedby*='cke_76']")
    WebElement iframe;
    @FindBy(css = "body[class*='_editable']")
    WebElement htmlContentField;
    @FindBy(linkText = "Plain Text Content")
    WebElement plainTextContentLink;
    @FindBy(id = "content_text")
    WebElement textContentField;
    @FindBy(linkText = "Send Test Email")
    WebElement sendTestEmailLink;
    @FindBy(id = "email_test")
    WebElement recipientEmailField;
    @FindBy(xpath = "//input[@type='submit' and @value='Save']")
    WebElement saveButton;
    @FindBy(css = ".tiny")
    WebElement saveAndSendButton;
    @FindAll(
            @FindBy(xpath = "//div[@id='gui_message']/div[@class='success']")
    )
    List<WebElement> successMessage;
    public void selectTemplate(){
        Select select=new Select(templateField);
        List<String> options=new ArrayList<>(Arrays.asList(DataBase.template.NONE.getValue(),
                DataBase.template.DEFAULT_NEWSLETTER.getValue(),DataBase.template.DEFAULT_EMAILS.getValue()));
        Random random=new Random();
        String toBeSelected= options.get(random.nextInt(options.size()));
        select.selectByVisibleText(toBeSelected);
    }
    public void createNewsLetter(String newsletterSubject,String senderName,String senderEmail,
                                 String htmlContent, String plainTextContent, String recipientEmail){
        functionLibrary.waitForElementPresent(createNewsLetterLink);
        createNewsLetterLink.click();
        newsLetterSubjectField.sendKeys(newsletterSubject);
        senderNameField.sendKeys(senderName);
        senderEmailField.sendKeys(senderEmail);
        functionLibrary.sleep();
        selectTemplate();
        htmlContentLink.click();
        functionLibrary.sleep();
        driver.switchTo().frame(iframe);
        Actions actions=new Actions(driver);
        actions.sendKeys(htmlContentField,htmlContent).perform();
        driver.switchTo().defaultContent();
        functionLibrary.sleep();
        plainTextContentLink.click();
        functionLibrary.waitForElementPresent(textContentField);
        textContentField.sendKeys(plainTextContent);
        functionLibrary.sleep();
        sendTestEmailLink.click();
        functionLibrary.waitForElementPresent(recipientEmailField);
        recipientEmailField.sendKeys(recipientEmail);
        saveAndSendButton.click();
        System.out.println("Newsletter subject is: "+newsletterSubject);
        screenShotUtility.takeScreenShot("createNewsletterTest",driver);
    }

    public boolean verifyNewsletterCreated(){
        return successMessage.size()>=1;
    }
}