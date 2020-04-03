package com.honyelchak.springbootelasticsearch.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Honyelchak
 * @create 2020-04-03 9:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
// 若ES中没有指定的索引，会自动创建。
// shards 分片数  replicas 副本数 refreshInterval 刷新间隔
@Document(indexName = "hello_world", type="user", shards = 5, replicas = 0)
public class Item implements Serializable {
    @Id
    private String id;
    @Field(type = FieldType.Text, fielddata = true)
    private String userName;
    @Field(type = FieldType.Keyword, index = false)
    private String passWord;
    @Field(type = FieldType.Integer)
    private Integer age;
    // text 类型的会分词
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String comment;
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmt_create;
}
