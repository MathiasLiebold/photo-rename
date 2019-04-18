package de.liebold.photorename.ui.javafx.component;

import java.util.List;

import de.liebold.photorename.logic.bean.FileInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FileTableView implements CustomUI {

	/**
	 * @see: http://docs.oracle.com/javafx/2/ui_controls/table-view.htm
	 */
	private TableView root = new TableView();

	public FileTableView(List<FileInfo> fileInfos) {
		root.getColumns().add(getTableColumn("From", "originalName"));
		root.getColumns().add(getTableColumn("To (Preview) ", "proposedName"));

		final ObservableList<FileInfo> data = FXCollections.observableArrayList(fileInfos);
		root.setItems(data);
	}

	private TableColumn getTableColumn(String title, String attribute) {
		TableColumn tableColumn = new TableColumn(title);

		tableColumn.setCellValueFactory(new PropertyValueFactory<FileInfo, String>(attribute));
		tableColumn.setMinWidth(450);

		return tableColumn;

	}

	@Override
	public Node draw() {
		return root;
	}

}
