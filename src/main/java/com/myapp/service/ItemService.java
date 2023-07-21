package com.myapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.myapp.ItemStatus;
import com.myapp.entity.Item;

public interface ItemService {

	public Item addItem(Item item);
	public Item updateItem(Item item);
	public Item getItemById(Long itemId);
	public void deleteItemById(Long itemId);
	public void deleteAllItem();
	public Page<Item> getItemList();
	//public Page<Item> findItemByItemStatusAndItemEnteredByUser(String itemStatus, String itemEnteredByUser, Pageable pageable);
}