package com.github.diegopacheco.sandbox.java.consistent.hash.test;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.github.diegopacheco.sandbox.java.consistent.hash.ConsistentHash;
import com.github.diegopacheco.sandbox.java.consistent.hash.SimpleHashFunction;

public class ConsistentHashTest {
	
	@Test
	public void simpleConsistentHashingTest(){
		
		ConsistentHash<String> cs = new ConsistentHash<>(new SimpleHashFunction(), 1, Arrays.asList(new String[]{"node1","node2","node3"}));
		Assert.assertNotNull(cs);
		
		String result = cs.get("node10");
		System.out.println("Key node10 == " + result);
		
		result = cs.get("node20");
		System.out.println("key node20 == " + result);
		
		result = cs.get("node30");
		System.out.println("Key node30 == " + result);
		
		result = cs.get("100");
		System.out.println("key 100 = " + result);
		
		result = cs.get("1");
		System.out.println("key: 1 = " + result);
	}
	
}
