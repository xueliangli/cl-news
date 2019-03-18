package com.clnews.processor;

/**
 * @program: cl-news
 * @description:
 * @analysis:
 * @author: 李学亮    email: 18222027300@163.com
 * @create: 2019-03-17 19:33
 **/
public class CsdnBlog {
    // 编号
    private int id;
    // 标题
    private String title;
    // 日期
    private String date;
    // 标签
    private String tags;
    // 分类
    private String category;
    // 阅读人数
    private int view;
    // 评论人数
    private int comments;
    // 是否原创
    private int copyright;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getCopyright() {
        return copyright;
    }

    public void setCopyright(int copyright) {
        this.copyright = copyright;
    }

    @Override
    public String toString() {
        return "CsdnBlog [id=" + id + ", title=" + title + ", date=" + date + ", tags=" + tags + ", category="
                + category + ", view=" + view + ", comments=" + comments + ", copyright=" + copyright + "]";
    }
}
