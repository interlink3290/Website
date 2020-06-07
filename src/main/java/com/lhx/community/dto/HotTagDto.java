package com.lhx.community.dto;

import lombok.Data;


@Data
public class HotTagDto implements Comparable<HotTagDto> {
    private String name;
    private Integer priority;

    @Override
    public int compareTo(HotTagDto o) {
        return this.priority - o.getPriority();
    }
}
