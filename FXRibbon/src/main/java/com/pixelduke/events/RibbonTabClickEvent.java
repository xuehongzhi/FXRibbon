/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pixelduke.events;
import javafx.event.Event;
import javafx.event.EventType;
/**
 *
 * @author XueHZ
 */
public class RibbonTabClickEvent extends Event{
    
    private int tabIndex;
    public static  EventType<RibbonTabClickEvent> ribbonTabClick = new EventType<>("RibbonTabClickEvent");
    public RibbonTabClickEvent(EventType<? extends Event> et) {
        super(et);
    }
    
    public RibbonTabClickEvent(int index) {
        this(ribbonTabClick);
        tabIndex = index;
    }
    
    public int getTabIndex(){
        return tabIndex;
    }
}
