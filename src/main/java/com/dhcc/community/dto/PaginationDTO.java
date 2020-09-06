package com.dhcc.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questionDTOList;
    private Integer totalPage;
    private boolean showPrevious;
    private boolean showNext;
    private boolean showFirst;
    private boolean showLast;
    private Integer currentPage;
    private List<Integer> pages = new ArrayList<>();

    public void setPagination(Integer page, Integer size, Integer totalCount) {
        if (totalCount%size==0) {
            this.totalPage = totalCount/size;
        } else {
            this.totalPage = totalCount/size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        this.currentPage = page;
        if (this.currentPage > 1) {
            this.showPrevious = true;
        } else {
            this.showPrevious = false;
        }
        if (this.currentPage < totalPage) {
            this.showNext = true;
        } else {
            this.showNext = false;
        }
        int start = this.currentPage-2;
        if (start<1) start = 1;
        for (int i=start; i<this.currentPage; i++) {
            this.pages.add(i);
        }
        int end = this.currentPage+2;
        if (end>this.totalPage) end = this.totalPage;
        for (int i=this.currentPage; i<=end; i++) {
            this.pages.add(i);
        }
        if (this.pages.contains(1)) {
            this.showFirst = false;
        } else {
            this.showFirst = true;
        }
        if (this.pages.contains(this.totalPage)) {
            this.showLast = false;
        } else {
            this.showLast = true;
        }
    }
}
