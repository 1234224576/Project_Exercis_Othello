package simpleStrategies;

import game.GameState;
import game.Move;
import game.OthelloMoveException;
import game.Player;
import game.Strategy;

public class SenteKuroPlayer extends Strategy {
	private int[][] check_state = new int[SIZE][SIZE];
	private int currentTurn = 1;
// コンストラクタは改造せずこのまま使うこと
	public SenteKuroPlayer(Player _thisPlayer, int size) {
		super(_thisPlayer, size);
	}

//	Override
	public Move nextMove(GameState currentState, int remainingTime) {

// 8*8マスの石の状態をcheck_state配列に格納
		check(currentState);

// check_state配列の表示用
// 無0　黒1　白2



		 Move m = new Move();
        Negamax n = new Negamax(Search.Phasing.BLACK);
        System.out.println("＝＝＝＝＝＝＝＝BLACK＝＝＝＝＝＝＝＝");
        Point p = n.move(check_state,Search.Phasing.BLACK,this.currentTurn);
        System.out.println("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
        m.x = p.x;
        m.y = p.y;
        currentTurn+=2;
		return m;
		//   int yoko, tate;
		// do {
		// 	yoko = (int)(Math.random()*SIZE);
		// 	tate = (int)(Math.random()*SIZE);
		// } while (!currentState.isLegal(thisPlayer,yoko,tate));

		// m.x = yoko;
		// m.y = tate;
		// return m;
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
