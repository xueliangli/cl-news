package com.clnews.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with cl-news
 * Created By lxc
 * Date: 2019-03-18
 *
 * @author lxc
 */
public class News implements Serializable {

    private static final long serialVersionUID = 7993799811820966212L;

    private Long id;
    private String source;
    private String sourceName;
    private String title;
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
