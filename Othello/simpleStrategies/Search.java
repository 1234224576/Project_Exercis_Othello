package simpleStrategies;
public class Search {

	static final int SIZE = 8;

	public enum Phasing{
		BLACK,
		WHITE,
	}

	public Search(){

	}

	public int[][] checkNextBoard(int[][] originalBoard,Point p,Phasing ph){
        int[][] board = new int[SIZE][SIZE];
        for(int j=0;j<SIZE;j++){
            for(int i=0;i<SIZE;i++){
                board[i][j] = originalBoard[i][j];
            }
        }

		int yoko = p.x;
		int tate = p.y;

        int back_color;
        int front_color;

        if(ph == ph.BLACK){
            front_color = 1;
            back_color = 2;
        }else{
            front_color = 2;
            back_color = 1;
        }

        /* 上方向の探索 */
        if(tate != 0) { // そのポイントが端でない場合
            int search = board[yoko][tate-1]; // 隣のマスの情報を代入
            int turn = 0;
            int turn_tate = 0;
            if(search == back_color && (tate-1) != 0) { // 隣のマスに対戦相手の石があるなら探索開始
                                               // ただし、それが端のマスでない場合
                for(int i=0; i<=tate-2; i++) {
                    search = board[yoko][tate-2-i]; // さらに隣のマスの情報を代入
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
                        board[yoko][turn_tate+i] = front_color;
                    }
                }
            }
        }
        
        /* 下方向の探索 */
        if(tate != 7) { // そのポイントが端でない場合
            int search = board[yoko][tate+1]; // 隣のマスの情報を代入
            int turn = 0;
            int turn_tate = 0;
            if(search == back_color && (tate+1) != 7) { // 隣のマスに対戦相手の石があるなら探索開始
                // ただし、それが端のマスでない場合
                for(int i=0; i<=5-tate; i++) {
                    search = board[yoko][tate+2+i]; // さらに隣のマスの情報を代入
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
                        board[yoko][turn_tate-i] = front_color;
                    }
                }
            }
        }

        /* 左方向の探索 */
        if(yoko != 0) { // そのポイントが端でない場合
            int search = board[yoko-1][tate]; // 隣のマスの情報を代入
            int turn = 0;
            int turn_yoko = 0;
            if(search == back_color && (yoko-1) != 0) { // 隣のマスに対戦相手の石があるなら探索開始
                // ただし、それが端のマスでない場合
                for(int i=0; i<=yoko-2; i++) {
                    search = board[yoko-2-i][tate]; // さらに隣のマスの情報を代入
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
                        board[turn_yoko+i][tate] = front_color;
                    }
                }
            }
        }

        /* 右方向の探索 */
        if(yoko != 7) { // そのポイントが端でない場合
            int search = board[yoko+1][tate]; // 隣のマスの情報を代入
            int turn = 0;
            int turn_yoko = 0;
            if(search == back_color && (yoko+1) != 7) { // 隣のマスに対戦相手の石があるなら探索開始
                // ただし、それが端のマスでない場合
                for(int i=0; i<=5-yoko; i++) {
                    search = board[yoko+2+i][tate]; // さらに隣のマスの情報を代入
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
                        board[turn_yoko-i][tate] = front_color;
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
            if(search == back_color && ((yoko-1) != 0 && (tate-1) != 0)) {
                if(yoko<=tate) {
                    d = yoko-2;
                } else {
                    d = tate-2;
                }
                for(int i=0; i<=d; i++) {
                    search = board[yoko-2-i][tate-2-i]; // さらに隣のマスの情報を代入
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
                        board[turn_yoko+i][turn_tate+i] = front_color;
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
            if(search == back_color && ((yoko-1) != 0 && (tate+1) != 7)) {
                if(yoko<=7-tate) {
                    d = yoko-2;
                } else {
                    d = 5-tate;
                }
                for(int i=0; i<=d; i++) {
                    search = board[yoko-2-i][tate+2+i]; // さらに隣のマスの情報を代入
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
                        board[turn_yoko+i][turn_tate-i] = front_color;
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
            if(search == back_color && ((yoko+1) != 7 && (tate-1) != 0)) {
                if(7-yoko<=tate) {
                    d = 5-yoko;
                } else {
                    d = tate-2;
                }
                for(int i=0; i<=d; i++) {
                    search = board[yoko+2+i][tate-2-i]; // さらに隣のマスの情報を代入
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
                        board[turn_yoko-i][turn_tate+i] = front_color;
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
            if(search == back_color && ((yoko+1) != 7 && (tate+1) != 7)) {
                if(7-yoko<=7-tate) {
                    d = 5-yoko;
                } else {
                    d = 5-tate;
                }
                for(int i=0; i<=d; i++) {
                    search = board[yoko+2+i][tate+2+i]; // さらに隣のマスの情報を代入
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
                        board[turn_yoko-i][turn_tate-i] = front_color;
                    }
                }
            }
        }
        return board;
	}

    public int[][] obtainMovablePosition(int[][] originalBoard,Phasing ph){
        
        int[][] board = new int[SIZE][SIZE];
        for(int j=0;j<SIZE;j++){
            for(int i=0;i<SIZE;i++){
                board[i][j] = originalBoard[i][j];
            }
        }

        int[] put_yoko = new int[SIZE*SIZE];
        int[] put_tate = new int[SIZE*SIZE];
        int put_list = 0; // リストの数
        int next_place = 0;

        int front_color;
        int back_color;

        if(ph == ph.BLACK){
            front_color = 1;
            back_color = 2;
        }else{
            front_color = 2;
            back_color = 1;
        }
        
        for(int j=0; j<SIZE; j++){
            for(int i=0; i<SIZE; i++){
                next_place = 0;
                
                /* 上方向の探索 */
                if(board[i][j] == 0 && j != 0) { // そのポイントが端でない場合
                    int search = board[i][j-1]; // 隣のマスの情報を代入
                    int turn = 0;
                    int turn_tate = 0;
                    if(search == back_color && (j-1) != 0) { // 隣のマスに対戦相手の石があるなら探索開始
                        // ただし、それが端のマスでない場合
                        for(int k=0; k<=j-2; k++) {
                            search = board[i][j-2-k]; // さらに隣のマスの情報を代入
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
                if(board[i][j] == 0 && j != 7 && next_place == 0) { // そのポイントが端でない場合
                    int search = board[i][j+1]; // 隣のマスの情報を代入
                    int turn = 0;
                    int turn_tate = 0;
                    if(search == back_color && (j+1) != 7) { // 隣のマスに対戦相手の石があるなら探索開始
                        // ただし、それが端のマスでない場合
                        for(int k=0; k<=5-j; k++) {
                            search = board[i][j+2+k]; // さらに隣のマスの情報を代入
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
                if(board[i][j] == 0 && i != 0 && next_place == 0) { // そのポイントが端でない場合
                    int search = board[i-1][j]; // 隣のマスの情報を代入
                    int turn = 0;
                    int turn_yoko = 0;
                    if(search == back_color && (i-1) != 0) { // 隣のマスに対戦相手の石があるなら探索開始
                        // ただし、それが端のマスでない場合
                        for(int k=0; k<=i-2; k++) {
                            search = board[i-2-k][j]; // さらに隣のマスの情報を代入
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
                if(board[i][j] == 0 && i != 7 && next_place == 0) { // そのポイントが端でない場合
                    int search = board[i+1][j]; // 隣のマスの情報を代入
                    int turn = 0;
                    int turn_yoko = 0;
                    if(search == back_color && (i+1) != 7) { // 隣のマスに対戦相手の石があるなら探索開始
                        // ただし、それが端のマスでない場合
                        for(int k=0; k<=5-i; k++) {
                            search = board[i+2+k][j]; // さらに隣のマスの情報を代入
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
                if(board[i][j] == 0 && i != 0 && j != 0 && next_place == 0) { // そのポイントが端でない場合
                    int search = board[i-1][j-1]; // 隣のマスの情報を代入
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
                            search = board[i-2-k][j-2-k]; // さらに隣のマスの情報を代入
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
                if(board[i][j] == 0 && i != 0 && j != 7 && next_place == 0) { // そのポイントが端でない場合
                    int search = board[i-1][j+1]; // 隣のマスの情報を代入
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
                            search = board[i-2-k][j+2+k]; // さらに隣のマスの情報を代入
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
                if(board[i][j] == 0 && i != 7 && j != 0 && next_place == 0) { // そのポイントが端でない場合
                    int search = board[i+1][j-1]; // 隣のマスの情報を代入
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
                            search = board[i+2+k][j-2-k]; // さらに隣のマスの情報を代入
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
                if(board[i][j] == 0 && i != 7 && j != 7 && next_place == 0) { // そのポイントが端でない場合
                    int search = board[i+1][j+1]; // 隣のマスの情報を代入
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
                            search = board[i+2+k][j+2+k]; // さらに隣のマスの情報を代入
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

        int list_end = put_list-1;
        int[][] movablePointArray = new int[SIZE][SIZE];
        put_list = 0;
        for(int j=0; j<SIZE; j++){
            for(int i=0; i<SIZE; i++){
                if(put_yoko[put_list] == i && put_tate[put_list] == j && list_end >= 0) {
                    movablePointArray[i][j] = 1;
                    if(list_end != put_list) put_list++;
                } else {
                    movablePointArray[i][j] = 0;
                }
            }
        }
        return movablePointArray;
    }

    public boolean getIsGameOver(int[][] board){
        int blackCount = 0;
        int whiteCount = 0;
        int spaceCount = 64;

        for(int j=0; j<SIZE; j++){
            for(int i=0; i<SIZE; i++){
                if(board[i][j] == 0) spaceCount--;
                if(board[i][j] == 1) blackCount++;
                if(board[i][j] == 2) whiteCount++;
            }
        }
        if(blackCount==0 || whiteCount==0 || spaceCount==0) return true;
        return false;
    }

    //現在の盤面
	public void plotBoard(int[][] board,Point p){
		int yoko = p.x;
		int tate = p.y;
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

    public void plotMovableBoard(int[][] board){
        for(int j=0; j<SIZE; j++){
            for(int i=0; i<SIZE; i++){
                if(board[i][j] == 1) {
                    System.out.print("1 ");
                } else {
                     System.out.print("0 ");
                }
            }
            System.out.println();
        }
    }

    // public static void main(String[] args){


    //     int[][] test = {{0,0,0,0,0,0,0,0},
    //                     {0,0,0,0,0,0,0,0},
    //                     {0,0,2,2,2,2,1,0},
    //                     {0,0,1,1,1,0,0,0},
    //                     {0,0,1,1,1,0,0,0},
    //                     {0,0,0,0,2,0,0,0},
    //                     {0,0,0,0,2,0,0,0},
    //                     {0,0,0,0,0,0,0,0},
    //                     };

    //     Search app = new Search();

    //     int[][] movable = app.obtainMovablePosition(test,Phasing.BLACK);

    //      app.plotMovableBoard(movable);
    //     Point p = new Point();
    //     p.x = 1;
    //     p.y = 2;
    //     // for(int i=0;i<SIZE;i++){
    //     //     for(int j=0;j<SIZE;j++){
    //     //         if(movable[i][j] == 1){
    //     //             p.x = i;
    //     //             p.y = j;
    //     //         }
    //     //     }
    //     // }

    //     int [][] obtainBoard = app.checkNextBoard(test,p,Phasing.BLACK);
    //     app.plotBoard(obtainBoard,p);

    //     System.out.println("テスト用テスト用テスト用テスト用テスト用テスト用テスト用テスト用テスト用");
    // }

}