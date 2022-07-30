package Com.CalculatingData;

import Com.Interfaces.Calculate;

import java.util.Scanner;

public class PotentialMethod{

    MethodsHelper methodsHelper;
    Scanner scanner = new Scanner(System.in);

   public int[][] findDeliveryPath(int[][] deliveryMatrix) {
        methodsHelper = new MethodsHelper();

        System.out.println("\n-----------Potential method----");
        int[] startCell;
        boolean needOptimize = true;

        while (needOptimize && methodsHelper.potentialPossibility(deliveryMatrix)) {
            methodsHelper.findPotential(deliveryMatrix);
            startCell = methodsHelper.indirectCost(deliveryMatrix);

            if (startCell[0] == -1 && startCell[1] == -1) {
                needOptimize = false;
            } else {
                deliveryMatrix = methodsHelper.recalculatePath(startCell,  deliveryMatrix);
            }
        }
        return  deliveryMatrix;
    }
}
