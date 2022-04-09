package com.nowcoder.community;
import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.User;
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
public class SensitiveTests {
    @Autowired
    private SensitiverFilter sensitiverFilter;
    @Test
    public void testSensitiverFilter(){
        String text = "这里可以赌^博，可以*嫖#娼，可以嫖色";
        text = sensitiverFilter.filter(text);
        System.out.println(text);
    }
}
