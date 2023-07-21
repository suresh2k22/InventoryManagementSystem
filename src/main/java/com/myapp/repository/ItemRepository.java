package com.myapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myapp.ItemStatus;
import com.myapp.entity.Item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface ItemRepository  extends JpaRepository<Item, Long>{

	@Query("SELECT i FROM Item i WHERE i.itemStatus = :itemStatus and i.itemEnteredByUser = :itemEnteredByUser")
	Page<Item> findItemByItemStatusAndItemEnteredByUser(@Param("itemStatus") ItemStatus itemStatus, 
	  @Param("itemEnteredByUser") String itemEnteredByUser, Pageable pageable);
	
	@Query("SELECT i FROM Item i")
	Page<Item> findAll(Pageable pageable);
}
