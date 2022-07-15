package Com;

import java.util.Random;
import java.util.Scanner;

public class Matrix {
    private String[] supermarket,
            storage;
    private int[] order,
            storage_balances;

    private int[][] matrix_delivery_north_west_corner,
            matrix_delivery_minimum_element,
            matrix_delivery_double_points,
            optimized_matrix,
            matrix_coast;


    Scanner scanner = new Scanner(System.in);
    Random rand = new Random();
    Support_methods support_methods = new Support_methods();

    Matrix(int width, int length) {
        int choose = 0;

        create_supermarkets();
        create_storage();

        create_coast_matrix(storage.length, supermarket.length);

        matrix_output(matrix_coast, "coast");
        demand_supply();

        System.out.println("------next method------");
        matrix_delivery_north_west_corner = support_methods.north_west_corner(order, storage_balances);
        matrix_output(matrix_delivery_north_west_corner, "north west delivery");
        transportation_amount(matrix_delivery_north_west_corner, matrix_coast);

        System.out.println("------next method------");
        matrix_delivery_minimum_element = support_methods.minimum_element(order, storage_balances, matrix_coast);
        matrix_output(matrix_delivery_minimum_element, "minimum element delivery");
        transportation_amount(matrix_delivery_minimum_element, matrix_coast);

        System.out.println("------next method------");
        matrix_delivery_double_points = support_methods.double_points(order, storage_balances, matrix_coast);
        matrix_output(matrix_delivery_double_points, "double points delivery");
        transportation_amount(matrix_delivery_double_points, matrix_coast);

        optimized_matrix = support_methods.potential_method(order, storage_balances, matrix_delivery_north_west_corner, matrix_coast);
        matrix_output(optimized_matrix, "optimized delivery");

        transportation_amount(optimized_matrix, matrix_coast);

    }

    boolean demand_supply() {
        boolean equals = false;
        int order_sum = 0,
                demand_sum = 0,
                difference = 0;

        for (int i = 0; i < storage_balances.length; i++)
            demand_sum += storage_balances[i];

        for (int i = 0; i < order.length; i++)
            order_sum += order[i];

        if (demand_sum != order_sum) {

            if (demand_sum > order_sum) {
                System.out.println("\nDemand is bigger than order " +
                        "\n demand:" + demand_sum + " order:" + order_sum);

                difference = demand_sum - order_sum;
                change_matrix(difference, 0, order, supermarket);

            } else {
                System.out.println("\nOrder is bigger than demand \n demand:" + demand_sum + " order:" + order_sum);

                difference = order_sum - demand_sum;
                change_matrix(difference, 1, storage_balances, storage);
            }

            System.out.print("\n!!!New matrix!!!");
            matrix_output(matrix_coast, "coast");
        }
        equals = true;

        return equals;
    }

    void change_matrix(int difference, int kind, int[] values, String[] names) {
        int[][] new_delivery_matrix;
        String fictive;

        if (kind == 0) {
            new_delivery_matrix = new int[matrix_coast.length][matrix_coast[0].length + 1];
            fictive = "supermarket";
        } else {
            new_delivery_matrix = new int[matrix_coast.length + 1][matrix_coast[0].length];
            fictive = "storage";
        }

        int[] temp_storage = new int[values.length + 1];
        String[] temp_name = new String[names.length + 1];

        for (int i = 0; i < temp_storage.length; i++) {

            if (i == (temp_storage.length - 1)) {
                temp_storage[i] = difference;
                temp_name[i] = "Fictive_" + fictive;

            } else {
                temp_storage[i] = values[i];
                temp_name[i] = names[i];
            }
        }

        if (kind == 0) {
            this.order = new int[temp_storage.length];
            this.supermarket = new String[temp_name.length];

            for (int i = 0; i < temp_storage.length; i++) {
                this.order[i] = temp_storage[i];
                this.supermarket[i] = temp_name[i];
            }
        } else {
            this.storage_balances = new int[temp_storage.length];
            this.storage = new String[temp_name.length];

            for (int i = 0; i < temp_storage.length; i++) {
                this.storage_balances[i] = temp_storage[i];
                this.storage[i] = temp_name[i];
            }
        }

        for (int i = 0; i < new_delivery_matrix.length; i++) {
            for (int j = 0; j < new_delivery_matrix[0].length; j++) {
                switch (kind) {
                    case 0:
                        if (j == new_delivery_matrix[0].length - 1)
                            new_delivery_matrix[i][j] = 0;
                        else new_delivery_matrix[i][j] = matrix_coast[i][j];
                        break;

                    case 1:
                        if (i == new_delivery_matrix.length - 1)
                            new_delivery_matrix[i][j] = 0;
                        else new_delivery_matrix[i][j] = matrix_coast[i][j];
                }
            }
        }

        this.matrix_coast = new int[new_delivery_matrix.length][new_delivery_matrix[0].length];
        for (int i = 0; i < matrix_coast.length; i++) {
            for (int j = 0; j < matrix_coast[0].length; j++) {
                matrix_coast[i][j] = new_delivery_matrix[i][j];
            }
        }
    }

    void matrix_output(int[][] matrix, String kind_of_matrix) {
        System.out.println("\n------Matrix " + kind_of_matrix + "------");
        for (int i = 0; i < supermarket.length; i++)
            System.out.print(supermarket[i] + " ");

        System.out.println();
        for (int i = 0; i < order.length; i++)
            System.out.print(order[i] + " ");

        System.out.println("\n----");

        for (int i = 0; i < matrix.length; i++) {
            System.out.print(storage[i] + " " + storage_balances[i] + " |");
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

    }

    private void create_coast_matrix(int count_storage, int count_supermarkets) {
        matrix_coast = new int[count_storage][count_supermarkets];

        System.out.println("\nHow you want to fill delivery matrix? \nEnter 0 if random and 1 if input");
        int choice = scanner.nextInt();

        while (choice != 0 && choice != 1) {
            System.out.println("Input correct choice");
            choice = scanner.nextInt();
        }

        if (choice == 1) {
            //input delivery value
            for (int i = 0; i < count_storage; i++) {
                for (int j = 0; j < count_supermarkets; j++) {

                    System.out.println("Enter count delivery from  storage " + storage[i] +
                            " to " + supermarket[j] + " supermarket");
                    matrix_coast[i][j] = scanner.nextInt();
                }
            }
        } else {
            //random delivery value
            for (int i = 0; i < count_storage; i++) {
                for (int j = 0; j < count_supermarkets; j++) {
                    matrix_coast[i][j] = rand.nextInt(1, 10);
                }
            }
        }
    }

    private void create_supermarkets() {
        System.out.println("Enter supermarkets count");
        int count_supermarkets = scanner.nextInt();
        int value;
        this.supermarket = new String[count_supermarkets];
        this.order = new int[count_supermarkets];

        for (int i = 0; i < count_supermarkets; i++) {
            System.out.println("\nEnter name supermarket");
            this.supermarket[i] = scanner.next();

            System.out.println("Enter order count");
            value = scanner.nextInt();
            this.order[i] = value;
        }
    }

    private void create_storage() {
        System.out.println("\nEnter storage count");
        int count_storage = scanner.nextInt();
        int value;
        this.storage = new String[count_storage];
        this.storage_balances = new int[count_storage];

        for (int i = 0; i < count_storage; i++) {
            System.out.println("\nEnter name storage");
            this.storage[i] = scanner.next();

            System.out.println("Enter count things at storage");
            value = scanner.nextInt();
            this.storage_balances[i] = value;
        }
    }

    int transportation_amount(int[][] matrix_delivery, int[][] matrix_coast) {
        int transportation_amount = 0;

        for (int i = 0; i < matrix_delivery.length; i++) {
            for (int j = 0; j < matrix_delivery[0].length; j++) {
                if (matrix_delivery[i][j] != 0)
                    transportation_amount += matrix_delivery[i][j] * matrix_coast[i][j];
            }
        }

        System.out.println("transportation_amount:" + transportation_amount);
        return transportation_amount;
    }

}
