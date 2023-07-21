package com.myapp.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.myapp.ItemStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Item")
public class Item {

	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private Long itemId;

	private String itemName;
	private String itemEnteredByUser;
	
	private Date itemEnteredDate;
	private Double itemBuyingPrice;
	private Double itemSellingPrice;
	private Date itemLastModifiedDate;
	
	private String itemLastModifiedByUser;
	
	private ItemStatus itemStatus;
}
