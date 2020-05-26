package test;

public class SList {
	
	private SListNode head;
	private int size = 0;
	
	public SList(){
		head = null;
		size = 0;
	}
	
	public void insertEnd(Object obj) {
		//不知對還錯
		while(head != null) {
			head = head.next;
		}
		
		head = new SListNode(obj);
		System.out.println(++size);
	}
	
	class SListNode{
		
		public Object item;
		public SListNode next;
		
		SListNode(Object item){
			this.item = item;
			this.next = null;
//			Thread.dumpStack();
		}

	}

	public static void main(String[] args) {
		SList list = new SList();
		String str = "string";
		list.insertEnd(str);
	}

}
