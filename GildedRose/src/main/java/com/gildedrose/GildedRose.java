package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }
    
    private int addQuality(int value) {
    	if (value > 0 && value <= 50) {
    		return value;
    	}else if(value > 50) {
    		return 50;
    	}else return 0;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
        	
        	
        	
        	if(items[i].name.equals("Aged Brie")) {
        		
        		if(items[i].sellIn > 0) {
        			items[i].quality = addQuality(items[i].quality + 1);
        		}else {
        			items[i].quality = addQuality(items[i].quality + 2);
        		}	
        		
        	}else if(items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
        		
        		//gestionar error
        		
        	}else if(items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
        		
        		if(items[i].sellIn <= 0) {
        			items[i].quality = 0;
        		}else if(items[i].sellIn > 0 && items[i].sellIn <= 5){
        			items[i].quality = addQuality(items[i].quality + 3);
        		}else if(items[i].sellIn > 5 && items[i].sellIn <= 10){
        			items[i].quality = addQuality(items[i].quality + 2);
        		}else {
        			items[i].quality = addQuality(items[i].quality + 1);
        		}
        		
        	}else if(items[i].name.equals("Conjured Mana Cake")) {
        		
        		if(items[i].sellIn <= 0) {
        			items[i].quality = addQuality(items[i].quality - 4);
        		}else{
        			items[i].quality = addQuality(items[i].quality - 2);
        		}
        		
        	}else {
        		
        		if(items[i].sellIn <= 0) {
        			items[i].quality = addQuality(items[i].quality - 2);
        		}else{
        			items[i].quality = addQuality(items[i].quality - 1);
        		}
        		
        	}
        	
        	if(!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
            	items[i].sellIn = items[i].sellIn - 1;
        	}

            
        }
    }
}