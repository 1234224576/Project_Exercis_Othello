package simpleStrategies;
public class NormalEvaluter extends Evaluter {
	private int openLevel; //開放度を格納する
	private int[][] board = new int[Search.SIZE][Search.SIZE];
	private int myNum;
	private int eneNum;

	public NormalEvaluter(){
		
	}

	public int evalute(int[][] originalBoard,int myNum,int eneNum,int movableCount){

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
		eval += obtainMovableCountEvalution(movableCount);
		eval += obtainDecidedStoneEvalution();
		eval += kariEval();
		return eval;
	}

	private int kariEval(){
		int eval = 0;
		if(board[1][1] == myNum) eval -= 100;
		if(board[6][1] == myNum) eval -= 100;
		if(board[1][6] == myNum) eval -= 100;
		if(board[6][6] == myNum) eval -= 100;
		return eval;
	}

	private int obtainOpenLevelEvalution(){
		int eval = 0;
		eval = openLevel * 10;
		return eval;
	}

	private int obtainMovableCountEvalution(int movableCount){
		int eval = 0;
		eval =  movableCount * 20;
		return eval;
	}

	private int obtainDecidedStoneEvalution(){ // 確定石
        int eval = 0;
       
        if(board[0][0] == myNum){
            for(int i=1;i<Search.SIZE;i++){
                if(board[i][0] == myNum) {
                    eval+=100;
                } else {
                    break;
                }
            }
            for(int i=1;i<Search.SIZE;i++){
                if(board[0][i] == myNum) {
                    eval+=100;
                } else {
                    break;
                }
            }
        }
        if(board[Search.SIZE-1][0] == myNum){
            for(int i=Search.SIZE-1;i>=0;i--){
                if(board[i][0] == myNum) {
                    eval+=100;
                } else {
                    break;
                }
            }
            for(int i=1;i<Search.SIZE;i++){
                if(board[Search.SIZE-1][i] == myNum) {
                    eval+=100;
                } else {
                    break;
                }
            }
        }
        if(board[0][Search.SIZE-1] == myNum){
            for(int i=1;i<Search.SIZE;i++){
                if(board[i][Search.SIZE-1] == myNum) {
                    eval+=100;
                } else {
                    break;
                }
            }
            for(int i=Search.SIZE-1;i>=0;i--){
                if(board[i][0] == myNum) {
                    eval+=100;
                } else {
                    break;
                }
            }
        }
        if(board[Search.SIZE-1][Search.SIZE-1] == myNum){

            for(int i=Search.SIZE-1;i>=0;i--){
                if(board[i][Search.SIZE-1] == myNum) {
                    eval+=100;
                } else {
                    break;
                }
            }
            for(int i=Search.SIZE-1;i>=0;i--){
                if(board[i][Search.SIZE-1] == myNum) {
                    eval+=100;
                } else {
                    break;
                }
            }
        }
        
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
