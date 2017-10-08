package comp4004.library;

public class SOutput {
	String out;
	int state;
	
	public SOutput(String output,int state){
		this.out=output;
		this.state=state;
	}
	
	public String toString(){
		return "["+this.out+","+this.state+"]";
	}

	public String getOutput() {
		return out;
	}

	public void setOutput(String output) {
		this.out = output;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}