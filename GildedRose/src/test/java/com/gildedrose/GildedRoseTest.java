package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

class GildedRoseTest {

	@Test
	void foo() {
		Item[] items = new Item[] { new Item("foo", 0, 0) };
		GildedRose app = new GildedRose(items);
		app.updateQuality();
		assertEquals("foo", app.items[0].name);
	}

	@ParameterizedTest(name = "sellIn={0} quality={1} update-> sellIn={2} quality={3}")
	@CsvSource(value = { 
			"-1, 40, -2, 42",
			"10, 49, 11, 5",
			"-1, 40, -2, 42" })
	void agedBrieTest(int sellIN, int quality, int sellInRes, int quelityRes) {
		String name = "Sulfuras, Hand of Ragnaros";
		Item[] items = new Item[] { new Item("", sellIN, quality) };
		GildedRose app = new GildedRose(items);
		app.updateQuality();
		assertEquals(quelityRes, app.items[0].quality);
		assertEquals(sellInRes, app.items[0].sellIn);
	}

	

	@ParameterizedTest(name = "sellIn={0} quality={1} update-> sellIn={2} quality={3}")
	@CsvSource(value = { 
			"0, 80, -1, 80",
			"-2, 79, -1, 79" })
	void sulfurasTest(int sellIN, int quality, int sellInRes, int quelityRes) {
		String name = "Aged Brie";
		Item[] items = new Item[] { new Item(name, sellIN, quality) };
		GildedRose app = new GildedRose(items);
		app.updateQuality();
		assertEquals(quelityRes, app.items[0].quality);
		assertEquals(sellInRes, app.items[0].sellIn);
	}

	

	@ParameterizedTest(name = "item= {4} sellIn={0} quality={1} update-> sellIn={2} quality={3}")
	@CsvSource(value = { 
			"0, 80, 0, 80, ''",
			
			})
	void otherTest(int sellIN, int quality, int sellInRes, int quelityRes) {
		String name = "other";
		Item[] items = new Item[] { new Item(name, sellIN, quality) };
		GildedRose app = new GildedRose(items);
		app.updateQuality();
		assertEquals(quelityRes, app.items[0].quality);
		assertEquals(sellInRes, app.items[0].sellIn);
	}

	

	@ParameterizedTest(name = "item= {4} sellIn={0} quality={1} update-> sellIn={2} quality={3}")
	@CsvSource(value = { 
			"0, 80, 0, 80, ''"
			})
	void BackstageTest(int sellIN, int quality, int sellInRes, int quelityRes) {
		String name = "Backstage passes to a TAFKAL80ETC concert";
		Item[] items = new Item[] { new Item(name, sellIN, quality) };
		GildedRose app = new GildedRose(items);
		app.updateQuality();
		assertEquals(quelityRes, app.items[0].quality);
		assertEquals(sellInRes, app.items[0].sellIn);
	}

}


