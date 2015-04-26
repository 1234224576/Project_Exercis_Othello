package simpleStrategies;
public class Negamax extends AI {


	private Search search;
	public Negamax(){
		search = new Search();
	}
	@Override
	public Point move(int[][] board,Search.Phasing ph){

		int limit = 15;
		//打てる手を全て生成
		int[][] movables = search.obtainMovablePosition(board,ph);
		// search.plotMovableBoard(movables);

		//打てる所が１箇所ならそこに打つ
		int movableCount = 0;
		for(int j=0; j<Search.SIZE;j++){
			for(int i=0;i < Search.SIZE;i++){
				if(movables[i][j] == 1) movableCount++;
			}
		}
		if(movableCount == 1){
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
		int eval,eval_max = Integer.MIN_VALUE;

		for(int j=0; j<Search.SIZE;j++){
			for(int i=0;i < Search.SIZE;i++){
				if(movables[i][j] == 1){
					//仮想盤上に手を打つ
					Point p = new Point();
					p.x = i;
					p.y = j;
					int[][] nextBoard = search.checkNextBoard(board,p,ph);


					// search.plotBoard(board,p);

					//次の手番
					Search.Phasing nextPh;
					if(ph == Search.Phasing.BLACK){
						nextPh = Search.Phasing.WHITE;
					}else{
						nextPh = Search.Phasing.BLACK;
					}
					eval =  negamax(nextBoard,limit-1,-Integer.MAX_VALUE,-Integer.MIN_VALUE,nextPh);

					if(eval > eval_max){
						//打つ手を決定
						resultPos.x = i;
						resultPos.y = j;
						eval_max = eval;
					}

				}
			}
		}
		// search.plotBoard(board,resultPos);
		System.out.println("最終結果:"+eval_max);
		return resultPos;
	}

	private int negamax(int[][] board,int limit,int alpha,int beta,Search.Phasing ph){

		if(limit == 0 || search.getIsGameOver(board)){
			System.out.println("FIRST_SCORE:" + evalute(board));
			return evalute(board);
		}

		//可能な手を全て生成;
		int[][] movables = search.obtainMovablePosition(board,ph);
		int eval;

		boolean isMovale = false;
		for(int j=0; j<Search.SIZE;j++){
			for(int i=0;i < Search.SIZE;i++){
				if(movables[i][j] == 1) isMovale = true;
			}
		}
		if(!isMovale){
			Search.Phasing nextPh;
			if(ph == Search.Phasing.BLACK){
				nextPh = Search.Phasing.WHITE;
			}else{
				nextPh = Search.Phasing.BLACK;
			}
			eval = -negamax(board,limit,-beta,-alpha,nextPh);
			return eval;
		}


		for(int j=0; j<Search.SIZE;j++){
			for(int i=0;i < Search.SIZE;i++){
				if(movables[i][j] == 1){
					Point p = new Point();
					p.x = i;
					p.y = j;
					int[][] nextBoard = search.checkNextBoard(board,p,ph);
					// System.out.println("============");
					// search.plotBoard(board,p);

					Search.Phasing nextPh;
					if(ph == Search.Phasing.BLACK){
						nextPh = Search.Phasing.WHITE;
					}else{
						nextPh = Search.Phasing.BLACK;
					}

					eval = -negamax(nextBoard,limit-1,-beta,-alpha,nextPh);
					System.out.println("SCORE"+eval);

					alpha = Math.max(alpha,eval);
					if(alpha >= beta){
						//ベータ値を上回ったら探索を中止
						return alpha;
					}
				}
			}
		}
	
		System.out.println("SELECT" + alpha);
		return alpha;
	}

	// /*評価関数・現在は仮実装。最終的に複数クラスに分離する*/
	private int evalute(int[][] board){

		int count = 0;
		for(int j=0;j<board.length;j++){
			for(int i=0;i<board.length;i++){
				if(board[i][j] == 1) count ++;
			}
		}
		return count;
	}
}