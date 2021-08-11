package tweet;

import base.RestAPI;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.core.Is;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class TweetAPIClient extends RestAPI {

private final String CREATE_TWEET_ENDPOINT="/statuses/update.json";
private final String DELETE_TWEET_ENDPOINT="/statuses/destroy.json";
private final String GET_TWEET_ENDPOINT="/statuses/home_timeline.json";

public ValidatableResponse createTweet(String tweet){

    return given().auth().oauth(this.apiKey,this.apiSecretKey,this.accessToken,this.accessTokenSecret).param("status",tweet)
            .when().post(this.baseUrl+this.CREATE_TWEET_ENDPOINT).then();
}

public ValidatableResponse getTweet(long tweetId){
    return given().auth().oauth(this.apiKey,this.apiSecretKey,this.accessToken,this.accessTokenSecret).param("id",tweetId).
            when().
            get("https://api.twitter.com/1.1/statuses/home_timeline.json")
            .then().statusCode(200);
}

public ValidatableResponse uploadPic(String pic){
    return given().auth().oauth(this.apiKey,this.apiSecretKey,this.accessToken,this.accessTokenSecret)
            .when().post("https://upload.twitter.com/1.1/media/upload.json").
                    then().statusCode(200);
}

public ValidatableResponse deleteTweet(Long tweetId){
    return  given().auth().oauth(this.apiKey,this.apiSecretKey,this.accessToken,this.accessTokenSecret)
            .param("Id",tweetId).when().post(this.baseUrl+DELETE_TWEET_ENDPOINT).then();
}

    public ValidatableResponse responseTime(){
        System.out.println(given().auth().oauth(this.apiKey,this.apiSecretKey, this.accessToken,this.accessTokenSecret)
                .when().get(this.baseUrl+this.GET_TWEET_ENDPOINT)
                .timeIn(TimeUnit.MILLISECONDS));
        return given().auth().oauth(this.apiKey,this.apiSecretKey, this.accessToken,this.accessTokenSecret)
                .when().get(this.baseUrl+this.GET_TWEET_ENDPOINT)
                .then();

    }

    // Header Value
    public Headers headerValue(){
        System.out.println(given().auth().oauth(this.apiKey,this.apiSecretKey, this.accessToken,this.accessTokenSecret)
                .when().get(this.baseUrl+this.GET_TWEET_ENDPOINT)
                .then().extract().headers());

        return given().auth().oauth(this.apiKey,this.apiSecretKey, this.accessToken,this.accessTokenSecret)
                .when().get(this.baseUrl+this.GET_TWEET_ENDPOINT)
                .then().extract().headers();



    }

    public  void checkProperty(){
        Response response= given().auth().oauth(this.apiKey,this.apiSecretKey, this.accessToken,this.accessTokenSecret)
                .when().get(this.baseUrl+this.GET_TWEET_ENDPOINT);
        JsonPath pathEvaluator= response.jsonPath();
        String createdAt=pathEvaluator.get("id");
        System.out.println(createdAt);
    }


}
