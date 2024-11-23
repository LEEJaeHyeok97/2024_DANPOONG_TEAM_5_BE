package com.jangburich.domain.menu.domain;

import com.jangburich.domain.common.BaseEntity;
import com.jangburich.domain.store.domain.Store;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import javax.print.attribute.standard.MediaSize.NA;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "price")
	private Integer price;

	@Column(name = "is_signature_menu")
	private Boolean isSignatureMenu;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	private Store store;

	public static Menu create(String name, String description, String imageUrl, Integer price, Store store) {
		Menu menu = new Menu();
		menu.setName(name);
		menu.setDescription(description);
		menu.setImageUrl(imageUrl);
		menu.setPrice(price);
		menu.setStore(store);
		return menu;
	}
}
