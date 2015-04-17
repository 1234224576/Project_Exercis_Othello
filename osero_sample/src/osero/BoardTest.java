package osero;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class ConsoleBoard extends Board{
	public void print(){
		System.out.println("　　a  b  c  d  e  f  g  h　");
		for(int y=1;y<=8;y++){
			System.out.print(" " + y);
			for(int x=1;x<=8;x++){
				switch (getColor(new Point(x,y))) {
				case Disc.BLACK:
					System.out.print(" ● ");
					break;
				case Disc.WHITE:
					System.out.print(" ○ ");
					break;
				default:
					System.out.print("   ");
					break;
				}
			}
			System.out.println();
		}
	}
}

public class BoardTest {
	public static void main(String args[]){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		ConsoleBoard board = new ConsoleBoard();
		
		while(true){
			board.print();
			System.out.print("黒石" + board.countDisc(Disc.BLACK)+ " ");
			System.out.print("白石" + board.countDisc(Disc.WHITE)+ " ");
			System.out.print("空マス" + board.countDisc(Disc.EMPTY)+ " ");
			System.out.println();
			System.out.print("手を入力して下さい:");
			Point p;
			String in;
			
			try {
				in = br.readLine();
			} catch (IOException e) {
				// TODO: handle exception
				return;
			}
			
			if(in.equals("p")){
				if(!board.pass()){
					System.out.println("パス出来ません！");
				}
				continue;
			}
			if(in.equals("u")){
				board.undo();
				continue;
			}
			try {
				p = new Point(in);
			} catch (IllegalArgumentException e) {
				// TODO: handle exception
				System.out.println("リバーシ形式の手を入力して下さい！");
				continue;
			}
			if(board.move(p) == false){
				System.out.println("そこには置けません！");
				continue;
			}
			if(board.isGameOver()){
				System.out.println("ーーーーーーーーーーーーーーゲーム終了ーーーーーーーーーーーーーー");
				return;
			}
		}
		
	}
}
