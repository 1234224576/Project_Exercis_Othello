package osero;

class PerfectEvaluator implements Evaluator
{
	public int evaluate(Board board)
	{
		int discdiff
			= board.getCurrentColor()
			* (board.countDisc(Disc.BLACK) - board.countDisc(Disc.WHITE));
		
		return discdiff;
	}
}
