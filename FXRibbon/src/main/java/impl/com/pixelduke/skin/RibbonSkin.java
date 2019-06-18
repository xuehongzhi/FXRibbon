/*
 *  Copyright (c) 2016, 2018 Pixel Duke (Pedro Duque Vieira - www.pixelduke.com)
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *    * Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *    * Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *    * Neither the name of Pixel Duke, any associated website, nor the
 *  names of its contributors may be used to endorse or promote products
 *  derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL PIXEL DUKE BE LIABLE FOR ANY
 *  DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package impl.com.pixelduke.skin;

import com.pixelduke.control.Ribbon;
import com.pixelduke.control.ribbon.RibbonTab;
import com.pixelduke.events.RibbonTabClickEvent;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


import java.util.Collection;

public class RibbonSkin extends SkinBase<Ribbon> {

    private final AnchorPane outerContainer;
    private final TabPane tabPane;

    /**
     * Constructor for all SkinBase instances.
     *
     * @param control The control for which this Skin should attach to.
     */
    public RibbonSkin(Ribbon control) {
        super(control);
        tabPane = new TabPane();
        //outerContainer = new VBox();
        outerContainer = new AnchorPane();

        final Button addButton = new Button("Btn1");
        addButton.setPrefWidth(40);
        addButton.setPrefHeight(15);

        AnchorPane.setTopAnchor(tabPane, .0);
        AnchorPane.setLeftAnchor(tabPane, 5.0);
        AnchorPane.setRightAnchor(tabPane, 5.0);
        AnchorPane.setTopAnchor(addButton, 5.0);
        AnchorPane.setRightAnchor(addButton, 10.0);


        control.getTabs().addListener(this::tabsChanged);
        updateAddedRibbonTabs(control.getTabs());

        outerContainer.getStyleClass().setAll("outer-container");
        outerContainer.getChildren().addAll( tabPane, addButton);
        getChildren().add(outerContainer);

        control.selectedRibbonTabProperty().addListener((observable, oldValue, newValue) -> tabPane.getSelectionModel().select((RibbonTab)newValue));
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> control.setSelectedRibbonTab((RibbonTab)tabPane.getSelectionModel().getSelectedItem()));
        tabPane.setOnMouseClicked(event -> {
            EventHandler<RibbonTabClickEvent>  handler = control.getOnTabClick();
            if(handler != null) {
                handler.handle(new RibbonTabClickEvent(tabPane.getSelectionModel().getSelectedIndex()));
            }
        });

        tabPane.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                EventHandler<MouseEvent>  handler = control.getOnTabPaneExited();
                if(handler != null) {
                    handler.handle(event);
                }
            }
        });

    }

    private void updateAddedRibbonTabs(Collection<? extends RibbonTab> ribbonTabs) {
        for (RibbonTab ribbonTab : ribbonTabs)
            tabPane.getTabs().add(ribbonTab);
    }

    private void tabsChanged(ListChangeListener.Change<? extends RibbonTab> changed) {
        while(changed.next())
        {
            if (changed.wasAdded())
            {
                updateAddedRibbonTabs(changed.getAddedSubList());
            }
            if(changed.wasRemoved())
            {
                for (RibbonTab ribbonTab : changed.getRemoved())
                    tabPane.getTabs().remove(ribbonTab);
            }
        }
    }

}
