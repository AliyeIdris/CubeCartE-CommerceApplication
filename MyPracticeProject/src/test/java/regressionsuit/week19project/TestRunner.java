package regressionsuit.week19project;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import regressionsuit.pageobjectmodel.DashboardPage;
import regressionsuit.pageobjectmodel.LoginPage;
import regressionsuit.testngproject.TestData;
import regressionsuit.testngproject.TestBaseForTestNG;

public class TestRunner extends TestBaseForTestNG {
    LoginPage loginPage;
    DashboardPage dashboardPage;
    TestData testData;
    @BeforeClass
    public void setUp(){
        testData =new TestData();
        if (testData.headlessMode==1){
            setUpBrowserInHeadlessMode();
        }else {
            openBrowser();
        }
        loginPage=new LoginPage(driver);
        loginPage.login(testData.userName, testData.userPassword);
        dashboardPage=new DashboardPage(driver);
    }
    @Test
    public void verifyCustomerListPage(){
       VerifyCustomerListPage verifyCustomerListPage =new VerifyCustomerListPage(driver);
       dashboardPage.clickCustomerList();
      Assert.assertTrue(verifyCustomerListPage.verifyCustomerListPage());
    }
    @Test
    public void verifyOrdersPage(){
        VerifyOrdersPage verifyOrdersPage=new VerifyOrdersPage(driver);
        dashboardPage.clickOnOrders();
        Assert.assertTrue(verifyOrdersPage.verifyOrdersPage());
    }
    @Test
    public void verifyProductsPage(){
        VerifyProductsPage verifyProductsPage=new VerifyProductsPage(driver);
        dashboardPage.clickOnProductsLink();
        Assert.assertTrue(verifyProductsPage.verifyProductPage());
    }
    @Test
    public void verifyEmailLog(){
        VerifyEmailLogPage emailLogPage=new VerifyEmailLogPage(driver);
        dashboardPage.clickOnEmailLogLink();
        Assert.assertTrue(emailLogPage.verifyEmailLogPage());
    }
    @AfterClass
    public void tearDown(){
        dashboardPage.clickLogOut();
        closeBrowser();
    }

}
