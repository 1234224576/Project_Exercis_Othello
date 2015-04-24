package simpleStrategies;
public class Negamax extends AI {

	public Negamax(){

	}
	@Override
	public Point move(int[][] board){
		//打てる手を全て生成

		 Point p = null;
		// int eval,eval_max = Integer.MIN_VALUE;
		// for(int i=0;i < movables.size();i++){
		// 	//仮想盤上に手を打つ

		// 	eval =  -negamax(board,limit-1,-beta,-alpha);
		
		// 	if(eval > eval_max){
		// 		//打つ手を決定
				
		// 		//p = (Point)movables.get(i);
		// 		eval_max = eval;
		// 	}
		// }
		//手（Point)を返す
		return p;
	}

	// private int negamax(int[][] board,int limit,int alpha,int beta){
	// 	if(limit == 0){
	// 		return evalute(board);
	// 	}

	// 	//可能な手を全て生成;

	// 	int score,scoreMax;

	// 	foreach(それぞれの手){
	// 		//手を打つ;
	// 		score = -negamax(-beta,-alpha);
	// 		//手を戻す;

	// 		if(score => beta){
	// 			//ベータ値を上回ったら探索を中止
	// 			return score;
	// 		}

	// 		if(score > score_max){
	// 			//よりよい手がみつかった
	// 			score_max = score;
	// 			alpha = max(alpha,score_max);
	// 		}
	// 	}
	// 	return score_max;
	// }

	// /*評価関数・現在は仮実装。最終的に複数クラスに分離する*/
	// private Int evalute(int[][] board){

	// 	int count = 0;
	// 	for(int i=0;i<board.length;i++)
	// 		for(int j=0;j<board.length;j++){
	// 			if(board[i][j] == 1) count ++;
	// 		}
	// 	return count;
	// }
}