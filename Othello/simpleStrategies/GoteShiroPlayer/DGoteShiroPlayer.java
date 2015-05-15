package simpleStrategies;

import game.GameState;
import game.Move;
import game.OthelloMoveException;
import game.Player;
import game.Strategy;


public class GoteShiroPlayer extends Strategy {

	private int[][] check_state = new int[SIZE][SIZE];
	private Evaluter evaluter = new NormalEvaluter();
	private int currentTurn = 1;
// コンストラクタは改造せずこのまま使うこと
	public GoteShiroPlayer(Player _thisPlayer, int size) {
		super(_thisPlayer, size);
	}

//	Override
	public Move nextMove(GameState currentState, int remainingTime) {

// 8*8マスの石の状態をcheck_state配列に格納
		check(currentState);

		Move m = new Move();
        Negamax n = new Negamax(Search.Phasing.WHITE);
        n.limit = 7;
        Point p = n.move(check_state,Search.Phasing.WHITE,this.currentTurn);
        m.x = p.x;
        m.y = p.y;
        currentTurn+=2;

		return m;
	}

	private void check(GameState currentState) {
		for(int i=0; i<SIZE; i++){
			for(int j=0; j<SIZE; j++){
				if( (""+currentState.getState(i,j)).equals("Empty")){ check_state[i][j] = 0; }
				else if( (""+currentState.getState(i,j)).equals("Black")){ check_state[i][j] = 1; }
				else { check_state[i][j] = 2; }
			}
		}
	}
}
