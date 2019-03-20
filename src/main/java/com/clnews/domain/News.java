package com.clnews.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: cl-news
 * @description: 新闻实体
 * @analysis:
 * @author: 李学亮    email: 18222027300@163.com
 * @create: 2019-03-19 14:24
 **/
public class News implements Serializable {
    /**
     * @description: 实现序列化的 id todo：有什么作用
     */
    private static final long serialVersionUID = 7993799811820966212L;
    /**
     * @description: 名片
     */
    private Long id;
    /**
     * @description: 来源
     */
    private String source;
    /**
     * @description: 来源名称
     */
    private String sourceName;
    /**
     * @description: 标题
     */
    private String title;
    /**
     * @description: 内容
     */
    private String content;
    private String contentUrl;
    private String imageUrl;
    private Date createDate;
    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "News{" +
                "source='" + source + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", contentUrl='" + contentUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
