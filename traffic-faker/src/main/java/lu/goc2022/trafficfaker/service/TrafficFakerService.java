package lu.goc2022.trafficfaker.service;

import com.github.javafaker.Faker;
import lu.goc2022.trafficfaker.annotation.LazyAutowired;
import lu.goc2022.trafficfaker.step.LoginSteps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TrafficFakerService {

    Logger logger = LoggerFactory.getLogger(TrafficFakerService.class);

    @Value( "${count}" )
    private int count;

    @LazyAutowired
    LoginSteps loginSteps;

    public void generateFakeTraffic(String url){

        logger.info(">>>>>> Let's nuke {} {} times!", url, count);

        Faker faker = new Faker();

        for (int i = 0; i < count; i++) {
            fillLoginForm(url, faker.internet().emailAddress(), faker.internet().password());
        }

    }

    private void fillLoginForm(String url, String email, String pwd){
        logger.info("     --> email : {} / pwd : {}", email, pwd);
        loginSteps
                .doALogin(url, email, pwd);
                //.thenIVerifyUserNameErrorMessages("Wrong Email address or Password. Please try again");
    }

}
