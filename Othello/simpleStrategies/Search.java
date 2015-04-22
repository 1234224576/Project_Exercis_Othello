package simpleStrategies;
public class Search {

	static final int SIZE = 8;
	public enum Phasing{
		BLACK,
		WHITE;
	}

	public Search(){

	}

	public int[][] checkNextBoard(int[][] board,Point p,Phasing ph){

		int yoko = p.x;
		int tate = p.y;

        /* 上方向の探索 */
        if(tate != 0) { // そのポイントが端でない場合
            int search = board[yoko][tate-1]; // 隣のマスの情報を代入
            int turn = 0;
            int turn_tate = 0;
            if(search == 2 && (tate-1) != 0) { // 隣のマスに対戦相手の石があるなら探索開始
                // ただし、それが端のマスでない場合
                for(int i=0; i<=tate-2; i++) {
                    search = board[yoko][tate-2-i]; // さらに隣のマスの情報を代入
                    if(search == 1) {
                        turn_tate = tate-1-i; // 1つ前のマスの位置を代入
                        turn = 1; // ひっくり返せる
                        break;
                    } else if(search == 0) { // なにもなければ探索終了
                        break;
                    }
                }
                if(turn == 1) { // 挟んだ相手の石をひっくり返していく
                    for(int i=0; i<(tate-turn_tate); i++) {
                        board[yoko][turn_tate+i] = 1;
                    }
                }
            }
        }
        
        /* 下方向の探索 */
        if(tate != 7) { // そのポイントが端でない場合
            int search = board[yoko][tate+1]; // 隣のマスの情報を代入
            int turn = 0;
            int turn_tate = 0;
            if(search == 2 && (tate+1) != 7) { // 隣のマスに対戦相手の石があるなら探索開始
                // ただし、それが端のマスでない場合
                for(int i=0; i<=5-tate; i++) {
                    search = board[yoko][tate+2+i]; // さらに隣のマスの情報を代入
                    if(search == 1) {
                        turn_tate = tate+1+i; // 1つ前のマスの位置を代入
                        turn = 1; // ひっくり返せる
                        break;
                    } else if(search == 0) { // なにもなければ探索終了
                        break;
                    }
                }
                if(turn == 1) { // 挟んだ相手の石をひっくり返していく
                    for(int i=0; i<(turn_tate-tate); i++) {
                        board[yoko][turn_tate-i] = 1;
                    }
                }
            }
        }
        
        /* 左方向の探索 */
        if(yoko != 0) { // そのポイントが端でない場合
            int search = board[yoko-1][tate]; // 隣のマスの情報を代入
            int turn = 0;
            int turn_yoko = 0;
            if(search == 2 && (yoko-1) != 0) { // 隣のマスに対戦相手の石があるなら探索開始
                // ただし、それが端のマスでない場合
                for(int i=0; i<=yoko-2; i++) {
                    search = board[yoko-2-i][tate]; // さらに隣のマスの情報を代入
                    if(search == 1) {
                        turn_yoko = yoko-1-i; // 1つ前のマスの位置を代入
                        turn = 1; // ひっくり返せる
                        break;
                    } else if(search == 0) { // なにもなければ探索終了
                        break;
                    }
                }
                if(turn == 1) { // 挟んだ相手の石をひっくり返していく
                    for(int i=0; i<(yoko-turn_yoko); i++) {
                        board[turn_yoko+i][tate] = 1;
                    }
                }
            }
        }
        
        /* 右方向の探索 */
        if(yoko != 7) { // そのポイントが端でない場合
            int search = board[yoko+1][tate]; // 隣のマスの情報を代入
            int turn = 0;
            int turn_yoko = 0;
            if(search == 2 && (yoko+1) != 7) { // 隣のマスに対戦相手の石があるなら探索開始
                // ただし、それが端のマスでない場合
                for(int i=0; i<=5-yoko; i++) {
                    search = board[yoko+2+i][tate]; // さらに隣のマスの情報を代入
                    if(search == 1) {
                        turn_yoko = yoko+1+i; // 1つ前のマスの位置を代入
                        turn = 1; // ひっくり返せる
                        break;
                    } else if(search == 0) { // なにもなければ探索終了
                        break;
                    }
                }
                if(turn == 1) { // 挟んだ相手の石をひっくり返していく
                    for(int i=0; i<(turn_yoko-yoko); i++) {
                        board[turn_yoko-i][tate] = 1;
                    }
                }
            }
        }
        
        /* 左上方向の探索 */
        if(yoko != 0 && tate != 0) { // そのポイントが端でない場合
            int search = board[yoko-1][tate-1]; // 隣のマスの情報を代入
            int turn = 0;
            int turn_yoko = 0;
            int turn_tate = 0;
            int d = 0;
            int turn_d = 0;
            // 隣のマスに対戦相手の石があるなら探索開始
            // ただし、それが端のマスでない場合
            if(search == 2 && ((yoko-1) != 0 && (tate-1) != 0)) {
                if(yoko<=tate) {
                    d = yoko-2;
                } else {
                    d = tate-2;
                }
                for(int i=0; i<=d; i++) {
                    search = board[yoko-2-i][tate-2-i]; // さらに隣のマスの情報を代入
                    if(search == 1) {
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
                        board[turn_yoko+i][turn_tate+i] = 1;
                    }
                }
            }
        }
        
        /* 左下方向の探索 */
        if(yoko != 0 && tate != 7) { // そのポイントが端でない場合
            int search = board[yoko-1][tate+1]; // 隣のマスの情報を代入
            int turn = 0;
            int turn_yoko = 0;
            int turn_tate = 0;
            int d = 0;
            int turn_d = 0;
            // 隣のマスに対戦相手の石があるなら探索開始
            // ただし、それが端のマスでない場合
            if(search == 2 && ((yoko-1) != 0 && (tate+1) != 7)) {
                if(yoko<=7-tate) {
                    d = yoko-2;
                } else {
                    d = 5-tate;
                }
                for(int i=0; i<=d; i++) {
                    search = board[yoko-2-i][tate+2+i]; // さらに隣のマスの情報を代入
                    if(search == 1) {
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
                        board[turn_yoko+i][turn_tate-i] = 1;
                    }
                }
            }
        }
        
        /* 右上方向の探索 */
        if(yoko != 7 && tate != 0) { // そのポイントが端でない場合
            int search = board[yoko+1][tate-1]; // 隣のマスの情報を代入
            int turn = 0;
            int turn_yoko = 0;
            int turn_tate = 0;
            int d = 0;
            int turn_d = 0;
            // 隣のマスに対戦相手の石があるなら探索開始
            // ただし、それが端のマスでない場合
            if(search == 2 && ((yoko+1) != 7 && (tate-1) != 0)) {
                if(7-yoko<=tate) {
                    d = 5-yoko;
                } else {
                    d = tate-2;
                }
                for(int i=0; i<=d; i++) {
                    search = board[yoko+2+i][tate-2-i]; // さらに隣のマスの情報を代入
                    if(search == 1) {
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
                        board[turn_yoko-i][turn_tate+i] = 1;
                    }
                }
            }
        }
        
        /* 右下方向の探索 */
        if(yoko != 7 && tate != 7) { // そのポイントが端でない場合
            int search = board[yoko+1][tate+1]; // 隣のマスの情報を代入
            int turn = 0;
            int turn_yoko = 0;
            int turn_tate = 0;
            int d = 0;
            int turn_d = 0;
            // 隣のマスに対戦相手の石があるなら探索開始
            // ただし、それが端のマスでない場合
            if(search == 2 && ((yoko+1) != 7 && (tate+1) != 7)) {
                if(7-yoko<=7-tate) {
                    d = 5-yoko;
                } else {
                    d = 5-tate;
                }
                for(int i=0; i<=d; i++) {
                    search = board[yoko+2+i][tate+2+i]; // さらに隣のマスの情報を代入
                    if(search == 1) {
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
                        board[turn_yoko-i][turn_tate-i] = 1;
                    }
                }
            }
        }

		return board;
	}

	private void plotBoard(int[][] board,Point p){
		int tate = p.x;
		int yoko = p.y;

		for(int j=0; j<SIZE; j++){
            for(int i=0; i<SIZE; i++){
                if(i == yoko && j == tate) {
                    System.out.print("× ");
                } else {
                    if(board[i][j] == 0) System.out.print("  ");
                    else if(board[i][j] == 1) System.out.print("● ");
                    else if(board[i][j] == 2) System.out.print("○ ");
                }
            }
            System.out.println();
        }
	}

}