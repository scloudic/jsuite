package com.scloudic.jsuite.article.entity;
import com.scloudic.rabbitframework.jbatis.mapping.GenerationType;
import java.util.Date;
import com.scloudic.rabbitframework.jbatis.annontations.*;
import java.io.Serializable;

@Table
public class ArticleComment implements Serializable {
	private static final long serialVersionUID = 1L;
    public static final String FIELDS = " article_comment_id,article_id,create_time,vote_down,user_id,sort_num,comment_content,vote_up,parent_article_comment_id,reply_count ";

    /**
    *
    * 文章评论主键
    *
    */
    @ID(keyType = GenerationType.MANUAL)
    private String articleCommentId;

    /**
    *
    * 文章主键
    *
    */
    @Column
    private String articleId;

    /**
    *
    * 创建时间
    *
    */
    @Column
    private Date createTime;

    /**
    *
    * “踩”的数量
    *
    */
    @Column
    private Integer voteDown;

    /**
    *
    * 评论人
    *
    */
    @Column
    private String userId;

    /**
    *
    * 排序编号
    *
    */
    @Column
    private Integer sortNum;

    /**
    *
    * 评论内容
    *
    */
    @Column
    private String commentContent;

    /**
    *
    * “顶”的数量
    *
    */
    @Column
    private Integer voteUp;

    /**
    *
    * 回复评论主键
    *
    */
    @Column
    private String parentArticleCommentId;

    /**
    *
    * 评论的回复数量
    *
    */
    @Column
    private Integer replyCount;

    public void setArticleCommentId(String articleCommentId) {
        this.articleCommentId = articleCommentId;
    }

    public String getArticleCommentId() {
        return articleCommentId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setVoteDown(Integer voteDown) {
        this.voteDown = voteDown;
    }

    public Integer getVoteDown() {
        return voteDown;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setVoteUp(Integer voteUp) {
        this.voteUp = voteUp;
    }

    public Integer getVoteUp() {
        return voteUp;
    }

    public void setParentArticleCommentId(String parentArticleCommentId) {
        this.parentArticleCommentId = parentArticleCommentId;
    }

    public String getParentArticleCommentId() {
        return parentArticleCommentId;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

}
