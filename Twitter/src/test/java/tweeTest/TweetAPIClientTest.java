package tweeTest;

import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBodyExtractionOptions;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tweet.TweetAPIClient;

import java.util.UUID;

public class TweetAPIClientTest {


TweetAPIClient tweetAPIClient;

    @BeforeClass
    public void setUpTweetAPI() {
        this.tweetAPIClient = new TweetAPIClient();
    }
@Test(enabled = true)
    public void testUserCanTweet(){
    String tweet="this is the first tweet using RestAPI 5" ;

    ValidatableResponse response=this.tweetAPIClient.createTweet(tweet);

System.out.println(response.extract().asPrettyString());
response.statusCode(200);

}
@Test()
public void testUserCanUploadPic(){
String pic="..\\Screenshot 2021-07-25 23.36.31.png";
ValidatableResponse response=this.tweetAPIClient.uploadPic(pic);
String result=response.extract().asPrettyString().toString();
System.out.println(result);
}

@Test(enabled = false)
    public void deleteUserTweet() {
    String tweet = "this is the first tweet using RestAPIfe34f6b8-3a1a-4a47-949c-6e287aaa18db";
    ValidatableResponse deleteResponse = this.tweetAPIClient.deleteTweet(1422687023211683841l);
    System.out.println(deleteResponse.extract().asPrettyString());
    //deleteResponse.statusCode(404);
    String actualTweet = deleteResponse.extract().body().path("errors[0].message");
    Assert.assertEquals(tweet, actualTweet);
    //Assert.assertNotEquals("404",200);
}

@Test
public void checkTweetExistence(){
    String tweet="1423003975599673353";
    ValidatableResponse response = this.tweetAPIClient.getTweet(1423003975599673353l);
    //System.out.println(response.extract().asPrettyString());
    String actualTweet=response.extract().body().path("status");
    System.out.println(actualTweet);

   Assert.assertEquals(actualTweet,tweet,"not found");
    }





   @Test
           public void testRespondTime(){
     ValidatableResponse response=this.tweetAPIClient.responseTime();

    }

    @Test(enabled = true)
    public void testHeaderValue(){
        Headers response=this.tweetAPIClient.headerValue();
    }

    @Test(enabled = true)
    public void testPropertyFromResponse() {
        //1. User send a tweet
        String tweet = "Try Test Properties from response" + UUID.randomUUID().toString();
        ValidatableResponse response = this.tweetAPIClient.createTweet(tweet);
        //2. Verify that the tweet was successful
         response.statusCode(200);

        this.tweetAPIClient.checkProperty();
       // JsonPath pathEvaluator=response;
        System.out.println(response.extract().body().asPrettyString());
        System.out.println(response.extract().body().asPrettyString().contains("id"));

        String actualTweet = response.extract().body().path("text");
        Assert.assertEquals(actualTweet, tweet, "Tweet is not match");
    }







}
