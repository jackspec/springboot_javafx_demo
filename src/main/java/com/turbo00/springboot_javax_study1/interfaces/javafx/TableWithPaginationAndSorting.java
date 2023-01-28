package com.turbo00.springboot_javax_study1.interfaces.javafx;

import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class TableWithPaginationAndSorting<T> {
    private Page<T> page;
    private TableView<T> tableView;
    private Pagination pagination;

    public Page<T> getPage() {
        return page;
    }

    public TableView<T> getTableView() {
        return tableView;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public TableWithPaginationAndSorting(Page<T> page, TableView<T> tableView, Pagination pagination) {
        this.page = page;
        this.tableView = tableView;
        this.pagination = pagination;
        this.pagination.pageCountProperty().bindBidirectional(page.totalPageProperty());
        updatePagination();
    }

    public void updatePagination() {
        pagination.setPageFactory(pageIndex -> {
            tableView.setItems(FXCollections.observableList(page.getCurrentPageDataList(pageIndex)));
            //a dummy group.当返回一个group时，在界面上不占用位置
            //如果直接返回tableView,则tableView的高度会消失，变成上下挤在一起的一个tableView
            Group group = new Group();
            group.setVisible(false);
            group.setUserData(new String("content of page(zero based):" + pageIndex));
            return group;
        });
    }

    public void refresh() {
        updatePagination();
        page.initialize();
    }

    private HashMap<Label, String> orderMap = new HashMap<>();
    public void addGlobalOrdering(TableColumn<T, ?> column,
                                  Comparator<? super T> ascComparator,
                                  Comparator<? super T> descComparator) {

        /** label setting **/
        // set  column name to label text
        Label label = new Label(column.getText());
        label.setMinWidth(column.getMinWidth());
        label.setMaxWidth(column.getMaxWidth());
        label.setPrefWidth(column.getPrefWidth());

        // by default, set the column has not been sort
        orderMap.put(label, "NO");

        /** column setting **/
        // set built-in column name to null
        column.setText(null);

        // turn off built-in order in TableView
        column.setSortable(false);

        // make label replace original table header
        column.setGraphic(label);

        ImageView ascImg = new ImageView("/image-app/asc.png");
        ImageView descImg = new ImageView("/image-app/desc.png");
        ImageView noImg = new ImageView("/image-app/no.png");

        // set event
        label.setOnMouseClicked(mouseEvent -> {

            // sort only one column every time, so make other columns unsorted
            orderMap.keySet().stream().forEach(lab -> lab.setGraphic(noImg));
            switch (orderMap.get(label)) {
                case "NO":
                    orderMap.replace(label, "ASC");
                    label.setGraphic(ascImg);
                    order(ascComparator);
                    updatePagination();
                    break;
                case "ASC":
                    orderMap.put(label, "DESC");
                    label.setGraphic(descImg);
                    order(descComparator);
                    updatePagination();
                    break;
                case "DESC":
                    orderMap.put(label, "ASC");
                    label.setGraphic(ascImg);
                    order(ascComparator);
                    updatePagination();
                    break;
            }
        });
    }

    /**
     * sort a list by given comparator
     * @param comparator
     */
    private void order(Comparator<? super T> comparator) {
        Collections.sort(page.listPartRow(page.getCurrentPage() * page.getPageSize(), page.getPageSize()), comparator);
    }
}
