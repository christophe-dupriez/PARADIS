package be.labo.data;

import be.labo.data.auto._Placemap;

public class Placemap extends _Placemap {
	
	public int getXposInt() {
		return (int) ((double)this.getXpos());
	}

	public int getYposInt() {
		return (int) ((double)this.getYpos());
	}


}
