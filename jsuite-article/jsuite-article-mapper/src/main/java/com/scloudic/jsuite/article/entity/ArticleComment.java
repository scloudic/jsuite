package com.scloudic.jsuite.article.entity;
import java.util.Date;
import com.scloudic.rabbitframework.jbatis.mapping.GenerationType;
import com.scloudic.rabbitframework.jbatis.annontations.*;
import java.io.Serializable;

/**
* This class corresponds to the database table article_comment
*/
@Table
public class ArticleComment implements Serializable {
	private static final long serialVersionUID = 1L;
    public static final String FIELDS = " article_comment_id,article_id,create_time,vote_down,user_id,sort_num,comment_content,vote_up,parent_article_comment_id,reply_count ";

    /**
    * This field corresponds to the database column article_comment.article_comment_id
    * <p>
    * description:文章评论主键
    */
    @ID(keyType = GenerationType.MANUAL)
    private String articleCommentId;

    /**
    * This field corresponds to the database column article_comment.article_id
    * <p>
    * description:文章主键
    */
    @Column
    private String articleId;

    /**
    * This field corresponds to the database column article_comment.create_time
    * <p>
    * description:创建时间
    */
    @Column
    private Date createTime;

    /**
    * This field corresponds to the database column article_comment.vote_down
    * <p>
    * description:“踩”的数量
    */
    @Column
    private Integer voteDown;

    /**
    * This field corresponds to the database column article_comment.user_id
    * <p>
    * description:评论人
    */
    @Column
    private String userId;

    /**
    * This field corresponds to the database column article_comment.sort_num
    * <p>
    * description:排序编号
    */
    @Column
    private Integer sortNum;

    /**
    * This field corresponds to the database column article_comment.comment_content
    * <p>
    * description:评论内容
    */
    @Column
    private String commentContent;

    /**
    * This field corresponds to the database column article_comment.vote_up
    * <p>
    * description:“顶”的数量
    */
    @Column
    private Integer voteUp;

    /**
    * This field corresponds to the database column article_comment.parent_article_comment_id
    * <p>
    * description:回复评论主键
    */
    @Column
    private String parentArticleCommentId;

    /**
    * This field corresponds to the database column article_comment.reply_count
    * <p>
    * description:评论的回复数量
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
