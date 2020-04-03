package com.honyelchak.springbootelasticsearch.controller;

import com.honyelchak.springbootelasticsearch.dao.ItemRepository;
import com.honyelchak.springbootelasticsearch.model.Item;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author Honyelchak
 * @create 2020-04-03 10:02
 */
@RestController
public class ItemController {
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;


    @GetMapping("createIndex")
    public boolean createIndex(String indexName) {
        return elasticsearchTemplate.createIndex(indexName);
    }

    @GetMapping("deleteIndex")
    public boolean deleteIndex(String indexName) {
        return elasticsearchTemplate.deleteIndex(indexName);
    }

    @GetMapping("indexIsExist")
    public boolean indexIsExist(String indexName) {
        return elasticsearchTemplate.indexExists(indexName);
    }

    @GetMapping("typeIsExist")
    public boolean typeIsExist(String indexName, String type) {
        return elasticsearchTemplate.typeExists(indexName, type);
    }

    @GetMapping("getMapping")
    public Map getMapping(String indexName, String type) {
        return elasticsearchTemplate.getMapping(indexName, type);
    }

    @GetMapping("getSetting")
    public Map getSetting(String indexName) {
        return elasticsearchTemplate.getSetting(indexName);
    }

}
