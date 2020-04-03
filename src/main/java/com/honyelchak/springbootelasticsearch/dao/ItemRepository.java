package com.honyelchak.springbootelasticsearch.dao;

import com.honyelchak.springbootelasticsearch.model.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import javax.xml.bind.SchemaOutputResolver;
import java.util.List;

/**
 * @author Honyelchak
 * @create 2020-04-03 9:36
 */

public interface ItemRepository extends ElasticsearchRepository<Item, String> {

    List<Item> findByUserName(String userName);
    List<Item> findAll();
    Item findItemById(String id);
}
