# Red-Black-Tree (Single Pass)


1) I have used BST(repository code), to find the parent of current node.

2) While adding any node into RBT,I am checking the tree from top and reshuffling it at any point of violation. When finally entire tree is balanced, I add the new node into the balanced tree.

3) While removing a node from RBT, I remove the node if found in tree and then traverse and reshuffle the tree upwards till no violation in the tree is found.

4) I have used a header node whose right child is the root of the RBT.

5) Value of header node is negative infinity.

6) To check the code, you can select any option from 1-3. 
   Option 1 : To add a node
   Option 2 : To remove a node
   Option 3 : To print the in-order and level order of the current RBT
   All other options : To exit from the code.
