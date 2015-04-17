package osero;

public class Point {
	
	public int x;
	public int y;
	
	public Point(){
		this(0,0);
	}
	
	public Point(int x,int y){
		this.x = x;
		this.y = y;
	}
	
	public Point(String coord) throws IllegalArgumentException{
		if(coord == null || coord.length() < 2)
			throw new IllegalArgumentException("The Argument must be Reversi style coordinates!");
		x = coord.charAt(0) - 'a' + 1;
		y = coord.charAt(1) - '1' + 1;
	}
	
	public String toString(){
		String coord = new String();
		coord += (char)('a' + x-1);
		coord += (char)('1' + y-1);
		return coord;
	}

}
