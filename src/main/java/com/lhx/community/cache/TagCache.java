package com.lhx.community.cache;

import com.lhx.community.dto.TagDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TagCache {

    public static List<TagDto> get() {
        List<TagDto> tagDtos = new ArrayList<>();
        TagDto type = new TagDto();
        type.setCategoryName("板块");
        type.setTags(Arrays.asList("讨论区", "课程推荐", "刷题", "校园周边", "资源共享"));
        tagDtos.add(type);

        TagDto program = new TagDto();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("java", "c/c++", "python", "c#", "php", "javascript", "html", "html5"));
        tagDtos.add(program);

        TagDto db = new TagDto();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("sqlserver", "mysql", "redis", "sql", "oracle", "sqlite"));
        tagDtos.add(db);

        TagDto tool = new TagDto();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("intellij-idea", "git", "github", "vscode", "eclipse", "maven", "ide", "atom"));
        tagDtos.add(tool);

        TagDto other = new TagDto();
        other.setCategoryName("其它");
        other.setTags(Arrays.asList( "测试", "灌水", "交友", "电影", "音乐", "读书", "美食", "游戏", "数码"));
        tagDtos.add(other);
        return tagDtos;
    }


}
