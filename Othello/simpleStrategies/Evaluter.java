package simpleStrategies;
public abstract class Evaluter {
	public int[][] openLevel = new int[Search.SIZE][Search.SIZE]; //開放度を格納する

	abstract public int evalute(int[][] originalBoard,int myNum,int eneNum);
}