public class UserSolution {
	private Node root =null;
	private Node cur=null;
	public class MyList<E> {
	    private int size = 0;
	    private static final int DEFAULT_CAPACITY = 10;
	    private Object elements[];
	    public MyList() {
	        elements = new Object[DEFAULT_CAPACITY];
	    }
	    public void add(E e) {
	        if (size == elements.length) {
	            ensureCapa();
	        }
	        elements[size++] = e;
	    }

	    private void ensureCapa() {
	        int newSize = elements.length * 2;
	        Object temp[] = new Object[newSize];
	        for(int i=0; i<elements.length; i++) {
	        	temp[i] = elements[i];
	        }
	        elements = temp;
	    }

	    public E get(int i) {
	        if (i>= size || i <0) {
	            throw new IndexOutOfBoundsException("Index: " + i + ", Size " + i );
	        }
	        return (E) elements[i];
	    }
	    public E remove(int i) {
	        if (i>= size || i <0) {
	            throw new IndexOutOfBoundsException("Index: " + i + ", Size " + i );
	        }
	        E res = (E) elements[i];
	        elements[i] = null;
	        for(int k=i;k<size-1; k++) {
	        	elements[k] = elements[k+1];
	        }
	        size--;
	        return res;
	    }
	}
	class Node{
		MyList<Node> childNode = new MyList<>();
		Node parent;
		String data;
		int size=0;
		public Node(String str) {
			data= str;
		}
		public Node(String str, Node par) {
			data= str;
			parent = par;
		}
		public void insertNode(String str) {
			Node newnode = new Node(str);
			childNode.add(newnode);
			size++;
			newnode.parent = this;
			int i=0;
		}
		public Node removeNode(int i) {
			size--;
			return childNode.remove(i);
		}
		public void reset() {
			childNode = new MyList<>();
			size=0;
		}
	}
	void init(){
		root = new Node("/");
		cur = root;
	}
	
	void make(char[] keyword){
		cur.insertNode(removeSpace(getvalue(keyword)));
	}
	
	void change(char[] keyword)	{
		String word = removeSpace(getvalue(keyword));
		if(equal(word,"/") ) cur = root;
		else if(equal(word,"..") && cur.parent!=null) {
			cur = cur.parent;
			}
		else if(equal(word,"*")) {
			String match = substr(word,0,word.length()-1);
			Node res = null;
			for(int i=0; i<cur.size; i++) {
				String str = cur.childNode.get(i).data;
				if(startWith(str,match)) {
					if(res==null) res = cur.childNode.get(i);
					else if(compare(str,res.data)<0) res = cur.childNode.get(i);
				}
			}
			if(res!=null) cur = res;
		}
		else if(word.length()>1 && endWith(word,"*")) {
			String match = substr(word,0,word.length()-1);
			Node res = null;
			for(int i=0; i<cur.size; i++) {
				String str = cur.childNode.get(i).data;
				if(startWith(str,match)) {
					if(res==null) res = cur.childNode.get(i);
					else if(compare(str,res.data)<0) res = cur.childNode.get(i);
				}
			}
			if(res!=null) cur = res;
		}
		else {
			Node res = null;
			String match = word;
			for(int i=0; i<cur.size; i++) {
				String str = cur.childNode.get(i).data;
				if(equal(str,match)) {
					res = cur.childNode.get(i);
					break;
				}
			}
			if(res!=null) cur = res;
		}
	}
	
	int remove(char[] keyword){
		String word = removeSpace(getvalue((keyword)));
		String match="";
		int total=0;
		if(equal(word,"*")) {
			for(int i=0; i<cur.size; i++) {
				Node target = cur.childNode.get(i);
				total += count(cur.childNode.get(i)) + 1;
			}
			cur.reset();
		}
		else if(word.length() > 1 && endWith(word,"*")) {
			match = substr(word,0, word.length()-1);
			for(int i=0; i<cur.size; i++) {
				Node target = cur.childNode.get(i);
				if(startWith(target.data,match)) {
					total += count(cur.childNode.get(i)) + 1;
					cur.removeNode(i);
					i--;
				} 
			}
			
		}else {
			match = word;
			for(int i=0; i<cur.size; i++) {
				Node target = cur.childNode.get(i);
				if(equal(target.data,match)) {
					total += count(cur.childNode.get(i)) + 1;
					cur.removeNode(i);
					break;
				} 
			}
		}
		return total;
	}	
	
	int find(char keyword[]){
		String word = removeSpace(getvalue(keyword));
		String match="";
		int mode =-1;
		if(equal(word,"*")) {
			mode=0;
		}
		else if(endWith(word,"*")) {
			mode =1;
			match = substr(word,0, word.length()-1);
		}else {
			mode = 2;
			match = word;
		}
		return findMatchingNodes(cur, mode, match);
	}
	int findMatchingNodes(Node node, int mode, String match) {
		//0 모든 폴더 //1 문자열* //2 문자열
		int total =0;
		if(mode==0) {
			total += node.size;
		}
		for(int i=0; i<node.size; i++) {
			String str = node.childNode.get(i).data;
			if(mode==1 && startWith(str,match)) {
				total++;
			}else if(mode==2 && equal(str,match)) {
				total++;
			}
			total += findMatchingNodes(node.childNode.get(i), mode, match);
		}
		return total;
	}
	int count(Node node) {
		int total=node.size;
		for(int i=0; i<node.size; i++) {
			total += count(node.childNode.get(i));
		}
		return total;
	}
	String getvalue(char c[]) {
		String ret="";
		for(int i=0; i<c.length;i++ ) {
			ret+=c[i];
		}
		return ret;
	}
	String removeSpace(String s) {
		int i=0;
		while(i < s.length() && s.charAt(i)=='\u0000') {
			i++;
		}
		int j = s.length()-1;
		while(j >=0 && s.charAt(j)=='\u0000') {
			
			j--;
		}
		return substr(s,i, j+1);
	}
	boolean endWith(String base,String str) {
		if(base.length() < str.length()) return false;
		int j= base.length()-1;
		for(int i=str.length()-1; i>=0; i--) {
			if(str.charAt(i)!=base.charAt(j)) return false;
			j--;
		}
		return true;
	}
	boolean startWith(String base, String str) {
		if(base.length() < str.length()) return false;
		int j= 0;
		for(int i=0; i<str.length(); i++) {
			if(str.charAt(i)!=base.charAt(j)) return false;
			j++;
		}
		return true;
	}
	boolean equal(String s1, String s2) {
		if(s1.length()!=s2.length()) return false;
		int j=0;
		for(int i=0; i<s1.length(); i++) {
			if(s1.charAt(i)!=s2.charAt(j)) return false;
			j++;
		}
		return true;
	}
	String substr(String src, int i, int j) {
		String ret="";
		for(int k=i; k<j; k++) {
			ret+=src.charAt(k);
		}
		return ret;
		
	}
	int compare(String s1, String s2) {
		for(int i=0; i<s1.length() && i<s2.length(); i++) {
		    if(s1.charAt(i) != s2.charAt(i))
		        return s1.charAt(i) < s2.charAt(i) ? -1 : 1;
		}
		return s1.length() < s2.length() ? -1 : s1.length() == s2.length() ? 0 : 1;
	}
}

