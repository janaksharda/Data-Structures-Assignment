class node{
	Object data;
	node next;
	node(){
		data = new Object();
	}
}
class Myset{
	node a;
	public Myset (){
		a = new node();
		a.next=null;
	}
	public Boolean IsEmpty(){
		if(a.next==null) return true;
		return false;
	}
	void Insert(Object n){
		if(IsMember(n)) return;
		node temp = a;
		node b = new node();
		b.data=n;
		b.next=a;
		a=b;
		return;
	}
	void print(){
		node temp = new node();                           //Myset temp = new Myset();
		temp = a;                                         //temp.a=a.next;
		while(temp.next!=null){                           //System.out.println(a.data);
			System.out.println(temp.data);                //temp.print();
			temp = temp.next;
		}
		return;
	}
	void delete(Object n){

		node temp = a;
		if(a.data==n){
			a=a.next;
			return;
		}
		while(a.next!=null){
			if (a.next.data==n){
				a.next=a.next.next;
				break;
			}
			a=a.next;
		}
		a=temp;
		return;
	}
	public Boolean IsMember(Object n){
		node temp = a;
		int c=0;
		while(temp.next!=null){
			if(temp.data==n){
				c=1;
				break;
			}
			temp=temp.next;
		}
		if(c==1) return true;
		return false;
	}
	public Myset Union(Myset b){
		Myset union = new Myset();
		node tempa,tempb;
		tempa=a;
		tempb=b.a;
		while(tempa.next!=null) {
			union.Insert(tempa.data); 
			tempa=tempa.next;
		}
		while(tempb.next!=null) {
			union.Insert(tempb.data);
			tempb=tempb.next;
		}
		return union;
	}
	public Myset Intersection(Myset b){
		Myset intersection = new Myset();
		node tempa;
		tempa=a;
		while(tempa.next!=null){
			if(b.IsMember(tempa.data)) intersection.Insert(tempa.data);
			tempa = tempa.next;
		}
		return intersection;
	}
}