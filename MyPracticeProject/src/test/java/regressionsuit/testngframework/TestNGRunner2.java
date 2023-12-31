package regressionsuit.testngframework;

import com.unitedcoder.configutility.ApplicationConfig;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import regressionsuit.pageobjectmodel.*;
import regressionsuit.testngproject.TestData;
import regressionsuit.testngproject.TestBaseForTestNG;

import java.util.List;
public class TestNGRunner2 extends TestBaseForTestNG {
    LoginPage loginPage;
    DashboardPage dashboardPage;
    ProductPage productPage;
    CustomerPage customerPage;
    TestData testData;
    OrdersPage ordersPage;
    String userName= ApplicationConfig.readConfigProperties("config.properties","username");
    String password=ApplicationConfig.readConfigProperties("config.properties","password");
    @BeforeClass
    public void setUp(){
        testData=new TestData();
        if (testData.headlessMode==1){
            setUpBrowserInHeadlessMode();
        }else {
            openBrowser();
        }
        loginPage=new LoginPage(driver);
        loginPage.login(userName,password);
        dashboardPage=new DashboardPage(driver);
        Assert.assertTrue(dashboardPage.verifyLogin());
        productPage=new ProductPage(driver);
        customerPage=new CustomerPage(driver);
        ordersPage=new OrdersPage(driver);
    }
    @Test
    public void viewProduct(){
        dashboardPage.clickOnProductsLink();
        Assert.assertTrue(productPage.viewProductList());

    }
    @Test
    public void addCustomer(){
        dashboardPage.clickCustomerList();
        customerPage.addCustomer();
        Assert.assertTrue(customerPage.verifyAddCustomerSuccessfully());

    }
    @Test(dataProvider = "orderData")
    public void createOrderTest(String customerEmail, String dispatchDate, String shippingMethod, String shippingDate,
                                List<String> trackingInfo, double weight, int quantity, String productName,
                                double discountAmount, double shippingCost, double taxAmount, String internalNotes, String publicNotes){
        dashboardPage.clickOnOrders();
        ordersPage.createOrder(customerEmail,dispatchDate,shippingMethod,shippingDate,trackingInfo,weight,quantity,productName,
                discountAmount,shippingCost,taxAmount,internalNotes,publicNotes);
        Assert.assertTrue(ordersPage.verifyCreateOrderSuccessful());
    }
    @Test(dependsOnMethods = "createOrderTest")
    public void searchOrder(){
        dashboardPage.clickOnOrders();
        ordersPage.searchOrder();
        Assert.assertTrue(ordersPage.verifySearchOrder());
    }
    @DataProvider
    public Object[][] orderData(){
        return new Object[][]{
                {"dione.kessler@hotmail.com",testData.dispatchDate(),testData.shippingMethod(),
                        testData.shippingDate(),testData.trackingInfo(),100.54,testData.orderQuantity,
                        "Magic Hair Brush",testData.discountAmount,testData.shippingCost,testData.taxAmount,testData.internalNote,
                        testData.publicNote}

        };
    }
    @AfterClass
    public void tearDown(){
        dashboardPage.clickLogOut();
        closeBrowser();
    }
}
