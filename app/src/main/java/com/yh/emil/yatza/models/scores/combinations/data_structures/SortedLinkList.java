package com.yh.emil.yatza.models.scores.combinations.data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedLinkList<E extends Comparable<E>> implements Iterable<E> {

	private Node first;
	private int size;

	public static <T extends Comparable<T>> SortedLinkList parseArray(T[] unsortedArray) {

		SortedLinkList<T> sortedList = new SortedLinkList<T>();
		for (T element : unsortedArray) {
			sortedList.add(element);
		}

		return sortedList;
	}

	public SortedLinkList() {
		first = null;
		size = 0;
	}

	public void add(E obj) {

		Node newNode = new Node(obj);

		size++;

		if (first == null || obj.compareTo(first.obj) < 0) {
			newNode.next = first;
			first = newNode;
		} else {

			Node current = first;
			Node previous = first;

			while (true) {
				previous = current;
				current = current.next;

				if (current == null || obj.compareTo(current.obj) < 0) {
					previous.next = newNode;
					newNode.next = current;
					return;
				}
			}
		}
	}

	public void remove(E obj) {

		if (first == null) {
			throw new NullPointerException("List is empty!");
		}

		Node previous = first;
		Node current = first;

		while (current != null) {

			if (current.obj.equals(obj)) {

				if (first == current) {
					first = first.next;
				} else {
					previous.next = current.next;
				}
				size--;
				return;
			}

			previous = current;
			current = current.next;
		}

	}

	public int size() {
		return size;
	}

	@Override
	public Iterator<E> iterator() {
		return new LLIterator();
	}

	private class LLIterator implements Iterator<E> {

		Node nextNode;

		private LLIterator() {
			nextNode = first;
		}

		@Override
		public boolean hasNext() {
			return nextNode != null;
		}

		@Override
		public E next() {
			
			if (nextNode == null) {
				throw new NoSuchElementException("Trying to access null element!");
			}
			
			Node currentNode = nextNode;
			nextNode = nextNode.next;
			
			return currentNode.obj;
		}

		@Override
		public void remove() {}
	}

	private class Node {

		private Node next;
		private E obj;

		private Node(E obj) {
			this.obj = obj;
			next = null;
		}
	}
}
