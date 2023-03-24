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
    
    @Test
    void Sulfuras() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 0, 80) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(80, app.items[0].quality);
        assertEquals(0, app.items[0].sellIn);
    }
    
    @Nested
    class Brie{
    	String name = "Aged Brie";
    	
    	@Nested
    	@DisplayName("Aged Brie OK")
    	class OK{
    		
    		@ParameterizedTest(name = "sellIn={0} quality={1} update-> sellIn={2} quality={3}")
    		@CsvSource(value = {
    				"0, 80, 0, 80"
    				})
    		void sulfurasTest(int sellIN, int quality, int sellInRes, int quelityRes) {
    	        Item[] items = new Item[] { new Item(name, sellIN, quality) };
    	        GildedRose app = new GildedRose(items);
    	        app.updateQuality();
    	        assertEquals(quelityRes, app.items[0].quality);
    	        assertEquals(sellInRes, app.items[0].sellIn);
    		}
    		
    	}
    	
    	@Nested
    	@DisplayName("Aged Brie KO")
    	class KO{
    		
    	}
    	
    }
    
    @Nested
    class Sulfuras{
    	String name = "Sulfuras, Hand of Ragnaros";
    	
    	@Nested
    	@DisplayName("Sulfuras OK")
    	class OK{
    		
    		@ParameterizedTest(name = "sellIn={0} quality={1} update-> sellIn={2} quality={3}")
    		@CsvSource(value = {
    				"0, 80, -1, 80",
    				"-2, 79, -1, 79"
    				})
    		void sulfurasTest(int sellIN, int quality, int sellInRes, int quelityRes) {
    	        Item[] items = new Item[] { new Item(name, sellIN, quality) };
    	        GildedRose app = new GildedRose(items);
    	        app.updateQuality();
    	        assertEquals(quelityRes, app.items[0].quality);
    	        assertEquals(sellInRes, app.items[0].sellIn);
    		}
    		
    	}
    	
    	@Nested
    	@DisplayName("Sulfuras KO")
    	class KO{
    		
    	}
    	
    }
    

}
