package com.test.model;

public class MovieModelClass {
    String  name, release, image, info, stars, lang, pop;
    public MovieModelClass( String name, String release, String image, String info, String stars, String lang, String pop) {
        this.name = name;//назва
        this.release = release;//дата виходу
        this.image = image;//постер
        this.info = info;//опис
        this.stars = stars;//рейтинг
        this.lang = lang;//мова оригіналу
        this.pop = pop;//популярність
    }
    public MovieModelClass() {
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRelease() {
        return release;
    }
    public void setRelease(String release) {
        this.release = release;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public String getStars() {
        return stars;
    }
    public void setStars(String stars) {
        this.stars = stars;
    }
    public String getLang() {return lang;}
    public void setLang(String lang) {
        this.lang = lang;
    }
    public String getPop() {
        return pop;
    }
    public void setPop(String pop) {
        this.pop = pop;
    }
}
