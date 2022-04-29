package com.example.demo.dao;

import com.example.demo.model.persistent.TodayNews;
import com.example.demo.model.queryUtil.TodayNewsQuery;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TodayNewsDao {
    /**
     * 插入文章
     *
     * @param news
     * @return
     */
    int insertNews(TodayNews news);

    /**
     * 更新文章，如果资讯已发布，则变更为未发布状态
     *
     * @param news
     * @return
     */
    @Update("update today_news set title = #{news.title}, content = #{news.content}, cover_img = #{news.coverImg}, sequence = #{news.sequence}," +
            "is_publish = 0, published_at = null, published_by = null, updated_by = #{username}, updated_at = now() where id = #{news.id}")
    int updateNews(TodayNews news, String username);


    /**
     * 删除文章
     *
     * @param id
     * @return
     */
    @Delete("delete from today_news where id = #{id}")
    int deleteNews(Long id);

    /**
     * 根据文章id获取文章内容
     *
     * @param id
     * @return
     */
    @Select("select * from today_news where id = #{id}")
    TodayNews getNewsById(Long id);

    /**
     * 发布文章
     *
     * @param id
     * @param username
     * @return
     */
    @Update("update today_news set is_publish = 1,published_at = now(),published_by = #{username} where id = #{id}")
    int publishNews(Long id, String username);

    /**
     * 取消发布文章
     *
     * @param id
     * @return
     */
    @Update("update today_news set is_publish = 0,published_at = null,published_by = null where id = #{id}")
    int cancelPublishNews(Long id);

    /**
     * 获取文章列表
     *
     * @param
     * @return
     */
    @Select("<script> select * from today_news <where>" +
             "<if test=\"query.title != null\">title like concat('%',#{query.title},'%')</if>"+
             "<if test=\"query.isPublish != null\">title like concat('%',#{query.isPublish},'%')</if>"+
             "<if test=\"query.createdStar != null and query.createdEnd != null\">" +
            "TO_DAYS(created_at) >= TO_DAYS(#{query.createdAt})</if>"+
            "</where>"+"</script>")
    List<TodayNews> getList(TodayNewsQuery query);

}
