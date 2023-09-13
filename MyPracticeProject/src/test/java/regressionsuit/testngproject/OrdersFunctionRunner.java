package regressionsuit.testngproject;

import com.unitedcoder.configutility.ApplicationConfig;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import regressionsuit.pageobjectmodel.*;

import java.util.List;

public class OrdersFunctionRunner extends TestBaseForTestNG{
    String userName= ApplicationConfig.readConfigProperties("config.properties","username");
    String password=ApplicationConfig.readConfigProperties("config.properties","password");
    DataBase testData;
    LoginPage loginPage;
    DashboardPage dashboardPage;
    OrdersPage ordersPage;
    @BeforeClass
    public void setUp(){
        openBrowser();
        loginPage=new LoginPage(driver);
        loginPage.login(userName,password);
        dashboardPage=new DashboardPage(driver);
        dashboardPage.verifyDashboardPage();
        testData=new DataBase();
        ordersPage=new OrdersPage(driver);
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
    @Test
    public void viewOrdersTest(){
        dashboardPage.clickOnOrders();
        Assert.assertTrue(ordersPage.viewOrders());
    }
    @Test(dependsOnMethods = "createOrderTest")
    public void editOrderTest(){
        dashboardPage.clickOnOrders();
        Assert.assertTrue(ordersPage.editOrderFromEditIcon());
    }
    @Test(dependsOnMethods = "createOrderTest")
    public void deleteOrderTest(){
        dashboardPage.clickOnOrders();
        Assert.assertTrue(ordersPage.deleteOrder());
    }
    @AfterClass
    public void tearDown(){
        closeBrowser();
    }
    @DataProvider
    public Object[][] orderData(){
        return new Object[][]{
                {testData.email,testData.dispatchDate(),testData.shippingMethod(),
                        testData.shippingDate(),testData.trackingInfo(),testData.productWeight,testData.orderQuantity,
                        testData.productName,testData.discountAmount,testData.shippingCost,testData.taxAmount,testData.internalNote,
                        testData.publicNote}

        };
    }
}
