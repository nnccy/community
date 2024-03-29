package com.nowcoder.community;
import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.LoginTicketMapper;
import com.nowcoder.community.dao.MessageMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.Message;
import com.nowcoder.community.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import java.util.Date;
import java.util.List;


@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTest{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    DiscussPostMapper discussPostMapper;
    @Autowired
    LoginTicketMapper loginTicketMapper;
    @Autowired
    MessageMapper messageMapper;

    @Test
    public void testSelectUser(){
        User user = userMapper.selectById(101);
        System.out.println(user);
        User user2 = userMapper.selectByName("liubei");
        System.out.println(user2);
        User user3 = userMapper.selectByEmail("nowcoder101@sina.com");
        System.out.println(user3);
    }
    @Test
    public void testInsertUser(){
        User user = new User();
        user.setUsername("test");
        user.setPassword("123");
        user.setSalt("abc");
        user.setEmail("test@qq.com");
        user.setHeaderUrl("http://hh.com");
        user.setCreateTime(new Date());

        int rows = userMapper.insertUser(user);
        System.out.println(rows);
        System.out.println(user.getId());
    }
    @Test
    public void updateUser(){
        int rows = userMapper.updateHeader(150,"xixi.com");
        System.out.println(rows);
        rows = userMapper.updatePassword(150,"4456");
        System.out.println(rows);
        rows = userMapper.updateStatus(150,1);
        System.out.println(rows);
    }
    @Test
    public void testSelectDiscussPost(){
        List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPosts(0,2,10,0);
        for (int i = 0; i < discussPosts.size(); i++) {
            System.out.println(discussPosts.get(i));
        }
        int nums = discussPostMapper.selectDiscussPostRows(0);
        System.out.println(nums);
    }

    @Test
    public void testInsertLoginTicket(){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setTicket("hh");
        loginTicket.setUserId(1230);
        loginTicket.setStatus(1);
        loginTicket.setExpired(new Date(System.currentTimeMillis()+1000*60*10));
        int a = loginTicketMapper.insertLoginTicket(loginTicket);
        LoginTicket loginTicket1 = loginTicketMapper.selectByTicket("hh");
        System.out.println(loginTicket1);

        a = loginTicketMapper.updateStatus("hh",3);
    }

    @Test
    public void testMessageMapper(){
        List<Message> list = messageMapper.selectConversations(111,0,20);
        for (Message message:list) {
            System.out.println(message.toString());
        }
        System.out.println("---------------------------------");
        int count = messageMapper.selectConversationCount(111);
        System.out.println(count);
        System.out.println("---------------------------------");
        List<Message> list2 = messageMapper.selectLetters("111_112",0,20);
        for (Message message:list2) {
            System.out.println(message.toString());
        }
        System.out.println("---------------------------------");
        int count2 = messageMapper.selectLetterCount("111_112");
        System.out.println(count2);
        System.out.println("---------------------------------");
        int count3 = messageMapper.selectLetterUnreadCount(112,"111_112");
        System.out.println(count3);
    }

}