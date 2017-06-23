package org.cloudbus.cloudsim;

import net.sourceforge.jswarm_pso.FitnessFunction;

public class SchedularFitnessFunction extends FitnessFunction {
    static double[][] execTimeMatrix, communTimeMatrix;
    
	SchedularFitnessFunction() {
		super(false);
		initMatrices();
	}
	
	@Override
	public double evaluate(double[] position) {
		double alpha = 0.3;
        return alpha * calcTotalTime(position) + (1 - alpha) * calcMakespan(position);
	}
	
	private double calcTotalTime(double[] position) {
        double totalCost = 0;
        for (int i = 0; i < Constants.NO_OF_TASKS; i++) {
            int dcId = (int) position[i];
            totalCost += execTimeMatrix[i][dcId] + communTimeMatrix[i][dcId];
        }
        return totalCost;
    }

    private double calcMakespan(double[] position) {
        double makespan = 0;
        double[] dcWorkingTime = new double[Constants.NO_OF_DATA_CENTERS];

        for (int i = 0; i < Constants.NO_OF_TASKS; i++) {
            int dcId = (int) position[i];
            dcWorkingTime[dcId] += execTimeMatrix[i][dcId] + communTimeMatrix[i][dcId];
            makespan = Math.max(makespan, dcWorkingTime[dcId]);
        }
        return makespan;
    }
    
	private void initMatrices() {
		System.out.println("Initializing input matrices (e.g. exec time & communication time matrices");
        execTimeMatrix = new double[Constants.NO_OF_TASKS][Constants.NO_OF_DATA_CENTERS];
        communTimeMatrix = new double[Constants.NO_OF_TASKS][Constants.NO_OF_DATA_CENTERS];
        
        for (int i = 0; i < Constants.NO_OF_TASKS; i++) {
            for (int j = 0; j < Constants.NO_OF_DATA_CENTERS; j++) {
                execTimeMatrix[i][j] = Math.random() * 5000;
                communTimeMatrix[i][j] = Math.random() * 5000;
            }
        }
	}
}