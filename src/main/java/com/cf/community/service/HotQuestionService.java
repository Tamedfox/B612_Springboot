package com.cf.community.service;

import com.cf.community.dao.QuestionDao;
import com.cf.community.model.Question;
import com.cf.community.model.dto.HotQuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

@Component
public class HotQuestionService {

    @Autowired
    private QuestionDao questionDao;

    private static final Integer HOT_QUESTION_AMOUNT = 6;

    private List<Question> hotQuestions = new ArrayList<>();


    public List<Question> updateHotQuestions(Map<Long, Integer> map){

        PriorityQueue<HotQuestionDTO> priorityQueue = new PriorityQueue<>();

        map.forEach((id,priority) -> {
            HotQuestionDTO hotQuestionDTO = new HotQuestionDTO(id,priority);
            if(priorityQueue.size() < HOT_QUESTION_AMOUNT){
                priorityQueue.offer(hotQuestionDTO);
            }else{
                if(hotQuestionDTO.compareTo(priorityQueue.peek()) > 0){
                    priorityQueue.poll();
                    priorityQueue.offer(hotQuestionDTO);
                }
            }
        });

        while(priorityQueue.peek() != null){
//            System.out.println("问题增加了" + priorityQueue.peek().getId() + ":" + priorityQueue.peek().getPriority() );
            Question question = questionDao.findById(priorityQueue.poll().getId()).get();
//            System.out.println("问题是：" + question.getTitle());
            hotQuestions.add(question);
//            System.out.println("------------------");
        }

        return hotQuestions;
    }

}
