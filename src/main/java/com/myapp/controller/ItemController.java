package com.myapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.ItemStatus;
import com.myapp.entity.Item;
import com.myapp.exception.ResourceNotFoundException;
import com.myapp.repository.ItemRepository;
import com.myapp.service.ItemService;

@RestController
@RequestMapping("/app/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	@Autowired ItemRepository itemRepo;
	
	@PostMapping
    public ResponseEntity<?> createItem(@Valid @RequestBody Item item) {
		//System.out.println(item.getItemStatus());
        // Check if the itemId exists in the database
        if (itemRepo.existsById(item.getItemId())) {
            return ResponseEntity.badRequest().build();
        }
        
        // Item doesn't exist, insert the data
        Item savedItem = itemService.addItem(item);
        
        // Return the inserted item with status code 201 (Created)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
    }

	@DeleteMapping("/{itemId}")
	public ResponseEntity<?> deleteItemById(
			@PathVariable(value = "itemId") Long itemId) throws ResourceNotFoundException {
        
		// Check if the itemId exists in the database
        if (!itemRepo.findById(itemId).isPresent()) {
        	System.out.println("Is not Present");
            return ResponseEntity.badRequest().build();
        }
		System.out.println("Is Present");
		itemService.deleteItemById(itemId);
        return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteAllItem(){
		itemService.deleteAllItem();
		/*Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);*/
		return ResponseEntity.status(HttpStatus.OK).build();
	}	

	@GetMapping("/{itemId}")
	public ResponseEntity<Item> getItemById(
			@PathVariable(value = "itemId") Long itemId) throws ResourceNotFoundException {
		
		// Check if the itemId exists in the database
        if (!itemRepo.findById(itemId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Item item = itemService.getItemById(itemId);
		return ResponseEntity.ok().body(item);
	}

	@PutMapping("/{itemId}")
	public ResponseEntity<Item> updateItem(
			@PathVariable(value = "itemId") Long itemId,
			@Valid @RequestBody Item itemDetails) throws ResourceNotFoundException {
		if (!itemRepo.findById(itemId).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Item item = itemService.getItemById(itemId);
		item.setItemStatus(itemDetails.getItemStatus());
		final Item updatedItem = itemService.updateItem(item);
		return ResponseEntity.ok(updatedItem);
	}

	@GetMapping
    public ResponseEntity<Page<Item>> getItems(@RequestParam(required = false) ItemStatus itemStatus, 
    		@RequestParam(required = false) String itemEnteredByUser, 
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false) String sortBy) {
		if (sortBy != null) {
			System.out.println("Pagable");
			Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortBy));
			Page<Item> itemPage = itemPage = itemRepo.findAll(pageable);
			 return ResponseEntity.ok(itemPage);
        } else if (itemStatus != null && itemEnteredByUser != null) {
        	System.out.println("Item Status list");        	
        	Pageable pageable = PageRequest.of(page, pageSize, Sort.by("itemId"));
        	Page<Item> itemPage = itemRepo.findItemByItemStatusAndItemEnteredByUser(itemStatus, itemEnteredByUser, pageable);
        	return ResponseEntity.ok(itemPage);
        	
        	/*Page<Item> itemPage = itemService.getItemList();
        	return ResponseEntity.ok(itemPage);*/
        } else {
        	Page<Item> itemPage = itemService.getItemList();
        	return ResponseEntity.ok(itemPage);
        }
    }
}
