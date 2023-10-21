

public class BrokenTwoFourTree {
    private class TwoFourTreeItem {
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

        // isTwoNodes
        // return true if values == 2? (So leftChild and Right Child can be null)
        // or return true if leftChild and RightChild exist?
        public boolean isTwoNode() {
        	if(values==1)return true;
            return false;
        }

        public boolean isThreeNode() {
        	if(values==2)return true;
            return false;
        }

        public boolean isFourNode() {
        	if(values==3)return true;
            return false;
        }

        public boolean isRoot() {
           	if(parent==null)return true;
           	return false;
        }

        public TwoFourTreeItem(int value1) {
        	this.values = 1;
        	this.value1=value1;
        	this.value2 = 0;                             // exists iff the node is a 3-node or 4-node.
            this.value3 = 0;                             // exists iff the node is a 4-node.
            this.isLeaf = true;
             
            this.parent = null;              // parent exists iff the node is not root.
            this.leftChild = null;           // left and right child exist iff the note is a non-leaf.
            this.rightChild = null;          
            this.centerChild = null;         // center child exists iff the node is a non-leaf 3-node.
            this.centerLeftChild = null;     // center-left and center-right children exist iff the node is a non-leaf 4-node.
            this.centerRightChild = null;
        }

        public TwoFourTreeItem(int value1, int value2) {
        	this.values = 2;
        	this.value1=value1;
        	this.value2 = value2;                             // exists iff the node is a 3-node or 4-node.
            this.value3 = 0;                             // exists iff the node is a 4-node.
            this.isLeaf = true;
             
            this.parent = null;              // parent exists iff the node is not root.
            this.leftChild = null;           // left and right child exist iff the note is a non-leaf.
            this.rightChild = null;          
            this.centerChild = null;         // center child exists iff the node is a non-leaf 3-node.
            this.centerLeftChild = null;     // center-left and center-right children exist iff the node is a non-leaf 4-node.
            this.centerRightChild = null;
        }

        public TwoFourTreeItem(int value1, int value2, int value3) {
        	this.values = 3;
        	this.value1=value1;
        	this.value2 = value2;                             // exists iff the node is a 3-node or 4-node.
            this.value3 = value3;                             // exists iff the node is a 4-node.
            this.isLeaf = true;
             
            this.parent = null;              // parent exists iff the node is not root.
            this.leftChild = null;           // left and right child exist iff the note is a non-leaf.
            this.rightChild = null;          
            this.centerChild = null;         // center child exists iff the node is a non-leaf 3-node.
            this.centerLeftChild = null;     // center-left and center-right children exist iff the node is a non-leaf 4-node.
            this.centerRightChild = null;
        }

        private void printIndents(int indent) {
            for(int i = 0; i < indent; i++) System.out.printf("  ");
        }

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

    public boolean addValue(int value) {
    	if(root==null) {
    		root=new TwoFourTreeItem(value);
    		return true;
    	}
    	
    	TwoFourTreeItem temp = root;
    	
    	// NEED TO CHECK IF VALUE EXIST (call hasValue)
    	if(hasValue(value) == true) return false;
    	
    	// Else add value
		
    	boolean splitted = false;
    	while(temp!=null) {
    		// If we haven't splitted yet, and we reach a four node
    		if(!splitted&&temp.isFourNode()) {
    			//System.out.println("SPLITTING TIME");
    			// We need to split
        		// If it is a root, then we don't have a parent
        		if(temp.isRoot()) {
        			//System.out.println("I'm A ROOT");
        			// Make Left Child
        			TwoFourTreeItem newLeftChild=new TwoFourTreeItem(temp.value1);
        			newLeftChild.parent=temp;
        			if(temp.leftChild!=null) {
        				newLeftChild.leftChild = temp.leftChild;
        				temp.leftChild.parent=newLeftChild;
        				newLeftChild.isLeaf=false;
        			}else {
        				newLeftChild.leftChild = null;
        			}
        			if(temp.centerLeftChild!=null) {
        				newLeftChild.rightChild = temp.centerLeftChild;
        				temp.centerLeftChild.parent=newLeftChild;
        				newLeftChild.isLeaf=false;
        			}else {
        				newLeftChild.rightChild = null;
        			}
        			// Connect to Tree
        			temp.leftChild=newLeftChild;
        			
        			
        			TwoFourTreeItem newRightChild=new TwoFourTreeItem(temp.value3);
        			newRightChild.parent=temp;
        			
        			if(temp.centerRightChild!=null) {
        				newRightChild.leftChild = temp.centerRightChild;
        				temp.centerRightChild.parent=newRightChild;
        				newRightChild.isLeaf=false;
        			}else {
        				newRightChild.leftChild = null;
        			}
        			if(temp.rightChild!=null) {
        				newRightChild.rightChild = temp.rightChild;
        				temp.rightChild.parent=newRightChild;
        				newRightChild.isLeaf=false;
        			}else {
        				newRightChild.rightChild = null;
        			}
        			// Connect to tree
        			
        			temp.rightChild=newRightChild;
        			
        			
        			temp.centerLeftChild=null;
        			temp.centerRightChild=null;
	        		// Root is no longer a leaf
        			temp.isLeaf=false;
        			temp.value1 = temp.value2;
        			temp.values = 1;
	        		// Make value 2 & 3 null because this is now a two node
        			temp.value2 = 0;
	        		temp.value3 = 0;
        		} else {
        			//System.out.println("HELLO");
        			// We are on a branch
        			if(temp.parent.isTwoNode()) {
        				//System.out.println("PARENT IS TWO NODE");
        				TwoFourTreeItem tempParent = temp.parent;
        				TwoFourTreeItem currentNode = temp;
        				if(tempParent.rightChild==currentNode) {
        					// Add value 2 to the parent
        					tempParent.value2 = currentNode.value2;
        					tempParent.values = 2;
        					// Add Center Node
        					
        					TwoFourTreeItem newCenterChild=new TwoFourTreeItem(currentNode.value1);
        					newCenterChild.parent=tempParent;
                			
                			if(temp.leftChild!=null) {
                				newCenterChild.leftChild = temp.leftChild;
                				temp.leftChild.parent=newCenterChild;
                				newCenterChild.isLeaf=false;
                			}else {
                				newCenterChild.leftChild = null;
                			}
                			if(temp.centerLeftChild!=null) {
                				newCenterChild.rightChild = temp.centerLeftChild;
                				temp.centerLeftChild.parent=newCenterChild;
                				newCenterChild.isLeaf=false;
                			}else {
                				newCenterChild.rightChild = null;
                			}
        					
                			// Connect to Tree
                			tempParent.centerChild=newCenterChild;
                			
                			
                			TwoFourTreeItem newRightChild=new TwoFourTreeItem(temp.value3);
                			newRightChild.parent=tempParent;
                			if(temp.centerRightChild!=null) {
                				newRightChild.leftChild = temp.centerRightChild;
                				temp.centerRightChild.parent=newRightChild;
                				newRightChild.isLeaf=false;
                			}else {
                				newRightChild.leftChild = null;
                			}
                			if(temp.rightChild!=null) {
                				newRightChild.rightChild = temp.rightChild;
                				temp.rightChild.parent=newRightChild;
                				newRightChild.isLeaf=false;
                			}else {
                				newRightChild.rightChild = null;
                			}
                		
                			tempParent.rightChild=newRightChild;
        					
        					// Clear up current node
        					currentNode.value1=currentNode.value3;
        					currentNode.values = 1;
        					currentNode.value2 = 0;
        					currentNode.value3 = 0;
        			
        					temp=temp.parent;

        				}else if(tempParent.leftChild==currentNode) {
        					// DO THIS FOUR NODE FROM PARENT'S LEFT CHILD
							// Add value 2 to the parent
							tempParent.value2 = tempParent.value1;
        					tempParent.value1 = currentNode.value2;
        					tempParent.values = 2;
        					// Add Center Node
        					
        					TwoFourTreeItem newCenterChild=new TwoFourTreeItem(currentNode.value3);
        					newCenterChild.parent=tempParent;
                			if(temp.centerRightChild!=null) {
                				newCenterChild.leftChild = temp.centerRightChild;
                				temp.centerRightChild.parent=newCenterChild;
                				newCenterChild.isLeaf=false;
                			}else {
                				newCenterChild.leftChild = null;
                			}
                			if(temp.rightChild!=null) {
                				newCenterChild.rightChild = temp.rightChild;
                				temp.rightChild.parent=newCenterChild;
                				newCenterChild.isLeaf=false;
                			}else {
                				newCenterChild.rightChild = null;
                			}
                			
                			tempParent.centerChild=newCenterChild;
                			
                			
                			TwoFourTreeItem newLeftChild=new TwoFourTreeItem(temp.value1);
                			newLeftChild.parent=tempParent;
                			if(temp.leftChild!=null) {
                				newLeftChild.leftChild = temp.leftChild;
                				temp.leftChild.parent=newLeftChild;
                				newLeftChild.isLeaf=false;
                			}else {
                				newLeftChild.leftChild = null;
                			}
                			if(temp.centerLeftChild!=null) {
                				newLeftChild.rightChild = temp.centerLeftChild;
                				temp.centerLeftChild.parent=newLeftChild;
                				newLeftChild.isLeaf=false;
                			}else {
                				newLeftChild.rightChild = null;
                			}
                			
                			tempParent.leftChild=newLeftChild;
        					
        					// Clear up current node
                			currentNode.value1=currentNode.value1;
        					currentNode.values = 1;
        					currentNode.value2 = 0;
        					currentNode.value3 = 0;
        					
        					temp=temp.parent;
							
        				}
        				
        			}else if(temp.parent.isThreeNode()) {
        				TwoFourTreeItem tempParent = temp.parent;
        				TwoFourTreeItem currentNode = temp;

        				if(tempParent.leftChild==currentNode) {
        					// DO THIS FOUR NODE FROM PARENT'S LEFT CHILD
							tempParent.value3 = tempParent.value2;
							tempParent.value2 = tempParent.value1;
        					tempParent.value1 = currentNode.value2;
        					tempParent.values = 3;
        					// Fix Center Children
        					TwoFourTreeItem newCenterLeftChild=new TwoFourTreeItem(currentNode.value3);
        					newCenterLeftChild.parent=tempParent;
        					if(temp.centerRightChild!=null) {
        						newCenterLeftChild.leftChild = temp.centerRightChild;
                				temp.centerRightChild.parent=newCenterLeftChild;
                				newCenterLeftChild.isLeaf=false;
                			}else {
                				newCenterLeftChild.leftChild = null;
                			}
                			if(temp.rightChild!=null) {
                				newCenterLeftChild.rightChild = temp.rightChild;
                				temp.rightChild.parent=newCenterLeftChild;
                				newCenterLeftChild.isLeaf=false;
                			}else {
                				newCenterLeftChild.rightChild = null;
                			}
        					tempParent.centerLeftChild=newCenterLeftChild;
        					
                			
                			TwoFourTreeItem newLeftChild=new TwoFourTreeItem(currentNode.value1);
                			newLeftChild.parent=tempParent;
                			
                			if(temp.leftChild!=null) {
                				newLeftChild.leftChild = temp.leftChild;
                				temp.leftChild.parent=newLeftChild;
                				newLeftChild.isLeaf=false;
                			}else {
                				newLeftChild.leftChild = null;
                			}
                			if(temp.centerLeftChild!=null) {
                				newLeftChild.rightChild = temp.centerLeftChild;
                				temp.centerLeftChild.parent=newLeftChild;
                				newLeftChild.isLeaf=false;
                			}else {
                				newLeftChild.rightChild = null;
                			}
                			
                			tempParent.leftChild=newLeftChild;
                			
                			// reassign center child
                			tempParent.centerRightChild=tempParent.centerChild;
                			tempParent.centerChild=null;
        					// Clear up current node
                			currentNode.value1=currentNode.value1;
        					currentNode.values = 1;
        					currentNode.value2 = 0;
        					currentNode.value3 = 0;
        					temp=temp.parent;

        				}else if (tempParent.centerChild==currentNode) {
        					// DO THIS FOUR NODE FROM PARENT'S CENTER CHILD
							tempParent.value3 = tempParent.value2;
							tempParent.value1 = tempParent.value1;
        					tempParent.value2 = currentNode.value2;
        					tempParent.values = 3;
        					// Fix Center Children
        					
        					TwoFourTreeItem newCenterLeftChild=new TwoFourTreeItem(currentNode.value1);
        					newCenterLeftChild.parent=tempParent;
        					
        					if(temp.leftChild!=null) {
        						newCenterLeftChild.leftChild = temp.leftChild;
                				temp.leftChild.parent=newCenterLeftChild;
                				newCenterLeftChild.isLeaf=false;
                			}else {
                				newCenterLeftChild.leftChild = null;
                			}
                			if(temp.centerLeftChild!=null) {
                				newCenterLeftChild.rightChild = temp.centerLeftChild;
                				temp.centerLeftChild.parent=newCenterLeftChild;
                				newCenterLeftChild.isLeaf=false;
                			}else {
                				newCenterLeftChild.rightChild = null;
                			}
        					
                			tempParent.centerLeftChild=newCenterLeftChild;
                			
                			
                			TwoFourTreeItem newCenterRightChild=new TwoFourTreeItem(temp.value3);
                			newCenterRightChild.parent=tempParent;
                			
                			if(temp.centerRightChild!=null) {
                				newCenterRightChild.leftChild = temp.centerRightChild;
                				temp.centerRightChild.parent=newCenterRightChild;
                				newCenterRightChild.isLeaf=false;
                			}else {
                				newCenterRightChild.leftChild = null;
                			}
                			if(temp.rightChild!=null) {
                				newCenterRightChild.rightChild = temp.rightChild;
                				temp.rightChild.parent=newCenterRightChild;
                				newCenterRightChild.isLeaf=false;
                			}else {
                				newCenterRightChild.rightChild = null;
                			}
                			tempParent.centerRightChild=newCenterRightChild;
        					
                			tempParent.centerChild=null;
        					// Clear up current node
                			// This where you free currentNode Data
        					currentNode.values = 0;
        					currentNode.value1 = 0;
        					currentNode.value2 = 0;
        					currentNode.value3 = 0;
        					temp=temp.parent;
        				}else if (tempParent.rightChild==currentNode) {
        					// DO THIS FOUR NODE FROM PARENT'S RIGHT CHILD
        					tempParent.value3 = currentNode.value2;
        					tempParent.values = 3;
        					// Fix Center Children
        					
        					TwoFourTreeItem newCenterRightChild=new TwoFourTreeItem(currentNode.value1);
        					newCenterRightChild.parent=tempParent;
        					
        					if(temp.leftChild!=null) {
        						newCenterRightChild.leftChild = temp.leftChild;
                				temp.leftChild.parent=newCenterRightChild;
                				newCenterRightChild.isLeaf=false;
                			}else {
                				newCenterRightChild.leftChild = null;
                			}
                			if(temp.centerLeftChild!=null) {
                				newCenterRightChild.rightChild = temp.centerLeftChild;
                				temp.centerLeftChild.parent=newCenterRightChild;
                				newCenterRightChild.isLeaf=false;
                			}else {
                				newCenterRightChild.rightChild = null;
                			}
                			tempParent.centerRightChild=newCenterRightChild;
                			
                			
                			TwoFourTreeItem newRightChild=new TwoFourTreeItem(currentNode.value3);
                			newRightChild.parent=tempParent;
                			
                			if(temp.centerRightChild!=null) {
                				newRightChild.leftChild = temp.centerRightChild;
                				temp.centerRightChild.parent=newRightChild;
                				newRightChild.isLeaf=false;
                			}else {
                				newRightChild.leftChild = null;
                			}
                			if(temp.rightChild!=null) {
                				newRightChild.rightChild = temp.rightChild;
                				temp.rightChild.parent=newRightChild;
                				newRightChild.isLeaf=false;
                			}else {
                				newRightChild.rightChild = null;
                			}
                			
                			tempParent.rightChild=newRightChild;
   
                			tempParent.centerLeftChild=tempParent.centerChild;
                			tempParent.centerChild=null;
        					// Clear up current node
                			currentNode.value1=currentNode.value3;
        					currentNode.values = 1;
        					currentNode.value2 = 0;
        					currentNode.value3 = 0;
        					temp=temp.parent;
        				}
        				
        			}
        		}
        		// We have splitted
        		splitted = true;
    		}else {
    			if(temp.isLeaf) {
        			if(temp.isTwoNode()) {
    	        		if(value < temp.value1) {
    	        			temp.value2=temp.value1;
    	        			temp.value1=value;
    	        		}else if(value > temp.value1) {
    	        			temp.value2=value;
    	        		}
    	        		temp.values=2;
    	        		return true;
    	        	}else if(temp.isThreeNode()) {
    	        		// DO THIS

    					if(value < temp.value1) {
    						temp.value3=temp.value2;
    	        			temp.value2=temp.value1;
    	        			temp.value1=value;
    	        		}else if(value > temp.value1 && value < temp.value2){
    						temp.value3 = temp.value2;
    						temp.value2 = value;
    					}else if(value > temp.value2) {
    	        			temp.value3=value;

    	        		}
    	        		temp.values=3;
    	        		return true;
    	        	}else if(temp.isFourNode()) {
    	        		// Continue to be later split
    	        		// Don't add anything here
    	        	}
        		}
    			// We tranverse the Tree down
    			// check the left child, rightchild, center (if 3node), or center right/left (if 4node)
    			// temp = temp.targetchild;
    			if(temp.isTwoNode()) {
    				splitted=false;
    				// We need to check left child and right child
    				 if(value>temp.value1){
    					 if(temp.rightChild == null){                                               // go to right node
    						 temp.rightChild = new TwoFourTreeItem(value);
    						 System.out.println("WEE WOO");
    					 } else {
    						 temp = temp.rightChild;
    						 
    					 }
    				 }
    				 else if(value<temp.value1) {
                         if(temp.leftChild == null){                                               // go to left node
                            temp.leftChild = new TwoFourTreeItem(value); 
                            System.out.println("WEE WOO");
                        } else {
                        	temp = temp.leftChild;
                        }
    				 }
    			}else if(temp.isThreeNode()) {
    				splitted=false;
    				// Check left child, right child, center child
    				 if(value>temp.value2){
    					 if(temp.rightChild == null){                                               // go to right node
    						 temp.rightChild = new TwoFourTreeItem(value);
    						 System.out.println("WEE WOO");
    					 } else {
    						 temp = temp.rightChild;
    					 }
    				 }else if(value > temp.value1 && value < temp.value2) {
    					 if(temp.centerChild == null){                                               // go to right node
    						 temp.centerChild = new TwoFourTreeItem(value);
    						 System.out.println("WEE WOO");
    					 } else {
    						 temp = temp.centerChild;
    					 }
    				 }else if(value<temp.value1) {
                         if(temp.leftChild == null){                                               // go to left node
                            temp.leftChild = new TwoFourTreeItem(value);
                            System.out.println("WEE WOO");
                        } else {
                        	temp = temp.leftChild;
                        }
    				 }
    			}else if(temp.isFourNode()) {
    				splitted=false;
    				// Check left child, center left child, center right child, right child
					if(value>temp.value3){
    					 if(temp.rightChild == null){                                               // go to right node
    						 temp.rightChild = new TwoFourTreeItem(value);
    						 System.out.println("WEE WOO");
    					 } else {
    						 temp = temp.rightChild;
    					 }
    				 }else if(value > temp.value1 && value < temp.value2) {
    					 if(temp.centerLeftChild == null){                                               // go to right node
    						 temp.centerLeftChild = new TwoFourTreeItem(value);
    						 System.out.println("WEE WOO");
    					 } else {
    						 temp = temp.centerLeftChild;
    					 }
    				 }else if(value > temp.value2 && value < temp.value3) {
    					 if(temp.centerRightChild == null){                                               // go to right node
    						 temp.centerRightChild = new TwoFourTreeItem(value);
    						 System.out.println("WEE WOO");
    					 } else {
    						 temp = temp.centerRightChild;
    					 }
    				 }else if(value<temp.value1) {
                         if(temp.leftChild == null){                                               // go to left node
                            temp.leftChild = new TwoFourTreeItem(value);
                            System.out.println("WEE WOO");
                        } else {
                        	temp = temp.leftChild;
                        }
    				 }
    			}
    			
    		}
    		// Add value when we reach a list
    		
    		}
			
        return false; // we Added the value;
    }

	public TwoFourTreeItem nodeSuccesor(TwoFourTreeItem node) {
    	if(node.leftChild==null) return node;
		return nodeSuccesor(node.leftChild);
    }
    
    public boolean nodeHasValue(TwoFourTreeItem node, int value) {
    	if(node.values>=1) {
    		// value 1 exist
    		if(node.value1==value) return true;
    	}
    	if(node.values>=2) {
    		// value 2 exist
    		if(node.value2==value) return true;
    	}
    	if(node.values==3) {
    		// Value 3 Exist
    		if(node.value3==value) return true;
    	}
    	return false;
    }

    public boolean hasValue(int value) {
    	TwoFourTreeItem temp = root;
    	while(temp!=null) {
    		if(nodeHasValue(temp, value)) return true;
        	// Node does not have values go to the next node
    		if(temp.isTwoNode()) {
    			if(value>temp.value1) temp = temp.rightChild;// go to right node
    			else if(value<temp.value1) temp = temp.leftChild;// go to left node
    		}else if(temp.isThreeNode()) {
    			if(value<temp.value1) temp = temp.leftChild;// go to left node
    			else if(value>temp.value1 && value<temp.value2) temp = temp.centerChild;// go to center node
    			else if(value>temp.value2) temp = temp.rightChild;// go to right node
    		}else if(temp.isFourNode()) {
    			// Four Node
				if(value<temp.value1) temp = temp.leftChild;// go to left node
    			else if(value>temp.value1 && value<temp.value2) temp = temp.centerLeftChild;// go to center node
				else if(value>temp.value2 && value<temp.value3) temp = temp.centerRightChild;
    			else if(value>temp.value3) temp = temp.rightChild;// go to right node
    		}
    	}
    	
    	// Loop until you get to the value or you reach a leaf
    	return false;
    }

    public boolean deleteValue(int value) {
    	
    	
    	/*
    	 *	1. If the element, k is in the node and the node is a leaf containing at least 2 keys, simply remove k from the node.

			2. If the element, k is in the node and the node is an internal node perform one of the following:
					2.1	If the element's left child has at least 2 keys, replace the element with its predecessor, p, and then recursively delete p.
					2.2	If the element's right child has at least 2 keys, replace the element with its successor, s, and then recursively delete s.
					2.3	If both children have only 1 key (the minimum), merge the right child into the left child and include the element, k, in the left child. Free the right child and recursively delete k from the left child.
			3. If the element, k, is not in the internal node, follow the proper link to find k. To ensure that all nodes we travel through will have at least 2 keys, you may need to perform one of the following before descending into a node. Then, you will descend into the corresponding node. Eventually, case 1 or 2 will be arrived at (if k is in the tree).
					3.1	If the child node (the one being descending into) has only 1 key and has an immediate sibling with at least 2 keys, move an element down from the parent into the child and move an element from the sibling into the parent.
					3.2	If both the child node and its immediate siblings have only 1 key each, merge the child node with one of the siblings and move an element down from the parent into the merged node. This element will be the middle element in the node. Free the node whose elements were merged into the other node.
	     * 
    	 */
    	// TargetNode is the node that should hold the value we are deleting
    	TwoFourTreeItem targetNode = root;
    	while(targetNode!=null) {
    		// Check #1 and #2 (#1 and #2 only happens when the value you are trying to delete is in the node)
    		if(nodeHasValue(targetNode, value)) {
    			// Now we have to do Case #1 or #2
    			// Case #1 -  We delete the value from the list if it is 3/4 node
    			// 1. If the element, k is in the node and the node is a leaf containing at least 2 keys, simply remove k from the node.
    			if(targetNode.isLeaf) {
    				if(targetNode.isThreeNode()){
    					if(targetNode.value1 == value){
    						targetNode.value1 = targetNode.value2;
    						targetNode.value2 = 0;
    						targetNode.values = 1;
    					}else{
    						targetNode.value2 = 0;
    						targetNode.values = 1;
    					}
    				}else if(targetNode.isFourNode()){
    					if(targetNode.value1 == value){ //left value in four node is to be deleted
    						targetNode.value1 = targetNode.value2;
    						targetNode.value2 = targetNode.value3;
    						targetNode.value3 = 0;
    						targetNode.values = 2;
    					} else if(targetNode.value2 == value){ //middle value in four node is to be deleted
    						targetNode.value2 = targetNode.value3;
    						targetNode.value3 = 0;
    						targetNode.values = 2;
    					} else { //right value in four node is to be deleted (default case)
    						targetNode.value3 = 0;
    						targetNode.values = 2;
    					} 
    				// There is only one exception to Case #1, when there is only one value in the tree (just a root that is a two node, then we just remove that node)
    				}else if(targetNode.isTwoNode()&&targetNode.isRoot()) {
    					targetNode=null;
    				}
    				// return true since we have sucessfully deleted the value;
    				return true;
    			}else {
    				// Since the targetValue is an internal node, we have to do Case #2
    				//2. If the element, k is in the node and the node is an internal node perform one of the following:
    				//		2.1	If the element's left child has at least 2 keys, replace the element with its predecessor, p, and then recursively delete p.
    				//		2.2	If the element's right child has at least 2 keys, replace the element with its successor, s, and then recursively delete s.
    				// 		If the adjacent left and right child are two key, but their adjacent sibilings aren't rotate
    				//		2.3	If both children have only 1 key (the minimum), merge the right child into the left child and include the element, k, in the left child. 
    				// 				Free the right child and recursively delete k from the left child. 
    				//			[So merge two nodes, and make a 4 node, then find the predecessor of the center value (targetValue)]
    				// TL:DR, we need to check neighbooring left and right child to do case 1 or 2
    				//			When you do case 2.1 you can then only go to the far right (predecessor) after going left once while checking for 3.1 and 3.2
    				//			When you do case 2.2 you can then only got o the far left (sucessor) after going right once while checking for 3.1 and 3.2
    				//		
    				if(targetNode.isTwoNode()) {
    					// TargetValue is value1
    					// Check left child and right child
    					if(targetNode.leftChild.isFourNode()) {
    						// Do 2.1
    					}else if(targetNode.leftChild.isThreeNode()) {
    						// Do 2.1
    					}else if(targetNode.rightChild.isFourNode()) {
    						// Do 2.2
    					}else if(targetNode.rightChild.isThreeNode()){
    						// Do 2.2
    					}else {
    						// If we are here, then bothe neighbooring children are two node
    						// Do 2.3
    					}
    				}else if(targetNode.isThreeNode()) {
    					// Check if targetValye is value1 or 2
    					if(targetNode.value1==value) {
	    					// TargetValue is value1
	    					// Check left and center child
	    					if(targetNode.leftChild.isFourNode()) {
	    						// Do 2.1
	    					}else if(targetNode.leftChild.isThreeNode()) {
	    						// Do 2.1
	    					}else if(targetNode.centerChild.isFourNode()) {
	    						// Do 2.2
	    					}else if(targetNode.centerChild.isThreeNode()){
	    						// Do 2.2
	    					}else {
	    						// If we are here, then bothe neighbooring children are two node
	    						// Do 2.3
	    					}
    					}else if(targetNode.value2==value) {
    						// TargetValue is value2
	    					// Check center and right child
	    					if(targetNode.centerChild.isFourNode()) {
	    						// Do 2.1
	    					}else if(targetNode.centerChild.isThreeNode()) {
	    						// Do 2.1
	    					}else if(targetNode.rightChild.isFourNode()) {
	    						// Do 2.2
	    					}else if(targetNode.rightChild.isThreeNode()){
	    						// Do 2.2
	    					}else {
	    						// If we are here, then bothe neighbooring children are two node
	    						// Do 2.3
	    					}
    					}
    				}else if(targetNode.isFourNode()) {
    					// Check if targetValye is value1, value2, or value3
    					if(targetNode.value1==value) {
	    					// TargetValue is value1
	    					// Check left and centerLeft child
	    					if(targetNode.leftChild.isFourNode()) {
	    						// Do 2.1
	    					}else if(targetNode.leftChild.isThreeNode()) {
	    						// Do 2.1
	    					}else if(targetNode.centerLeftChild.isFourNode()) {
	    						// Do 2.2
	    					}else if(targetNode.centerLeftChild.isThreeNode()){
	    						// Do 2.2
	    					}else {
	    						// If we are here, then bothe neighbooring children are two node
	    						// Do 2.3
	    					}
    					}else if(targetNode.value2==value) {
    						// TargetValue is value2
	    					// Check centerLeft and centerRight child
	    					if(targetNode.centerLeftChild.isFourNode()) {
	    						// Do 2.1
	    					}else if(targetNode.centerLeftChild.isThreeNode()) {
	    						// Do 2.1
	    					}else if(targetNode.centerRightChild.isFourNode()) {
	    						// Do 2.2
	    					}else if(targetNode.centerRightChild.isThreeNode()){
	    						// Do 2.2
	    					}else {
	    						// If we are here, then bothe neighbooring children are two node
	    						// Do 2.3
	    					}
    					}else if(targetNode.value3==value) {
    						// TargetValue is value3
	    					// Check centerRight and Right child
	    					if(targetNode.centerRightChild.isFourNode()) {
	    						// Do 2.1
	    					}else if(targetNode.centerRightChild.isThreeNode()) {
	    						// Do 2.1
	    					}else if(targetNode.centerChild.isFourNode()) {
	    						// Do 2.2
	    					}else if(targetNode.centerChild.isThreeNode()){
	    						// Do 2.2
	    					}else {
	    						// If we are here, then bothe neighbooring children are two node
	    						// Do 2.3
	    					}
    					}
    				}
    			}
    			
    		}else {
    			// Since we haven't found the value yet, we do Case #3 and tranverse down the tree
    			/*	3. If the element, k, is not in the internal node, follow the proper link to find k. 
    			 * 		To ensure that all nodes we travel through will have at least 2 keys, 
    			 * 		you may need to perform one of the following before descending into a node. 
    			 * 		Then, you will descend into the corresponding node. Eventually, case 1 or 2 will be arrived at (if k is in the tree).
							3.1	If the child node (the one being descending into) has only 1 key and has an immediate sibling with at least 2 keys, 
									move an element down from the parent into the child and move an element from the sibling into the parent.
							3.2	If both the child node and its immediate siblings have only 1 key each, merge the child node with one of the siblings 
									and move an element down from the parent into the merged node. This element will be the middle element in the node. Free the node whose elements were merged into the other node.
    			 */
    			// TL:DR Tranverse the to the proper child, but check if the child is a Two Node, if so, then rotate (if neighbooring sibling isn't a twoNode)
    			//			or merge one ofthe neighborring sibilings
    			//		One you made sure that the child you are tranversing to isn't a two node, then you go through it
    			if(targetNode.isTwoNode()) {
        			if(value>targetNode.value1) {
        				// go to right child
        				// check for Case 3.1 & 3.2
        				if(targetNode.rightChild.isTwoNode()) {
        					// You need to do 3.1 or 3.2
        					if(targetNode.leftChild.isFourNode()) {
	    						// Do 3.1
        						// Rotate from left to right (Clockwise)
								// take care of the values
								targetNode.rightChild.value2 = targetNode.rightChild.value1;
								targetNode.rightChild.value1 = targetNode.value1;
								targetNode.value1 = targetNode.leftChild.value3;
								targetNode.leftChild.value3 = 0;
								targetNode.leftChild.values = 2;
								targetNode.rightChild.values = 2;

								//takes care of target nodes grandchild...their links
								// Right child's right child stays the same
								//targetNode.rightChild.rightChild = targetNode.rightChild.rightChild;
								targetNode.rightChild.centerChild = targetNode.rightChild.leftChild;
								targetNode.rightChild.leftChild.parent = targetNode.rightChild.centerChild;
								targetNode.rightChild.leftChild = targetNode.leftChild.rightChild;
								targetNode.leftChild.rightChild.parent=targetNode.rightChild.leftChild;
								targetNode.leftChild.rightChild = targetNode.leftChild.centerRightChild;
								targetNode.leftChild.centerChild = targetNode.leftChild.centerLeftChild;
								targetNode.leftChild.centerRightChild = null;
								targetNode.leftChild.centerLeftChild = null;
								
	    					}else if(targetNode.leftChild.isThreeNode()) {
	    						// Do 3.1
								// take care of the values
								targetNode.rightChild.value2 = targetNode.rightChild.value1;
								targetNode.rightChild.value1 = targetNode.value1;
								targetNode.value1 = targetNode.leftChild.value2;
								targetNode.leftChild.value2 = 0;
								targetNode.leftChild.values = 1;
								targetNode.rightChild.values = 2;

								//takes care of target nodes grandchild...their links
								// Right child's right child stays the same
								//targetNode.rightChild.rightChild = targetNode.rightChild.rightChild;
								targetNode.rightChild.centerChild = targetNode.rightChild.leftChild;
								targetNode.rightChild.leftChild = targetNode.leftChild.rightChild;
								targetNode.leftChild.rightChild = targetNode.leftChild.centerChild;
								targetNode.leftChild.centerChild = null;

	    					}else {
	    						// If we are here, then the neighbooring children are two node
	    						// Do 3.2
								targetNode.value2 = targetNode.value1;
								targetNode.value1 = targetNode.leftChild.value1;
								targetNode.value3 = targetNode.rightChild.value1;


								targetNode.leftChild = targetNode.leftChild.leftChild;
								targetNode.centerLeftChild = targetNode.leftChild.rightChild;
								targetNode.centerRightChild = targetNode.rightChild.leftChild;
								targetNode.rightChild = targetNode.rightChild.rightChild;

								targetNode.values = 3;
								targetNode.centerLeftChild.values = targetNode.leftChild.rightChild.values;
								targetNode.centerRightChild.values = targetNode.rightChild.leftChild.values;

	    					}
        				}
        				// Once you know that the child you are going to isn't a two node, then go
        				targetNode=targetNode.rightChild;
        			}else if(value<targetNode.value1) {
        				// go to left child
        				// check for Case 3.1 & 3.2
        				if(targetNode.leftChild.isTwoNode()) {
        					// You need to do 3.1 or 3.2
        					if(targetNode.rightChild.isFourNode()) {
	    						// Do 3.1
								targetNode.leftChild.value2 = targetNode.value1;
								targetNode.value1 = targetNode.rightChild.value1;
								targetNode.rightChild.value1 = targetNode.rightChild.value2;
								targetNode.rightChild.value2 = targetNode.rightChild.value3;

								//takes care of target nodes grandchild...their links
								targetNode.leftChild.centerChild = targetNode.leftChild.rightChild;
								targetNode.leftChild.rightChild = targetNode.rightChild.leftChild;
								targetNode.rightChild.leftChild = targetNode.rightChild.centerLeftChild;
								targetNode.rightChild.centerChild = targetNode.rightChild.centerRightChild;

								targetNode.leftChild.values = 2;
								targetNode.rightChild.values = 2;

	    					}else if(targetNode.rightChild.isThreeNode()) {
	    						// Do 3.1
								targetNode.leftChild.value2 = targetNode.value1;
								targetNode.value1 = targetNode.rightChild.value1;
								targetNode.rightChild.value1 = targetNode.rightChild.value2;
								

								//takes care of target nodes grandchild...their links
								targetNode.leftChild.centerChild = targetNode.leftChild.rightChild;
								targetNode.leftChild.rightChild = targetNode.rightChild.leftChild;
								targetNode.rightChild.leftChild = targetNode.rightChild.centerChild;
								targetNode.rightChild.centerChild = null;

								targetNode.leftChild.values = 2;
								targetNode.rightChild.values = 1;

	    					}else {
	    						// If we are here, then the neighbooring children are two node
	    						// Do 3.2
	    						TwoFourTreeItem newItem = new TwoFourTreeItem(targetNode.leftChild.value1, targetNode.value1, targetNode.rightChild.value1);
	    						newItem.parent=targetNode.parent;
	    						if(newItem.parent==null) {
	    							// THIS IS THE ROOT
	    							
	    						}

								targetNode.leftChild = targetNode.leftChild.leftChild;
								targetNode.centerLeftChild = targetNode.leftChild.rightChild;
								targetNode.centerRightChild = targetNode.rightChild.leftChild;
								targetNode.rightChild = targetNode.rightChild.rightChild;

								targetNode.values = 3;
								targetNode.centerLeftChild.values = targetNode.leftChild.rightChild.values;
								targetNode.centerRightChild.values = targetNode.rightChild.leftChild.values;
	    					}
        				}
        				// Once you know that the child you are going to isn't a two node, then go
        				targetNode=targetNode.leftChild;
        			}
        		}else if(targetNode.isThreeNode()) {
        			if(value<targetNode.value1) {
        				// go to left node
        				// check for Case 3.1 & 3.2
        				if(targetNode.leftChild.isTwoNode()) {
        					// You need to do 3.1 or 3.2
        					if(targetNode.centerChild.isFourNode()) {
	    						// Do 3.1
								targetNode.leftChild.value2 = targetNode.value1;
								targetNode.value1 = targetNode.centerChild.value1;
								targetNode.centerChild.value1 = targetNode.centerChild.value2;
								targetNode.centerChild.value2 = targetNode.centerChild.value3;

								//takes care of target nodes grandchildren...their links
								targetNode.leftChild.centerChild = targetNode.leftChild.rightChild;
								targetNode.leftChild.rightChild = targetNode.centerChild.leftChild;
								targetNode.centerChild.leftChild = targetNode.centerChild.centerLeftChild;
								targetNode.centerChild.centerChild = targetNode.centerChild.centerRightChild;

								targetNode.leftChild.values = 2; //left child grows
								targetNode.centerChild.values = 2; //right childs shrinks

	    					}else if(targetNode.centerChild.isThreeNode()) {
	    						// Do 3.1
								targetNode.leftChild.value2 = targetNode.value1;
								targetNode.value1 = targetNode.centerChild.value1;
								targetNode.centerChild.value1 = targetNode.centerChild.value2;

								//takes care of target nodes grandchildren...their links
								targetNode.leftChild.centerChild = targetNode.leftChild.rightChild;
								targetNode.leftChild.rightChild = targetNode.centerChild.leftChild;
								targetNode.centerChild.leftChild = targetNode.centerChild.centerChild;
								targetNode.rightChild.centerChild = null;

								targetNode.leftChild.values = 2;
								targetNode.centerChild.values = 1;

	    					}else {
	    						// If we are here, then the neighbooring children are two node
	    						// Do 3.2 
								// WHAT DO
								// WHAT DO
								// WHAT DO
								// WHAT DO
								// WHAT DO
								// WHAT DO
								
	    					}
        				}
        				// Once you know that the child you are going to isn't a two node, then go
        				targetNode=targetNode.leftChild;
        			}else if(value>targetNode.value1 && value<targetNode.value2) {
        				// go to center node
        				// check for Case 3.1 & 3.2
        				if(targetNode.centerChild.isTwoNode()) {
        					// You need to do 3.1 or 3.2
        					if(targetNode.leftChild.isFourNode()) {
	    						// Do 3.1
								targetNode.centerChild.value2 = targetNode.centerChild.value1;
								targetNode.centerChild.value1 = targetNode.value1;
								targetNode.value1 = targetNode.leftChild.value3;

								//takes care of target nodes grandchildren...their links
								targetNode.centerChild.centerChild = targetNode.centerChild.leftChild;
								targetNode.centerChild.leftChild = targetNode.leftChild.rightChild;
								targetNode.leftChild.rightChild = targetNode.leftChild.centerRightChild;
								targetNode.leftChild.centerChild = targetNode.leftChild.centerLeftChild;
								
								targetNode.leftChild.values = 2;
								targetNode.centerChild.values = 2;

	    					}else if(targetNode.leftChild.isThreeNode()) {
	    						// Do 3.1
								targetNode.centerChild.value2 = targetNode.centerChild.value1;
								targetNode.centerChild.value1 = targetNode.value1;
								targetNode.value1 = targetNode.leftChild.value2;

								//takes care of target nodes grandchildren...their links
								targetNode.centerChild.centerChild = targetNode.centerChild.leftChild;
								targetNode.centerChild.leftChild = targetNode.leftChild.rightChild;
								targetNode.leftChild.rightChild = targetNode.leftChild.centerChild;
								
								targetNode.leftChild.values = 1;
								targetNode.centerChild.values = 2;

	    					}if(targetNode.rightChild.isFourNode()) {
	    						// Do 3.1
								targetNode.centerChild.value2 = targetNode.value2;
								targetNode.value2 = targetNode.rightChild.value1;
								targetNode.rightChild.value1 = targetNode.rightChild.value2;
								targetNode.rightChild.value2 = targetNode.rightChild.value3;

								//takes care of target nodes grandchild...their links
								targetNode.centerChild.centerChild = targetNode.centerChild.rightChild;
								targetNode.centerChild.rightChild = targetNode.rightChild.leftChild;
								targetNode.rightChild.leftChild = targetNode.rightChild.centerLeftChild;
								targetNode.rightChild.centerChild = targetNode.rightChild.centerRightChild;

								targetNode.centerChild.values = 2;
								targetNode.rightChild.values = 2;

	    					}else if(targetNode.rightChild.isThreeNode()) {
	    						// Do 3.1
								targetNode.centerChild.value2 = targetNode.value2;
								targetNode.value2 = targetNode.rightChild.value1;
								targetNode.rightChild.value1 = targetNode.rightChild.value2;

								//takes care of target nodes grandchild...their links
								targetNode.centerChild.centerChild = targetNode.centerChild.rightChild;
								targetNode.centerChild.rightChild = targetNode.rightChild.leftChild;
								targetNode.rightChild.leftChild = targetNode.rightChild.centerChild;

								targetNode.centerChild.values = 2;
								targetNode.rightChild.values = 1;

	    					}else {
	    						// If we are here, then the neighbooring children are two node
	    						// Do 3.2
								// WHAT DO
								// WHAT DO
								// WHAT DO
								// WHAT DO
								// WHAT DO
								// WHAT DO

	    					}
        				}
        				// Once you know that the child you are going to isn't a two node, then go
        				targetNode=targetNode.centerChild;
        			}
        			else if(value>targetNode.value2) {
        				// go to right node
        				// check for Case 3.1 & 3.2
        				if(targetNode.rightChild.isTwoNode()) {
        					// You need to do 3.1 or 3.2
        					if(targetNode.centerChild.isFourNode()) {
	    						// Do 3.1
								targetNode.rightChild.value2 = targetNode.rightChild.value1;
								targetNode.rightChild.value1 = targetNode.value2;
								targetNode.value2 = targetNode.centerChild.value3;

								//takes care of target nodes grandchildren...their links
								targetNode.rightChild.centerChild = targetNode.rightChild.leftChild;
								targetNode.rightChild.leftChild = targetNode.centerChild.rightChild;
								targetNode.centerChild.rightChild = targetNode.centerChild.centerRightChild;
								targetNode.centerChild.centerChild = targetNode.centerChild.centerLeftChild;
								targetNode.centerChild.centerLeftChild = null;

								targetNode.rightChild.values = 2; //left child grows
								targetNode.centerChild.values = 2; //right childs shrinks

	    					}else if(targetNode.centerChild.isThreeNode()) {
	    						// Do 3.1
								targetNode.rightChild.value2 = targetNode.rightChild.value1;
								targetNode.rightChild.value1 = targetNode.value2;
								targetNode.value2 = targetNode.centerChild.value3;

								//takes care of target nodes grandchildren...their links
								targetNode.rightChild.centerChild = targetNode.rightChild.leftChild;
								targetNode.rightChild.leftChild = targetNode.centerChild.rightChild;
								targetNode.centerChild.rightChild = targetNode.centerChild.centerRightChild;
								targetNode.centerChild.centerChild = targetNode.centerChild.centerLeftChild;
								targetNode.centerChild.centerLeftChild = null;

								targetNode.rightChild.values = 2; //left child grows
								targetNode.centerChild.values = 2; //right childs shrinks

	    					}else {
	    						// If we are here, then the neighbooring children are two node
	    						// Do 3.2
								// WHAT DO
								// WHAT DO
								// WHAT DO
								// WHAT DO
								// WHAT DO
								// WHAT DO

	    					}
        				}
        				// Once you know that the child you are going to isn't a two node, then go
        				targetNode=targetNode.rightChild;
        			}
        		}else if(targetNode.isFourNode()) {
        			// Four Node
    				if(value<targetNode.value1) {
    					// go to left node
    					// check for Case 3.1 & 3.2
        				if(targetNode.leftChild.isTwoNode()) {
        					// You need to do 3.1 or 3.2
        					if(targetNode.centerLeftChild.isFourNode()) {
	    						// Do 3.1
								targetNode.leftChild.value2 = targetNode.value1;
								targetNode.value1 = targetNode.centerLeftChild.value1;
								targetNode.centerLeftChild.value1 = targetNode.centerLeftChild.value2;
								targetNode.centerLeftChild.value2 = targetNode.centerLeftChild.value3;

								//takes care of target nodes grandchildren...their links
								targetNode.leftChild.centerChild = targetNode.leftChild.rightChild;
								targetNode.leftChild.rightChild = targetNode.centerLeftChild.leftChild;
								targetNode.centerLeftChild.leftChild = targetNode.centerLeftChild.centerLeftChild;
								targetNode.centerLeftChild.centerChild = targetNode.centerLeftChild.centerRightChild;
								targetNode.centerLeftChild.centerRightChild = null;

								targetNode.leftChild.values = 2;
								targetNode.centerLeftChild.values = 2;

	    					}else if(targetNode.centerLeftChild.isThreeNode()) {
	    						// Do 3.1
								targetNode.leftChild.value2 = targetNode.value1;
								targetNode.value1 = targetNode.centerLeftChild.value1;
								targetNode.centerLeftChild.value1 = targetNode.centerLeftChild.value2;

								//takes care of target nodes grandchildren...their links
								targetNode.leftChild.centerChild = targetNode.leftChild.rightChild;
								targetNode.leftChild.rightChild = targetNode.centerLeftChild.leftChild;
								targetNode.centerLeftChild.leftChild = targetNode.centerLeftChild.centerChild;
								targetNode.centerLeftChild.centerChild = null;

								targetNode.leftChild.values = 2;
								targetNode.centerLeftChild.values = 1;

	    					}else {
	    						// If we are here, then the neighbooring children are two node
	    						// Do 3.2
								// WHAT DO
								// WHAT DO
								// WHAT DO
								// WHAT DO
								// WHAT DO
								// WHAT DO

	    					}
        				}
        				// Once you know that the child you are going to isn't a two node, then go
        				targetNode=targetNode.leftChild;
    				}else if(value>targetNode.value1 && value<targetNode.value2) {
    					// go to center left node
    					// check for Case 3.1 & 3.2
        				if(targetNode.centerLeftChild.isTwoNode()) {
        					// You need to do 3.1 or 3.2
        					if(targetNode.leftChild.isFourNode()) {
	    						// Do 3.1
								targetNode.centerLeftChild.value2 = targetNode.centerLeftChild.value1;
								targetNode.centerLeftChild.value1 = targetNode.value1;
								targetNode.value1 = targetNode.leftChild.value3;

								//takes care of target nodes grandchildren...their links
								targetNode.centerLeftChild.centerChild = targetNode.centerLeftChild.leftChild;
								targetNode.centerLeftChild.leftChild = targetNode.leftChild.rightChild;
								targetNode.leftChild.rightChild = targetNode.leftChild.centerRightChild;
								targetNode.leftChild.centerChild = targetNode.leftChild.centerLeftChild;
								
								targetNode.leftChild.values = 2;
								targetNode.centerLeftChild.values = 2;

	    					}else if(targetNode.leftChild.isThreeNode()) {
	    						// Do 3.1
								targetNode.centerLeftChild.value2 = targetNode.centerLeftChild.value1;
								targetNode.centerLeftChild.value1 = targetNode.value1;
								targetNode.value1 = targetNode.leftChild.value2;

								//takes care of target nodes grandchildren...their links
								targetNode.centerLeftChild.centerChild = targetNode.centerLeftChild.leftChild;
								targetNode.centerLeftChild.leftChild = targetNode.leftChild.rightChild;
								targetNode.leftChild.rightChild = targetNode.leftChild.centerChild;
								
								targetNode.leftChild.values = 1;
								targetNode.centerLeftChild.values = 2;

	    					}if(targetNode.centerRightChild.isFourNode()) {
	    						// Do 3.1
								targetNode.centerLeftChild.value2 = targetNode.value2;
								targetNode.value1 = targetNode.centerRightChild.value1;
								targetNode.centerRightChild.value1 = targetNode.centerRightChild.value2;
								targetNode.centerRightChild.value2 = targetNode.centerRightChild.value3;

								//takes care of target nodes grandchildren...their links
								targetNode.centerLeftChild.centerChild = targetNode.centerLeftChild.rightChild;
								targetNode.centerLeftChild.rightChild = targetNode.centerRightChild.leftChild;
								targetNode.centerRightChild.leftChild = targetNode.leftChild.centerLeftChild;
								targetNode.centerRightChild.centerChild = targetNode.centerRightChild.centerRightChild;
								
								targetNode.centerRightChild.values = 2;
								targetNode.centerLeftChild.values = 2;
	    					}else if(targetNode.centerRightChild.isThreeNode()) {
	    						// Do 3.1
								targetNode.centerLeftChild.value2 = targetNode.value2;
								targetNode.value1 = targetNode.centerRightChild.value1;
								targetNode.centerRightChild.value1 = targetNode.centerRightChild.value2;
								targetNode.centerRightChild.value2 = 0;

								//takes care of target nodes grandchildren...their links
								targetNode.centerLeftChild.centerChild = targetNode.centerLeftChild.rightChild;
								targetNode.centerLeftChild.rightChild = targetNode.centerRightChild.leftChild;
								targetNode.centerRightChild.leftChild = targetNode.leftChild.centerChild;
								targetNode.leftChild.centerChild = null;
								
								targetNode.centerRightChild.values = 1;
								targetNode.centerLeftChild.values = 2;

	    					}else {
	    						// If we are here, then the neighbooring children are two node
	    						// Do 3.2
								// WHAT DO
								// WHAT DO
								// WHAT DO
								// WHAT DO
								// WHAT DO
								// WHAT DO

	    					}
        				}
        				// Once you know that the child you are going to isn't a two node, then go
        				targetNode=targetNode.centerLeftChild;
    				}else if(value>targetNode.value2 && value<targetNode.value3) {
    					// got to center right node
    					// check for Case 3.1 & 3.2
        				if(targetNode.centerRightChild.isTwoNode()) {
        					// You need to do 3.1 or 3.2
        					if(targetNode.centerLeftChild.isFourNode()) {
	    						// Do 3.1
								targetNode.centerRightChild.value2 = targetNode.centerRightChild.value1;
								targetNode.centerRightChild.value1 = targetNode.value2;
								targetNode.value1 = targetNode.centerLeftChild.value3;
								targetNode.centerLeftChild.value3 = 0;

								//takes care of target nodes grandchildren...their links
								targetNode.centerRightChild.centerChild = targetNode.centerRightChild.leftChild;
								targetNode.centerRightChild.leftChild = targetNode.centerLeftChild.rightChild;
								targetNode.centerLeftChild.rightChild = targetNode.centerLeftChild.centerRightChild;
								targetNode.centerLeftChild.centerChild = targetNode.centerLeftChild.centerLeftChild;
								targetNode.centerLeftChild.centerLeftChild = null;
								targetNode.centerLeftChild.centerRightChild = null;

								targetNode.centerRightChild.values = 2;
								targetNode.centerLeftChild.values = 2;

	    					}else if(targetNode.centerLeftChild.isThreeNode()) {
	    						// Do 3.1
								targetNode.centerRightChild.value2 = targetNode.centerRightChild.value1;
								targetNode.centerRightChild.value1 = targetNode.value2;
								targetNode.value1 = targetNode.centerLeftChild.value2;
								targetNode.centerLeftChild.value2 = 0;

								//takes care of target nodes grandchildren...their links
								targetNode.centerRightChild.centerChild = targetNode.centerRightChild.leftChild;
								targetNode.centerRightChild.leftChild = targetNode.centerLeftChild.rightChild;
								targetNode.centerLeftChild.rightChild = targetNode.centerLeftChild.centerChild;
								targetNode.centerLeftChild.centerChild = null;

								targetNode.centerRightChild.values = 2;
								targetNode.centerLeftChild.values = 1;

	    					}if(targetNode.rightChild.isFourNode()) {
	    						// Do 3.1
								targetNode.centerRightChild.value2 = targetNode.value3;
								targetNode.value3 = targetNode.rightChild.value1;
								targetNode.rightChild.value1 = targetNode.rightChild.value2;
								targetNode.rightChild.value2 = targetNode.rightChild.value3;

								//takes care of target nodes grandchild...their links
								targetNode.centerRightChild.centerChild = targetNode.centerRightChild.rightChild;
								targetNode.centerRightChild.rightChild = targetNode.rightChild.leftChild;
								targetNode.rightChild.leftChild = targetNode.rightChild.centerLeftChild;
								targetNode.rightChild.centerChild = targetNode.rightChild.centerRightChild;
								targetNode.rightChild.centerRightChild = null;
								targetNode.rightChild.centerLeftChild = null;

								targetNode.centerRightChild.values = 2;
								targetNode.rightChild.values = 2;


	    					}else if(targetNode.rightChild.isThreeNode()) {
	    						// Do 3.1
								targetNode.centerRightChild.value2 = targetNode.value3;
								targetNode.value3 = targetNode.rightChild.value1;
								targetNode.rightChild.value1 = targetNode.rightChild.value2;
								targetNode.rightChild.value2 = 0;

								//takes care of target nodes grandchild...their links
								targetNode.centerRightChild.centerChild = targetNode.centerRightChild.rightChild;
								targetNode.centerRightChild.rightChild = targetNode.rightChild.leftChild;
								targetNode.rightChild.leftChild = targetNode.rightChild.centerChild;
								targetNode.rightChild.centerChild = null;

								targetNode.centerRightChild.values = 2;
								targetNode.rightChild.values = 1;

	    					}else {
	    						// If we are here, then the neighbooring children are two node
	    						// Do 3.2
								// WHAT DO
								// WHAT DO
								// WHAT DO
								// WHAT DO
								// WHAT DO
								// WHAT DO

	    					}
        				}
        				// Once you know that the child you are going to isn't a two node, then go
        				targetNode=targetNode.centerRightChild;
    				}else if(value>targetNode.value3) {
        				// go to right node
    					// check for Case 3.1 & 3.2
        				if(targetNode.rightChild.isTwoNode()) {
        					// You need to do 3.1 or 3.2
        					if(targetNode.centerRightChild.isFourNode()) {
	    						// Do 3.1
								targetNode.rightChild.value2 = targetNode.rightChild.value1;
								targetNode.rightChild.value1 = targetNode.value3;
								targetNode.value3 = targetNode.centerRightChild.value3;
								targetNode.centerRightChild.value3 = 0;

								//takes care of target nodes grandchildren...their links
								targetNode.rightChild.centerChild = targetNode.rightChild.leftChild;
								targetNode.rightChild.leftChild = targetNode.centerRightChild.rightChild;
								targetNode.centerRightChild.rightChild = targetNode.leftChild.centerRightChild;
								targetNode.centerRightChild.centerChild = targetNode.centerRightChild.centerLeftChild;
								targetNode.leftChild.centerRightChild = null;
								targetNode.leftChild.centerLeftChild = null;
								
								targetNode.rightChild.values = 2;
								targetNode.centerRightChild.values = 2;

	    					}else if(targetNode.centerRightChild.isThreeNode()) {
	    						// Do 3.1
								targetNode.rightChild.value2 = targetNode.rightChild.value1;
								targetNode.rightChild.value1 = targetNode.value3;
								targetNode.value3 = targetNode.centerRightChild.value2;
								targetNode.centerRightChild.value2 = 0;

								//takes care of target nodes grandchildren...their links
								targetNode.rightChild.centerChild = targetNode.rightChild.leftChild;
								targetNode.rightChild.leftChild = targetNode.centerRightChild.rightChild;
								targetNode.centerRightChild.rightChild = targetNode.leftChild.centerRightChild;
								targetNode.centerRightChild.centerChild = targetNode.centerRightChild.centerLeftChild;
								targetNode.leftChild.centerRightChild = null;
								targetNode.leftChild.centerLeftChild = null;
								
								targetNode.rightChild.values = 2;
								targetNode.centerRightChild.values = 1;
	    					}else {
	    						// If we are here, then the neighbooring children are two node
	    						// Do 3.2
								// WHAT DO
								// WHAT DO
								// WHAT DO
								// WHAT DO
								// WHAT DO
								// WHAT DO
								
	    					}
        				}
        				// Once you know that the child you are going to isn't a two node, then go
        				targetNode=targetNode.rightChild;
        			}
        		}
    		}
    	}
    	// if targetNode==null, then we did not find the value, so return false
    	//	return false;
    	
    	
    	
    	
    	
    	
    	// ALL THE CODE BELOW WE DON'T NEED
    	// HOWEVER
    	// YOU CAN REUSE THE CODE BECAUSE THE CODE WE MADE BEFORE AND THE CODE WE NEED TO MAKE NOW
    	// ARE ALMOST IDENTICAL, JUST OUT OF ORDER
		TwoFourTreeItem temp = root;
		while(temp!=null){
			
    		if(nodeHasValue(temp, value)) {
				break;
			}
        	// Node does not have values go to the next node
    		if(temp.isTwoNode()) {
    			if(value>temp.value1) temp = temp.rightChild;// go to right node
    			else if(value<temp.value1) temp = temp.leftChild;// go to left node
    		}else if(temp.isThreeNode()) {
    			if(value<temp.value1) temp = temp.leftChild;// go to left node
    			else if(value>temp.value1 && value<temp.value2) temp = temp.centerChild;// go to center node
    			else if(value>temp.value2) temp = temp.rightChild;// go to right node
    		}else if(temp.isFourNode()) {
    			// Four Node
				if(value<temp.value1) temp = temp.leftChild;// go to left node
    			else if(value>temp.value1 && value<temp.value2) temp = temp.centerLeftChild;// go to center node
				else if(value>temp.value2 && value<temp.value3) temp = temp.centerRightChild;
    			else if(value>temp.value3) temp = temp.rightChild;// go to right node
    		}
		}
		
		if(temp == null){
			return false;
		}
		
		TwoFourTreeItem deleteTarget = temp;
		TwoFourTreeItem targetParent = temp.parent;

		if(!deleteTarget.isLeaf){ // this makes it a leaf...
			int swapValue = 0;
			if(deleteTarget.isTwoNode()){
				TwoFourTreeItem successor = nodeSuccesor(deleteTarget.rightChild);
				swapValue = successor.value1;
				successor.value1 = deleteTarget.value1;
				deleteTarget.value1 = swapValue;
				deleteTarget = successor;
				deleteTarget.parent = successor.parent;

			}else if(deleteTarget.isThreeNode()){
				if (deleteTarget.value1 == value){
					TwoFourTreeItem successor = nodeSuccesor(deleteTarget.centerChild);
					swapValue = successor.value1;
					successor.value1 = deleteTarget.value1;
					deleteTarget.value1 = swapValue;
					deleteTarget = successor;
					targetParent = successor.parent;

				} else{
					TwoFourTreeItem successor = nodeSuccesor(deleteTarget.rightChild);
					swapValue = successor.value1;
					successor.value1 = deleteTarget.value2;
					deleteTarget.value2 = swapValue;
					deleteTarget = successor;
					targetParent = successor.parent;

				}
				
			} else if(deleteTarget.isFourNode()){
				if (deleteTarget.value1 == value){
					TwoFourTreeItem successor = nodeSuccesor(deleteTarget.centerChild);
					swapValue = successor.value1;
					successor.value1 = deleteTarget.value1;
					deleteTarget.value1 = swapValue;
					deleteTarget = successor;
					targetParent = successor.parent;

				} else if (deleteTarget.value2 == value){
					TwoFourTreeItem successor = nodeSuccesor(deleteTarget.centerLeftChild);
					swapValue = successor.value1;
					successor.value1 = deleteTarget.value2;
					deleteTarget.value2 = swapValue;
					deleteTarget = successor;
					targetParent = successor.parent;

				} else {
					TwoFourTreeItem successor = nodeSuccesor(deleteTarget.rightChild);
					swapValue = successor.value1;
					successor.value1 = deleteTarget.value2;
					deleteTarget.value2 = swapValue;
					deleteTarget = successor;
					targetParent = successor.parent;

				}
			}
		}

		if(deleteTarget.isTwoNode()){
			// Check case 2
			if(targetParent.isThreeNode()){
				// Check case 1
				if(deleteTarget == targetParent.leftChild) {
					// Checking if case 1 is possible
					if(targetParent.centerChild.isThreeNode()){
						// Do Case 1
						deleteTarget.value1 = targetParent.value1;
						targetParent.value1 = targetParent.centerChild.value1;
						targetParent.centerChild.value1 = targetParent.centerChild.value2;
						targetParent.leftChild.values = 1;
						targetParent.centerChild.values = 1;

					}else if(targetParent.centerChild.isFourNode()) {
						// Do Case 1
						deleteTarget.value1 = targetParent.value1;
						targetParent.value1 = targetParent.centerChild.value1;
						targetParent.centerChild.value1 = targetParent.centerChild.value2;
						targetParent.centerChild.value2 = targetParent.centerChild.value3;
						targetParent.leftChild.values = 1;
						targetParent.centerChild.values = 2;

					}else if(targetParent.centerChild.isTwoNode()) {
						// Case 3
						deleteTarget.value1 = targetParent.value1;
						deleteTarget.value2 = targetParent.centerChild.value1;
						targetParent.value1 = targetParent.value2;
						targetParent.value2 = 0;
						targetParent.centerChild = null;
						targetParent.values = 1;
						deleteTarget.values = 2;

					}	
				} else if(deleteTarget == targetParent.rightChild){			//check this again
					if(targetParent.centerChild.isThreeNode()){
						// Do Case 1
						deleteTarget.value1 = targetParent.value2;
						targetParent.value2 = targetParent.centerChild.value2;
						targetParent.rightChild.values = 1;
						targetParent.centerChild.values = 1;
					}else if(targetParent.centerChild.isFourNode()) {
						// Do Case 1
						deleteTarget.value1 = targetParent.value2;
						targetParent.value2 = targetParent.centerChild.value3;
						targetParent.rightChild.values = 1;
						targetParent.centerChild.values = 2;
					}else if(targetParent.centerChild.isTwoNode()) {
						// Case 3
						deleteTarget.value2 = targetParent.value2;
						deleteTarget.value1 = targetParent.centerChild.value1;
						targetParent.value2 = 0;
						targetParent.centerChild = null;
						targetParent.values = 1;
						deleteTarget.values = 2;
					}
				} else if (deleteTarget == targetParent.centerChild){
					// Checking if case 1 is possible
					if(targetParent.leftChild.isThreeNode()){
						deleteTarget.value1 = targetParent.value1;
						targetParent.value1 = targetParent.leftChild.value2;
						targetParent.leftChild.value2 = 0;
						targetParent.leftChild.values = 1;
					}else if(targetParent.leftChild.isFourNode()) {
						// Do Case 1
						deleteTarget.value1 = targetParent.value1;
						targetParent.value1 = targetParent.leftChild.value3;
						targetParent.leftChild.value3 = 0;
						targetParent.leftChild.values = 2;
					}else if(targetParent.rightChild.isThreeNode()){
						// Do Case 1
						deleteTarget.value1 = targetParent.value2;
						targetParent.value2 = targetParent.rightChild.value1;
						targetParent.rightChild.value1 = targetParent.rightChild.value2;
						targetParent.rightChild.values = 1;
					}else if(targetParent.rightChild.isFourNode()) {
						// Do Case 1
						deleteTarget.value1 = targetParent.value2;
						targetParent.value2 = targetParent.rightChild.value1;
						targetParent.rightChild.value1 = targetParent.rightChild.value2;
						targetParent.rightChild.value2 = targetParent.rightChild.value3;
						targetParent.rightChild.values = 2;
					}else {
						// Do Case 3
						// Both left and right child are two node
						// Shouldn't matter if you merge with left or right child
						targetParent.leftChild.value2 = targetParent.value1;
						targetParent.value1 = targetParent.value2;
						targetParent.value2 = 0;
						targetParent.centerChild = null;
						targetParent.values = 1;

					}
					
				}
			} else if(targetParent.isFourNode()){
				//if broken...this is probably why
				if(deleteTarget == targetParent.leftChild) {
					// Checking if case 1 is possible
					if(targetParent.centerLeftChild.isThreeNode()){
						// Do Case 1
						deleteTarget.value1 = targetParent.value1;
						targetParent.value1 = targetParent.centerLeftChild.value1;
						targetParent.centerLeftChild.value1 = targetParent.centerLeftChild.value2;
						targetParent.centerLeftChild.value2 = 0;
						targetParent.leftChild.values = 1;
						targetParent.centerLeftChild.values = 1;

					}else if(targetParent.centerLeftChild.isFourNode()) {
						// Do Case 1
						deleteTarget.value1 = targetParent.value1;
						targetParent.value1 = targetParent.centerLeftChild.value1;
						targetParent.centerLeftChild.value1 = targetParent.centerLeftChild.value2;
						targetParent.centerLeftChild.value2 = targetParent.centerLeftChild.value3;
						targetParent.centerLeftChild.value3 = 0;
						targetParent.leftChild.values = 1;
						targetParent.centerLeftChild.values = 2;

					}else if(targetParent.centerLeftChild.isTwoNode()) {
						// Case 3
						deleteTarget.value1 = targetParent.value1;
						deleteTarget.value2 = targetParent.centerLeftChild.value1;
						targetParent.value1 = targetParent.value2;
						targetParent.value2 = targetParent.value3;
						targetParent.value3 = 0;
						targetParent.centerChild = targetParent.centerRightChild;
						targetParent.centerLeftChild = null;
						targetParent.centerRightChild = null;
						targetParent.values = 2;
						deleteTarget.values = 2;

					}
				} else if(deleteTarget == targetParent.rightChild){
					if(targetParent.centerRightChild.isThreeNode()){
						// Do Case 1
						deleteTarget.value1 = targetParent.value3;
						targetParent.value3 = targetParent.centerRightChild.value2;
						targetParent.centerRightChild.values = 1;

					}else if(targetParent.centerRightChild.isFourNode()) {
						// Do Case 1
						deleteTarget.value1 = targetParent.value3;
						targetParent.value3 = targetParent.centerRightChild.value3;
						targetParent.centerRightChild.values = 2;

					}else if(targetParent.centerRightChild.isTwoNode()) {
						// Case 3
						deleteTarget.value2 = targetParent.value3;
						deleteTarget.value1 = targetParent.centerRightChild.value1;
						targetParent.centerRightChild.values = 1;
						targetParent.value3 = 0;
						targetParent.centerChild = targetParent.centerLeftChild;
						targetParent.centerLeftChild = null;
						targetParent.centerRightChild = null;
						targetParent.values = 2;
						deleteTarget.values = 2;

					}
				} else if (deleteTarget == targetParent.centerLeftChild){
					// Checking if case 1 is possible
					if(targetParent.leftChild.isThreeNode()){
						// Do Case 1
						deleteTarget.value1 = targetParent.value1;
						targetParent.value1 = targetParent.leftChild.value2;
						targetParent.leftChild.value2 = 0;
						targetParent.leftChild.values = 1;

					}else if(targetParent.leftChild.isFourNode()) {
						// Do Case 1
						deleteTarget.value1 = targetParent.value1;
						targetParent.value1 = targetParent.leftChild.value3;
						targetParent.leftChild.value3 = 0;
						targetParent.leftChild.values = 2;

					}else if(targetParent.centerRightChild.isThreeNode()){
						// Do Case 1
						deleteTarget.value1 = targetParent.value2;
						targetParent.value2 = targetParent.centerRightChild.value1;
						targetParent.centerRightChild.value1 = targetParent.centerRightChild.value2;
						targetParent.centerRightChild.value2 = 0;
						targetParent.centerRightChild.values = 1;

					}else if(targetParent.centerRightChild.isFourNode()) {
						// Do Case 1
						deleteTarget.value1 = targetParent.value2;
						targetParent.value2 = targetParent.centerRightChild.value1;
						targetParent.centerRightChild.value1 = targetParent.centerRightChild.value2;
						targetParent.centerRightChild.value2 = targetParent.centerRightChild.value3;
						targetParent.centerRightChild.value3 = 0;
						targetParent.centerRightChild.values = 2;

					}else {
						// Do Case 3
						// Both left and right child are two node
						// Shouldn't matter if you merge with left or right child
						deleteTarget.value1 = targetParent.value2;
						deleteTarget.value2 = targetParent.centerRightChild.value1;
						targetParent.value2 = targetParent.value3;
						targetParent.value3 = 0;
						targetParent.centerChild = targetParent.centerLeftChild;
						targetParent.centerLeftChild = null;
						targetParent.centerRightChild = null;
						targetParent.values = 2;
						targetParent.centerChild.values = 2;

					}
				} else if (deleteTarget == targetParent.centerRightChild){
					// Checking if case 1 is possible
					if(targetParent.rightChild.isThreeNode()){
						// Do Case 1
						deleteTarget.value1 = targetParent.value3;
						targetParent.value3 = targetParent.rightChild.value1;
						targetParent.rightChild.value1 = targetParent.rightChild.value2;
						targetParent.leftChild.value2 = 0;
						targetParent.leftChild.values = 1;

					}else if(targetParent.rightChild.isFourNode()) {
						// Do Case 1
						deleteTarget.value1 = targetParent.value3;
						targetParent.value3 = targetParent.rightChild.value1;
						targetParent.rightChild.value1 = targetParent.rightChild.value2;
						targetParent.rightChild.value2 = targetParent.rightChild.value3;
						targetParent.leftChild.value3 = 0;
						targetParent.leftChild.values = 2;

					}else if(targetParent.centerLeftChild.isThreeNode()){
						// Do Case 1
						deleteTarget.value1 = targetParent.value2;
						targetParent.value2 = targetParent.centerLeftChild.value2;
						targetParent.centerLeftChild.value2 = 0;
						targetParent.centerLeftChild.values = 1;

					}else if(targetParent.centerLeftChild.isFourNode()) {
						// Do Case 1
						deleteTarget.value1 = targetParent.value2;
						targetParent.value2 = targetParent.centerLeftChild.value3;
						targetParent.centerLeftChild.value3 = 0;
						targetParent.centerLeftChild.values = 2;

					}else {
						// Do Case 3
						// Both left and right child are two node
						// Shouldn't matter if you merge with left or right child
						deleteTarget.value1 = targetParent.value3;
						deleteTarget.value2 = targetParent.centerRightChild.value1;
						targetParent.value3 = 0;
						targetParent.centerChild = targetParent.centerLeftChild;
						targetParent.rightChild = targetParent.centerRightChild;
						targetParent.centerLeftChild = null;
						targetParent.centerRightChild = null;
						targetParent.values = 2;
						targetParent.rightChild.values = 2;

					}	
				}
			} else if(targetParent.isTwoNode()) {
				if(deleteTarget == targetParent.leftChild) {
					// Checking if case 1 is possible
					if(targetParent.rightChild.isThreeNode()){
						// Do Case 1
						deleteTarget.value1 = targetParent.value1;
						targetParent.value1 = targetParent.rightChild.value1;
						targetParent.rightChild.value1 = targetParent.rightChild.value2;
						targetParent.rightChild.values = 1;

					}else if(targetParent.rightChild.isFourNode()) {
						// Do Case 1
						deleteTarget.value1 = targetParent.value1;
						targetParent.value1 = targetParent.rightChild.value1;
						targetParent.rightChild.value1 = targetParent.rightChild.value2;
						targetParent.rightChild.value2 = targetParent.rightChild.value3;
						targetParent.rightChild.values = 2;
					}else {
						// Case 2
						targetParent.value2 = targetParent.rightChild.value1;
						targetParent.values = 2;
						System.out.println("Debug info >>> " + deleteTarget);
						//deleteTarget = null;
						targetParent.rightChild = null;
						targetParent.leftChild = null;
						System.out.println("Debug info >>> " + targetParent);
						/*
						if(targetParent.isRoot()){
							targetParent.value2 = targetParent.rightChild.value1;
							targetParent.values = 2;
							deleteTarget = null;
							targetParent.rightChild = null;
						}
						this is special*/
					}
				} else if(deleteTarget == targetParent.rightChild){
					if(targetParent.leftChild.isThreeNode()){
						// Do Case 1
						deleteTarget.value1 = targetParent.value1;
						targetParent.value1 = targetParent.leftChild.value2;
						targetParent.leftChild.values = 1;
						
					}else if(targetParent.leftChild.isFourNode()) {
						// Do Case 1
						deleteTarget.value1 = targetParent.value1;
						targetParent.value1 = targetParent.leftChild.value3;
						targetParent.leftChild.values = 2;
					}else {
						// Case 2
						targetParent.value2 = targetParent.value1;
						targetParent.value1 = targetParent.leftChild.value1;
						targetParent.values = 2;
						deleteTarget = null;
						targetParent.leftChild = null;
						targetParent.rightChild = null;
					}
				}
			}
			
		}
		
		// AFTER DOING THE CASES ABOVE, IT IS GARENTEED THAT YOUR VALUE THAT YOU ARE DELETING
		// IS IN A THREE OR FOUR NODE
		
		if(deleteTarget.isThreeNode()){
			
			if(deleteTarget.value1 == value){
				deleteTarget.value1 = deleteTarget.value2;
				deleteTarget.value2 = 0;
				deleteTarget.values = 1;
				temp = deleteTarget;

			}else{
				deleteTarget.value2 = 0;
				deleteTarget.values = 1;
				temp = deleteTarget;

			}
		}else if(deleteTarget.isFourNode()){
			if(deleteTarget.value1 == value){ //left value in four node is to be deleted
				deleteTarget.value1 = deleteTarget.value2;
				deleteTarget.value2 = deleteTarget.value3;
				deleteTarget.value3 = 0;
				deleteTarget.values = 2;
				temp = deleteTarget;

			} else if(deleteTarget.value2 == value){ //middle value in four node is to be deleted
				deleteTarget.value2 = deleteTarget.value3;
				deleteTarget.value3 = 0;
				deleteTarget.values = 2;
				temp = deleteTarget;

			} else { //right value in four node is to be deleted (default case)
				deleteTarget.value3 = 0;
				deleteTarget.values = 2;
				temp = deleteTarget;

			} 
		}
		
        return true;
    }

    public void printInOrder() {
        if(root != null) root.printInOrder(0);
    }

    public BrokenTwoFourTree() {
    	this.root=null;
    }
    
    public void printAllInfo() {
    	if(root==null)return;
    	TwoFourTreeItem temp = root;
    	printAllInfoR(temp);
    }
    
    public void printAllInfoR(TwoFourTreeItem temp) {
    	if(temp==null)return;
    	System.out.println(temp+"\n");
    	printAllInfoR(temp.leftChild);
    	printAllInfoR(temp.centerLeftChild);
    	printAllInfoR(temp.centerChild);
    	printAllInfoR(temp.centerRightChild);
    	printAllInfoR(temp.rightChild);
    }
}



