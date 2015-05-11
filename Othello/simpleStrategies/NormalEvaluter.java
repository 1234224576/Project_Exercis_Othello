package simpleStrategies;
public class NormalEvaluter extends Evaluter {
    private int openLevel; //開放度を格納する
    private int cornerLevel; //角についての減点
    private int[][] board = new int[Search.SIZE][Search.SIZE];
    private int myNum;
    private int eneNum;

    public NormalEvaluter(){
        
    }

    public int evalute(int[][] originalBoard,int myNum,int eneNum,int movableCount){

        this.myNum = myNum;
        this.eneNum = eneNum;
        //情報をセット
        for(int j=0;j<Search.SIZE;j++){
            for(int i=0;i<Search.SIZE;i++){
                board[i][j] = originalBoard[i][j];
            }
        }

        int eval = 100000;

        for(int j=0;j<board.length;j++){
            for(int i=0;i<board.length;i++){
                if(board[i][j] == this.myNum) eval+=10;
            }
        }

        eval += obtainOpenLevelEvalution();
        eval += obtainMountainEvaluation();
        eval += obtainMovableCountEvalution(movableCount);
        eval += obtainDecidedStoneEvalution();
        eval -= obtainWingEvalution();
        eval += kariEval();
        return eval;
    }

    private int kariEval(){
        return this.cornerLevel;
    }
    public void calcConerLevel(int[][] board,int x,int y,Search.Phasing ph){
        if(ph == Search.Phasing.BLACK){
            myNum = 1;
        }else{
            myNum = 2;
        }
        System.out.println(myNum);
        
        int eval = 0;
        if(x == 1 && y == 1 && board[0][0] == 0){
            System.out.println("WARNING");
            eval -= 15000;
        }
        if(x == 6 && y == 1 && board[7][0] == 0){
            System.out.println("WARNING");

            eval -= 15000;
        }
        if(x == 1 && y == 6 && board[0][7] == 0){
            System.out.println("WARNING");

            eval -= 15000;
        }
        if(x == 6 && y == 6 && board[7][7] == 0){
            System.out.println("WARNING");

            eval -= 15000;
        }

        if(board[0][0] != myNum){
            if(x == 0 && y == 1) eval -= 10000;
            if(x == 1 && y == 0) eval -= 10000;
        }
        if(board[0][7] != myNum){
            if(x == 0 && y == 6) eval -= 10000;
            if(x == 1 && y == 7) eval -= 10000;
        }
        if(board[7][0] != myNum){
            if(x == 6 && y == 0) eval -= 10000;
            if(x == 7 && y == 1) eval -= 10000;
        }
        if(board[7][7] != myNum){
            if(x == 7 && y == 6) eval -= 10000;
            if(x == 6 && y == 7) eval -= 10000;
        }
        this.cornerLevel = eval;

    }

    private int obtainOpenLevelEvalution(){
        int eval = 0;
        eval =  openLevel * openLevel * (-20);

        return eval;
    }

    private int obtainMovableCountEvalution(int movableCount){
        int eval = 0;
        eval =  movableCount * 15;
        return eval;
    }

    private int obtainDecidedStoneEvalution(){ // 確定石
        int eval = 0;

        if(board[0][0] == myNum) eval += 10000;
        if(board[7][0] == myNum) eval += 10000;
        if(board[0][7] == myNum) eval += 10000;
        if(board[7][7] == myNum) eval += 10000;
       
        if(board[0][0] == myNum){
            for(int i=1;i<Search.SIZE;i++){
                if(board[i][0] == myNum) {
                    eval+=1000;
                } else {
                    break;
                }
            }
            for(int i=1;i<Search.SIZE;i++){
                if(board[0][i] == myNum) {
                    eval+=1000;
                } else {
                    break;
                }
            }
        }
        if(board[Search.SIZE-1][0] == myNum){
            for(int i=Search.SIZE-1;i>=0;i--){
                if(board[i][0] == myNum) {
                    eval+=1000;
                } else {
                    break;
                }
            }
            for(int i=1;i<Search.SIZE;i++){
                if(board[Search.SIZE-1][i] == myNum) {
                    eval+=1000;
                } else {
                    break;
                }
            }
        }
        if(board[0][Search.SIZE-1] == myNum){
            for(int i=1;i<Search.SIZE;i++){
                if(board[i][Search.SIZE-1] == myNum) {
                    eval+=1000;
                } else {
                    break;
                }
            }
            for(int i=Search.SIZE-1;i>=0;i--){
                if(board[i][0] == myNum) {
                    eval+=1000;
                } else {
                    break;
                }
            }
        }
        if(board[Search.SIZE-1][Search.SIZE-1] == myNum){

            for(int i=Search.SIZE-1;i>=0;i--){
                if(board[i][Search.SIZE-1] == myNum) {
                    eval+=1000;
                } else {
                    break;
                }
            }
            for(int i=Search.SIZE-1;i>=0;i--){
                if(board[i][Search.SIZE-1] == myNum) {
                    eval+=1000;
                } else {
                    break;
                }
            }
        }
        
        return eval;
    }
    
    private int obtainWingEvalution() { // 辺（ウィング）
        int eval = 0;
        int wing = 0;
        int wingTotal = 0;
        
        for(int i=1;i<Search.SIZE;i++) {
            wing = 0;
            if(board[i][0]== 0 && board[i][Search.SIZE-1] == 0){
                if((board[i][1]== myNum && board[i][Search.SIZE-2] == 0) || (board[i][1]== 0 && board[i][Search.SIZE-2] == myNum)) {
                    wing = 1;
                    for(int j=2;j<Search.SIZE-2;j++) {
                        if(board[i][j] != myNum) {
                            wing = 0;
                            break;
                        }
                    }
                }
            }
            if(wing == 1) {
                wingTotal++;
            }
        }
        for(int i=1;i<Search.SIZE;i++) {
            wing = 0;
            if(board[0][i]== 0 && board[Search.SIZE-1][i] == 0){
                if((board[1][i]== myNum && board[Search.SIZE-2][i] == 0) || (board[1][i]== 0 && board[Search.SIZE-2][i] == myNum)) {
                    wing = 1;
                    for(int j=2;j<Search.SIZE-2;j++) {
                        if(board[j][i] != myNum) {
                            wing = 0;
                            break;
                        }
                    }
                }
            }
            if(wing == 1) {
                wingTotal++;
            }
        }
        
        eval = wingTotal * (-400);
        
        return eval;
    }


    private int obtainMountainEvaluation(){
        int eval = 0;

        for(int j=0;j<8;j++){
            boolean judge = true;         
            for(int i=0;i<8;i++){
            if(board[0][j] != 0){
                judge = false;
            }
            if(i != 0 || i != 7 && board[i][j] != myNum ){
                judge = false;
            }
            if(board[7][j] != 0){
                judge = false;
            }   
            }
            if(judge = true){
            eval += 50;
            }       
        }
        for(int i=0;i<8;i++){
            boolean judge = true;
            for(int j=0;j<8;j++){
            if(board[i][0] != 0){
                judge = false;
            }
            if(j != 0 || j != 7 && board[j][i] != myNum){
                judge = false;
            }
            if(board[i][7] != 0){
                judge = false;
            }
            }
            if(judge == true){
            eval += 50;
            }
        }
    

        return eval;
    }

    public void calcOpenLevel(int[][] board,int[][] nextboard,int px,int py){

        int[][] changePoint = new int[8][8];

        for(int j=0;j<8;j++){
            for(int i=0;i<8;i++){
                if(i == px && j == py){
                    changePoint[i][j] = 0;
                    continue;
                }
                if(board[i][j] != nextboard[i][j]){
                    changePoint[i][j] = 1;
                }else{
                    changePoint[i][j] = 0;
                }
            }
        }

        int count = 0;

        int[][] countPlace = new int[8][8];
        for(int j=0;j<8;j++){
            for(int i=0;i<8;i++){
                if(changePoint[i][j] != 1) continue;

                int x = i;
                int y = j;

                if(x < 7 && countPlace[x+1][y] != 1 && nextboard[x+1][y] == 0){
                    countPlace[x+1][y] = 1;
                    count++;
                }
                
                if(x > 0 && countPlace[x-1][y] != 1 && nextboard[x-1][y] == 0){
                    countPlace[x-1][y] = 1;
                    count++;
                }
                
                if(y < 7 && countPlace[x][y+1] != 1 && nextboard[x][y+1] == 0){
                    countPlace[x][y+1] = 1;
                    count++;
                }
                
                if(y > 0 && countPlace[x][y-1] != 1 && nextboard[x][y-1] == 0){
                    countPlace[x][y-1] = 1;
                    count++;
                }
                
                if(x < 7 && y < 7 && countPlace[x+1][y+1] != 1 && nextboard[x+1][y+1] == 0){
                    countPlace[x+1][y+1] = 1;
                    count++;
                }
                
                if(x < 7 && y > 0 && countPlace[x+1][y-1] != 1 && nextboard[x+1][y-1] == 0){
                    countPlace[x+1][y-1] = 1;
                    count++;
                }
                if(x > 0 && y < 7 && countPlace[x-1][y+1] != 1 && nextboard[x-1][y+1] == 0){
                    countPlace[x-1][y+1] = 1;
                    count++;
                }
                if(x > 0 && y > 0 && countPlace[x-1][y-1] != 1 && nextboard[x-1][y-1] == 0){
                    countPlace[x-1][y-1] = 1;
                    count++;
                }
            }
        }
        System.out.println("count:"+count);
        this.openLevel = count;
    }
}
