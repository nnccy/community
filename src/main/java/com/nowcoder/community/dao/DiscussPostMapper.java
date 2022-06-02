package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
    List<DiscussPost> selectDiscussPosts(int userId,int offset,int limit,int orderMode);
    int selectDiscussPostRows(@Param("userId") int userId);     //注解是别名，动态拼一个条件，但是方法只有一个参数，在<if>中使用，那么就要加注解
    int insertDiscussPost(DiscussPost discussPost);

    DiscussPost selectDiscussPostById(int id);
    int updateCommentCount(int id,int commentCount); //返回更新的整数

    int updateType(int id, int type);
    int updateStatus(int id, int status);
    int updateScore(int id, double score);
}
