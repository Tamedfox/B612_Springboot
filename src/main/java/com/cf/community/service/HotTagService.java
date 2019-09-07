package com.cf.community.service;

import com.cf.community.dao.TagDao;
import com.cf.community.model.Tag;
import com.cf.community.model.dto.HotTagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

@Component
public class HotTagService {

    @Autowired
    private TagDao tagDao;

    //热度前五个标签
    private static final Integer HOT_TAG_AMOUNT = 5;

    private List<String> hotTags = new ArrayList<>();

    public List<String> upadteHotTags(Map<Long, Integer> tags){

        PriorityQueue<HotTagDTO> priorityQueue = new PriorityQueue<>(HOT_TAG_AMOUNT);

        tags.forEach((id,priority) -> {
            HotTagDTO hotTagDTO = new HotTagDTO(id,priority);
            if(priorityQueue.size() < HOT_TAG_AMOUNT){
                priorityQueue.offer(hotTagDTO);
            }else{
                if(hotTagDTO.compareTo(priorityQueue.peek()) > 0){
                    priorityQueue.poll();
                    priorityQueue.offer(hotTagDTO);
                }
            }
        });

        while(priorityQueue.peek() != null){
//            System.out.println("tag增加了" + priorityQueue.peek().getId() + ":" + priorityQueue.peek().getPriority() );
            Tag tag = tagDao.findById(priorityQueue.poll().getId()).get();
//            System.out.println("标签是：" + tag.getName());
            hotTags.add(tag.getName());
//            System.out.println("------------------");
        }

        return hotTags;
    }

}
