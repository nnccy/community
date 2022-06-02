package com.nowcoder.community.util;

public class RedisKeyUtil {

    private static final String SPLIT = ":";
    private static final String PREFIX_ENTITY_LIKE = "like:entity";
    private static final String PREFIX_USER_LIKE = "like:user";
    private static final String PREFIX_FOLLOWEE = "followee";
    private static  final String PREFIX_FOLLOWER = "follower";
    private static final String PREFIX_KAPTCHA = "kaptcha";
    private static final String PREFIX_TICKET= "ticket";
    private static final String PREFIX_USER= "user";
    private static final String PREFIX_UV= "uv";
    private static final String PREFIX_DAU= "dau";
    private static final String PREFIX_POST= "post";

    // 某个实体的赞
    // like:entity:entityType:entityId -> set(userId)
    public static String getEntityLikeKey(int entityType,int entityId) {
        return PREFIX_ENTITY_LIKE+SPLIT+entityType+SPLIT+entityId;
    }

    //某个用户的赞
    public static String getUserLikeKey(int userId){
        return PREFIX_USER_LIKE+SPLIT+userId;
    }

    //某个用户关注的实体
    //followee:userId:entityType ->zset(entityId,now)
    public static String getFolloweeKey(int userId,int entityType){
        return PREFIX_FOLLOWEE+SPLIT+userId+SPLIT+entityType;
    }
    //某个用户拥有的粉丝
    //follwer:entityType:entityId ->zset(userId,now)
    public static String getFollowerKey(int entityType,int entityId){
        return PREFIX_FOLLOWER+SPLIT+entityType+SPLIT+entityId;
    }
    //登录验证码
    public static String getKaptchaKey(String owner){ //验证码的临时凭证，存在cookies里
        return PREFIX_KAPTCHA+SPLIT+owner;
    }
    //登陆的凭证
    public static String getTicketKey(String ticket){ //验证码的临时凭证，存在cookies里
        return PREFIX_TICKET+SPLIT+ticket;
    }
    //用户
    public static String getUserKey(int userId){ //验证码的临时凭证，存在cookies里
        return PREFIX_USER+SPLIT+userId;
    }

    // 单日UV
    public static String getUVKey(String date){ //验证码的临时凭证，存在cookies里
        return PREFIX_UV+SPLIT+date;
    }

    // 区间UV
    public static String getUVKey(String startDate,String endDate){ //验证码的临时凭证，存在cookies里
        return PREFIX_UV+SPLIT+startDate+SPLIT+endDate;
    }

    // 单日DAU
    public static String getDAUKey(String date){ //验证码的临时凭证，存在cookies里
        return PREFIX_DAU + SPLIT+date;
    }

    // 区间UV
    public static String getDAUKey(String startDate,String endDate){ //验证码的临时凭证，存在cookies里
        return PREFIX_DAU + SPLIT + startDate + SPLIT + endDate;
    }
    // 帖子分数
    public static String getPostScoreKey() {
        return PREFIX_POST + SPLIT + "score";
    }
}
