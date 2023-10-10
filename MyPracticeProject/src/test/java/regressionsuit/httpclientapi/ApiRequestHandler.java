package regressionsuit.httpclientapi;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ApiRequestHandler {
    //get all customers
    public ApiResponseHandler getAllCustomer(String userName,String password,String url,int port,String endPoint){
            ApiResponseHandler responseHandler=new ApiResponseHandler();
            AuthenticationProvider authenticationProvider= new AuthenticationProvider();
        StopWatch stopWatch=new StopWatch();
        System.err.println(stopWatch);
        CloseableHttpClient httpClient= HttpClientBuilder.create()
                .setDefaultCredentialsProvider(authenticationProvider.getCredentials(userName,password))
                .build();

        //create get request
        HttpGet httpGet=new HttpGet(url+":"+port+"/"+endPoint);
        //get the response;
        CloseableHttpResponse response= null;
        try {
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 0) {
                responseHandler.setResponseCode(statusCode);
                responseHandler.setResponseTime(stopWatch.getTime(TimeUnit.MILLISECONDS));
            }
            //response Body
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String responseContent = EntityUtils.toString(entity);
                System.out.println(responseContent);
                responseHandler.setResponseContent(responseContent);
            }
        }catch (IOException e) {
                throw new RuntimeException(e);
            }
        return responseHandler;
    }
}
