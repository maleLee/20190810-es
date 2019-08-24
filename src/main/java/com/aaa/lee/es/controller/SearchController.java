package com.aaa.lee.es.controller;

import com.aaa.lee.es.base.BaseController;
import com.aaa.lee.es.base.ResultData;
import com.aaa.lee.es.status.StatusEnum;
import com.aaa.lee.es.utils.ESUtil;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/8/10 15:52
 * @Description
 **/
@RestController
public class SearchController extends BaseController {

    /**
     * @author Seven Lee
     * @description
     *      报错：java.lang.IllegalArgumentException: The number of object passed must be even but was [1]
     *      在ES的5.x版本以后，所有的JSONObject对象被废弃，需要使用Map进行传递数据
     * @param
     * @date 2019/8/10
     * @return java.lang.String
     * @throws 
    **/
    @RequestMapping("/addData")
    public ResultData addData() {
        try {
            /*JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", 20L);
            jsonObject.put("name", "zhaoliu");
            jsonObject.put("age", 30);*/
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("id", 20L);
            dataMap.put("name", "zhaoliu");
            dataMap.put("age", 30);
            Map<String, String> stringStringMap = ESUtil.addData(dataMap, "test_index_4", "test_type2", "20");
            // 如果stringStringMap是FAILED，只会打印操作失败，并且打印编号信息
            if(StatusEnum.SUCCESS.getCode().equals(stringStringMap.get("code"))) {
                return this.success("数据添加成功");
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @author Seven Lee
     * @description
     *      通过id进行查询
     * @param [id]
     * @date 2019/8/10
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @throws
    **/
    @RequestMapping("/selectOne")
    public Map<String, Object> selectById(String id) {
        Map<String, Object> stringObjectMap = ESUtil.searchDataById("test_index_4", "test_type2", "20", null);
        return stringObjectMap;
    }

    /**
     * @author Seven Lee
     * @description
     *      查询所有的数据
     * @param []
     * @date 2019/8/10
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @throws
    **/
    @RequestMapping("/all")
    public List<Map<String, Object>>  selectAll() {
        // 1.获取QueryBuilder对象
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 2.设置查询条件(查询所有的数据信息)
        BoolQueryBuilder must = boolQueryBuilder.must(QueryBuilders.matchAllQuery());
        // 3.使用工具类进行查询
        return ESUtil.searchListData("test_index_4", "test_type2", must, 100, null, null, null);
    }

    /**
     * @author Seven Lee
     * @description
     *      模糊查询
     *      如果使用的是模糊查询matchPhraseQuery()方法中text属性值所要查询的数据一定要有连接符号、空格，如果没有连接符号/空格，ES会认为
     *      这就是一个词，不进行识别模糊匹配
     *          zhang44：没有连接符号或者空格--->会认为是一个词识别不了模糊匹配
     *          zhang-44   zhang_44 zhang,44 zhang 44....
     *          就因为分词的原因！！！因为是英文分词器所以无法识别到！！！
     *       添加中文中文分词器！！
     *          在es的linux服务器的根目录上创建ik文件夹
     *          从网上下载ik分词器(zip)
     *          unzip 压缩包名
     *          ik分词器的压缩包解压到ik目录中即可，不需要任何配置！
     * @param [flag]
     * @date 2019/8/10
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @throws
    **/
    @RequestMapping("/selectAll")
    public List<Map<String, Object>> selectAll(boolean flag) {
        // 1.获取QueryBuilder对象
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 2.设置查询条件(查询所有的数据信息)
        MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders.matchPhraseQuery("name", "zhang");
        BoolQueryBuilder must = boolQueryBuilder.must(matchPhraseQueryBuilder);
        return ESUtil.searchListData("test_index_4", "test_type2", must, 100, null, null, "name");
    }

}
