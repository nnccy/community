package com.nowcoder.community.controller;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.SearchResult;
import com.nowcoder.community.service.ElasticsearchService;
import com.nowcoder.community.service.LikeService;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SearchController implements CommunityConstant {
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
    @Autowired
    private ElasticsearchService elasticsearchService;

    @Autowired
    private UserService userService;
    @Autowired
    private LikeService likeService;

    // search?keyword=xxx
    @RequestMapping(path = "/search",method = RequestMethod.GET)
    public String search(String keyword, Page page, Model model){ //Get请求体不能用keyword来传

        //搜索帖子
        try {
            SearchResult searchResult = elasticsearchService.searchDiscussPost(keyword, (page.getCurrent() - 1)*10, page.getLimit());
            List<Map<String,Object>> discussPosts = new ArrayList<>(); //返回的结果
            List<DiscussPost> list = searchResult.getList();
            if(list != null) {
                for (DiscussPost post : list) {
                    Map<String,Object> map = new HashMap<>();
                    //帖子 和 作者
                    map.put("post",post);
                    map.put("user",userService.findUserById(post.getUserId()));
                    // 点赞数目
                    map.put("likeCount",likeService.findEntityLikeCount(ENTITY_TYPE_POST, post.getId()));

                    discussPosts.add(map);
                }
            }
            model.addAttribute("discussPosts",discussPosts);
            model.addAttribute("keyword",keyword);
            //分页信息
            page.setPath("/search?keyword=" + keyword);
            page.setRows(searchResult.getTotal() == 0 ? 0 : (int) searchResult.getTotal());
        } catch (IOException e) {
            logger.error("系统出错，没有数据：" + e.getMessage());
        }
        return "/site/search";

    }
}
