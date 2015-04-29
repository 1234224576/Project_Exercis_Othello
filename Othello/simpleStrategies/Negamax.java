package simpleStrategies;
public class Negamax extends AI {
	private Evaluter evaluter;
	private Search search;
	private Search.Phasing myColor;

	public Negamax(Search.Phasing mc){
		myColor = mc;
		search = new Search();
	}
	@Override
	public Point move(int[][] board,Search.Phasing ph,int currentTurn){

		int limit = 9;
		//打てる手を全て生成
		int[][] movables = search.obtainMovablePosition(board,ph);

		//打てる所が１箇所ならそこに打つ
		int movableCount = 0;
		for(int j=0; j<Search.SIZE;j++){
			for(int i=0;i < Search.SIZE;i++){
				if(movables[i][j] == 1) movableCount++;
			}
		}
		if(movableCount == 1){
			// search.plotMovableBoard(movables);
			for(int j=0; j<Search.SIZE;j++){
				for(int i=0;i < Search.SIZE;i++){
					if(movables[i][j] == 1){
						Point p = new Point();
						p.x = i;
						p.y = j;
						return p;
					}
				}
			}
		}

		Point resultPos = new Point();
		int eval,eval_max = -Integer.MAX_VALUE;

		for(int j=0; j<Search.SIZE;j++){
			for(int i=0;i < Search.SIZE;i++){
				if(movables[i][j] == 1){
					//仮想盤上に手を打つ
					Point p = new Point();
					p.x = i;
					p.y = j;

					int[][] nextBoard = search.checkNextBoard(board,p,ph);

					//次の手番
					Search.Phasing nextPh;
					if(ph == Search.Phasing.BLACK){
						nextPh = Search.Phasing.WHITE;
					}else{
						nextPh = Search.Phasing.BLACK;
					}

					NormalEvaluter evaluter = new NormalEvaluter();
					evaluter.calcOpenLevel(board,i,j); //打つ予定の場所の開放度を計算する

					eval =  negamax(nextBoard,limit-1,-Integer.MAX_VALUE,Integer.MAX_VALUE,nextPh,evaluter);

					System.out.println("最後の選択:"+eval);
					if(eval > eval_max){
						//打つ手を決定
						resultPos.x = i;
						resultPos.y = j;
						eval_max = eval;
					}

				}
			}
		}
		System.out.println("最終結果:"+eval_max);
		return resultPos;
	}

	private int negamax(int[][] originlboard,int limit,int alpha,int beta,Search.Phasing ph,Evaluter evaluter){
		int[][] board = new int[8][8];

		for(int j=0;j<8;j++){
            for(int i=0;i<8;i++){
                board[i][j] = originlboard[i][j];
            }
        }

		if(limit == 0 || search.getIsGameOver(board)){
			System.out.println("評価値:" + evalute(board,evaluter));
			return evalute(board,evaluter);
		}

		//可能な手を全て生成;
		int[][] movables = search.obtainMovablePosition(board,ph);
		int eval;

		//パス、打てる場所一個かどうか判定
		int movableCount = 0;
		for(int j=0; j<Search.SIZE;j++){
			for(int i=0;i < Search.SIZE;i++){
				if(movables[i][j] == 1) movableCount++;
			}
		}
		//打てる場所が一箇所ならすぐ返す
		// if(movableCount == 1){

		// 	return 0;
		// }
		//打てないならパス
		if(movableCount == 0){
			Search.Phasing nextPh;
			if(ph == Search.Phasing.BLACK){
				nextPh = Search.Phasing.WHITE;
			}else{
				nextPh = Search.Phasing.BLACK;
			}
			eval = negamax(board,limit-1,-beta,-alpha,nextPh,evaluter);
			return eval;
		}
		//打てる手全てに対して探索
		for(int j=0; j<Search.SIZE;j++){
			for(int i=0;i < Search.SIZE;i++){
				if(movables[i][j] == 1){
					Point p = new Point();
					p.x = i;
					p.y = j;
					int[][] nextBoard = search.checkNextBoard(board,p,ph);
					Search.Phasing nextPh;
					if(ph == Search.Phasing.BLACK){
						nextPh = Search.Phasing.WHITE;
					}else{
						nextPh = Search.Phasing.BLACK;
					}

					// search.plotBoard(nextBoard,p);
					eval = -negamax(nextBoard,limit-1,-beta,-alpha,nextPh,evaluter);
					// System.out.println("SCORE:"+eval);

					alpha = Math.max(alpha,eval);

					if(alpha >= beta){
						//ベータ値を上回ったら探索を中止
						// System.out.println("芝刈り：a="+alpha+",b="+beta);
						return alpha;
					}
				}
			}
		}
	
		System.out.println("SELECT" + alpha);
		return alpha;
	}

	//評価する
	private int evalute(int[][] board,Evaluter evaluter){
		int myNum;
		int eneNum;
		if(this.myColor == Search.Phasing.BLACK){
			myNum = 1;
			eneNum = 2;
		}else{
			myNum = 2;
			eneNum = 1;
		}
		return evaluter.evalute(board,myNum,eneNum);
	}
}