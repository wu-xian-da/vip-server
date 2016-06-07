package com.jianfei.core.dto;

/**
 * TODO
 *
 * @version 1.0.0
 * @author : liu.dongsong@jianfeitech.com
 * @date: 2016/6/1 17:29
 */
public class BaseDto {
    /**
     * id
     */
    private String id;
    /**
     * name
     */
    private String name;

    public BaseDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public BaseDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
