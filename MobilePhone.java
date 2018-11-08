class MobilePhone{
	int UniqueID;
	Exchange exchangelocation;
	int Status;
	MobilePhone(int n){
		UniqueID = n;
		exchangelocation = null;
		Status =0;
	}
	public void switchOn(){
		Status = 1;
		return;
	}
	public void switchOff(){
		Status = 0;
		return;
	}
	public Boolean status(){
		if(Status==1) return true;
		return false;
	} 
	public Exchange location(){
		return exchangelocation;
	}
}