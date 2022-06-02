package com.nowcoder.community;

import com.nowcoder.community.dao.AlphaDao;
import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.AlphaService;
import com.nowcoder.community.util.MailClient;
import com.nowcoder.community.util.SensitiverFilter;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class TransactionalTests {
    @Autowired
    private AlphaDao alphaDao;
    @Autowired
    private AlphaService alphaService;



    @Test
    public void testThing(){
        Object obj = alphaService.save1();
        System.out.println(obj.toString());
    }

    @Test
    public void testThing2(){
        Object obj = alphaService.save2();
        System.out.println(obj.toString());
    }
}
