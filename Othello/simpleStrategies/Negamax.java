package simpleStrategies;
public class Negamax extends AI {


	private Search search;
	public Negamax(){
		search = new Search();
	}
	@Override
	public Point move(int[][] board,Search.Phasing ph,int limit,int alpha,int beta){
		//打てる手を全て生成
		int[][] movables = search.obtainMovablePosition(board,ph);

		Point resultPos = new Point();
		int eval,eval_max = Integer.MIN_VALUE;

		for(int j=0; j<Search.SIZE;j++){
			for(int i=0;i < Search.SIZE;i++){
				if(movables[i][j] == 1){
					//仮想盤上に手を打つ
					Point p = new Point();
					p.x = j;
					p.y = i;
					int[][] nextBoard = search.checkNextBoard(board,p,ph);
					//次の手番
					Search.Phasing nextPh;
					if(ph == Search.Phasing.BLACK){
						nextPh = Search.Phasing.WHITE;
					}else{
						nextPh = Search.Phasing.BLACK;
					}
					eval =  -negamax(board,limit-1,-beta,-alpha,nextPh);

					if(eval > eval_max){
						//打つ手を決定
						System.out.println("x:" + j + "y:" + i);
						resultPos.x = j;
						resultPos.y = i;
						eval_max = eval;
					}

				}
			}
		}
		System.out.println("MAX:"+eval_max);
		return resultPos;
	}

	private int negamax(int[][] board,int limit,int alpha,int beta,Search.Phasing ph){
		if(limit == 0){
			System.out.println("SCORE:" + evalute(board));
			return evalute(board);
		}

		//可能な手を全て生成;
		int[][] movables = search.obtainMovablePosition(board,ph);

		int score,scoreMax;
		scoreMax = 0;
		score = 0;
		for(int j=0; j<Search.SIZE;j++){
			for(int i=0;i < Search.SIZE;i++){
				if(movables[i][j] == 1){
					Point p = new Point();
					p.x = j;
					p.y = i;
					int[][] nextBoard = search.checkNextBoard(board,p,ph);

					Search.Phasing nextPh;
					if(ph == Search.Phasing.BLACK){
						nextPh = Search.Phasing.WHITE;
					}else{
						nextPh = Search.Phasing.BLACK;
					}

					score = -negamax(nextBoard,limit-1,-beta,-alpha,nextPh);
					if(score >= beta){
						//ベータ値を上回ったら探索を中止
						return score;
					}

					if(score > scoreMax){
						//よりよい手がみつかった
						scoreMax = score;
						alpha = Math.max(alpha,scoreMax);
					}
				}
			}
		}
	
		// System.out.println("score:" + scoreMax);
		return scoreMax;
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