package com.dhcc.community.service;

import com.dhcc.community.dto.PaginationDTO;
import com.dhcc.community.dto.QuestionDTO;
import com.dhcc.community.mapper.QuestionMapper;
import com.dhcc.community.mapper.UserMapper;
import com.dhcc.community.model.Question;
import com.dhcc.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDTO list(Integer page, Integer size) {
        Integer offset = size * (page - 1);
        PaginationDTO paginationDTO = new PaginationDTO();
        List<Question> questionList = questionMapper.list(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            User user = userMapper.findById(question.getCreator());
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestionDTOList(questionDTOList);
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(page,size,totalCount);
        return paginationDTO;
    }
}
