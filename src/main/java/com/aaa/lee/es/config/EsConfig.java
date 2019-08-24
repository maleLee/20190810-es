package com.aaa.lee.es.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/8/10 15:35
 * @Description
 *      因为项目中使用的TransportClient进行连接，所以需要获取和初始化该对象
 **/
@SpringBootApplication
public class EsConfig {

    @Autowired
    private ESProperties esProperties;

    /**
     * @author Seven Lee
     * @description
     *      创建并初始化TransportClient对象，使用该对象对ES进行增删改查
     *      cluster.name:集群名字
     *      node.name:节点名字
     *      client.transport.sniff:客户端(java项目)一直监视ES的节点状态(节点数)，不再需要自动手动添加节点，如果有新的节点产生了，会自动加载进项目中
     *      thread_pool.search.size:线程池
     * @param
     * @date 2019/8/10
     * @return org.elasticsearch.client.transport.TransportClient
     * @throws 
    **/
    @Bean("transportClient")
    public TransportClient getTransportClient() {
        // 1.创建TransportClient对象
        TransportClient transportClient = null;
        try {
            // 2.设置Java对ES的集群信息
            Settings settings = Settings.builder().put("cluster.name", esProperties.getClusterName())
                    .put("node.name", esProperties.getNodeName())
                    .put("client.transport.sniff", true)
                    .put("thread_pool.search.size", esProperties.getPool()).build();
            // 3.初始化TransportClient对象
            transportClient = new PreBuiltTransportClient(settings);
            // 4.配置对ES的连接信息
            TransportAddress transportAddress = new TransportAddress(InetAddress.getByName(esProperties.getIp()), Integer.parseInt(esProperties.getPort()));
            // 5.把对ES的连接对象放到transportClient对象中
            transportClient.addTransportAddress(transportAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return transportClient;
    }

}
