import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
public class Forest<Tree> implements Iterable<Tree>{
	
ArrayList<Tree> treeList = new ArrayList<Tree>();
	public Forest(){
		treeList.add(new Tree("oak"));
		addTree(new Tree("redwood"));
		addTree(new Tree("pine"));
		addTree(new Tree("palm"));
	}
	public void addTree(Tree t){
		treeList.add(t);
	}
	
	
	
	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	public Forest (Tree t){
		
		
	}
	
	

}



class PersistentStorage implements Serializable {
	private static final long serialVersionUID = 123L;
    private String s1;
    private String s2;

    public PersistentStorage(String s1, String s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    public String getInstanceSecret() {
        return s2 + s1;
    }
    
    public String getComputedSecret() {
    	return System.getProperty("user.dir");
    }
}
