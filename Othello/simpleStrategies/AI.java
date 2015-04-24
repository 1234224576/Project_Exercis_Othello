package simpleStrategies;
public abstract class AI {
	abstract public Point move(int[][] borad);
	
	public int presearch_depth = 3;
	public int normal_depth = 15;
	public int wld_depth = 15;
	public int perfect_depth = 13;

}