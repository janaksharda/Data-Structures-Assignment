class ExchangeListNode{
	Exchange data;
	ExchangeListNode next;
	ExchangeListNode(){
		data = null;
		next = null;
	}
}
class ExchangeList{
	ExchangeListNode a = null;
	ExchangeList(){
		a = new ExchangeListNode();
	}
	void add(Exchange b){
		ExchangeListNode node = new ExchangeListNode();
		node.data=b;
		node.next = a;
		a = node;
	}
	String stringExchangeID(){
		String s="";
		ExchangeListNode temp = a;
		if(temp==null) return s;
		if((temp.next == null)||(temp.data==null))return "";
		while((temp.next!=null)&&(temp.data!=null)){
			s=temp.data.UniqueID+", "+s;
			temp = temp.next;
		}
		s = s.substring(0,s.length()-2);
		return s;
	}
	void addbefore(Exchange b,Exchange c){
		ExchangeListNode newnode = new ExchangeListNode();
		ExchangeListNode abc = new ExchangeListNode();
		newnode.data = b;
		ExchangeListNode temp =a;
		if(temp.data == c){
			add(b);	
		}
		else{
			while(temp.next.data!=c){
				temp=temp.next;
			}
		}
		ExchangeListNode x = temp.next;
		temp.next = newnode;
		newnode.next = x;
		return;
	}
}