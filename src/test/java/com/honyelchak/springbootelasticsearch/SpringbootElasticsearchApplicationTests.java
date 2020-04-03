package com.honyelchak.springbootelasticsearch;

import com.honyelchak.springbootelasticsearch.dao.ItemRepository;
import com.honyelchak.springbootelasticsearch.model.Item;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringbootElasticsearchApplicationTests {
    @Autowired
    private ItemRepository itemRepository;


    @Before
    public void initRepositoryData() {
        //清除ES中数据以免影响测试结果
        System.out.println("start insert !!!!");
        itemRepository.deleteAll();
        itemRepository.save(new Item("1", "中国你好", "admin", 4, " " +
                " " +
                "我们的责任， " +
                " " +
                "就是要团结带领全党全国各族人民， " +
                " " +
                "接过历史的接力棒， " +
                " " +
                "继续为实现中华 " +
                " " +
                "民族伟大复兴而努力奋斗， " +
                " " +
                "使中华民族更加坚强有力地自立于世界民族之林， " +
                " " +
                "为人类作出新 " +
                " " +
                "的更大的贡献。", new Date()));
        itemRepository.save(new Item("2", "aaa", "root", 12, "这个重大的责任， " +
                " " +
                "是对民族的责任。 " +
                " " +
                "我们的民族是伟大的民族。 " +
                " " +
                "在五千多年的文明发展 " +
                " " +
                "历程中， " +
                " " +
                "中华民族为人类的文明进步作出了不可磨灭的贡献。 " +
                " " +
                "近代以后， " +
                " " +
                "我们的民族历经磨 " +
                " " +
                "难， " +
                " " +
                "中华民族到了最危险的时候。 " +
                " " +
                "自那时以来， " +
                " " +
                "为了实现中华民族伟大复兴， " +
                " " +
                "无数仁人志士 " +
                " " +
                "奋起抗争，但一次又一次地失败了。 " +
                " " +
                "  " +
                " " +
                "     " +
                " " +
                "中国共产党成立后， " +
                " " +
                "团结带领人民前赴后继、 " +

                "顽强奋斗， " +

                "把贫穷落后的旧中国变成日益 " +

                "走向繁荣富强的新中国，中华民族伟大复兴展现出前所未有的光明前景。", new Date()));
        itemRepository.save(new Item("3", "fanqi", "sa", 13, "我们召开了中国共产党第十八届中央委员会第一次全体会议， " +

                "会议上选举产生了 " +

                "新一届中央领导机构。全会选举产生了七位中央政治局常委，选举我担任中共中央总书记。 " +

                "接下来，我把其他六位常委同事向大家介绍一下。 " +
                "他们是：李克强同志、张德江同志、俞正声同志、刘云山同志、王岐山同志、张高丽同 " +
                " " +
                "志。 " +
                "李克强同志是十七届中央政治局常委， " +

                "其他同志都是十七届中央政治局委员， " +

                "大家对他 " +
                "们都比较了解。 " +
                "在这里， " +
                "我代表新一届中央领导机构成员， " +
                "衷心感谢全党同志对我们的信任。 " +
                "我们一定 " +
                "不负重托，不辱使命！ " +
                "全党同志的重托， " +
                "全国各族人民的期望， " +
                "这是对我们做好工作的巨大鼓舞， " +
                "也是我们肩 " +
                "上沉沉的担子。", new Date()));
        System.out.println("end !!!");
    }

    @Test
    public void contextLoads() {
        System.out.println("start !!!!");
        List<Item> items = itemRepository.findByUserName("fanqi");
        items.forEach(System.out::println);
        System.out.println("end !!!!");
    }

    @Test
    public void findByUserName() {
        System.out.println("start !!!!");
        List<Item> items = itemRepository.findByUserName("fanqi");
        items.forEach(System.out::println);
        System.out.println("end !!!!");
    }

    @Test
    public void findById() {
        Item item = itemRepository.findItemById("1");
        System.out.println(item);
    }

    @Test
    public void updateItem() {
        Item item = itemRepository.findItemById("1");
        item.setUserName("aaaa我被修改了");
        itemRepository.save(item);
        System.out.println(item);
    }

    // 查询userName为fanqi的用户，并且以age倒序返回。
    @Test
    public void searchByUserNameSortByAge() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.should(QueryBuilders.termQuery("userName", "fanqi"));
        FieldSortBuilder sortBuilder = SortBuilders.fieldSort("age").order(SortOrder.DESC);

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        nativeSearchQueryBuilder.withSort(sortBuilder);

        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
        Page<Item> itemsPage = itemRepository.search(searchQuery);

        if (itemsPage != null) {
            List<Item> items = itemsPage.getContent();
            items.forEach(System.out::println);
        } else {
            System.out.println("can not find that!");
        }

    }

    // 分词查询 ：comment中包含中国的用户，并且以age倒序返回。
    @Test
    public void searchByCommentSortByAge() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.should(QueryBuilders.termQuery("comment", "中国"));
        FieldSortBuilder sortBuilder = SortBuilders.fieldSort("age").order(SortOrder.DESC);

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        nativeSearchQueryBuilder.withSort(sortBuilder);

        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
        Page<Item> itemsPage = itemRepository.search(searchQuery);

        if (itemsPage != null) {
            List<Item> items = itemsPage.getContent();
            items.forEach(System.out::println);
        } else {
            System.out.println("can not find that!");
        }

    }
    // 分词查询 ：comment中包含中国的用户，并且以age倒序返回，并且对匹配到的词语设置为高亮
    // TODO(honyelchak) 测试失败
    @Test
    public void searchByCommentSortByAgeHighlight() {
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("中国", "comment", "userName");

        FieldSortBuilder sortBuilder = SortBuilders.fieldSort("age").order(SortOrder.DESC);


        HighlightBuilder highlightBuilder = new HighlightBuilder().field("*").requireFieldMatch(false);
        highlightBuilder.preTags("<span style=\"color:red\">");
        highlightBuilder.postTags("</span>");

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(multiMatchQueryBuilder);
        nativeSearchQueryBuilder.withSort(sortBuilder);
        nativeSearchQueryBuilder.withHighlightBuilder(highlightBuilder);

        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
        Page<Item> itemsPage = itemRepository.search(searchQuery);

        if (itemsPage != null) {
            List<Item> items = itemsPage.getContent();
            items.forEach(System.out::println);
        } else {
            System.out.println("can not find that!");
        }

    }

    @Test
    public void sort() {
        Sort sort = Sort.by("userName").ascending()
                .and(Sort.by("age").descending());
        Iterable<Item> all = itemRepository.findAll(sort);
        for (Item item : all) {
            System.out.println(item);
        }
    }


}
