package com.nowcoder.community;

//单链表
class ListNode {
    int val;
    ListNode next;
    ListNode(){}
    ListNode(int val) {
        this.val=val;
    }
    ListNode(int val, ListNode next) {
        this.val = val; this.next = next;
    }
}
class MyLinkedList {
    //size存储链表元素的个数
    int size;
    //虚拟头结点
    ListNode head;

    //初始化链表
    public MyLinkedList() {
        size = 0;
        head = new ListNode(0);
    }

    //获取第index个节点的数值
    public int get(int index) {
        //如果index非法，返回-1
        if (index < 0 || index >= size) {
            return -1;
        }
        ListNode currentNode = head;
        //包含一个虚拟头节点，所以查找第 index+1 个节点
        for (int i = 0; i <= index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.val;
    }

    //在链表最前面插入一个节点
    public void addAtHead(int val) {
        addAtIndex(0, val);
    }

    //在链表的最后插入一个节点
    public void addAtTail(int val) {
        addAtIndex(size, val);
    }

    // 在第 index 个节点之前插入一个新节点，例如index为0，那么新插入的节点为链表的新头节点。
    // 如果 index 等于链表的长度，则说明是新插入的节点为链表的尾结点
    // 如果 index 大于链表的长度，则返回空
    public void addAtIndex(int index, int val) {
        if (index > size) {
            return;
        }
        if (index < 0) {
            index = 0;
        }
        size++;
        //找到要插入节点的前驱
        ListNode pred = head;
        for (int i = 0; i < index; i++) {
            pred = pred.next;
        }
        ListNode toAdd = new ListNode(val);
        toAdd.next = pred.next;
        pred.next = toAdd;
    }

    //删除第index个节点
    public void deleteAtIndex(int index) {
        if (index < 0 || index >= size) {
            return;
        }
        size--;
        ListNode pred = head;
        for (int i = 0; i < index; i++) {
            pred = pred.next;
        }
        pred.next = pred.next.next;
    }

    public ListNode swapPairs(ListNode head) {

        if(head == null){
            return null;
        }
        if(head.next ==  null){
            return head;
        }
        ListNode newHead = head.next;
        ListNode pre = new ListNode(-1,head);
        ListNode cur = head;
        ListNode last = null;

        while(last!=null && cur != null ){
            last = cur.next;
            cur.next = last.next;
            last.next = cur;
            pre.next=last;
            pre = cur;
            cur = cur.next;
        }
        return newHead;
    }

    public void printLink (ListNode head) {
        ListNode cur = this.head.next;
        while (cur != null) {

            System.out.println(cur.val);
            cur = cur.next;
        }
        System.out.println("------------------------");
    }
}






/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */


public class testLink {
    public static void main(String[] args) {
        MyLinkedList linkedList = new MyLinkedList();
        linkedList.addAtHead(1);
        linkedList.addAtTail(2);
        linkedList.addAtTail(3);
        linkedList.addAtTail(4);
        ListNode newHead =  linkedList.swapPairs(linkedList.head);
        linkedList.printLink(linkedList.head);
        linkedList.printLink(newHead);
        int[] a = new int[30];

//        linkedList.addAtIndex(3,0);
//        linkedList.printLink();
//        linkedList.deleteAtIndex(2);  //现在链表是1-> 3
//        linkedList.printLink();
//        linkedList.addAtHead(6);
//        linkedList.printLink();
//        linkedList.addAtTail(4);
//        linkedList.printLink();
//        System.out.println(linkedList.get(4));
        //linkedList.printLink();

    }

}
