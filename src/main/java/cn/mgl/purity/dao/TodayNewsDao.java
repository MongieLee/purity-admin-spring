package cn.mgl.purity.dao;

import cn.mgl.purity.model.persistent.TodayNews;
import cn.mgl.purity.model.queryUtil.TodayNewsListQuery;
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
    @Insert("insert into today_news (title,content,cover_img,created_by,created_at)" +
            "values (#{title},#{content},#{coverImg},#{createdBy},now())")
    int createNews(TodayNews news);

    /**
     * 更新文章，如果资讯已发布，则变更为未发布状态
     *
     * @param news
     * @return
     */
    @Update("update today_news set title = #{title}, content = #{content}, cover_img = #{coverImg}, " +
            "is_publish = #{isPublish}, published_at = #{publishedAt}, published_by = #{publishedBy}, updated_by = #{updatedBy}, updated_at = now() where id = #{id}")
    int updateNews(TodayNews news);

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
            "<if test=\"query.title != null\">and title like concat('%',#{query.title},'%') </if>" +
            "<if test=\"query.isPublish != null\">and is_publish = #{query.isPublish} </if>" +
            "<if test=\"query.createdStar != null and query.createdEnd != null\">" +
            "and TO_DAYS(created_at) >= TO_DAYS(#{query.createdStar})</if>" +
            "</where>" + "</script>")
    List<TodayNews> getList(@Param("query") TodayNewsListQuery query);

}
