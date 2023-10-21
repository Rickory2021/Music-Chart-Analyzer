/*	A TwoFourTree, also called A TwoThreeFourTree, is a self-balancing tree 
 * 		where all external (leaf) nodes are at the same depth
 * 	It is a B-Tree of order 4 where one node can have 1-3 values with 2-4 children based on the value of the it
 * 		A 2-Node has one value and can have 2 children (if it isn't a leaf)
 * 		A 3-Node has two values annd can have 3 children (if it isn't a leaf)
 * 		A 4-Node has three values and can have 4 children (if it isn't a leaf)
 *		Since this is a B-Tree, values of the node should also sorted in order
 */
public class TwoFourTree {
	private class TwoFourTreeItem {
    	// Anytime we construct a new TwoFourTreeItem, these values will be the default
        int values = 1;
        int value1 = 0;                             // always exists.
        int value2 = 0;                             // exists iff the node is a 3-node or 4-node.
        int value3 = 0;                             // exists iff the node is a 4-node.
        boolean isLeaf = true;
        
        TwoFourTreeItem parent = null;              // parent exists iff the node is not root.
        TwoFourTreeItem leftChild = null;           // left and right child exist iff the note is a non-leaf.
        TwoFourTreeItem rightChild = null;          
        TwoFourTreeItem centerChild = null;         // center child exists iff the node is a non-leaf 3-node.
        TwoFourTreeItem centerLeftChild = null;     // center-left and center-right children exist iff the node is a non-leaf 4-node.
        TwoFourTreeItem centerRightChild = null;

        /**
         * returns true if the object is a twoNode (only has value1 with 2 children) otherwise, false
         * @return boolean if object is a twoNode
         */
        public boolean isTwoNode() {
            if(values==1)return true;
            return false;
        }

        /**
         * returns true if the object is a threeNode (only has value1 & value2 with 3 children) otherwise, false
         * @return boolean if object is a threeNode
         */
        public boolean isThreeNode() {
        	if(values==2)return true;
            return false;
        }

        /**
         * returns true if the object is a fourNode (has value1, value2, & value 3 with 4 children) otherwise, false
         * @return boolean if object is a fourNode
         */
        public boolean isFourNode() {
        	if(values==3)return true;
            return false;
        }

        /**
         * returns true if the object is a root where ithas no parent 
         * @return boolean if object is a root
         */
        public boolean isRoot() {
        	if(parent==null) return true;
            return false;
        }
        
        /**
         * Checks only the current Node's Values to see if it has said value (Doesn't check children)
         * @param value - the value that is being checked for
         * @return true if current Node has value, otherwise false
         */
        public boolean doesCurrentNodeHaveValue(int value) {
        	// Checks whether value 1/2/3 exist and if it equals the targetd value
        	if((this.values>=1&&this.value1==value)
        			||(this.values>=2&&this.value2==value)
        			||(this.values>=3&&this.value3==value))
        		return true;
        	return false;
        }
        
        /**
         * Give the node needed to find the value given (This also means that it wil return itself if the value is found in the node)
         * @param value - The target Value we are tranversing to
         * @return returns the node to go to find the value (could return itself)
         */
        public TwoFourTreeItem getNextNode(int value) {
        	if(this.isTwoNode()) {
    			if(value<this.value1) return this.leftChild;// go to left node
    			else if(value>this.value1) return this.rightChild;// go to right node
    		}else if(this.isThreeNode()) {
    			if(value<this.value1) return this.leftChild;// go to left node
    			else if(value>this.value1 && value<this.value2) return this.centerChild;// go to center node
    			else if(value>this.value2) return this.rightChild;// go to right node
    		}else if(this.isFourNode()) {
    			// Four Node
    			if(value<this.value1) return this.leftChild;// go to left node
    			else if(value>this.value1 && value<this.value2) return this.centerLeftChild;// go to center left node
    			else if(value>this.value2 && value<this.value3) return this.centerRightChild;// go to center right node
    			else if(value>this.value3) return this.rightChild;// go to right node
    		}
        	// The only reason you would possibly be here is that value is in the current Node
        	return this;
        }
        
        /**
         * Connects Two Nodes Together, makes the parent not a leaf, and disconnect previous child's parent's connections
         * @param connectionType - Left/Center Left/Center/Center Right/ Right
         * @param child - The node that will act as the new child or null
         * @return return true if connection is made successfully, otherwise false
         */
        public boolean assignChild(String connectionType, TwoFourTreeItem child) {
        	// Return false if parent or child doesn't exist
        	if(connectionType.toUpperCase().equals("LEFT")) {
           		if(child!=null) {
        			// Disconnects the Child's Previous connection & node is garenteed not a leaf
        			child.removeParent();
        			child.parent = parent;
        			this.isLeaf=false;
        		}
        		this.leftChild = child;
        		return true;
        	}else if(connectionType.toUpperCase().equals("CENTER LEFT")) {
        		if(child!=null) {
        			// Disconnects the Child's Previous connection & node is garenteed not a leaf
        			child.removeParent();
        			child.parent = parent;
        			this.isLeaf=false;
        		}
        		this.centerLeftChild = child;
        		return true;
        	}else if(connectionType.toUpperCase().equals("CENTER")) {
        		if(child!=null) {
        			// Disconnects the Child's Previous connection & node is garenteed not a leaf
        			child.removeParent();
        			child.parent = parent;
        			this.isLeaf=false;
        		}
        		this.centerChild = child;
        		return true;
        	}else if(connectionType.toUpperCase().equals("CENTER RIGHT")) {
        		if(child!=null) {
        			// Disconnects the Child's Previous connection & node is garenteed not a leaf
        			child.removeParent();
        			child.parent = parent;
        			this.isLeaf=false;
        		}
        		this.centerRightChild = child;
        		return true;
        	}else if(connectionType.toUpperCase().equals("RIGHT")) {
        		if(child!=null) {
        			// Disconnects the Child's Previous connection & node is garenteed not a leaf
        			child.removeParent();
        			child.parent = parent;
        			this.isLeaf=false;
        		}	
        		this.rightChild = child;
        		return true;
        	}
        	// If we reach here, then that means that connectionType was not found
        	return false;
        }
        
        /**
         * Disconnects the Child from its parent and assign it to a new parent
         * @param child - The child that is trying to be disconnect
         */
        public void removeParent() {
        	if(this.parent!=null) {
    			if(this.parent.leftChild==this)this.parent.leftChild=null;
    			else if(this.parent.centerLeftChild==this)this.parent.centerLeftChild=null;
    			else if(this.parent.centerChild==this)this.parent.centerChild=null;
    			else if(this.parent.centerRightChild==this)this.parent.centerRightChild=null;
        		else if(this.parent.rightChild==this)this.parent.rightChild=null;
        	}
        	this.parent=null;
        }

        /**
         * Constructor for a twoNode
         * defaults isLeaf and connections as null
         * @param value1 - value of the twoNode
         */
        public TwoFourTreeItem(int value1) {
        	this.values = 1;
        	this.value1=value1;
        }

        /**
         * Constructor for a threeNode
         * defaults isLeaf and connections as null
         * @param value1 - left value of the twoNode
         * @param value2 - right value of the twoNode
         */
        public TwoFourTreeItem(int value1, int value2) {
        	this.values = 2;
        	this.value1=value1;
        	this.value2 = value2;  
        }

        /**
         * Constructor for a fourNode
         * defaults isLeaf and connections as null
         * @param value1 - left value of the twoNode
         * @param value2 - center value of the twoNode
         * @param value3 - ritght value of the twoNode
         */
        public TwoFourTreeItem(int value1, int value2, int value3) {
        	this.values = 3;
        	this.value1=value1;
        	this.value2 = value2;
            this.value3 = value3;  
        }

        /**
         * Prints white space based on the indent by 2
         * @param indent - number of 2 white spaces to print in console 
         */
        private void printIndents(int indent) {
            for(int i = 0; i < indent; i++) System.out.printf("  ");
        }

        /**
         * Recursive functions that print the entire TwoThreeFourTree
         * @param indent - used to keep track of the current indent (set as 0 on initial call)
         */
        public void printInOrder(int indent) {
            if(!isLeaf) leftChild.printInOrder(indent + 1);
            printIndents(indent);
            System.out.printf("%d\n", value1);
            if(isThreeNode()) {
                if(!isLeaf) centerChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value2);
            } else if(isFourNode()) {
                if(!isLeaf) centerLeftChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value2);
                if(!isLeaf) centerRightChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value3);
            }
            if(!isLeaf) rightChild.printInOrder(indent + 1);
        }
        
        /**
         * @return returns a String that Prints all active elements of the node
         */
        @Override
        public String toString() {
        	StringBuilder output = new StringBuilder();
        	if(this!=null)output.append(String.format("Node Values:%d >> %d %d %d\n", this.values, this.value1, this.value2, this.value3));
        	if(this.parent!=null)output.append(String.format("Parent Values:%d >> %d %d %d\n", this.parent.values, this.parent.value1, this.parent.value2, this.parent.value3));
        	if(this.leftChild!=null)output.append(String.format("Left Values:%d >> %d %d %d\n", this.leftChild.values, this.leftChild.value1, this.leftChild.value2, this.leftChild.value3));
        	if(this.centerLeftChild!=null)output.append(String.format("Center Left Values:%d >> %d %d %d\n", this.centerLeftChild.values, this.centerLeftChild.value1, this.centerLeftChild.value2, this.centerLeftChild.value3));
        	if(this.centerChild!=null)output.append(String.format("Center Values:%d >> %d %d %d\n", this.centerChild.values, this.centerChild.value1, this.centerChild.value2, this.centerChild.value3));
        	if(this.centerRightChild!=null)output.append(String.format("Center Right Values:%d >> %d %d %d\n", this.centerRightChild.values, this.centerRightChild.value1, this.centerRightChild.value2, this.centerRightChild.value3));
        	if(this.rightChild!=null)output.append(String.format("Right Values:%d >> %d %d %d\n", this.rightChild.values, this.rightChild.value1, this.rightChild.value2, this.rightChild.value3));
        	return output.toString();
        }
    }

    TwoFourTreeItem root = null;
    
    /**
     * Adding/Inserting a value is always performed at the leaf node
     * If the value already exist (duplicate) it will not add
     * 
     * Since A node cannot hold more than three values, we need to split the node 
     * 	(middle value is the parent and left and right values as it's children)
     * 	However, what happens if the parent of the node we are splitting also has 3 values?
     * 	We also have to split the parent node as well (or else the tree is no longer balanced)
     * So, while tranversing down to add the value, we need to split nodes with three values to
     * 	keep the tree balanced
     * @param value - the new value to be added to the tree
     * @return true if sucessfully inserted, otherwise false (only when there is a duplicate value)
     */
    public boolean addValue(int value) {
    	// Checks whether there is even a tree to add to
    	if(root == null) {
    		// Creates the root
    		root = new TwoFourTreeItem(value);
    		return true;
    	}
    	/* To prevent adding duplicate values, as well as increasing the depth of the tree
    	 * to just find out that we aren't adding the value, we will tranverse the list first
    	 * to check if the value even exists
    	 */
    	if(hasValue(value))return false;
    	TwoFourTreeItem tranversingNode = root;
    	
    	// We know tranverse the tree until we reach the leaf, where we can add
    	while(tranversingNode!=null) {
    		if(tranversingNode.isFourNode()) {
    			// Then Split and go to the new node
    			//System.out.println("Before Split: "+tranversingNode);
    			tranversingNode = splitNode(tranversingNode);
    			//System.out.println("After Split: "+tranversingNode);
    		}else if(tranversingNode.isLeaf){
    			// We have reached the leaf as well as ensuring that we aren't in a four node (If Statement from above)
    			// So we can just add the value to the leaf with now worries
    			addValueToLeaf(tranversingNode, value);
    			return true;
    		}
    		/* Based on the previous if statements, when we reach here, know:
    		 * 	We aren't going through a four Node
    		 * 	We are in an internal/branch node
    		 * So, we can just continue to the next node to find the leaf
    		 */
    		tranversingNode = tranversingNode.getNextNode(value);
    	}
    	return false;
    }

    /**
     * Searches the tree to see if the tree has said value.
     * @param value - Value that is being searched for
     * @return true if the value is found in 
     */
    public boolean hasValue(int value) {
    	TwoFourTreeItem temp = root;
    	return hasValueRecursive(temp,value);
    }
    
    private boolean hasValueRecursive(TwoFourTreeItem node, int value) {
    	// End Cases
    	if(node==null)return false;
    	if(node.doesCurrentNodeHaveValue(value)) return true;
    	
    	return hasValueRecursive(node.getNextNode(value), value);
    }
    
    /**
     * Adds values only to a leaf
     * @param value - the value to add to Leaf
     * @return true if sucessfully added, otherwise false
     */
    public boolean addValueToLeaf(TwoFourTreeItem leaf, int value) {
    	if(!leaf.isLeaf) return false;
    	if(leaf.isTwoNode()) {
    		if(value < leaf.value1) {
    			leaf.value2=leaf.value1;
    			leaf.value1=value;
    		}else if(value > leaf.value1) {
    			leaf.value2=value;
    		}
    		leaf.values++;
    		return true;
    	}else if(leaf.isThreeNode()) {
			if(value < leaf.value1) {
				leaf.value3=leaf.value2;
				leaf.value2=leaf.value1;
				leaf.value1=value;
    		}else if(value > leaf.value1 && value < leaf.value2){
    			leaf.value3 = leaf.value2;
    			leaf.value2 = value;
			}else if(value > leaf.value2) {
				leaf.value3=value;
    		}
			leaf.values++;
    		return true;
    	}
    	return false;
    }
    
    /**
     * Splits a Four Node up (Used during Insertion of Values)
     * @param node - The node that is currently being split
     * @return returns the node where the middle value is present
     */
    public TwoFourTreeItem splitNode(TwoFourTreeItem node) {
    	if(node==null || !node.isFourNode()) return node;
    	if(node.isRoot()) {
    		// If we need to create new nodes for the left and right child of the split
    		TwoFourTreeItem newLeftChild=new TwoFourTreeItem(node.value1);
    		connectNodes(newLeftChild, "Left", node.leftChild);
    		connectNodes(newLeftChild, "Right", node.centerLeftChild);
    		connectNodes(node, "Left", newLeftChild);
    		
    		TwoFourTreeItem newRightChild=new TwoFourTreeItem(node.value3);
    		connectNodes(newRightChild, "Left", node.centerRightChild);
    		connectNodes(newRightChild, "Right", node.rightChild);
    		connectNodes(node, "Right", newRightChild);
    		
    		node.value1 = node.value2;
    		node.value2=0;
    		node.value3=0;
    		node.values=1;
    		
    		return node;
    	}else {
    		// The old node will be make as the new Left Child for the element that will pushed up
    		// We need to create a new node as the new Right Child for the element that will pushed up
    		int pushedValue = node.value2;
    		TwoFourTreeItem parent = node.parent, 
    				newRightNode = new TwoFourTreeItem(node.value3);
    		// Add the connections to the new Right Node
    		connectNodes(newRightNode, "Left", node.centerRightChild);
    		connectNodes(newRightNode, "Right", node.rightChild);
    		// Reassign and edit the old Node to be made into the newLeft Node
    		connectNodes(node, "Right", node.centerLeftChild);
    		node.value2=0;
    		node.value3=0;
    		node.values=1;
    		TwoFourTreeItem newLeftNode = node;
    		
    		//System.out.println("newRightNode:" + newRightNode);
    		//System.out.println("newLeftNode:" + newLeftNode);
    		// Push the center value up and connect with new Left and Right Node
    		if(parent.leftChild==node) {
    			// Shift Values to make space on the Left
    			parent.values+=1;
    			parent.value3=parent.value2;
    			parent.value2=parent.value1;
    			parent.value1=pushedValue;
    			//Reassign Left  Child
    			connectNodes(parent, "Left", newLeftNode);
    			if(parent.values==2) {
    				connectNodes(parent, "Center", newRightNode);
    			}else if(parent.values==3){
    				// If the parent had a center node before we pushed up, we need to change to centerRight
    				// Move center child to centerRight
    				connectNodes(parent, "Center Right", parent.centerChild);
    				connectNodes(parent, "Center Left", newRightNode);
    			}
    		}else if(parent.centerChild==node) {
    			// Only Three Nodes have this case
    			// Shift Values to make space on the center
    			parent.values+=1;
    			parent.value3=parent.value2;
    			parent.value2=pushedValue;
    			
    			connectNodes(parent, "Center Left", newLeftNode);
    			connectNodes(parent, "Center Right", newRightNode);
    			
    		}else if(parent.rightChild==node) {
    			// "Shift" Values to make space on the Right
    			parent.values+=1;   		
    			// Reassign Right Child
    			connectNodes(parent, "Right", newRightNode);
    			if(parent.values==2) { 
    				parent.value2=pushedValue;
    				connectNodes(parent, "Center", newLeftNode);
    			}else if(parent.values==3){
    				// If the parent had a center node before we pushed up, we need to change to centerRight
    				// Move center child to centerRight
    				parent.value3=pushedValue;
    				connectNodes(parent, "Center Right", newLeftNode);
    				connectNodes(parent, "Center Left", parent.centerChild);
    			}
    		}
    		return parent;
    	}
    }
    
    /**
     * Connects Two Nodes Together, makes the parent not a leaf, and disconnect previous child's parent's connections
     * @param parent - The node that will act as the new parent
     * @param connectionType - Left/Center Left/Center/Center Right/ Right
     * @param child - The node that will act as the new child
     * @return return true if connection is made successfully, otherwise false
     */
    public boolean connectNodes( TwoFourTreeItem parent, String connectionType, TwoFourTreeItem child) {
    	// Return false if parent or child doesn't exist
    	if(parent==null || child==null) return false;
    	if(connectionType.toUpperCase().equals("LEFT")) {
    		// Disconnects the Child's Previous connection
    		removeChildFromParent(child);
    		parent.leftChild = child;
    		child.parent = parent;
    		parent.isLeaf=false;
    		return true;
    	}else if(connectionType.toUpperCase().equals("CENTER LEFT")) {
    		// Disconnects the Child's Previous connection
    		removeChildFromParent(child);
    		parent.centerLeftChild = child;
    		child.parent = parent;
    		parent.isLeaf=false;
    		return true;
    	}else if(connectionType.toUpperCase().equals("CENTER")) {
    		// Disconnects the Child's Previous connection
    		removeChildFromParent(child);
    		parent.centerChild = child;
    		child.parent = parent;
    		parent.isLeaf=false;
    		return true;
    	}else if(connectionType.toUpperCase().equals("CENTER RIGHT")) {
    		// Disconnects the Child's Previous connection
    		removeChildFromParent(child);
    		parent.centerRightChild = child;
    		child.parent = parent;
    		parent.isLeaf=false;
    		return true;
    	}else if(connectionType.toUpperCase().equals("RIGHT")) {
    		// Disconnects the Child's Previous connection
    		removeChildFromParent(child);
    		parent.rightChild = child;
    		child.parent = parent;
    		parent.isLeaf=false;
    		return true;
    	}
    	// If we reach here, then that means that connectionType was not found
    	return false;
    }
    
    /**
     * Disconnects the Child from its parent and assign it to a new parent
     * @param child - The child that is trying to be disconnect
     */
    public void removeChildFromParent(TwoFourTreeItem child) {
    	if(child.parent!=null) {
			if(child.parent.leftChild==child)child.parent.leftChild=null;
			else if(child.parent.centerLeftChild==child)child.parent.centerLeftChild=null;
			else if(child.parent.centerChild==child)child.parent.centerChild=null;
			else if(child.parent.centerRightChild==child)child.parent.centerRightChild=null;
    		else if(child.parent.rightChild==child)child.parent.rightChild=null;
    	}
    }

    /**
     * Removing/Deleting a value is similar as insertion, but reverse
     * Deletion is also done at the leaf
     * Since A node cannot hold no values, we need to add values
     * 	This is done either through merging (But this can only be done sibilings have only 1 value)\
     * 	Or by rotation (To keep the tree balanced)
     * Just like insertions, while tranversing down, we need to rotate/merge nodes when we encounter a two node 
     * 	to keep the tree balanced 
     * If we find the value, but it is in an internal/branch node, then we need to find the predecessor or sucessor to swap
     * 	to get our value to a leaf
     * Remember when we try to find the predecessor or sucessor, we need to not tranverse through a two node (rotate or merge)
     * 
     * @param value - the new value that is being deleted
     * @return returns true if value was sucessfully deleted, otherwise false
     */
    public boolean deleteValue(int value) {
    	TwoFourTreeItem deletionNode = root;
    	/* To improve runtime, since we don't have to do unnecessary rotation/merges,
    	 * we will tranverse the list firstto check if the value even exists
    	 */
    	if(!hasValue(value))return false;
    	while(deletionNode!=null) {
    		// Check whether we have found the value is in the current node
    		if(deletionNode.doesCurrentNodeHaveValue(value)) {
    			// Check whether the node with the value is in a branch/internal node
    			if(!deletionNode.isLeaf) {
    				TwoFourTreeItem tranversingNode = deletionNode;
    				while(!tranversingNode.isLeaf) {
    					TwoFourTreeItem leftSib=null, rightSib=null;
        				// get left and right sibling of value to see if they are two node (So you don't have to waste time to merge)
        				if(deletionNode.values==1) {
        					leftSib=deletionNode.leftChild;
        					rightSib=deletionNode.rightChild;
        				}else if(deletionNode.values==2) {
        					if(deletionNode.value1==value) {
        						leftSib=deletionNode.leftChild;
            					rightSib=deletionNode.centerChild;
        					}else if(deletionNode.value2==value) {
        						leftSib=deletionNode.centerChild;
            					rightSib=deletionNode.rightChild;
        					}
        				}else if(deletionNode.values==3) {
        					if(deletionNode.value1==value) {
        						leftSib=deletionNode.leftChild;
            					rightSib=deletionNode.centerLeftChild;
        					}else if(deletionNode.value2==value) {
        						leftSib=deletionNode.centerLeftChild;
            					rightSib=deletionNode.centerRightChild;
        					}else if(deletionNode.value3==value) {
        						leftSib=deletionNode.centerRightChild;
            					rightSib=deletionNode.rightChild;
        					}
        				}
        				if(leftSib==null || rightSib==null) {
        					System.out.println("What left or right sib is null???? but we are in an Internal Node????");
        				}
    					if(!leftSib.isTwoNode()) {
        					// Get Predessor
    						//System.out.println("Getting Predecessor");
    						// To Left Once
    						tranversingNode = leftSib;
    						// Go Right Repeatedly while checking for merges/rotations
    						while(!tranversingNode.isLeaf) {
    							if(tranversingNode.rightChild.isTwoNode()) {
    								tranversingNode = expandNode(tranversingNode.rightChild);
    							}else {
    								tranversingNode=tranversingNode.rightChild;
    							}
    						}
    						// Now Swap deleted value with predessor (Far Right)
    						if(deletionNode.value1==value) {
    							int placeHolder = deletionNode.value1;
    							if(tranversingNode.values==1) {
    								deletionNode.value1 = tranversingNode.value1;
    								tranversingNode.value1 = placeHolder;
    							}else if (tranversingNode.values==2) {
    								deletionNode.value1 = tranversingNode.value2;
    								tranversingNode.value2 = placeHolder;
    							}else if (tranversingNode.values==3) {
    								deletionNode.value1 = tranversingNode.value3;
    								tranversingNode.value3 = placeHolder;
    							}
    							
    						}else if(deletionNode.value2==value) {
    							//System.out.println("TRAN"+tranversingNode);
    							//System.out.println("DEL"+deletionNode);
    							int placeHolder = deletionNode.value2;
    							if(tranversingNode.values==1) {
	    							deletionNode.value2 = tranversingNode.value1;
	    							tranversingNode.value1 = placeHolder;
    							}else if (tranversingNode.values==2) {
    								deletionNode.value2 = tranversingNode.value2;
	    							tranversingNode.value2 = placeHolder;
    							}else if (tranversingNode.values==3) {
    								deletionNode.value2 = tranversingNode.value3;
	    							tranversingNode.value3 = placeHolder;
    							}
    						}else if(deletionNode.value3==value) {
    							int placeHolder = deletionNode.value3;
    							if(tranversingNode.values==1) {
    								deletionNode.value3 = tranversingNode.value1;
    								tranversingNode.value1 = placeHolder;
    							}else if (tranversingNode.values==2) {
    								deletionNode.value3 = tranversingNode.value2;
    								tranversingNode.value2 = placeHolder;
    							}else if (tranversingNode.values==3) {
    								deletionNode.value3 = tranversingNode.value3;
    								tranversingNode.value3 = placeHolder;
    							}
    						}
    						deletionNode=tranversingNode;
        				}else if(!rightSib.isTwoNode()) {
        					// Get Sucessor
        					//System.out.println("Getting Sucessor");
        					// To Right Once
    						tranversingNode = rightSib;
    						// Go Left Repeatedly while checking for merges/rotations
    						while(!tranversingNode.isLeaf) {
    							if(tranversingNode.leftChild.isTwoNode()) {
    								tranversingNode = expandNode(tranversingNode.leftChild);
    							}else {
    								tranversingNode=tranversingNode.leftChild;
    							}
    						}
    						// Now Swap deleted value with sucessor (Far left)
    						if(deletionNode.value1==value) {
    							int placeHolder = deletionNode.value1;
    							deletionNode.value1 = tranversingNode.value1;
    							tranversingNode.value1 = placeHolder;
    						}else if(deletionNode.value2==value) {
    							int placeHolder = deletionNode.value2;
    							deletionNode.value2 = tranversingNode.value1;
    							tranversingNode.value1 = placeHolder;
    						}else if(deletionNode.value3==value) {
    							int placeHolder = deletionNode.value3;
    							deletionNode.value3 = tranversingNode.value1;
    							tranversingNode.value1 = placeHolder;
    						}
    						deletionNode=tranversingNode;
        				}else {
        					// We need to merge
        					tranversingNode = mergeNode(rightSib, "Left");
        					// deletion Node also goes down since the value is being pulled down
        					deletionNode = tranversingNode;
        					//System.out.println("MERGED DOWN DELETED VALUE");
        					//System.out.println(deletionNode);
        					// And check again
        				}
    				}
    			}
    			//System.out.println("IN LEAF");
    			// After this, the deleted value is in a leaf, so we can delete
    			removeValueFromLeaf(deletionNode, value);
    			if(deletionNode.values==0 && deletionNode.isRoot()) {
    				root=null;
    			}
    			return true;
    		}else {
    			// Since we haven't reached the targeted value, 
    			// 	we will tranverse down while checking for rotation or merging
    			TwoFourTreeItem nextNode= deletionNode.getNextNode(value);
    			// Check for rotation or merging 
    			if(nextNode.isTwoNode()) {
    				// Do rotation or merges
    				//System.out.println("EXPANDING");
    				//System.out.println(nextNode);
    				deletionNode = expandNode(nextNode);

    				
    				//root.printInOrder(0);
    				
    			}else {
    				deletionNode=nextNode;
    			}
    			
    		}
    	}
    	
        return false;
    }
    
    /**
     * Remove values only from a leaf
     * @param value - the value to remove from Leaf
     * @return true if sucessfully added, otherwise false
     */
    public boolean removeValueFromLeaf(TwoFourTreeItem leaf, int value) {
    	if(!leaf.isLeaf) return false;
    	if(leaf.value1==value) {
    		leaf.value1=leaf.value2;
    		leaf.value2=leaf.value3;
    		leaf.value3=0;
    		leaf.values--;
    		return true;
    	}else if(leaf.value2==value) {
    		leaf.value2=leaf.value3;
    		leaf.value3=0;
    		leaf.values--;
    		return true;
    	}else if(leaf.value3==value) {
    		leaf.value3=0;
    		leaf.values--;
    		return true;
    	}
    	return false;
    }
    
    public TwoFourTreeItem expandNode(TwoFourTreeItem twoNode) {
    	// Check for rotations
		// Find Immediate Siblings
		// If 1 Immedate Siblings values > 1, rotate
    	//System.out.println("Left SIb"+getImmediateSibling(twoNode,"Left"));
    	//System.out.println("Right SIb"+getImmediateSibling(twoNode,"Right"));
    	if(getImmediateSibling(twoNode,"Left")!=null && !getImmediateSibling(twoNode,"Left").isTwoNode()) {
    		//System.out.println("CLOWISE");
    		return rotateNode(twoNode, "Clockwise");
    	}else if(getImmediateSibling(twoNode,"Right")!=null && !getImmediateSibling(twoNode,"Right").isTwoNode()) {
    		//System.out.println("COUNTER CLOWISE");
    		return rotateNode(twoNode, "Counterclockwise");
    	}
    	// If we are here, then that means that all adjacent siblings are two nodes
    	// I DON'T THINK MERGING WITH LEFT OR RIGHT MATTERS
    	//System.out.println("MERGING TIME");
    	if(getImmediateSibling(twoNode,"Left")!=null)return mergeNode(twoNode,"Left");
    	else if(getImmediateSibling(twoNode,"Right")!=null)return mergeNode(twoNode,"Right");
    	
    	return null;
    }
    
    /**
     * 
     * @param twoNode - The node that we have are looking at
     * @param direction - direction of Left / Right
     * @return returns the left/right sibling, otherwise null
     */
    public TwoFourTreeItem getImmediateSibling(TwoFourTreeItem node, String direction) {
    	if(node.isRoot())return null;
    	//System.out.println("PARENT"+node.parent);
    	if(direction.toUpperCase().equals("LEFT")) {
    		if(node.parent.leftChild==node) {
    			return null;
    		}else if(node.parent.centerLeftChild==node || node.parent.centerChild==node) {
    			return node.parent.leftChild;
    		}else if(node.parent.centerRightChild==node) {
    			return node.parent.centerLeftChild;
    		}else if(node.parent.rightChild==node) {
    			if(node.parent.values==1) {
    				return node.parent.leftChild;
    			}else if(node.parent.values==2) {
    				return node.parent.centerChild;
    			}else if (node.parent.values==3) {
    				return node.parent.centerRightChild;
    			}
    		}
    	}else if(direction.toUpperCase().equals("RIGHT")) {
    		if(node.parent.leftChild==node) {
    			if(node.parent.values==1) {
    				return node.parent.rightChild;
    			}else if(node.parent.values==2) {
    				return node.parent.centerChild;
    			}else if (node.parent.values==3) {
    				return node.parent.centerLeftChild;
    			}
    		}else if(node.parent.centerLeftChild==node ) {
    			return node.parent.centerRightChild;
    		}else if(node.parent.centerChild==node ||node.parent.centerRightChild==node) {
    			return node.parent.rightChild;
    		}else if(node.parent.rightChild==node) {
    			return null;
    		}
    	}
		return null;
    }
    
    /**
     * 
     * @param twoNode - The node that we have to rotation into
     * @param direction - direction of Clockwise (From Left Sibiling) / Counterclockwise (From Right Sibling)
     * @return returns the node at the end of the rotation (Where we have to go after deletion)
     */
    public TwoFourTreeItem rotateNode(TwoFourTreeItem twoNode, String direction) {
    	if(twoNode==null || twoNode.parent==null) return null;
    	if(!twoNode.isTwoNode())return twoNode;
    	if(direction.toUpperCase().equals("CLOCKWISE")) {
    		TwoFourTreeItem sibling = getImmediateSibling(twoNode, "Left");
    		// Rotate the parent
    		if(twoNode.parent.leftChild==twoNode) {
    			// CAN'T BE LEFT CHILD 
    		}else if(twoNode.parent.centerLeftChild==twoNode) {
    			// Rotate from Left Child (Value1)
    			// Shift for parent value
    			twoNode.values++;
    			twoNode.value2=twoNode.value1;
    			twoNode.value1=twoNode.parent.value1;
    			TwoFourTreeItem threeNode = twoNode;
    			// Fix Connections
    			// Right is Still Right
    			connectNodes(threeNode, "Center", threeNode.leftChild);
    			
    			// Shift from the sibling
    			if(sibling.values==2) {
    				sibling.parent.value1 = sibling.value2;
    				sibling.value2=0;
    				sibling.values--;
    				// Fix Connections
    				connectNodes(threeNode, "Left", sibling.rightChild);
    				connectNodes(sibling, "Right", sibling.centerChild);
    				// Sibling's Left is still Left
    			}else if(sibling.values==3) {
    				sibling.parent.value1 = sibling.value3;
    				sibling.value3=0;
    				sibling.values--;
    				// Fix Connections
    				connectNodes(threeNode, "Left", sibling.rightChild);
    				connectNodes(sibling, "Right", sibling.centerRightChild);
    				connectNodes(sibling, "Center", sibling.centerLeftChild);
    				// Sibling's Left is still Left
    			}
    			return threeNode;
    		}else if(twoNode.parent.centerChild==twoNode) {
    			// Rotate from Left Child (Value1)
    			// Shift for parent value
    			twoNode.values++;
    			twoNode.value2=twoNode.value1;
    			twoNode.value1=twoNode.parent.value1;
    			TwoFourTreeItem threeNode = twoNode;
    			// Fix Connections
    			// Right is Still Right
    			connectNodes(threeNode, "Center", threeNode.leftChild);
    			
    			// Shift from the sibling
    			if(sibling.values==2) {
    				sibling.parent.value1 = sibling.value2;
    				sibling.value2=0;
    				sibling.values--;
    				// Fix Connections
    				connectNodes(threeNode, "Left", sibling.rightChild);
    				connectNodes(sibling, "Right", sibling.centerChild);
    				// Sibling's Left is still Left
    			}else if(sibling.values==3) {
    				sibling.parent.value1 = sibling.value3;
    				sibling.value3=0;
    				sibling.values--;
    				// Fix Connections
    				connectNodes(threeNode, "Left", sibling.rightChild);
    				connectNodes(sibling, "Right", sibling.centerRightChild);
    				connectNodes(sibling, "Center", sibling.centerLeftChild);
    				// Sibling's Left is still Left
    			}
    			return threeNode;
    		}else if(twoNode.parent.centerRightChild==twoNode) {
    			//Rotate from CenterLeft Child (Value2)
    			// Shift for parent value
    			twoNode.values++;
    			twoNode.value2=twoNode.value1;
    			twoNode.value1=twoNode.parent.value2;
    			TwoFourTreeItem threeNode = twoNode;
    			// Fix Connections
    			// Right is Still Right
    			connectNodes(threeNode, "Center", threeNode.leftChild);
    			
    			// Shift from the sibling
    			if(sibling.values==2) {
    				sibling.parent.value2 = sibling.value2;
    				sibling.value2=0;
    				sibling.values--;
    				// Fix Connections
    				connectNodes(threeNode, "Left", sibling.rightChild);
    				connectNodes(sibling, "Right", sibling.centerChild);
    				// Sibling's Left is still Left
    			}else if(sibling.values==3) {
    				sibling.parent.value2 = sibling.value3;
    				sibling.value3=0;
    				sibling.values--;
    				// Fix Connections
    				connectNodes(threeNode, "Left", sibling.rightChild);
    				connectNodes(sibling, "Right", sibling.centerRightChild);
    				connectNodes(sibling, "Center", sibling.centerLeftChild);
    				// Sibling's Left is still Left
    			}
    			return threeNode;
    		}else if(twoNode.parent.rightChild==twoNode) {
    			//Rotate from CenterRight or Center Child  or Left (value 1/ 2 / 3)
    			if(twoNode.parent.values==1) {
	    			// Shift for parent value
	    			twoNode.values++;
	    			twoNode.value2=twoNode.value1;
	    			twoNode.value1=twoNode.parent.value1;
	    			TwoFourTreeItem threeNode = twoNode;
	    			// Fix Connections
	    			// Right is Still Right
	    			connectNodes(threeNode, "Center", threeNode.leftChild);
	    			
	    			// Shift from the sibling
	    			if(sibling.values==2) {
	    				sibling.parent.value1 = sibling.value2;
	    				sibling.value2=0;
	    				sibling.values--;
	    				// Fix Connections
	    				connectNodes(threeNode, "Left", sibling.rightChild);
	    				connectNodes(sibling, "Right", sibling.centerChild);
	    				// Sibling's Left is still Left
	    			}else if(sibling.values==3) {
	    				sibling.parent.value1 = sibling.value3;
	    				sibling.value3=0;
	    				sibling.values--;
	    				// Fix Connections
	    				connectNodes(threeNode, "Left", sibling.rightChild);
	    				connectNodes(sibling, "Right", sibling.centerRightChild);
	    				connectNodes(sibling, "Center", sibling.centerLeftChild);
	    				// Sibling's Left is still Left
	    			}
	    			return threeNode;
    			}else if(twoNode.parent.values==2) {
	    			// Shift for parent value
	    			twoNode.values++;
	    			twoNode.value2=twoNode.value1;
	    			twoNode.value1=twoNode.parent.value2;
	    			TwoFourTreeItem threeNode = twoNode;
	    			// Fix Connections
	    			// Right is Still Right
	    			connectNodes(threeNode, "Center", threeNode.leftChild);
	    			
	    			// Shift from the sibling
	    			if(sibling.values==2) {
	    				sibling.parent.value2 = sibling.value2;
	    				sibling.value2=0;
	    				sibling.values--;
	    				// Fix Connections
	    				connectNodes(threeNode, "Left", sibling.rightChild);
	    				connectNodes(sibling, "Right", sibling.centerChild);
	    				// Sibling's Left is still Left
	    			}else if(sibling.values==3) {
	    				sibling.parent.value2 = sibling.value3;
	    				sibling.value3=0;
	    				sibling.values--;
	    				// Fix Connections
	    				connectNodes(threeNode, "Left", sibling.rightChild);
	    				connectNodes(sibling, "Right", sibling.centerRightChild);
	    				connectNodes(sibling, "Center", sibling.centerLeftChild);
	    				// Sibling's Left is still Left
	    			}
	    			return threeNode;
    			}else if(twoNode.parent.values==3) {
    				// Shift for parent value
	    			twoNode.values++;
	    			twoNode.value2=twoNode.value1;
	    			twoNode.value1=twoNode.parent.value3;
	    			TwoFourTreeItem threeNode = twoNode;
	    			// Fix Connections
	    			// Right is Still Right
	    			connectNodes(threeNode, "Center", threeNode.leftChild);
	    			
	    			// Shift from the sibling
	    			if(sibling.values==2) {
	    				sibling.parent.value3 = sibling.value2;
	    				sibling.value2=0;
	    				sibling.values--;
	    				// Fix Connections
	    				connectNodes(threeNode, "Left", sibling.rightChild);
	    				connectNodes(sibling, "Right", sibling.centerChild);
	    				// Sibling's Left is still Left
	    			}else if(sibling.values==3) {
	    				sibling.parent.value3 = sibling.value3;
	    				sibling.value3=0;
	    				sibling.values--;
	    				// Fix Connections
	    				connectNodes(threeNode, "Left", sibling.rightChild);
	    				connectNodes(sibling, "Right", sibling.centerRightChild);
	    				connectNodes(sibling, "Center", sibling.centerLeftChild);
	    				// Sibling's Left is still Left
	    			}
	    			return threeNode;
    			}
    		}
    		
    		
    		
    		
    	}else if(direction.toUpperCase().equals("COUNTERCLOCKWISE")) {
    		TwoFourTreeItem sibling = getImmediateSibling(twoNode, "Right");
    		
    		// Rotate the parent
    		if(twoNode.parent.leftChild==twoNode) {
    			//System.out.println("Couter in Method");
    			//Rotate from CenterLeft or Center Child or Right(value 1)
    			if(twoNode.parent.values==1) {
    				//System.out.println("PArent is 2 node");
	    			// Shift for parent value
	    			twoNode.values++;
	    			twoNode.value2=twoNode.parent.value1;
	    			TwoFourTreeItem threeNode = twoNode;
	    			// Fix Connections
	    			// Right is Still Right
	    			connectNodes(threeNode, "Center", threeNode.rightChild);
	    			
	    			// Shift from the sibling
	    			if(sibling.values==2) {
	    				sibling.parent.value1 = sibling.value1;
	    				sibling.value1=sibling.value2;
	    				sibling.value2=0;
	    				sibling.values--;
	    				// Fix Connections
	    				connectNodes(threeNode, "Right", sibling.leftChild);
	    				connectNodes(sibling, "Left", sibling.centerChild);
	    				// Sibling's Left is still Left
	    			}else if(sibling.values==3) {
	    				sibling.parent.value1 = sibling.value1;
	    				sibling.value1=sibling.value2;
	    				sibling.value2=sibling.value3;
	    				sibling.value3=0;
	    				sibling.values--;
	    				// Fix Connections
	    				connectNodes(threeNode, "Right", sibling.leftChild);
	    				connectNodes(sibling, "Left", sibling.centerLeftChild);
	    				connectNodes(sibling, "Center", sibling.centerRightChild);
	    				// Sibling's Left is still Left
	    			}
	    			return threeNode;
    			}else if(twoNode.parent.values==2) {
    				//System.out.println("PArent is 2 node");
	    			// Shift for parent value
	    			twoNode.values++;
	    			twoNode.value2=twoNode.parent.value1;
	    			TwoFourTreeItem threeNode = twoNode;
	    			// Fix Connections
	    			// Right is Still Right
	    			connectNodes(threeNode, "Center", threeNode.rightChild);
	    			
	    			// Shift from the sibling
	    			if(sibling.values==2) {
	    				sibling.parent.value1 = sibling.value1;
	    				sibling.value1=sibling.value2;
	    				sibling.value2=0;
	    				sibling.values--;
	    				// Fix Connections
	    				connectNodes(threeNode, "Right", sibling.leftChild);
	    				connectNodes(sibling, "Left", sibling.centerChild);
	    				// Sibling's Left is still Left
	    			}else if(sibling.values==3) {
	    				sibling.parent.value1 = sibling.value1;
	    				sibling.value1=sibling.value2;
	    				sibling.value2=sibling.value3;
	    				sibling.value3=0;
	    				sibling.values--;
	    				// Fix Connections
	    				connectNodes(threeNode, "Right", sibling.leftChild);
	    				connectNodes(sibling, "Left", sibling.centerLeftChild);
	    				connectNodes(sibling, "Center", sibling.centerRightChild);
	    				// Sibling's Left is still Left
	    			}
	    			return threeNode;
    			}else if(twoNode.parent.values==3) {
    				// Shift for parent value
	    			twoNode.values++;
	    			twoNode.value2=twoNode.parent.value1;
	    			TwoFourTreeItem threeNode = twoNode;
	    			// Fix Connections
	    			// Right is Still Right
	    			connectNodes(threeNode, "Center", threeNode.rightChild);
	    			
	    			// Shift from the sibling
	    			if(sibling.values==2) {
	    				sibling.parent.value1 = sibling.value1;
	    				sibling.value1=sibling.value2;
	    				sibling.value2=0;
	    				sibling.values--;
	    				// Fix Connections
	    				connectNodes(threeNode, "Right", sibling.leftChild);
	    				connectNodes(sibling, "Left", sibling.centerChild);
	    				// Sibling's Left is still Left
	    			}else if(sibling.values==3) {
	    				sibling.parent.value1 = sibling.value1;
	    				sibling.value1=sibling.value2;
	    				sibling.value2=sibling.value3;
	    				sibling.value3=0;
	    				sibling.values--;
	    				// Fix Connections
	    				connectNodes(threeNode, "Right", sibling.leftChild);
	    				connectNodes(sibling, "Left", sibling.centerLeftChild);
	    				connectNodes(sibling, "Center", sibling.centerRightChild);
	    				// Sibling's Left is still Left
	    			}
	    			return threeNode;
    			}
    		}else if(twoNode.parent.centerLeftChild==twoNode) {
    			// Rotate from centerRight Child (Value2)
    			// Shift for parent value
    			twoNode.values++;
    			twoNode.value2=twoNode.parent.value2;
    			TwoFourTreeItem threeNode = twoNode;
    			// Fix Connections
    			// Right is Still Right
    			connectNodes(threeNode, "Center", threeNode.rightChild);
    			
    			// Shift from the sibling
    			if(sibling.values==2) {
    				sibling.parent.value2 = sibling.value1;
    				sibling.value1=sibling.value2;
    				sibling.value2=0;
    				sibling.values--;
    				// Fix Connections
    				connectNodes(threeNode, "Right", sibling.leftChild);
    				connectNodes(sibling, "Left", sibling.centerChild);
    				// Sibling's Left is still Left
    			}else if(sibling.values==3) {
    				sibling.parent.value2 = sibling.value1;
    				sibling.value1=sibling.value2;
    				sibling.value2=sibling.value3;
    				sibling.value3=0;
    				sibling.values--;
    				// Fix Connections
    				connectNodes(threeNode, "Right", sibling.leftChild);
    				connectNodes(sibling, "Left", sibling.centerLeftChild);
    				connectNodes(sibling, "Center", sibling.centerRightChild);
    				// Sibling's Left is still Left
    			}
    			return threeNode;
    		}else if(twoNode.parent.centerChild==twoNode) {
    			// Rotate from Right Child (Value1)
    			// Shift for parent value
    			twoNode.values++;
    			twoNode.value2=twoNode.parent.value2;
    			TwoFourTreeItem threeNode = twoNode;
    			// Fix Connections
    			// Right is Still Right
    			connectNodes(threeNode, "Center", threeNode.rightChild);
    			
    			// Shift from the sibling
    			if(sibling.values==2) {
    				sibling.parent.value2 = sibling.value1;
    				sibling.value1=sibling.value2;
    				sibling.value2=0;
    				sibling.values--;
    				// Fix Connections
    				connectNodes(threeNode, "Right", sibling.leftChild);
    				connectNodes(sibling, "Left", sibling.centerChild);
    				// Sibling's Left is still Left
    			}else if(sibling.values==3) {
    				sibling.parent.value2 = sibling.value1;
    				sibling.value1=sibling.value2;
    				sibling.value2=sibling.value3;
    				sibling.value3=0;
    				sibling.values--;
    				// Fix Connections
    				connectNodes(threeNode, "Right", sibling.leftChild);
    				connectNodes(sibling, "Left", sibling.centerLeftChild);
    				connectNodes(sibling, "Center", sibling.centerRightChild);
    				// Sibling's Left is still Left
    			}
    			return threeNode;
    		}else if(twoNode.parent.centerRightChild==twoNode) {
    			//Rotate from Right Child (Value3)
    			// Shift for parent value
    			twoNode.values++;
    			twoNode.value2=twoNode.parent.value3;
    			TwoFourTreeItem threeNode = twoNode;
    			// Fix Connections
    			// Right is Still Right
    			connectNodes(threeNode, "Center", threeNode.rightChild);
    			
    			// Shift from the sibling
    			if(sibling.values==2) {
    				sibling.parent.value3 = sibling.value1;
    				sibling.value1=sibling.value2;
    				sibling.value2=0;
    				sibling.values--;
    				// Fix Connections
    				connectNodes(threeNode, "Right", sibling.leftChild);
    				connectNodes(sibling, "Left", sibling.centerChild);
    				// Sibling's Left is still Left
    			}else if(sibling.values==3) {
    				sibling.parent.value3 = sibling.value1;
    				sibling.value1=sibling.value2;
    				sibling.value2=sibling.value3;
    				sibling.value3=0;
    				sibling.values--;
    				// Fix Connections
    				connectNodes(threeNode, "Right", sibling.leftChild);
    				connectNodes(sibling, "Left", sibling.centerLeftChild);
    				connectNodes(sibling, "Center", sibling.centerRightChild);
    				// Sibling's Left is still Left
    			}
    			return threeNode;
    		}else if(twoNode.parent.rightChild==twoNode) {
    			// CAN'T BE LEFT CHILD 
    		}
    	}
    	return null;
    }
    
    /**
     * @param twoNode - The node that we have to merge into
     * @param direction - Chooses which immediate sibling (Left/Right)
     * @return returns the node at the end of the merge (Where we have to go after deletion)
     */
    public TwoFourTreeItem mergeNode(TwoFourTreeItem twoNode, String direction) {
    	if(twoNode==null || twoNode.parent==null) return null;
    	//System.out.println("Executing mergeNode medthod");
    	TwoFourTreeItem parent=twoNode.parent, leftChild=null, rightChild=null;
    	if(direction.toUpperCase().equals("LEFT")) {
    		TwoFourTreeItem sibling = getImmediateSibling(twoNode,"Left");
    		if(!twoNode.isTwoNode()||!sibling.isTwoNode())return null;
    		parent = twoNode.parent;
    		leftChild = sibling;
    		rightChild = twoNode;
    	}else if(direction.toUpperCase().equals("RIGHT")) {
    		TwoFourTreeItem sibling = getImmediateSibling(twoNode,"Right");
    		if(!twoNode.isTwoNode()||!sibling.isTwoNode())return null;
    		parent = twoNode.parent;
    		leftChild = twoNode;
    		rightChild = sibling;
    	}else {
    		return null;
    	}
		if(parent.isTwoNode()) {
			// Merge child and sibling together
			parent.value2=parent.value1;
			parent.value1=leftChild.value1;
			parent.value3=rightChild.value1;
			parent.values=3;
			
			parent.leftChild=null;
			parent.rightChild=null;
			connectNodes(parent, "Left", leftChild.leftChild);
			connectNodes(parent, "Center Left", leftChild.rightChild);
			connectNodes(parent, "Center Right", rightChild.leftChild);
			connectNodes(parent, "Right", rightChild.rightChild);
			//System.out.println(parent);
			if(parent.leftChild==null)parent.isLeaf=true;
			return parent;
		}
		
		if(rightChild.parent.leftChild==rightChild) {
			// Won't reach due to sibling not existing
		}else if(rightChild.parent.centerLeftChild==rightChild) {
			// Merge with leftChild (Sibling/twoNode) and pull down Value 1 (Merge Node is always the left Node)
			leftChild.value2=parent.value1;
			leftChild.value3=rightChild.value1;
			leftChild.values=3;
			parent.value1=parent.value2;
			parent.value2=parent.value3;
			parent.value3=0;
			parent.values--;
			
			connectNodes(parent, "Left", leftChild);
			connectNodes(parent, "Center", parent.centerRightChild);
			connectNodes(leftChild, "Left", leftChild.leftChild);
			connectNodes(leftChild, "Center Left", leftChild.rightChild);
			connectNodes(leftChild, "Center Right", rightChild.leftChild);
			connectNodes(leftChild, "Right", rightChild.rightChild);
			parent.centerLeftChild=null;
			parent.centerRightChild=null;
			return leftChild;
		}else if(rightChild.parent.centerChild==rightChild) {
			// Merge with leftChild (Sibling/twoNode)  and pull down Value 1 (Merge Node is always the left Node)
			leftChild.value2=parent.value1;
			leftChild.value3=rightChild.value1;
			leftChild.values=3;
			parent.value1=parent.value2;
			parent.value2=0;
			parent.values--;
			
			connectNodes(parent, "Left", leftChild);
			connectNodes(leftChild, "Left", leftChild.leftChild);
			connectNodes(leftChild, "Center Left", leftChild.rightChild);
			connectNodes(leftChild, "Center Right", rightChild.leftChild);
			connectNodes(leftChild, "Right", rightChild.rightChild);
			parent.centerChild=null;
			return leftChild;
		}else if(rightChild.parent.centerRightChild==rightChild) {
			// Merge with centerLeftChild (Sibling/twoNode)  and pull down Value 2 (Merge Node is always the left Node)
			leftChild.value2=parent.value2;
			leftChild.value3=rightChild.value1;
			leftChild.values=3;
			parent.value2=parent.value3;
			parent.value3=0;
			parent.values--;
			
			connectNodes(parent, "Center", leftChild);
			connectNodes(leftChild, "Left", leftChild.leftChild);
			connectNodes(leftChild, "Center Left", leftChild.rightChild);
			connectNodes(leftChild, "Center Right", rightChild.leftChild);
			connectNodes(leftChild, "Right", rightChild.rightChild);
			parent.centerLeftChild=null;
			parent.centerRightChild=null;
			return leftChild;
		}else if(rightChild.parent.rightChild==rightChild) {
			// Merge with centerChild or centerRightChild (Sibling/twoNode)  and pull down Value 2 or 3
			if(parent.isThreeNode()) {
				leftChild.value2=parent.value2;
    			leftChild.value3=rightChild.value1;
    			leftChild.values=3;
    			parent.value2=0;
    			parent.values--;
    			
    			connectNodes(parent, "Right", leftChild);
    			connectNodes(leftChild, "Left", leftChild.leftChild);
    			connectNodes(leftChild, "Center Left", leftChild.rightChild);
    			connectNodes(leftChild, "Center Right", rightChild.leftChild);
    			connectNodes(leftChild, "Right", rightChild.rightChild);
    			parent.centerChild=null;
    			return leftChild;
			}else if(parent.isFourNode()) {
				leftChild.value2=parent.value3;
    			leftChild.value3=rightChild.value1;
    			leftChild.values=3;
    			parent.value3=0;
    			parent.values--;
    			
    			connectNodes(parent, "Right", leftChild);
    			connectNodes(parent, "Center", parent.centerLeftChild);
    			connectNodes(leftChild, "Left", leftChild.leftChild);
    			connectNodes(leftChild, "Center Left", leftChild.rightChild);
    			connectNodes(leftChild, "Center Right", rightChild.leftChild);
    			connectNodes(leftChild, "Right", rightChild.rightChild);
    			parent.centerLeftChild=null;
    			parent.centerRightChild=null;
    			return leftChild;
			}
		}
    	
    	return null;
    }

    public void printInOrder() {
        if(root != null) root.printInOrder(0);
    }

    public TwoFourTree() {
    	
    }
}


//		https://azrael.digipen.edu/~mmead/www/Courses/CS280/Trees-2-3-4-delete.html
//		https://www.geeksforgeeks.org/2-3-4-tree/
//		https://algorithmtutor.com/Data-Structures/Tree/2-3-4-Trees/
//		https://en.wikipedia.org/wiki/2%E2%80%933%E2%80%934_tree