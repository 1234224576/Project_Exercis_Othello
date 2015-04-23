package simpleStrategies;

import game.GameState;
import game.Move;
import game.OthelloMoveException;
import game.Player;
import game.Strategy;

public class SenteKuroPlayer extends Strategy {

	private int[][] check_state = new int[SIZE][SIZE];

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
/*
        for(int j=0; j<SIZE; j++){
            for(int i=0; i<SIZE; i++){
                System.out.print(check_state[i][j] + " ");
            }
            System.out.println();
        }
*/

		Move m = new Move();
		int yoko, tate;

/*
8*8マスの中からランダムに1箇所選んで，
その箇所に石を置けるかどうかを
currentState.isLegalメソッドでチェック
置けるならそこに置く
置けないなら，置けるところが見つかるまで繰り返す
*/
		do {
            yoko = (int)(Math.random()*SIZE);
            tate = (int)(Math.random()*SIZE);
		} while (!currentState.isLegal(thisPlayer,yoko,tate));

/*
渡されたポイントに石を置いて、
ひっくり返った後の盤面の情報を返す
*/
        int front_color = 1; // 黒か白かを入れる変数
        int back_color = 2;
        check_state[yoko][tate] = front_color; // 渡されたポイントに石を置く
        
        System.out.println("現在の盤面と次に置く場所");
        for(int j=0; j<SIZE; j++){
            for(int i=0; i<SIZE; i++){
                if(i == yoko && j == tate) {
                    System.out.print("× ");
                } else {
                    if(check_state[i][j] == 0) System.out.print("  ");
                    else if(check_state[i][j] == 1) System.out.print("● ");
                    else if(check_state[i][j] == 2) System.out.print("○ ");
                }
            }
            System.out.println();
        }
        
        /* 上方向の探索 */
        if(tate != 0) { // そのポイントが端でない場合
            int search = check_state[yoko][tate-1]; // 隣のマスの情報を代入
            int turn = 0;
            int turn_tate = 0;
            if(search == back_color && (tate-1) != 0) { // 隣のマスに対戦相手の石があるなら探索開始
                                               // ただし、それが端のマスでない場合
                for(int i=0; i<=tate-2; i++) {
                    search = check_state[yoko][tate-2-i]; // さらに隣のマスの情報を代入
                    if(search == front_color) {
                        turn_tate = tate-1-i; // 1つ前のマスの位置を代入
                        turn = 1; // ひっくり返せる
                        break;
                    } else if(search == 0) { // なにもなければ探索終了
                        break;
                    }
                }
                if(turn == 1) { // 挟んだ相手の石をひっくり返していく
                    for(int i=0; i<(tate-turn_tate); i++) {
                        check_state[yoko][turn_tate+i] = 1;
                    }
                }
            }
        }
        
        /* 下方向の探索 */
        if(tate != 7) { // そのポイントが端でない場合
            int search = check_state[yoko][tate+1]; // 隣のマスの情報を代入
            int turn = 0;
            int turn_tate = 0;
            if(search == back_color && (tate+1) != 7) { // 隣のマスに対戦相手の石があるなら探索開始
                // ただし、それが端のマスでない場合
                for(int i=0; i<=5-tate; i++) {
                    search = check_state[yoko][tate+2+i]; // さらに隣のマスの情報を代入
                    if(search == front_color) {
                        turn_tate = tate+1+i; // 1つ前のマスの位置を代入
                        turn = 1; // ひっくり返せる
                        break;
                    } else if(search == 0) { // なにもなければ探索終了
                        break;
                    }
                }
                if(turn == 1) { // 挟んだ相手の石をひっくり返していく
                    for(int i=0; i<(turn_tate-tate); i++) {
                        check_state[yoko][turn_tate-i] = 1;
                    }
                }
            }
        }

        /* 左方向の探索 */
        if(yoko != 0) { // そのポイントが端でない場合
            int search = check_state[yoko-1][tate]; // 隣のマスの情報を代入
            int turn = 0;
            int turn_yoko = 0;
            if(search == back_color && (yoko-1) != 0) { // 隣のマスに対戦相手の石があるなら探索開始
                // ただし、それが端のマスでない場合
                for(int i=0; i<=yoko-2; i++) {
                    search = check_state[yoko-2-i][tate]; // さらに隣のマスの情報を代入
                    if(search == front_color) {
                        turn_yoko = yoko-1-i; // 1つ前のマスの位置を代入
                        turn = 1; // ひっくり返せる
                        break;
                    } else if(search == 0) { // なにもなければ探索終了
                        break;
                    }
                }
                if(turn == 1) { // 挟んだ相手の石をひっくり返していく
                    for(int i=0; i<(yoko-turn_yoko); i++) {
                        check_state[turn_yoko+i][tate] = 1;
                    }
                }
            }
        }

        /* 右方向の探索 */
        if(yoko != 7) { // そのポイントが端でない場合
            int search = check_state[yoko+1][tate]; // 隣のマスの情報を代入
            int turn = 0;
            int turn_yoko = 0;
            if(search == back_color && (yoko+1) != 7) { // 隣のマスに対戦相手の石があるなら探索開始
                // ただし、それが端のマスでない場合
                for(int i=0; i<=5-yoko; i++) {
                    search = check_state[yoko+2+i][tate]; // さらに隣のマスの情報を代入
                    if(search == front_color) {
                        turn_yoko = yoko+1+i; // 1つ前のマスの位置を代入
                        turn = 1; // ひっくり返せる
                        break;
                    } else if(search == 0) { // なにもなければ探索終了
                        break;
                    }
                }
                if(turn == 1) { // 挟んだ相手の石をひっくり返していく
                    for(int i=0; i<(turn_yoko-yoko); i++) {
                        check_state[turn_yoko-i][tate] = 1;
                    }
                }
            }
        }
        
        /* 左上方向の探索 */
        if(yoko != 0 && tate != 0) { // そのポイントが端でない場合
            int search = check_state[yoko-1][tate-1]; // 隣のマスの情報を代入
            int turn = 0;
            int turn_yoko = 0;
            int turn_tate = 0;
            int d = 0;
            int turn_d = 0;
            // 隣のマスに対戦相手の石があるなら探索開始
            // ただし、それが端のマスでない場合
            if(search == back_color && ((yoko-1) != 0 && (tate-1) != 0)) {
                if(yoko<=tate) {
                    d = yoko-2;
                } else {
                    d = tate-2;
                }
                for(int i=0; i<=d; i++) {
                    search = check_state[yoko-2-i][tate-2-i]; // さらに隣のマスの情報を代入
                    if(search == front_color) {
                        turn_yoko = yoko-1-i; // 1つ前のマスの位置を代入
                        turn_tate = tate-1-i; // 1つ前のマスの位置を代入
                        turn = 1; // ひっくり返せる
                        break;
                    } else if(search == 0) { // なにもなければ探索終了
                        break;
                    }
                }
                if(turn == 1) { // 挟んだ相手の石をひっくり返していく
                    if(yoko<=tate) {
                        turn_d = yoko-turn_yoko;
                    } else {
                        turn_d = tate-turn_tate;
                    }
                    for(int i=0; i<turn_d; i++) {
                        check_state[turn_yoko+i][turn_tate+i] = 1;
                    }
                }
            }
        }
        
        /* 左下方向の探索 */
        if(yoko != 0 && tate != 7) { // そのポイントが端でない場合
            int search = check_state[yoko-1][tate+1]; // 隣のマスの情報を代入
            int turn = 0;
            int turn_yoko = 0;
            int turn_tate = 0;
            int d = 0;
            int turn_d = 0;
            // 隣のマスに対戦相手の石があるなら探索開始
            // ただし、それが端のマスでない場合
            if(search == back_color && ((yoko-1) != 0 && (tate+1) != 7)) {
                if(yoko<=7-tate) {
                    d = yoko-2;
                } else {
                    d = 5-tate;
                }
                for(int i=0; i<=d; i++) {
                    search = check_state[yoko-2-i][tate+2+i]; // さらに隣のマスの情報を代入
                    if(search == front_color) {
                        turn_yoko = yoko-1-i; // 1つ前のマスの位置を代入
                        turn_tate = tate+1+i; // 1つ前のマスの位置を代入
                        turn = 1; // ひっくり返せる
                        break;
                    } else if(search == 0) { // なにもなければ探索終了
                        break;
                    }
                }
                if(turn == 1) { // 挟んだ相手の石をひっくり返していく
                    if(yoko<=7-tate) {
                        turn_d = yoko-turn_yoko;
                    } else {
                        turn_d = turn_tate-tate;
                    }
                    for(int i=0; i<turn_d; i++) {
                        check_state[turn_yoko+i][turn_tate-i] = 1;
                    }
                }
            }
        }

        /* 右上方向の探索 */
        if(yoko != 7 && tate != 0) { // そのポイントが端でない場合
            int search = check_state[yoko+1][tate-1]; // 隣のマスの情報を代入
            int turn = 0;
            int turn_yoko = 0;
            int turn_tate = 0;
            int d = 0;
            int turn_d = 0;
            // 隣のマスに対戦相手の石があるなら探索開始
            // ただし、それが端のマスでない場合
            if(search == back_color && ((yoko+1) != 7 && (tate-1) != 0)) {
                if(7-yoko<=tate) {
                    d = 5-yoko;
                } else {
                    d = tate-2;
                }
                for(int i=0; i<=d; i++) {
                    search = check_state[yoko+2+i][tate-2-i]; // さらに隣のマスの情報を代入
                    if(search == front_color) {
                        turn_yoko = yoko+1+i; // 1つ前のマスの位置を代入
                        turn_tate = tate-1-i; // 1つ前のマスの位置を代入
                        turn = 1; // ひっくり返せる
                        break;
                    } else if(search == 0) { // なにもなければ探索終了
                        break;
                    }
                }
                if(turn == 1) { // 挟んだ相手の石をひっくり返していく
                    if(7-yoko<=tate) {
                        turn_d = turn_yoko-yoko;
                    } else {
                        turn_d = tate-turn_tate;
                    }
                    for(int i=0; i<turn_d; i++) {
                        check_state[turn_yoko-i][turn_tate+i] = 1;
                    }
                }
            }
        }
        
        /* 右下方向の探索 */
        if(yoko != 7 && tate != 7) { // そのポイントが端でない場合
            int search = check_state[yoko+1][tate+1]; // 隣のマスの情報を代入
            int turn = 0;
            int turn_yoko = 0;
            int turn_tate = 0;
            int d = 0;
            int turn_d = 0;
            // 隣のマスに対戦相手の石があるなら探索開始
            // ただし、それが端のマスでない場合
            if(search == back_color && ((yoko+1) != 7 && (tate+1) != 7)) {
                if(7-yoko<=7-tate) {
                    d = 5-yoko;
                } else {
                    d = 5-tate;
                }
                for(int i=0; i<=d; i++) {
                    search = check_state[yoko+2+i][tate+2+i]; // さらに隣のマスの情報を代入
                    if(search == front_color) {
                        turn_yoko = yoko+1+i; // 1つ前のマスの位置を代入
                        turn_tate = tate+1+i; // 1つ前のマスの位置を代入
                        turn = 1; // ひっくり返せる
                        break;
                    } else if(search == 0) { // なにもなければ探索終了
                        break;
                    }
                }
                if(turn == 1) { // 挟んだ相手の石をひっくり返していく
                    if(7-yoko<=7-tate) {
                        turn_d = turn_yoko-yoko;
                    } else {
                        turn_d = turn_tate-tate;
                    }
                    for(int i=0; i<turn_d; i++) {
                        check_state[turn_yoko-i][turn_tate-i] = 1;
                    }
                }
            }
        }
        
        System.out.println("ひっくり返った後の盤面");
        for(int j=0; j<SIZE; j++){
            for(int i=0; i<SIZE; i++){
                if(check_state[i][j] == 0) System.out.print("  ");
                else if(check_state[i][j] == 1) System.out.print("● ");
                else if(check_state[i][j] == 2) System.out.print("○ ");
            }
            System.out.println();
        }
        
/*
さらに、ひっくり返したあと
次に打つことができる箇所の情報を返す
*/
        

        
		m.x = yoko;
		m.y = tate;
        
        

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
