package com.example.remotelogging;

import com.example.bootiful.war.HelloStaticServletInitializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloStaticServletInitializer.class)
public class HelloApplicationTests{

    @Test
    public void contextLoads(){

    }
}
