package com.lihao.entity.po;

import lombok.Getter;
import lombok.Setter;


public class Page {
    @Getter
    @Setter
    private Integer pageSize;
    @Getter
    @Setter
    private Integer pageNum;
    private Integer left;
    private Integer right;
    public Page(Integer pageSize, Integer pageNum) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        if(pageSize!=null && pageNum !=null){
            left = pageSize*(pageNum-1);
            right = pageSize*(pageNum);
        }
    }

    public Integer getLeft() {
        return this.left = pageSize*(pageNum-1);
    }

    public Integer getRight() {
        return this.right = pageSize*(pageNum);
    }
}
