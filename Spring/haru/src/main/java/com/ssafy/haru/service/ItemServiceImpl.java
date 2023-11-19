package com.ssafy.haru.service;

import com.ssafy.haru.model.dao.ItemDao;
import com.ssafy.haru.model.dto.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemServiceImpl implements ItemService{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemDao itemDao;

    @Override
    public List<Item> selectAll() {
        return itemDao.selectAll();
    }
}
