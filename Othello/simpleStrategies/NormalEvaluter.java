package simpleStrategies;
public class NormalEvaluter extends Evaluter {
	private int openLevel; //開放度を格納する
	private int[][] board = new int[Search.SIZE][Search.SIZE];
	private int myNum;
	private int eneNum;

	public NormalEvaluter(){
		
	}

	public int evalute(int[][] originalBoard,int myNum,int eneNum){

		this.myNum = myNum;
        this.eneNum = eneNum;

		//情報をセット
		for(int j=0;j<Search.SIZE;j++){
            for(int i=0;i<Search.SIZE;i++){
                board[i][j] = originalBoard[i][j];
            }
        }

		int eval = 0;

		for(int j=0;j<board.length;j++){
			for(int i=0;i<board.length;i++){
				if(board[i][j] == this.myNum) eval+=10;
			}
		}

		eval += obtainOpenLevelEvalution();

		return eval;
	}
	private int obtainOpenLevelEvalution(){
		int eval = 0;
		eval = openLevel * 10;
		return eval;
	}

	private int obtainMovableCountEvalution(){
		int eval = 0;

		return eval;
	}


	private int obtainMountainEvaluation(){
		int eval = 0;

		//ここに実装を書く

		return eval;
	}

	public void calcOpenLevel(int[][] board,int x,int y){
		int count = 8; //周りに石があるほど評価が高くなる、石が周りにあまりないときは評価を下げる

		//辺や角にある時は開放度を加算
		if(x == 7) count+=3;
		if(x == 0) count+=3;
		if(y == 7) count+=3;
		if(y == 0) count+=3;

		if(x < 7 && board[x+1][y] != 0)	count++;
		
		if(x > 0 && board[x-1][y] != 0)	count++;
		
		if(y < 7 && board[x][y+1] != 0)	count++;
		
		if(y > 0 && board[x][y-1] != 0)	count++;
		
		if(x < 7 && y < 7 && board[x+1][y+1] != 0)	count++;
		
		if(x < 7 && y > 0 && board[x+1][y-1] != 0)	count++;
		
		if(x > 0 && y < 7 && board[x-1][y+1] != 0)	count++;
		
		if(x > 0 && y > 0 && board[x-1][y-1] != 0)	count++;

		this.openLevel = count;
	}
}
