class test {
	public static void main (String[] args) throws Exception {
		long t1,t2;
		t1 = System.currentTimeMillis();
		for (int i=0; i<100000000; i++) {
			Math.random();
		}
		t2 = System.currentTimeMillis();
		System.out.println( (t2 -t1)/1000.0 + "sec");
	}
}
