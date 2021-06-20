package asset.tracker.problem.domain;

import java.time.LocalDate;

/**
 * A class for modeling asset objects.
 * 
 * @author Quack
 *
 */
public final class Asset {

	private final int id;
	private final int userId;
	private final String itemName;
	private final String description;
	private final double price;
	private final LocalDate dateObtained;

	/**
	 * Asset constructor.
	 * 
	 * @param id           the asset's id.
	 * @param userId       id of the asset's owner.
	 * @param itemName     the name of the asset.
	 * @param description  a description of the asset.
	 * @param price        the asset's price.
	 * @param dateObtained the date it was obtained.
	 */
	public Asset(int id, int userId, String itemName, String description, double price, LocalDate dateObtained) {
		this.id = id;
		this.userId = userId;
		this.itemName = itemName;
		this.description = description;
		this.price = price;
		this.dateObtained = dateObtained;
	}

	/**
	 * Gets the asset's id.
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the owner's id of the asset.
	 * 
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Gets the asset's name.
	 * 
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * Gets the asset's description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the asset's price.
	 * 
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Gets the asset's date obtained.
	 * 
	 * @return the dateObtained
	 */
	public LocalDate getDateObtained() {
		return dateObtained;
	}

}