class MobilePhoneSet{
	Myset head;
	public MobilePhoneSet(){
		head = new Myset();
		head.a.data = new MobilePhone(0);
	}
	void add(MobilePhone b){
		head.Insert(b);
	}
	void delete(MobilePhone b){
		head.delete(b);
	}
	MobilePhoneSet union(MobilePhoneSet b){
		MobilePhoneSet temp = new MobilePhoneSet();
		temp.head = b.head.Union(head);
		return temp;
	}
	MobilePhone findMobilewithID(int id){
		MobilePhone y = null;
		Myset temp = head;
		node x = temp.a;
		if(x.next==null) return y; 
		MobilePhone temp1 = (MobilePhone)x.data;
		while(x.next!=null){
			temp1 = (MobilePhone)x.data;
			if(temp1.UniqueID == id){
				y = temp1;
				break;
			}
			x = x.next;
		}
		return y;
	}
	Boolean IsMember(MobilePhone m){
		return head.IsMember(m);
	}
	String printMobileSetID(){
		String s="";
		MobilePhone y;
		Myset temp = head;
		node x = head.a;
		if(x.next == null)return "";
		while(x.next!=null){
			y = (MobilePhone)x.data;          
			if(y.status())s=y.UniqueID+", "+s;
			x = x.next;
		}
		s = s.substring(0,s.length()-2);
		return s;
	}
}