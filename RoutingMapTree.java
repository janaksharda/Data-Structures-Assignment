import java.util.*;
class RoutingMapTree{
	Exchange node;
	RoutingMapTree(){
		node = new Exchange(0);
	}
	public Boolean containsNode(Exchange a){
		int c=0;
		Exchange temp = node;
		if(temp==null) return false;
		if (temp==a) return true;
		for(int i=0; i<temp.numChildren();i++){
			RoutingMapTree x = a.subtree(i);
			if(x.containsNode(a)){
				c=1;
				break;
			}
		}
		if(c ==1) return true;
		return false;
	}
	public void switchOn(MobilePhone a, Exchange b){
		if(a.location()!=null) deleteMobile(a,a.location());
		a.switchOn();
		if(a.exchangelocation==b) return;
		a.exchangelocation = b;
		Exchange temp = b;
		while(temp!=null){
			MobilePhoneSet temp1 = temp.residentSet();
			temp1.add(a);
			temp=temp.parent();
		}
	}
	public Exchange findPhone(MobilePhone m){                             //new function
		if(!node.residentset.IsMember(m)) throw new IllegalArgumentException("MobilePhone does not exist");
		if(!m.status()) throw new IllegalArgumentException("MobilePhone is switched off");
		return m.location();
	}
	public Exchange lowestRouter(Exchange a, Exchange b){                 //new function
		if((a==null)||(b==null)) throw new IllegalArgumentException("a or b does not exist");
		if(a==b) return a;
		else return lowestRouter(a.parent(),b.parent());
	}
	public ExchangeList routeCall(MobilePhone a, MobilePhone b){          //new function
		Exchange lowestRouter = null;
		ExchangeList routecall = new ExchangeList();
		lowestRouter = lowestRouter(a.location(),b.location());
		Exchange temp = a.location();
		if(temp == lowestRouter){
			routecall.add(lowestRouter);
			return routecall;
		}
		while(temp!=lowestRouter){
			routecall.add(temp);
			temp = temp.parent();
		}
		routecall.add(lowestRouter);
		routecall.add(b.location());
		temp = b.location().parent();
		while(temp!=lowestRouter){
			routecall.addbefore(temp,lowestRouter);
			temp = temp.parent();
		}
		return routecall;
	}
	public void movePhone(MobilePhone a, Exchange b){                     //new function
		if(b.numChildren()!=0) throw new IllegalArgumentException("b is not a base station");
		if(a.location()==b) return;
		int aID = a.UniqueID;
		if(a!=null){
			deleteMobile(a,a.location());
		}
		MobilePhone x = new MobilePhone(aID);
		switchOn(x,b);
		return ;                  
	}
	void deleteMobile(MobilePhone a, Exchange b){
		Exchange temp = b;
		while(temp!=null){
			MobilePhoneSet templist = temp.residentSet();
			templist.delete(a);
			temp = temp.parent();
		}
	}
	public void switchOff(MobilePhone a){
		if(a.status()==false) return;
		a.switchOff();
	}
	public String performAction(String actionMessage){
		String str[] = actionMessage.split(" ");
		if (str[0].equals("addExchange")){
			Exchange newNode = new Exchange(Integer.parseInt(str[2]));
			int aID = Integer.parseInt(str[1]);
			try{
			if(findNodeFromID(aID)!=null){
				Exchange node = findNodeFromID(aID);
				node.addExchangeNode(newNode); 
				return "";
			}
			else throw new IllegalArgumentException("Error: Exchange Node with ID " +aID+" does not exist") ;
			}
			catch(Exception e){
			   return ("Error: Exchange Node with ID " +aID+" does not exist");
			}
		}
		else if(str[0].equals("switchOnMobile")){
			int aID = Integer.parseInt(str[1]);
			int bID = Integer.parseInt(str[2]);
			Exchange b = findNodeFromID(bID);
			try{
			if(b.numChildren()==-1) throw new IllegalArgumentException("Error- Exchange node with identifier "+bID+" is not a base station");
			if(b!=null){
				MobilePhone a = b.residentset.findMobilewithID(aID);
				if(a!=null){
					deleteMobile(a,a.location());
				} 
				MobilePhone x = new MobilePhone(aID);
				switchOn(x,b);
				return "";
			}
			else throw new IllegalArgumentException("Error- No exchange with identifier "+bID);  
			}
			catch(Exception e){
			    return "Error- No exchange with identifier b";
			}
		}
		else if(str[0].equals("switchOffMobile")){
			int aID = Integer.parseInt(str[1]);
			MobilePhone a = node.residentset.findMobilewithID(aID);
			try{
			if(a!=null){ 
				a.switchOff();
				return "";
			}
			else throw new IllegalArgumentException( "Error - No mobile phone with identifier "+aID);
			}
			catch(Exception e){
			    return "Error - No mobile phone with identifier "+aID;
			}
		}
		else if(str[0].equals("queryNthChild")){
			int aID = Integer.parseInt(str[1]);
			int bID = Integer.parseInt(str[2]);
			Exchange a = findNodeFromID(aID);
			try{
			if(a.numChildren()>=bID)
				return actionMessage+": "+a.child(bID).UniqueID;
			else throw new IllegalArgumentException( "Error - No b child of Exchange a");
			}
			catch(Exception e){
			    return "Error - No b child of Exchange a";
			}   
		}
		else if(str[0].equals("queryMobilePhoneSet")){
			int aID = Integer.parseInt(str[1]);
			Exchange a = findNodeFromID(aID);
		    try{
			if(a!=null){
				String t = a.residentSet().printMobileSetID();
				return actionMessage+": "+t;
			}
			else throw new IllegalArgumentException( "Error - No exchange with identifier "+aID);
		    }
		    catch(Exception e){
		        return "Error - No exchange with identifier a";
		    }   
		}
		else if(str[0].equals("findPhone")){
			int aID = Integer.parseInt(str[1]);
			MobilePhone m = node.residentset.findMobilewithID(aID);
			try{
			Exchange loc = findPhone(m);
			return "queryFindPhone "+aID+ ": " + loc.UniqueID;
			}
			catch(Exception e){
			    return "queryFindPhone "+aID+": Error - No mobile phone with identifier "+aID+" found in the network";
			}   
	    }
		else if(str[0].equals("lowestRouter")){
			int aID = Integer.parseInt(str[1]);
			int bID = Integer.parseInt(str[2]);
			Exchange e1 = findNodeFromID(aID);
			Exchange e2 = findNodeFromID(bID);
			try{
			return "queryLowestRouter "+aID+" "+bID+": "+lowestRouter(e1,e2).UniqueID;	
			}
			catch(Exception e){
			    if(e1==null) return "queryLowestRouter "+aID+" "+bID+": Error - No mobile phone with identifier "+aID+" found in the network";
 			    if(e2==null) return "queryLowestRouter "+aID+" "+bID+": Error - No mobile phone with identifier "+bID+" found in the network";
			}   
		}
		else if(str[0].equals("findCallPath")){
			int aID = Integer.parseInt(str[1]);
			int bID = Integer.parseInt(str[2]);
			try{
			MobilePhone m1 = node.residentset.findMobilewithID(aID);
			MobilePhone m2 = node.residentset.findMobilewithID(bID);
			if(m1.status()&&m2.status())
			    return "queryFindCallPath " +aID+" "+bID + ": " + routeCall(m1,m2).stringExchangeID(); 
			else throw new IllegalArgumentException();
			}
			catch(Exception e){
			    return "queryFindCallPath "+aID+" "+bID+": Error - Mobile phone with identifier "+bID+" is currently switched off";
			}   
		}
		else if(str[0].equals("movePhone")){
			int aID = Integer.parseInt(str[1]);
			int bID = Integer.parseInt(str[2]);
			try{
			    Exchange b = findNodeFromID(bID);
				MobilePhone a = b.residentset.findMobilewithID(aID);
				if(a!=null){
					deleteMobile(a,a.location());
				} 
				MobilePhone x = new MobilePhone(aID);
				switchOn(x,b);
				return "";
			}
			catch(Exception e){
			    if(findNodeFromID(bID)==null) return  "Error - No exchange with identifier "+bID;
			    if((findNodeFromID(bID)).residentset.findMobilewithID(aID)==null)return "movePhone "+aID+" "+bID+": Error - No mobile phone with identifier "+aID+" found in the network";
			}   
		}
		return "Error";
	}
	void disp(){
		if(node == null) return;
		System.out.println("ID "+this.node.UniqueID);
		MobilePhoneSet a = node.residentSet();
		a.printMobileSetID();
		RoutingMapTree temp = this;
		for (int i=0; i<node.numChildren();i++){
			this.node.subtree(i).disp();
		}
		return;
	}
	Exchange findNodeFromID(int Id){
		int d=0;
		Exchange c = new Exchange(0);
		if(node.numChildren()==0){
			if(node.UniqueID == Id) return node;
			else return null;
		}
		if(node.UniqueID == Id) return node;
		for (int i = 0; i<node.numChildren();i++){
			RoutingMapTree subtreei = node.subtree(i);
			c = subtreei.findNodeFromID(Id);
			if(c!=null){
				d=1;
				break;
			}
		}
		if(d==1) return c;
		return null;
	}
}