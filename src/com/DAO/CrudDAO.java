package com.DAO;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class CrudDAO {
    //objects
    private Connection connection = null;
    private Statement statement = null;

    //variables
    private final String url = "jdbc:mysql://localhost:3306/firstdb",
            username = "root",
            password = "1t5MyS0L";

    private String createTable = "",
            addValuesInTable = "",
            truncateTable = "truncate table  firstdb.",//in method table name adding using parameter
            getDataFromTable = "SELECT * FROM firstdb.";//in method table name adding using parameter

    private Connection databaseConnection() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (Exception ex) {
            System.out.println("Problem while connecting to db");
            System.out.println(ex);
        }
        return null;
    }

    //create
    public void createVaultsTable(Integer[] values, String[] names, String tableName) {
        String truncate = "";

        try {
            connection = databaseConnection();
            statement = connection.createStatement();

            if (tableName.equals("storages") || tableName.equals("supermarkets")) {
                truncate = truncateTable + tableName;

                //create if not exist
                createTable = "CREATE TABLE if not exists`firstdb`.`" + tableName + "` (\n" +
                        "  `" + tableName + "_id` INT NOT NULL AUTO_INCREMENT,\n" +
                        "  `" + tableName + "_name` VARCHAR(45) NOT NULL,\n" +
                        "  `" + tableName + "_value` INT NOT NULL,\n" +
                        "  PRIMARY KEY (`" + tableName + "_id`));";

                statement.executeUpdate(createTable);
                statement.executeUpdate(truncate);

                for (int i = 0; i < values.length; i++) {
                    //create new row with name and value
                    addValuesInTable = "INSERT INTO `firstdb`.`" + tableName + "` (`" + tableName + "_name`, `" + tableName + "_value`) " +
                            "VALUES ('" + names[i] + "', '" + values[i] + "')";
                    statement.executeUpdate(addValuesInTable);
                }
            } else {
                System.out.println("Incorrect table name! Update controller 'create...' passed parameter");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void createDeliveryTable(String tableName, Integer[][] delivery) {

        String[] columnName = new String[]{"storages_id",
                "supermarkets_id", ""};

        switch (tableName) {
            case "delivery_cost":
                columnName[2] = "delivery_cost";
                break;

            case "north_west_corner_delivery":
            case "minimum_element_delivery":
            case "double_points_delivery":
            case "optimized_delivery":
                columnName[2] = "delivery_path";
                break;

            default:
                JOptionPane.showMessageDialog(null, "Wrong parameter" + tableName + " in CrudDAO.createDeliveryTable!" +
                        "\nChoose correct matrix to show");
        }

        try {
            connection = databaseConnection();
            statement = connection.createStatement();

            //create if not exist
            createTable = "CREATE TABLE if not exists`firstdb`.`" + tableName + "` (\n" +
                    "  `" + columnName[0] + "` INT NOT NULL,\n" +
                    "  `" + columnName[1] + "` INT NOT NULL,\n" +
                    "  `" + columnName[2] + "` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`storages_id`,`supermarkets_id`));";

            statement.executeUpdate(createTable);
            statement.executeUpdate(truncateTable + tableName);

            for (int i = 0; i < delivery.length; i++) {
                //create new row with name and value
                for (int j = 0; j < delivery[0].length; j++) {
                    addValuesInTable = "INSERT INTO `firstdb`.`" + tableName + "` (`storages_id`, `supermarkets_id`, `" + columnName[2] + "`) " +
                            "VALUES ('" + i + "', '" + j + "', '" + delivery[i][j] + "')";
                    statement.executeUpdate(addValuesInTable);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    //read
    public ArrayList<ArrayList<String>> getTableData(String tableName) {
        getDataFromTable = "SELECT * FROM firstdb." + tableName + ";";
        ResultSet information;
        String[] columnName = new String[3];

        ArrayList<ArrayList<String>> data = new ArrayList<>();

        try {
            switch (tableName) {
                case "delivery_cost":
                    columnName = new String[]{"storages_id",
                            "supermarkets_id", "delivery_cost"};
                    break;

                case "storages":
                    columnName = new String[]{"storages_id",
                            "storages_name",
                            "storages_value"};
                    break;

                case "supermarkets":
                    columnName = new String[]{"supermarkets_id",
                            "supermarkets_name",
                            "supermarkets_value"};
                    break;

                case "north_west_corner_delivery":
                case "minimum_element_delivery":
                case "double_points_delivery":
                case "optimized_delivery":
                    columnName = new String[]{"storages_id",
                            "supermarkets_id",
                            "delivery_path"};
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Wrong parameter: " + tableName + "in Controller.showDataInTable!" +
                            "\nChoose correct support method");
            }

            System.out.println("");

            connection = databaseConnection();
            statement = connection.createStatement();

            information = statement.executeQuery(getDataFromTable);
            while (information.next()) {
                ArrayList<String> field = new ArrayList<>();
                field.add(information.getString(columnName[0]));
                field.add(information.getString(columnName[1]));
                field.add(information.getString(columnName[2]));

                data.add(field);
            }
            connection.close();
            return data;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    // update
    public void addValueInTable(String name) {
        try {
            connection = databaseConnection();
            statement = connection.createStatement();
            name += "s";

            if (name.equals("storages") || name.equals("supermarkets")) {

                //create new row with name and value
                addValuesInTable = "INSERT INTO `firstdb`.`" + name + "` (`" + name + "_name`, `" + name + "_value`) " +
                        "VALUES ('" + name + "', '0')";
                statement.executeUpdate(addValuesInTable);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}
