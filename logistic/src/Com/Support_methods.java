package Com;

import java.util.*;

public class Support_methods {
    int[][] optimized_matrix,
            array;
    int[] temp_order,
            temp_storage;
    Integer[] storage_potential,
            supermarket_potential;

    int[][] potential_method(int[] orders, int[] storage_balances,
                             int[][] matrix_delivery, int[][] matrix_coast) {
        optimized_matrix = new int[matrix_delivery.length][matrix_delivery[0].length];

        System.out.println("\n-----------Potential method----");
        int[] start_cell;
        boolean need_optimize = true;

        for (int i = 0; i < matrix_delivery.length; i++) {
            for (int j = 0; j < matrix_delivery[0].length; j++) {
                optimized_matrix[i][j] = matrix_delivery[i][j];
            }
        }

        while (need_optimize && potential_possibility(storage_balances.length, orders.length, optimized_matrix)) {
            find_potential(optimized_matrix, matrix_coast);
            start_cell = indirect_cost(optimized_matrix, matrix_coast);

            if (start_cell[0] == -1 && start_cell[1] == -1) {
                need_optimize = false;
            } else {
                optimized_matrix = recalculate_path(start_cell, optimized_matrix);
            }
        }

        System.out.println("-----optimized matrix-------");
        for (int i = 0; i < optimized_matrix.length; i++) {
            for (int j = 0; j < optimized_matrix[0].length; j++) {
                System.out.print(optimized_matrix[i][j] + " ");
            }
            System.out.println();
        }
        return optimized_matrix;
    }

    //support methods
    int[][] north_west_corner(int[] orders, int[] storage_balances) {
        fill_temp_data(orders, storage_balances);

        int i = 0,
                j = 0;

        while (i < temp_storage.length && j < temp_order.length) {
            if ((temp_storage[i] <= temp_order[j])) {
                optimized_matrix[i][j] = fill_cell(i, j);
                i++;

            } else {
                optimized_matrix[i][j] = fill_cell(i, j);
                j++;
            }
        }

        return optimized_matrix;
    }

    int[][] minimum_element(int[] orders, int[] storage_balances, int[][] matrix_coast) {
        int min;

        fill_temp_data(orders, storage_balances);

        while (!storage_order_sum()) {
            min = find_valid_cells(matrix_coast);

            for (int i = 0; i < optimized_matrix.length; i++) {
                for (int j = 0; j < optimized_matrix[0].length; j++) {
                    if (matrix_coast[i][j] == min)
                        this.optimized_matrix[i][j] = fill_cell(i, j);

                }
            }
        }
        return optimized_matrix;
    }

    int[][] double_points(int[] orders, int[] storage_balances, int[][] matrix_coast) {

        int[][] points;
        int[] min_row = new int[matrix_coast.length],
                min_column = new int[matrix_coast[0].length];

        boolean double_points = false;

        fill_temp_data(orders, storage_balances);

        for (int i = 0; i < matrix_coast.length; i++) {
            for (int j = 0; j < matrix_coast[0].length; j++) {
            }
        }

        while (!storage_order_sum()) {
            double_points = false;

            //find minimum in rows
            min_row = rows_min(matrix_coast);
            //find minimum in columns
            min_column = columns_min(matrix_coast);

            points = set_points(min_row, min_column, matrix_coast);

            for (int i = 0; i < points.length; i++) {
                for (int j = 0; j < points[0].length; j++) {
                    if (points[i][j] == 2)
                        double_points = true;
                }
            }

            for (int i = 0; i < matrix_coast.length; i++) {
                for (int j = 0; j < matrix_coast[0].length; j++) {
                    if (double_points) {
                        if (points[i][j] == 2) {
                            optimized_matrix[i][j] = fill_cell(i, j);
                        }
                    } else {
                        if (points[i][j] == 1 && optimized_matrix[i][j] == 0) {
                            optimized_matrix[i][j] = fill_cell(i, j);
                        }
                    }
                }
            }

        }
        return optimized_matrix;
    }

    //helper methods
    int[][] set_points(int[] min_row, int[] min_column, int[][] matrix_coast) {
        int[][] points = new int[matrix_coast.length][matrix_coast[0].length];

        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[0].length; j++) {
                if (min_row[i] == matrix_coast[i][j]) {
                    points[i][j] += 1;
                }
                if (min_column[j] == matrix_coast[i][j]) {
                    points[i][j] += 1;
                }
            }
        }

        return points;
    }

    int[] rows_min(int[][] matrix_coast) {
        int[] min_row = new int[matrix_coast.length];

        for (int i = 0; i < matrix_coast.length; i++) {
            if (temp_storage[i] == 0) {
                min_row[i] = -1;

            } else {
                if (temp_order[0] != 0)
                    min_row[i] = matrix_coast[i][0];
                else min_row[i] = -1;

                for (int j = 0; j < matrix_coast[0].length; j++) {
                    if (temp_order[j] != 0) {
                        if (min_row[i] > matrix_coast[i][j]) {
                            min_row[i] = matrix_coast[i][j];
                        } else if (min_row[i] == -1) {
                            min_row[i] = matrix_coast[i][j];
                        }
                    }

                }
            }
        }

        return min_row;
    }

    int[] columns_min(int[][] matrix_coast) {
        int[] min_column = new int[matrix_coast[0].length];

        for (int i = 0; i < matrix_coast[0].length; i++) {
            if (temp_order[i] == 0) {
                min_column[i] = -1;
            } else {

                if (temp_storage[0] != 0) {
                    min_column[i] = matrix_coast[0][i];
                } else min_column[i] = -1;

                for (int j = 0; j < matrix_coast.length; j++) {
                    if (temp_storage[j] != 0) {
                        if (min_column[i] > matrix_coast[j][i]) {
                            min_column[i] = matrix_coast[j][i];
                        } else if (min_column[i] == -1) {
                            min_column[i] = matrix_coast[j][i];
                        }
                    }
                }
            }
        }

        return min_column;
    }

    boolean storage_order_sum() {
        int demand_sum = 0,
                order_sum = 0;

        boolean empty = false;

        // data in arrays can change, rewrite it
        for (int i = 0; i < temp_storage.length; i++) {
            demand_sum += this.temp_storage[i];
        }

        for (int i = 0; i < temp_order.length; i++) {
            order_sum += this.temp_order[i];
        }

        if (demand_sum == 0 || order_sum == 0) {
            empty = true;
        }

        return empty;
    }

    int find_valid_cells(int[][] matrix) {
        array = new int[matrix.length][matrix[0].length];

        for (int i = 0; i < array.length; i++) {
            if (this.temp_storage[i] == 0) { // find empty storage
                for (int j = 0; j < array[0].length; j++) {
                    this.array[i][j] = -1;// fill cells in this line (-1)
                }
            } else
                for (int j = 0; j < array[0].length; j++) {
                    if (this.temp_order[j] == 0) {// find empty order
                        this.array[i][j] = -1;// fill this cell (-1)
                    } else {
                        this.array[i][j] = matrix[i][j];
                    }
                }
        }

        int min = matrix[0][0];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (array[i][j] != -1) {//find cell with (-1) that`s meaning that cell not uses
                    if (min > array[i][j]) {//if cell is not empty, check it
                        min = array[i][j];
                    } else if (min == -1)//min = first cell, check min(first cell) is empty?
                        min = array[i][j];
                }
            }
        }

        return min;
    }

    int fill_cell(int i, int j) {
        int value = 0;

        //if storage balance smaller then order
        if ((this.temp_storage[i] <= this.temp_order[j])) {
            value += this.temp_storage[i]; //write all storage balance in cell
            this.temp_order[j] -= this.temp_storage[i];//degree balance from order, because we fill this order
            this.temp_storage[i] = 0;// now this storage empty

        } else {

            value += this.temp_order[j];//write all order in cell
            this.temp_storage[i] -= temp_order[j];//degree order from balance, because we fill this order
            this.temp_order[j] = 0;// now this order full
        }

        return value;
    }

    void fill_temp_data(int[] orders, int[] storage_balances) {
        this.temp_order = new int[orders.length];
        this.temp_storage = new int[storage_balances.length];
        this.optimized_matrix = new int[storage_balances.length][orders.length];

        for (int i = 0; i < orders.length; i++)
            this.temp_order[i] = orders[i];

        for (int i = 0; i < storage_balances.length; i++)
            this.temp_storage[i] = storage_balances[i];
    }

    boolean potential_possibility(int storage_count, int supermarket_count, int[][] delivery_matrix) {
        boolean possible = false;
        Integer potential_count = storage_count + supermarket_count,
                filling_cells = 0;

        for (int i = 0; i < delivery_matrix.length; i++) {
            for (int j = 0; j < delivery_matrix[0].length; j++) {
                if (delivery_matrix[i][j] != 0)
                    filling_cells += 1;
            }
        }

        if (filling_cells.equals((potential_count - 1)))
            possible = true;

        System.out.println("Potentials possibility: " + possible + " potential count: " + potential_count
                + "; Filling cells count: " + filling_cells);

        return possible;
    }

    void find_potential(int[][] delivery_matrix, int[][] matrix_coast) {
        int[][] path_matrix = new int[delivery_matrix.length][delivery_matrix[0].length];
        supermarket_potential = new Integer[delivery_matrix[0].length];
        storage_potential = new Integer[delivery_matrix.length];

        for (int i = 0; i < delivery_matrix.length; i++) {
            for (int j = 0; j < delivery_matrix[0].length; j++) {
                if (delivery_matrix[i][j] != 0)
                    path_matrix[i][j] = matrix_coast[i][j];
                else {
                    path_matrix[i][j] = 0;
                }
                System.out.print(path_matrix[i][j] + " ");
            }
            System.out.println();
        }

        while (!potentials_is_filled(storage_potential, supermarket_potential)) {
            for (int i = 0; i < delivery_matrix.length; i++) {
                if (i == 0) {
                    this.storage_potential[i] = 0;

                    for (int j = 0; j < path_matrix[0].length; j++) {
                        if (path_matrix[i][j] != 0)
                            this.supermarket_potential[j] = path_matrix[i][j] - storage_potential[i];
                    }
                } else {
                    for (int j = 0; j < delivery_matrix[0].length; j++) {
                        if (path_matrix[i][j] != 0) {
                            if (storage_potential[i] != null) {
                                this.supermarket_potential[j] = path_matrix[i][j] - storage_potential[i];
                            } else if (supermarket_potential[j] != null) {
                                this.storage_potential[i] = path_matrix[i][j] - supermarket_potential[j];
                            }
                        }
                    }
                }
            }
        }

        System.out.println("------storage_potentials----\n");
        for (int i = 0; i < delivery_matrix.length; i++) {
            System.out.print(this.storage_potential[i] + " ");
        }

        System.out.println("\n------supermarket_potentials----\n");
        for (int i = 0; i < delivery_matrix[0].length; i++) {
            System.out.print(this.supermarket_potential[i] + " ");
        }
    }

    boolean potentials_is_filled(Integer[] storage_potential, Integer[] supermarket_potential) {
        boolean potentials_is_filled = true;

        for (int i = 0; i < storage_potential.length; i++) {
            if (storage_potential[i] == null)
                potentials_is_filled = false;
        }
        for (int i = 0; i < supermarket_potential.length; i++) {
            if (supermarket_potential[i] == null)
                potentials_is_filled = false;
        }

        return potentials_is_filled;
    }

    int[] indirect_cost(int[][] matrix_delivery, int[][] matrix_coast) {
        int[][] indirect_cost_matrix = new int[matrix_delivery.length][matrix_delivery[0].length];

        int[] possible_to_profit,
                profit_value,
                most_profitable = new int[3];

        int count = 0,
                max = 0;

        boolean can_optimize;

        System.out.println("\n---fill indirect coast matrix----");
        for (int i = 0; i < matrix_delivery.length; i++) {
            for (int j = 0; j < matrix_delivery[0].length; j++) {
                if (matrix_delivery[i][j] == 0) {
                    indirect_cost_matrix[i][j] = storage_potential[i] + supermarket_potential[j];
                    count += 1;
                }
                 System.out.print(indirect_cost_matrix[i][j] + " ");
            }
             System.out.println();
        }
        possible_to_profit = new int[count];
        profit_value = new int[count];

        count = 0;

        for (int i = 0; i < matrix_delivery.length; i++) {
            for (int j = 0; j < matrix_delivery[0].length; j++) {
                if (indirect_cost_matrix[i][j] != 0) {
                    if (indirect_cost_matrix[i][j] > matrix_coast[i][j]) {
                        possible_to_profit[count] = indirect_cost_matrix[i][j];
                        profit_value[count] = indirect_cost_matrix[i][j] - matrix_coast[i][j];
                        count++;
                    }
                }
            }
        }

        if (count != 0) {
            max = profit_value[0];
            for (int i = 0; i < profit_value.length; i++) {
                if (profit_value[i] != 0 && profit_value[i] > max) {
                    max = profit_value[i];
                }
            }

            for (int i = 0; i < profit_value.length; i++) {
                if (profit_value[i] == max) {

                    for (int a = 0; a < indirect_cost_matrix.length; a++) {
                        for (int j = 0; j < indirect_cost_matrix[0].length; j++) {
                            if (possible_to_profit[i] == indirect_cost_matrix[a][j]) {
                                most_profitable[0] = a;
                                most_profitable[1] = j;
                                most_profitable[2] = possible_to_profit[i];
                            }
                        }
                    }
                }
            }
            can_optimize = true;
            System.out.println("Most profitable:" + most_profitable[2] + " [" + most_profitable[0] +
                    "] [" + most_profitable[1]+"]; profit:"+ max);
        } else {
            most_profitable[0] = -1;
            most_profitable[1] = -1;
            can_optimize = false;
            System.out.println("The path is maximally optimized");
        }

        return most_profitable;
    }

    int[][] recalculate_path(int[] start_cell, int[][] matrix_delivery) {

        int[][] new_path = new int[matrix_delivery.length][matrix_delivery[0].length],
                recalculate_path = new int[new_path.length][new_path[0].length];
        String[][] action_with_lambda = new String[new_path.length][new_path[0].length];

        int[] current_cell = new int[start_cell.length + 1];
        ArrayList<Integer> numbers_minus_lambda = new ArrayList<Integer>();

        int  sequence = 0;

        for (int i = 0; i < matrix_delivery.length; i++) {
            current_cell[i] = start_cell[i];
        }

        for (int i = 0; i < new_path.length; i++) {
            for (int j = 0; j < new_path[0].length; j++) {
                if (i == start_cell[0] && j == start_cell[1]) {
                    optimized_matrix[i][j] = current_cell[2];
                } else {
                    optimized_matrix[i][j] = matrix_delivery[i][j];
                    recalculate_path[i][j] = matrix_delivery[i][j];
                }
            }
        }

        do {
            if (sequence % 2 == 0) {
                current_cell = find_cell_at_column(current_cell, optimized_matrix);
                recalculate_path[current_cell[0]][current_cell[1]] = current_cell[2];
                action_with_lambda[current_cell[0]][current_cell[1]] = "-";
                numbers_minus_lambda.add(current_cell[2]);

            } else {
                current_cell = find_cell_at_row(current_cell, optimized_matrix);
                recalculate_path[current_cell[0]][current_cell[1]] = current_cell[2];
                action_with_lambda[current_cell[0]][current_cell[1]] = "+";

            }
            sequence++;

            new_path[current_cell[0]][current_cell[1]] = current_cell[2];


        } while (current_cell[2] != start_cell[2]);

        for (int i = 0; i < recalculate_path.length; i++) {
            for (int j = 0; j < recalculate_path[0].length; j++) {

                if (recalculate_path[i][j] != 0 || recalculate_path[i][j] == start_cell[2]) {

                    if (start_cell[0] == i && start_cell[1] == j)
                        recalculate_path[i][j] -= start_cell[2];

                    if (action_with_lambda[i][j] == "-") {
                        recalculate_path[i][j] -= Collections.min(numbers_minus_lambda);
                    } else if (action_with_lambda[i][j] == "+") {
                        recalculate_path[i][j] += Collections.min(numbers_minus_lambda);
                    }

                }
                System.out.print(recalculate_path[i][j] + " ");
            }
            System.out.println();
        }

        return recalculate_path;
    }

    int[] find_cell_at_column(int[] coordinates, int[][] matrix_delivery) {
        coordinates[3] = 0;

        for (int i = coordinates[1]; i < matrix_delivery[0].length; i++) {
            for (int j = 0; j < matrix_delivery.length; j++) {

                if (matrix_delivery[j][i] != 0 && matrix_delivery[j][i] != coordinates[2]) {
                    for (int b = 0; b < matrix_delivery[0].length; b++) {
                        if (matrix_delivery[j][b] != 0 && (b != i)) {

                            coordinates[0] = j;//row
                            coordinates[1] = i;//column
                            coordinates[2] = matrix_delivery[j][i];//value
                            coordinates[3] = 1;//that mean we find cell. Like boolean true

                            b = matrix_delivery[0].length;
                            i = b;
                            j = matrix_delivery.length;
                        }
                    }
                }

            }
        }
        return coordinates;
    }

    int[] find_cell_at_row(int[] coordinates, int[][] matrix_delivery) {
        coordinates[3] = 0;

        for (int i = coordinates[0]; i < matrix_delivery.length; i++) {
            for (int j = 0; j < matrix_delivery[0].length; j++) {
                if (matrix_delivery[i][j] != 0 && matrix_delivery[i][j] != coordinates[2]) {

                    for (int b = 0; b < matrix_delivery.length; b++) {
                        if (matrix_delivery[b][j] != 0 && (b != i)) {

                            coordinates[0] = i;//row
                            coordinates[1] = j;//column
                            coordinates[2] = matrix_delivery[i][j];//value
                            coordinates[3] = 1;//that mean we find cell. Like boolean true

                            b = matrix_delivery.length;
                            i = b;
                            j = matrix_delivery[0].length;
                        }
                    }
                }
            }
        }
        return coordinates;
    }


}
