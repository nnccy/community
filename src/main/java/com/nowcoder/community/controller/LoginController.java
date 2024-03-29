package com.nowcoder.community.controller;

import com.google.code.kaptcha.Producer;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.MailClient;
import com.nowcoder.community.util.RedisKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class LoginController implements CommunityConstant {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private Producer kaptchaProducer;

    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Autowired
    private MailClient mailClient;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(path = "/register",method = RequestMethod.GET)  //访问注册页面
    public String getRegisterPage() {
        return "/site/register";
    }

    @RequestMapping(path = "/login",method = RequestMethod.GET)  //访问登录页面
    public String getLoginPage() {
        return "/site/login";
    }

    @RequestMapping(path = "/forget",method = RequestMethod.GET)  //访问登录页面
    public String getForgetPage() {
        return "/site/forget";
    }

    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public String register(Model model, User user){
        Map<String,Object> map = userService.register(user);
        if (map == null || map.isEmpty()){
            model.addAttribute("msg","注册成功，我们已经向您的邮箱发了一封激活邮件，请尽快激活！");
            model.addAttribute("target","/index");
            return "/site/operate-result";
        }else {
            model.addAttribute("usernameMsg",map.get("usernameMsg"));
            model.addAttribute("passwordMsg",map.get("passwordMsg"));
            model.addAttribute("emailMsg",map.get("emailMsg"));
            return "/site/register";
        }
    }
    //http://localhost:8080/community/activation/154/0d470216f74b456bbe67368502bcd743
    @RequestMapping(path = "/activation/{userId}/{code}",method = RequestMethod.GET)
    public String activation(Model model, @PathVariable("userId") int userId,@PathVariable("code") String code){
        int result = userService.activation(userId,code);
        if(result == ACTIVATION_SUCCESS){
            model.addAttribute("msg","激活成功，您的账号已经可以正常使用！");
            model.addAttribute("target","/login");
        }else if(result == ACTIVATION_REPEAT){
            model.addAttribute("msg","无效操作，该账号已经激活！");
            model.addAttribute("target","/index");
        }else {
            model.addAttribute("msg","激活失败，您提供的激活码不正确");
            model.addAttribute("target","/login");
        }
        return "/site/operate-result";
    }

    /**
     * 用redis重构
     */
//    @RequestMapping(path = "/kaptcha",method = RequestMethod.GET)  //访问登录页面
//    public void getKaptcha(HttpServletResponse response, HttpSession session) {
//        //生成验证码
//        String text = kaptchaProducer.createText();
//        BufferedImage image = kaptchaProducer.createImage(text);
//        //将验证码存入session
//        session.setAttribute("kaptcha",text);
//        //将图片输出给浏览器
//        response.setContentType("image/png");
//        try {
//            OutputStream os = response.getOutputStream();
//            ImageIO.write(image,"png",os);
//        } catch (IOException e) {
//            logger.error("响应验证码失败："+ e.getMessage());
//        }
//    }
    @RequestMapping(path = "/kaptcha",method = RequestMethod.GET)  //访问登录页面
    public void getKaptcha(HttpServletResponse response) {
        //生成验证码
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);
        //验证码的归属
        String kaptchaOwner = CommunityUtil.generateUUID();
        Cookie cookie = new Cookie("kaptchaOwner",kaptchaOwner);
        cookie.setMaxAge(60);
        cookie.setPath(contextPath);
        response.addCookie(cookie);
        //将验证码存入Redis
        String redisKey = RedisKeyUtil.getKaptchaKey(kaptchaOwner);
        redisTemplate.opsForValue().set(redisKey,text,60, TimeUnit.SECONDS);
        //将图片输出给浏览器
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(image,"png",os);
        } catch (IOException e) {
            logger.error("响应验证码失败："+ e.getMessage());
        }
    }

//    @RequestMapping(path="/login",method = RequestMethod.POST)
//    //如果参数是实体，参数会装到model里，例如user. 如果是如下方普通的参数，则不会装到model里
//    //1 人为加到model里。。2 request中也能去找
//    public String login(String username,String password,String code,Boolean rememberme,
//                        Model model,HttpSession session,HttpServletResponse response){
//        // 检查验证码
//        String kaptcha = (String) session.getAttribute("kaptcha");
//        if(StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !kaptcha.equalsIgnoreCase(code)){
//            model.addAttribute( "codeMsg","验证码不正确");
//            return "/site/login";
//        }
//        //检查账号，密码
//        if(rememberme == null){
//            rememberme = false;
//        }
//        int expiredSeconds = rememberme ? REMEMBER_EXPIRED_SECONDS : DEFAULT_EXPIRED_SECONDS;
//        Map<String,Object> map = userService.login(username,password,expiredSeconds);
//        if(map.containsKey("ticket")){
//            Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
//            cookie.setPath(contextPath);
//            cookie.setMaxAge(expiredSeconds);
//            response.addCookie(cookie);
//            return "redirect:/index";
//        }else {
//            model.addAttribute("usernameMsg",map.get("usernameMsg"));
//            model.addAttribute("passwordMsg",map.get("passwordMsg"));
//            return "/site/login";
//        }
//
//    }
    @RequestMapping(path="/login",method = RequestMethod.POST)
    //如果参数是实体，参数会装到model里，例如user. 如果是如下方普通的参数，则不会装到model里
    //1 人为加到model里。。2 request中也能去找
    public String login(String username,String password,String code,Boolean rememberme,
                        Model model,HttpServletResponse response,@CookieValue("kaptchaOwner") String kaptchaOwner){
        // 检查验证码
        String kaptcha = null;
        if(StringUtils.isNotBlank(kaptchaOwner)){
            String redisKey =RedisKeyUtil.getKaptchaKey(kaptchaOwner);
            kaptcha = (String) redisTemplate.opsForValue().get(redisKey);
        }
        if(StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !kaptcha.equalsIgnoreCase(code)){
            model.addAttribute( "codeMsg","验证码不正确");
            return "/site/login";
        }
        //检查账号，密码
        if(rememberme == null){
            rememberme = false;
        }
        int expiredSeconds = rememberme ? REMEMBER_EXPIRED_SECONDS : DEFAULT_EXPIRED_SECONDS;
        Map<String,Object> map = userService.login(username,password,expiredSeconds);
        if(map.containsKey("ticket")){
            Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
            cookie.setPath(contextPath);
            cookie.setMaxAge(expiredSeconds);
            response.addCookie(cookie);
            return "redirect:/index";
        }else {
            model.addAttribute("usernameMsg",map.get("usernameMsg"));
            model.addAttribute("passwordMsg",map.get("passwordMsg"));
            return "/site/login";
        }

    }

    @RequestMapping(path = "/logout",method = RequestMethod.GET)
    public String logout(@CookieValue("ticket") String ticket){
        userService.logout(ticket);
        SecurityContextHolder.clearContext();
        return "redirect:/login";
    }


    @RequestMapping(path = "/forget/code",method = RequestMethod.GET)
    public String sendCode(String email,HttpSession session){
        if (StringUtils.isBlank(email)) {
            return CommunityUtil.getJSONString(1, "邮箱不能为空！");
        }


        Context context = new Context();
        context.setVariable("email", email);
        String code = CommunityUtil.generateUUID().substring(0, 4);
        context.setVariable("verifyCode", code);
        String content = templateEngine.process("/mail/forget", context);
        mailClient.sendMail(email, "找回密码", content);

        session.setAttribute("verifyCode", code);

        return CommunityUtil.getJSONString(0);
    }

    @RequestMapping(path = "/forget/password",method = RequestMethod.POST)  //访问登录页面
    public String resetPassword(Model model,String email,String code,String newPassword,HttpSession session) {
        String verifyCode = (String)session.getAttribute("verifyCode");
        Map<String,Object> map = userService.resetPassword(email,code,verifyCode,newPassword);
        if(!map.isEmpty()){
            model.addAttribute("emailMsg",map.get("emailMsg"));
            model.addAttribute("codeMsg",map.get("codeMsg"));
            model.addAttribute("passwordMsg",map.get("passwordMsg"));
            return "/site/forget";
        }else{
            return "redirect:/login";
        }
    }

}
