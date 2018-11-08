class Exchange{
	int UniqueID;
	Exchange Parent;
	ExchangeList listOfChildren;
	MobilePhoneSet residentset;
	public Exchange(int n){
		UniqueID=n;
		residentset = new MobilePhoneSet();
		Parent = null;
		listOfChildren = new ExchangeList();
	}
	public Exchange parent(){
		return Parent;
	}
	public int numChildren(){
		int i=0;
		ExchangeList temp = listOfChildren;
		ExchangeListNode abc = new ExchangeListNode();
		abc = temp.a;
		while(abc!=null){
			i++;
			abc=abc.next;
		}
		return i-1;
	}
	public Exchange child(int i){
		ExchangeList temp = listOfChildren;
		ExchangeListNode abc = temp.a; 
		int k = numChildren();
		for(int n = 0; n<k-i-1; n++){
			abc = abc.next;
		}
		return abc.data;
	}
	public Boolean isRoot(){
		if (UniqueID==0) return true;
		return false;
	}
	public RoutingMapTree subtree(int i){
		RoutingMapTree subTree = new RoutingMapTree();
		subTree.node = child(i);
		return subTree;
	}
	public MobilePhoneSet residentSet(){
		return residentset;
	}
	void addExchangeNode(Exchange n){
		listOfChildren.add(n);
		n.Parent = this;
		residentset = residentset.union(n.residentset);
		return;
	}
}