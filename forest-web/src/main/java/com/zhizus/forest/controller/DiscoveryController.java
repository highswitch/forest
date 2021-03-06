package com.zhizus.forest.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zhizus.forest.common.MetaInfo;
import com.zhizus.forest.common.registry.AbstractServiceDiscovery;
import org.apache.curator.x.discovery.ServiceInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

/**
 * Created by Dempe on 2016/12/29.
 */
@Controller
@RequestMapping("/discovery")
public class DiscoveryController {

    @Autowired
    private AbstractServiceDiscovery<MetaInfo> discovery;

    @RequestMapping("/index")
    public ModelAndView index(ModelAndView modelAndView) throws Exception {
        Collection<String> names = discovery.queryForNames();
        modelAndView.addObject("names", names == null ? Lists.newArrayList() : names);
        modelAndView.setViewName("forest/discovery");
        return modelAndView;
    }

    @RequestMapping("/queryByName")
    @ResponseBody
    public String queryByName(@RequestParam String name) throws Exception {
        Collection<ServiceInstance<MetaInfo>> collection = discovery.queryForInstances(name);
        for (ServiceInstance<MetaInfo> metaInfoServiceInstance : collection) {
            MetaInfo payload = metaInfoServiceInstance.getPayload();
            System.out.println(payload);
        }
        return JSON.toJSONString(collection);
    }

    @RequestMapping("/updateServiceInstance")
    @ResponseBody
    public String updateServiceInstance() {
        return "{}";

    }

}
