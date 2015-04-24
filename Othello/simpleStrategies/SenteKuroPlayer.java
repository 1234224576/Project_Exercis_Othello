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
        int[] put_yoko = new int[SIZE*SIZE];
        int[] put_tate = new int[SIZE*SIZE];
        int put_list = 0; // リストの数
        int next_place = 0;
        
        for(int j=0; j<SIZE; j++){
            for(int i=0; i<SIZE; i++){
                next_place = 0;
                
                /* 上方向の探索 */
                if(check_state[i][j] == 0 && j != 0) { // そのポイントが端でない場合
                    int search = check_state[i][j-1]; // 隣のマスの情報を代入
                    int turn = 0;
                    int turn_tate = 0;
                    if(search == back_color && (j-1) != 0) { // 隣のマスに対戦相手の石があるなら探索開始
                        // ただし、それが端のマスでない場合
                        for(int k=0; k<=j-2; k++) {
                            search = check_state[i][j-2-k]; // さらに隣のマスの情報を代入
                            if(search == front_color) {
                                turn_tate = j-1-k; // 1つ前のマスの位置を代入
                                turn = 1; // ひっくり返せる
                                break;
                            } else if(search == 0) { // なにもなければ探索終了
                                break;
                            }
                        }
                        if(turn == 1) { // 置ける場所をリストにしていく
                            put_yoko[put_list] = i; // 横の座標を記憶する
                            put_tate[put_list] = j; // 縦の座標を記憶する
                            put_list++;
                            next_place = 1;
                        }
                    }
                }
                
                /* 下方向の探索 */
                if(check_state[i][j] == 0 && j != 7 && next_place == 0) { // そのポイントが端でない場合
                    int search = check_state[i][j+1]; // 隣のマスの情報を代入
                    int turn = 0;
                    int turn_tate = 0;
                    if(search == back_color && (j+1) != 7) { // 隣のマスに対戦相手の石があるなら探索開始
                        // ただし、それが端のマスでない場合
                        for(int k=0; k<=5-j; k++) {
                            search = check_state[i][j+2+k]; // さらに隣のマスの情報を代入
                            if(search == front_color) {
                                turn_tate = j+1+k; // 1つ前のマスの位置を代入
                                turn = 1; // ひっくり返せる
                                break;
                            } else if(search == 0) { // なにもなければ探索終了
                                break;
                            }
                        }
                        if(turn == 1) { // 置ける場所をリストにしていく
                            put_yoko[put_list] = i; // 横の座標を記憶する
                            put_tate[put_list] = j; // 縦の座標を記憶する
                            put_list++;
                            next_place = 1;
                        }
                    }
                }
                
                /* 左方向の探索 */
                if(check_state[i][j] == 0 && i != 0 && next_place == 0) { // そのポイントが端でない場合
                    int search = check_state[i-1][j]; // 隣のマスの情報を代入
                    int turn = 0;
                    int turn_yoko = 0;
                    if(search == back_color && (i-1) != 0) { // 隣のマスに対戦相手の石があるなら探索開始
                        // ただし、それが端のマスでない場合
                        for(int k=0; k<=i-2; k++) {
                            search = check_state[i-2-k][j]; // さらに隣のマスの情報を代入
                            if(search == front_color) {
                                turn_yoko = i-1-k; // 1つ前のマスの位置を代入
                                turn = 1; // ひっくり返せる
                                break;
                            } else if(search == 0) { // なにもなければ探索終了
                                break;
                            }
                        }
                        if(turn == 1) { // 置ける場所をリストにしていく
                            put_yoko[put_list] = i; // 横の座標を記憶する
                            put_tate[put_list] = j; // 縦の座標を記憶する
                            put_list++;
                            next_place = 1;
                        }
                    }
                }
                
                /* 右方向の探索 */
                if(check_state[i][j] == 0 && i != 7 && next_place == 0) { // そのポイントが端でない場合
                    int search = check_state[i+1][j]; // 隣のマスの情報を代入
                    int turn = 0;
                    int turn_yoko = 0;
                    if(search == back_color && (i+1) != 7) { // 隣のマスに対戦相手の石があるなら探索開始
                        // ただし、それが端のマスでない場合
                        for(int k=0; k<=5-i; k++) {
                            search = check_state[i+2+k][j]; // さらに隣のマスの情報を代入
                            if(search == front_color) {
                                turn_yoko = i+1+k; // 1つ前のマスの位置を代入
                                turn = 1; // ひっくり返せる
                                break;
                            } else if(search == 0) { // なにもなければ探索終了
                                break;
                            }
                        }
                        if(turn == 1) { // 置ける場所をリストにしていく
                            put_yoko[put_list] = i; // 横の座標を記憶する
                            put_tate[put_list] = j; // 縦の座標を記憶する
                            put_list++;
                            next_place = 1;
                        }
                    }
                }

                /* 左上方向の探索 */
                if(check_state[i][j] == 0 && i != 0 && j != 0 && next_place == 0) { // そのポイントが端でない場合
                    int search = check_state[i-1][j-1]; // 隣のマスの情報を代入
                    int turn = 0;
                    int turn_yoko = 0;
                    int turn_tate = 0;
                    int d = 0;
                    // 隣のマスに対戦相手の石があるなら探索開始
                    // ただし、それが端のマスでない場合
                    if(search == back_color && ((i-1) != 0 && (j-1) != 0)) {
                        if(i<=j) {
                            d = i-2;
                        } else {
                            d = j-2;
                        }
                        for(int k=0; k<=d; k++) {
                            search = check_state[i-2-k][j-2-k]; // さらに隣のマスの情報を代入
                            if(search == front_color) {
                                turn_yoko = i-1-k; // 1つ前のマスの位置を代入
                                turn_tate = j-1-k; // 1つ前のマスの位置を代入
                                turn = 1; // ひっくり返せる
                                break;
                            } else if(search == 0) { // なにもなければ探索終了
                                break;
                            }
                        }
                        if(turn == 1) { // 置ける場所をリストにしていく
                            put_yoko[put_list] = i; // 横の座標を記憶する
                            put_tate[put_list] = j; // 縦の座標を記憶する
                            put_list++;
                            next_place = 1;
                        }
                    }
                }
                
                /* 左下方向の探索 */
                if(check_state[i][j] == 0 && i != 0 && j != 7 && next_place == 0) { // そのポイントが端でない場合
                    int search = check_state[i-1][j+1]; // 隣のマスの情報を代入
                    int turn = 0;
                    int turn_yoko = 0;
                    int turn_tate = 0;
                    int d = 0;
                    // 隣のマスに対戦相手の石があるなら探索開始
                    // ただし、それが端のマスでない場合
                    if(search == back_color && ((i-1) != 0 && (j+1) != 7)) {
                        if(i<=7-j) {
                            d = i-2;
                        } else {
                            d = 5-j;
                        }
                        for(int k=0; k<=d; k++) {
                            search = check_state[i-2-k][j+2+k]; // さらに隣のマスの情報を代入
                            if(search == front_color) {
                                turn_yoko = i-1-k; // 1つ前のマスの位置を代入
                                turn_tate = j+1+k; // 1つ前のマスの位置を代入
                                turn = 1; // ひっくり返せる
                                break;
                            } else if(search == 0) { // なにもなければ探索終了
                                break;
                            }
                        }
                        if(turn == 1) { // 置ける場所をリストにしていく
                            put_yoko[put_list] = i; // 横の座標を記憶する
                            put_tate[put_list] = j; // 縦の座標を記憶する
                            put_list++;
                            next_place = 1;
                        }
                    }
                }
                
                /* 右上方向の探索 */
                if(check_state[i][j] == 0 && i != 7 && j != 0 && next_place == 0) { // そのポイントが端でない場合
                    int search = check_state[i+1][j-1]; // 隣のマスの情報を代入
                    int turn = 0;
                    int turn_yoko = 0;
                    int turn_tate = 0;
                    int d = 0;
                    // 隣のマスに対戦相手の石があるなら探索開始
                    // ただし、それが端のマスでない場合
                    if(search == back_color && ((i+1) != 7 && (j-1) != 0)) {
                        if(7-i<=j) {
                            d = 5-i;
                        } else {
                            d = j-2;
                        }
                        for(int k=0; k<=d; k++) {
                            search = check_state[i+2+k][j-2-k]; // さらに隣のマスの情報を代入
                            if(search == front_color) {
                                turn_yoko = i+1+k; // 1つ前のマスの位置を代入
                                turn_tate = j-1-k; // 1つ前のマスの位置を代入
                                turn = 1; // ひっくり返せる
                                break;
                            } else if(search == 0) { // なにもなければ探索終了
                                break;
                            }
                        }
                        if(turn == 1) { // 置ける場所をリストにしていく
                            put_yoko[put_list] = i; // 横の座標を記憶する
                            put_tate[put_list] = j; // 縦の座標を記憶する
                            put_list++;
                            next_place = 1;
                        }
                    }
                }
                
                /* 右下方向の探索 */
                if(check_state[i][j] == 0 && i != 7 && j != 7 && next_place == 0) { // そのポイントが端でない場合
                    int search = check_state[i+1][j+1]; // 隣のマスの情報を代入
                    int turn = 0;
                    int turn_yoko = 0;
                    int turn_tate = 0;
                    int d = 0;
                    // 隣のマスに対戦相手の石があるなら探索開始
                    // ただし、それが端のマスでない場合
                    if(search == back_color && ((i+1) != 7 && (j+1) != 7)) {
                        if(7-i<=7-j) {
                            d = 5-i;
                        } else {
                            d = 5-j;
                        }
                        for(int k=0; k<=d; k++) {
                            search = check_state[i+2+k][j+2+k]; // さらに隣のマスの情報を代入
                            if(search == front_color) {
                                turn_yoko = i+1+k; // 1つ前のマスの位置を代入
                                turn_tate = j+1+k; // 1つ前のマスの位置を代入
                                turn = 1; // ひっくり返せる
                                break;
                            } else if(search == 0) { // なにもなければ探索終了
                                break;
                            }
                        }
                        if(turn == 1) { // 置ける場所をリストにしていく
                            put_yoko[put_list] = i; // 横の座標を記憶する
                            put_tate[put_list] = j; // 縦の座標を記憶する
                            put_list++;
                            next_place = 1;
                        }
                    }
                }
            }
        }
        
/*
        for(int i=0; i<put_list; i++) {
            System.out.println(put_yoko[i] + " " + put_tate[i]);
        }
*/
    
        int list_end = put_list-1;
        put_list = 0;
        System.out.println("次に打つことができる場所");
        for(int j=0; j<SIZE; j++){
            for(int i=0; i<SIZE; i++){
                if(put_yoko[put_list] == i && put_tate[put_list] == j && list_end >= 0) {
                    System.out.print("× ");
                    if(list_end != put_list) put_list++;
                } else {
                    if(check_state[i][j] == 0) System.out.print("  ");
                    else if(check_state[i][j] == 1) System.out.print("● ");
                    else if(check_state[i][j] == 2) System.out.print("○ ");
                }
            }
            System.out.println();
        }

        
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
