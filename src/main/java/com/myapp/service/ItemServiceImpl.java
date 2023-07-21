package com.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.myapp.ItemStatus;
import com.myapp.entity.Item;
import com.myapp.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService{
	
	private ItemRepository itemRepository;
	
	@Autowired
	public ItemServiceImpl(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	@Override
	public Item addItem(Item item) {
		// TODO Auto-generated method stub
		return itemRepository.save(item);
	}

	@Override
	public Item updateItem(Item item) {
		// TODO Auto-generated method stub
		return itemRepository.save(item);
	}

	@Override
	public Item getItemById(Long itemId) {
		// TODO Auto-generated method stub
		return itemRepository.findById(itemId).get();
	}

	@Override
	public Page<Item> getItemList() {
		// TODO Auto-generated method stub
		int page = 0, pageSize = 10;
		List<Item> listItem = itemRepository.findAll();
    	System.out.println("Overall list");
    	pageSize = (int)listItem.stream().count();
    	Pageable pageable = PageRequest.of(page, pageSize, Sort.by("itemId"));
		return itemRepository.findAll(pageable);
	}

	@Override
	public void deleteItemById(Long itemId) {
		// TODO Auto-generated method stub
		itemRepository.deleteById(itemId);
	}

	@Override
	public void deleteAllItem() {
		// TODO Auto-generated method stub
		itemRepository.deleteAll();
	}

}
