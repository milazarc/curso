package com.gildedrose;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;


class GildedRoseTest {

	@ParameterizedTest(name = "SellIn: {0} -> {2}, Quality: {1} -> {3}")
	@CsvSource({
		"2, 0, 1, 1",
		"-1, 48, -2, 50",
		"2, 50, 1, 50",
		"-2, 49, -3, 50",
		"1, 1, 0, 2",
		})
	void productAgedBrieTest(int sellIn, int quality, int sellInResult, int qualityResult) {
		String name = "Aged Brie";
		Item product = new Item(name, sellIn, quality);
        GildedRose app = new GildedRose(new Item[] {
        		product
        });
        app.updateQuality();
        assertAll(name,
        		() -> assertEquals(name, product.name, "name"),
        		() -> assertEquals(sellInResult, product.sellIn, "sellIn"),
        		() -> assertEquals(qualityResult, product.quality, "quality")
        		);
	}

	@ParameterizedTest(name = "SellIn: {0} -> {2}, Quality: {1} -> {3}")
	@CsvSource({
		"1, 80, 1, 80",
		"0, 1, 0, 1",
		"-1, 1, -1, 1",
		})
	void productSulfurasTest(int sellIn, int quality, int sellInResult, int qualityResult) {
		String name = "Sulfuras, Hand of Ragnaros";
		Item product = new Item(name, sellIn, quality);
        GildedRose app = new GildedRose(new Item[] {
        		product
        });
        app.updateQuality();
        assertAll(name,
        		() -> assertEquals(name, product.name, "name"),
        		() -> assertEquals(sellInResult, product.sellIn, "sellIn"),
        		() -> assertEquals(qualityResult, product.quality, "quality")
        		);
	}

	@ParameterizedTest(name = "SellIn: {0} -> {2}, Quality: {1} -> {3}")
	@CsvSource({
		"11, 0, 10, 1",
		"4, 10, 3, 13",
		"1, 10, 0, 13",
		"10, 10, 9, 12",
		"7, 1, 6, 3",
		"7, 49, 6, 50",
		"5, 3, 4, 6",
		"0, 3, -1, 0",
		"-1, 3, -2, 0",
		})
	void productPassesTest(int sellIn, int quality, int sellInResult, int qualityResult) {
		String name = "Backstage passes to a TAFKAL80ETC concert";
		Item product = new Item(name, sellIn, quality);
        GildedRose app = new GildedRose(new Item[] {
        		product
        });
        app.updateQuality();
        assertEquals(name + ", " + sellInResult + ", " + qualityResult, product.toString());
//        assertAll(name,
//        		() -> assertEquals(name, product.name, "name"),
//        		() -> assertEquals(sellInResult, product.sellIn, "sellIn"),
//        		() -> assertEquals(qualityResult, product.quality, "quality")
//        		);
	}

	@ParameterizedTest(name = "SellIn: {0} -> {2}, Quality: {1} -> {3}")
	@CsvSource({
		"11, 10, 10, 9",
		"7, 1, 6, 0",
//		"5, -5, 4, 0",
		"0, 3, -1, 1",
		})
	void otherProductTest(int sellIn, int quality, int sellInResult, int qualityResult) {
		String name = "Normal Product";
		Item product = new Item(name, sellIn, quality);
        GildedRose app = new GildedRose(new Item[] {
        		product
        });
        app.updateQuality();
        assertAll(name,
        		() -> assertEquals(name, product.name, "name"),
        		() -> assertEquals(sellInResult, product.sellIn, "sellIn"),
        		() -> assertEquals(qualityResult, product.quality, "quality")
        		);
	}

	@ParameterizedTest(name = "SellIn: {0} -> {2}, Quality: {1} -> {3}")
	@CsvSource({
		"11, 10, 10, 8",
		"7, 1, 6, 0",
		"-5, 10, -6, 6",
		"0, 3, -1, 0",
		})
	void productConjuredTest(int sellIn, int quality, int sellInResult, int qualityResult) {
		String name = "Conjured Mana Cake";
		Item product = new Item(name, sellIn, quality);
        GildedRose app = new GildedRose(new Item[] {
        		product
        });
        app.updateQuality();
        assertAll(name,
        		() -> assertEquals(name, product.name, "name"),
        		() -> assertEquals(sellInResult, product.sellIn, "sellIn"),
        		() -> assertEquals(qualityResult, product.quality, "quality")
        		);
	}

}
