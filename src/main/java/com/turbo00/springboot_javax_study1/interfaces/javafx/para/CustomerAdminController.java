package com.turbo00.springboot_javax_study1.interfaces.javafx.para;

import com.turbo00.springboot_javax_study1.application.para.CustomerService;
import com.turbo00.springboot_javax_study1.domain.para.Customer;
import com.turbo00.springboot_javax_study1.domain.para.CustomerRepository;
import com.turbo00.springboot_javax_study1.infrastructure.persistence.hibernate.JpaCriteriaHolder;
import com.turbo00.springboot_javax_study1.interfaces.javafx.TableWithPaginationAndSorting;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Setter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.turbo00.springboot_javax_study1.SpringbootJavaxStudy1Application.loadFxml;

@Controller
public class CustomerAdminController implements Initializable {
    @Setter
    BorderPane parentContainer;

    @FXML
    Pagination pagi;
    @FXML
    TableView<Customer> tblCustomer;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerDialogController customerDialogController;

    TableWithPaginationAndSorting<Customer> table;
    int pageSize = 10;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblCustomer.setPrefWidth(parentContainer.getScene().getWidth());
        parentContainer.getScene().widthProperty().addListener(
                (observableValue, oldValue, newValue) -> tblCustomer.setPrefWidth(newValue.doubleValue())
        );

        tblCustomer.setPrefHeight(parentContainer.getScene().getHeight());
        parentContainer.getScene().heightProperty().addListener(
                (observableValue, oldValue, newValue) -> tblCustomer.setPrefHeight(newValue.doubleValue())
        );

        TableColumn<Customer, String> idCol = new TableColumn<>("id");
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        idCol.setVisible(false);
        TableColumn<Customer, String> nameCol = new TableColumn<>("姓名");
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        TableColumn<Customer, String> contactCol = new TableColumn<>("联系方式");
        contactCol.setCellValueFactory(new PropertyValueFactory("contact"));
        tblCustomer.getColumns().addAll(nameCol, contactCol);
        tblCustomer.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //create page object
        CustomerPage customerPage = new CustomerPage(getJpaCriteriaHolder(false), getJpaCriteriaHolder(true), pageSize);

        //get first page data
        tblCustomer.setItems(FXCollections.observableList(customerPage.listPartRow(0, customerPage.getPageSize())));

        //add pagination into table
        table = new TableWithPaginationAndSorting<>(customerPage, tblCustomer, pagi);

        //global sorting by column name
        Comparator<Customer> asc = (o1, o2) -> o1.getName().compareTo(o2.getName());
        Comparator<Customer> desc = (o1, o2) -> o2.getName().compareTo(o1.getName());
        table.addGlobalOrdering(tblCustomer.getColumns().get(1), asc, desc);
    }


    public JpaCriteriaHolder getJpaCriteriaHolder(boolean isCount) {
        Session session = customerRepository.getSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq;
        if (isCount) {
            cq = cb.createQuery(Long.class);
        } else {
            cq = cb.createQuery(Customer.class);
        }
        Root<Customer> root = cq.from(Customer.class);

        JpaCriteriaHolder jpaCriteriaHolder = new JpaCriteriaHolder();
        jpaCriteriaHolder.setSession(session);
        jpaCriteriaHolder.setCriteriaBuilder(cb);
        jpaCriteriaHolder.setCriteriaQuery(cq);
        jpaCriteriaHolder.setRoot(root);

        return jpaCriteriaHolder;
    }

    public void btnAddClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = loadFxml("/fxml/para/customerDialog.fxml");
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.setTitle("客户管理-新增");
        stage.initOwner(parentContainer.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        customerDialogController.setParentController(this);
        customerDialogController.setCustomer(null);
        stage.showAndWait();
    }

    /**
     * 重载数据
     */
    public void refresh() {
        int lastPageIndex = pagi.getCurrentPageIndex();
        table.refresh();
        pagi.setCurrentPageIndex(lastPageIndex);
    }

    public void btnDeleteClicked(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认");
        alert.setHeaderText("正在进行删除操作");
        alert.setContentText("真的要删除吗?");

        Customer customer = tblCustomer.getSelectionModel().getSelectedItem();
        if (customer != null) {
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                customerService.deleteCustomer(customer);
                int lastPageIndex = pagi.getCurrentPageIndex();
                table.refresh();
                pagi.setCurrentPageIndex(lastPageIndex);
            }
        }
    }

    public void btnModifyClicked(ActionEvent actionEvent) throws IOException {
        Customer customer = tblCustomer.getSelectionModel().getSelectedItem();
        if (customer != null) {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = loadFxml("/fxml/para/customerDialog.fxml");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.setTitle("客户管理-修改");
            stage.initOwner(parentContainer.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);

            customerDialogController.setParentController(this);
            customerDialogController.setCustomer(customer);

            stage.showAndWait();
        }
    }

    public void btnRefreshClicked(ActionEvent actionEvent) {
        table.refresh();
        pagi.setCurrentPageIndex(0);
    }
}
