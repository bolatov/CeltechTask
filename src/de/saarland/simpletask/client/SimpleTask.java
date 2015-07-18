package de.saarland.simpletask.client;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SimpleTask implements EntryPoint {

	private HorizontalPanel mainPanel = new HorizontalPanel();
	private FlexTable flexTable = new FlexTable();
	private Tree ontologiesTree = new Tree();
	private Tree saTree = new Tree();
	private Tree sesTree = new Tree();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		String[] sessionNames = { "LARGO Carney Petitioner - Map 1 (largo)",
				"LARGO Carney Petitioner - Map 2 (largo)",
				"ARGUNAUT - Map 1 (argunaut)" };

		flexTable.addStyleName("flexTable");
		flexTable.getRowFormatter().addStyleName(0, "flexTableHeader");
		flexTable.getRowFormatter().addStyleName(2, "flexTableHeader");
		flexTable.getRowFormatter().addStyleName(4, "flexTableHeader");

		// Top left.
		flexTable.setText(0, 0, "Agent");

		List<String> COLORS = Arrays.asList(
				"largo-default (largo, read/write)",
				"largo-v2 (largo, read/write)",
				"deeploop-default (argunaut, -/-)");

		TextCell textCell = new TextCell();
		CellList<String> cellList = new CellList<String>(textCell);
		cellList.setRowData(0, COLORS);
		cellList.setSize("350px", "100px");
		cellList.setStyleName("flexTable");


		final SingleSelectionModel<String> ssm = new SingleSelectionModel<String>();
		cellList.setSelectionModel(ssm);
		ssm.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel();
				Button button = new Button("Push to hide me");
//				simplePopup.setWidget(new Label("Click outside of this popup to close it"));
				simplePopup.setWidget(button);
				button.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						simplePopup.hide();
					}
				});
				simplePopup.show();
			}

		});
		flexTable.setWidget(1, 0, cellList);


		// Middle left.
		for (int i = 0; i < 3; i++) {
			TreeItem addItem = ontologiesTree.addTextItem(sessionNames[i]);
			addItem.addTextItem("largo-default");
			addItem.addTextItem("largo-test");
		}
		ontologiesTree.setAnimationEnabled(true);
		ScrollPanel scrollPanel = new ScrollPanel(ontologiesTree);
		scrollPanel.setHeight("150px");
		flexTable.setText(2, 0, "Ontologies -> Agents");
		flexTable.setWidget(3, 0, scrollPanel);

		// Bottom left.
		flexTable.setText(4, 0, "Session -> Agents");
		for (int i = 0; i < 3; i++) {
			TreeItem saItem = saTree.addTextItem(sessionNames[i]);
			saItem.addTextItem("largo-default");
			saItem.addTextItem("largo-test");
		}
		ScrollPanel scrollPanel2 = new ScrollPanel(saTree);
		scrollPanel2.setHeight("150px");
		flexTable.setWidget(5, 0, scrollPanel2);

		// Right
		flexTable.setText(0, 1, "Sessions");
		for (int i = 0; i < 3; i++) {
			TreeItem saItem = sesTree.addTextItem(sessionNames[i]);
			saItem.addTextItem("largo-default");
			saItem.addTextItem("largo-test");
		}
		FlexCellFormatter cellFormatter = flexTable.getFlexCellFormatter();
		cellFormatter.setRowSpan(1, 1, 5);
		cellFormatter.setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_TOP);
		cellFormatter.setWidth(0, 1, "150px");
		cellFormatter.setWidth(1, 1, "150px");
		flexTable.setWidget(1, 1, sesTree);

		mainPanel.add(flexTable);
		RootPanel.get("simpleTask").add(mainPanel);
	}
}
