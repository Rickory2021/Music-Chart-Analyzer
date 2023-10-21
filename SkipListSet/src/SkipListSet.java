import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

public class SkipListSet <T extends Comparable<T>> implements SortedSet<T> {

	private SkipListSetNode<T> head;
	private int totalLevels, size;
	
	static Random RandomGenerator = new Random();
	
	public SkipListSet(){
		this.head=new SkipListSetNode<T>();
		this.totalLevels=0;
		this.size=0;
		
	}
	
	public SkipListSetNode<T> getHead() {
		return head;
	}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		// HERE
		if(head == null) return true;
		else return false;
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method 
		if(getPredessor((T) o, head)!=null)return true;
		return false;
	}
	
	public SkipListSetNode<T> getPredessor(T e, SkipListSetNode<T> startingNode){
		SkipListSetNode<T> tranversingNode = startingNode;
		while(tranversingNode!=null) {
			if(tranversingNode.rightNode==null ||tranversingNode.rightNode.item.compareTo(e)>0) {
				tranversingNode=tranversingNode.downNode;
			}else if(tranversingNode.rightNode.item.compareTo(e)<0) {
				tranversingNode=tranversingNode.rightNode;
			}else if(tranversingNode.rightNode.item.compareTo(e)==0) {
				return tranversingNode;
			}
		}
		return null;
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return new SkipListSetIterator<T>(this);
	}

	@Override
	public Object[] toArray() {
		Object[] output = new Object[size]; int index=0;
		SkipListSetNode<T> tranversingNode = getLowestLevelNode(head);
		
		while(tranversingNode.rightNode!=null) {
			tranversingNode=tranversingNode.rightNode;
			output[index++]=tranversingNode.item.item;
		}
		return output;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		int index=0;
		if(a.length<size) {
			a = (T[]) Array.newInstance(a.getClass(), size); 
		}
		SkipListSetNode tranversingNode = getLowestLevelNode(head);
		
		while(tranversingNode.rightNode!=null) {
			tranversingNode=tranversingNode.rightNode;
			a[index++]=(T) tranversingNode.item.item;
		}
		return a;
	}

	@Override
	public boolean add(T e) {
		//System.out.println("STARTING1st");
		if(contains(e))return false;
		//System.out.println("STARTING2nd");
		SkipListSetItem<T> newItem = new SkipListSetItem<T>(e);
		// Generate Level of e
		//System.out.println("STARTING");
		//int elementLevel = (int)(Math.log(Math.random()*Integer.MAX_VALUE)/Math.log(2));
		int elementLevel = 0;
		while((int)(RandomGenerator.nextInt()%2)==1) {
			elementLevel++;
		}
		//System.out.println(elementLevel+" "+e);
		// Expand SkipList if needed
		while(totalLevels<elementLevel) {
			SkipListSetNode<T> newHead= new SkipListSetNode<T>();
			newHead.downNode=head;
			head=newHead;
			totalLevels++;
		}
		//Tranverse to the element Level, then add element to lower levels
		SkipListSetNode<T> tranversingNode = head, previousNewNode=null;
		for(int currentLevel = totalLevels; currentLevel>=0;currentLevel--) {
			
			if(currentLevel<=elementLevel) {
				// Add element to that Level
				tranversingNode = addItemToLevel(newItem,tranversingNode);
				//System.out.println("Status");
				//System.out.println(tranversingNode);
				//System.out.println(tranversingNode.rightNode);
				// Connects Down Nodes
				if(previousNewNode!=null) {
					previousNewNode.downNode=tranversingNode.rightNode;
				}
				previousNewNode=tranversingNode.rightNode;
			}else {
				tranversingNode = getToLevelPredessor(newItem,tranversingNode);
			}
			tranversingNode=tranversingNode.downNode;
		}
		size++;
//System.out.println("NEW ELEMENT LEVEL"+elementLevel);
		return true;
	}
	
	private SkipListSetNode<T> getToLevelPredessor(SkipListSetItem<T> newItem, SkipListSetNode<T> currentNode) {
		SkipListSetNode<T> tranversingNode = currentNode;
		while(tranversingNode.rightNode!=null) {
			if(tranversingNode.rightNode.item.compareTo(newItem.item)<0) {
				tranversingNode=tranversingNode.rightNode;
			}else {
				// We have reached where we need to insert
				return tranversingNode;
			}
		}
		return tranversingNode;
	}
	
	private SkipListSetNode<T> addItemToLevel(SkipListSetItem<T> newItem, SkipListSetNode<T> currentNode) {
		
		
		SkipListSetNode<T> tranversingNode = currentNode;
		while(tranversingNode.rightNode!=null) {
			if(tranversingNode.rightNode.item.compareTo(newItem.item)<0) {
				tranversingNode=tranversingNode.rightNode;
			}else {
				// We have reached where we need to insert
				SkipListSetNode<T> predecessor = tranversingNode;
				tranversingNode=new SkipListSetNode<T>(newItem, predecessor.rightNode);
				predecessor.rightNode=tranversingNode;
				return predecessor;
			}
		}
		SkipListSetNode<T> predecessor = tranversingNode;
		tranversingNode=new SkipListSetNode<T>(newItem, predecessor.rightNode);
		predecessor.rightNode=tranversingNode;
		return predecessor;
	}

	@Override
	public boolean remove(Object o) {
		boolean found=false;
		if(!(o instanceof Comparable<?>)) {
			System.out.println("Not COMPARABLE");
			return false;
		}
		SkipListSetNode<T> tranversingNode, predecessor=head;
		while((predecessor=(getPredessor((T)o, predecessor)))!=null){
			predecessor.rightNode=predecessor.rightNode.rightNode; found=true;
			if(head.rightNode==null) {
				// Empty Express lane
				head=head.downNode; totalLevels--;
				predecessor=head;
			}
		}
		
		if(found) {
			size--;
			return true;
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for(Object element:c) {
			if(!contains(element))return false;
		}
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		boolean errorFree = true;
		for(T t:c) {
			if(!add(t))errorFree=false;
			//System.out.println(toString());
		}
		return errorFree;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		Iterator collectionIterator = c.iterator();
		SkipListSet<T> oldSet = null;
		try {
			oldSet = (SkipListSet<T>) this.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.head=new SkipListSetNode<T>();
		this.totalLevels=0;
		int oldSize = size;
		this.size=0;
		while(collectionIterator.hasNext()) {
			Object obj = collectionIterator.next();
			if(oldSet.contains(obj)) {
				add((T)obj);
			}
			
		}
		if(oldSize!=size)return true;
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean error = false;
		for(Object t:c) {
			if(!remove(t))error=true;
		}
		return !error;
	}

	@Override
	public void clear() {
		this.head=new SkipListSetNode<T>();
		this.totalLevels=0;
		this.size=0;
		
	}

	@Override
	public Comparator<? super T> comparator() {
		return null;
	}

	@Override
	public SortedSet<T> subSet(T fromElement, T toElement) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SortedSet<T> headSet(T toElement) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SortedSet<T> tailSet(T fromElement) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T first() {
		SkipListSetNode<T> tranversingNode = getLowestLevelNode(head);
		if(tranversingNode.rightNode!=null) {
			return tranversingNode.rightNode.item.item;
		}else {
			return null;
		}
	}

	@Override
	public T last() {
		SkipListSetNode<T> tranversingNode = head;
		while(tranversingNode.rightNode!=null || tranversingNode.downNode!=null) {
			if(tranversingNode.rightNode!=null)tranversingNode=tranversingNode.rightNode;
			else tranversingNode=tranversingNode.downNode;
		}
		return tranversingNode.item.item;
	}
	
	public void reBalance() {
		SkipListSetNode<T> oldHead = getLowestLevelNode(head);
		this.head=new SkipListSetNode<T>();
		this.totalLevels=0;
		this.size=0;
		while(oldHead.rightNode!=null) {
			oldHead=oldHead.rightNode;
			add(oldHead.item.item);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuild = new StringBuilder();
		SkipListSetNode<T> tranversingHead = head, tranversingNode;
		if(head==null)return "EMPTY SKIP SET";
		for(int currentLevel = totalLevels; currentLevel>=0;currentLevel--) {
			tranversingNode=tranversingHead;
			while(tranversingNode!=null) {
				if(tranversingNode.item==null)stringBuild.append("NULL"+"\t");
				else {
					/*System.out.print("Current Item "+ tranversingNode.item);
					if(tranversingNode.rightNode!=null) {
						System.out.print("\tRightItem "+tranversingNode.rightNode.item);
					}else System.out.print("\tRightItem NULL");
					if(tranversingNode.downNode!=null) {
						System.out.print("\tdownNode "+tranversingNode.downNode.item);
					}else System.out.print("\tdownNode NULL");
					System.out.println();*/
					stringBuild.append(tranversingNode.item+"\t");
				}
				//System.out.println("HELLOS");
				tranversingNode=tranversingNode.rightNode;
			}
			stringBuild.append("\n");
			tranversingHead=tranversingHead.downNode;
		}
		return stringBuild.toString();
	}
	
	public SkipListSetNode<T> getLowestLevelNode(SkipListSetNode<T> currentNode) {
		SkipListSetNode<T> tranversingNode=currentNode;
		while(tranversingNode!=null&&tranversingNode.downNode!=null) {
			tranversingNode=tranversingNode.downNode;
		}
		return tranversingNode;
	}
	private class  SkipListSetIterator<T extends Comparable<T>> implements Iterator<T>{
		SkipListSet<T> skipList;
		SkipListSet<T>.SkipListSetNode<T> currentNode;
		
		public SkipListSetIterator(SkipListSet<T> skipList) {
			this.skipList=skipList;
			this.currentNode=skipList.getLowestLevelNode(skipList.getHead());
		}
		
		public SkipListSetIterator(SkipListSet<T> skipList, SkipListSet<T>.SkipListSetNode<T>  currentNode) {
			this.skipList=skipList;
			this.currentNode=currentNode;
		}
		
		@Override
		public boolean hasNext() {
			if(currentNode.rightNode != null) return true;
			else return false;
		}

		@Override
		public T next() {
			if(currentNode.rightNode != null) {
				return (T) currentNode.rightNode.item;
			}
			return null;
		}
		
		@Override
		public void remove() {
			skipList.remove(currentNode.item);
		}

		
		
	}
	
	private class SkipListSetItem<T extends Comparable<T>> implements Comparable<T>{
		private T item;
		public SkipListSetItem(T item) {
			this.item=item;
		}
		
		public T getItem() {
			return item;
		}
		
		public void setItem(T item) {
			this.item=item;
		}
		
		@Override
		public int compareTo(T other) {
			return this.item.compareTo(other);
		}
		
		@Override
		public String toString() {
			return item.toString();
		}
		
	}
	
	public class SkipListSetNode<T extends Comparable<T>>{
		SkipListSetItem<T> item;
		SkipListSetNode<T> downNode, rightNode;
		public SkipListSetNode() {
			this.item=null;
			this.downNode=null;
			this.rightNode=null;
		}
		
		public SkipListSetNode(SkipListSetItem<T> item, SkipListSetNode<T> rightNode) {
            this.item = item;
            this.downNode = null;
            this.rightNode = rightNode;
        }
		
		public SkipListSetNode(SkipListSetItem<T> item, SkipListSetNode<T> downNode, SkipListSetNode<T> rightNode) {
            this.item = item;
            this.downNode = downNode;
            this.rightNode = rightNode;
        }
		
		@Override
		public String toString() {
			if(item==null) return "NULL";
			return item.item.toString();
		}
	}

}
