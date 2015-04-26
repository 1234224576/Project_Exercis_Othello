package simpleStrategies;
public class NormalEvaluter extends Evaluter {

	private int[][] board = new int[Search.SIZE][Search.SIZE];
	private int myNum;
	private int eneNum;

	public NormalEvaluter(){
		
	}

	public int evalute(int[][] originalBoard,int myNum,int eneNum){
		//情報をセット
		for(int j=0;j<Search.SIZE;j++){
            for(int i=0;i<Search.SIZE;i++){
                board[i][j] = originalBoard[i][j];
            }
        }

        this.myNum = myNum;
        this.eneNum = eneNum;


		int eval = 0;

		for(int j=0;j<board.length;j++){
			for(int i=0;i<board.length;i++){
				if(board[i][j] == this.myNum) eval++;
			}
		}
		return eval;
	}
}
