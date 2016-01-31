/**
 * 
 */
package org.sobngwi;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Alain Narcisse SOBNGWI
 *
 */
public class TyrantMapTest {
	
	TyrantMap tyrant ;
	private byte[] KEY;
	private byte[] KEY1;
	private byte[] VALUE;
	private byte[] VALUE1;

	@Before
	public void setUp() throws Exception {
		tyrant = new TyrantMap() ;
		tyrant.openConnection();
		KEY = new byte[]{'k','e','y'};
		VALUE = new byte[]{'v','a','l', 'u', 'e'} ;
		KEY1 = new byte[]{'k','e','y', '1'};
		VALUE1 = new byte[]{'v','a','l', 'u', 'e', '1'};
	}

	@After
	public void tearDown() throws Exception {
		tyrant.clear();
		tyrant.closeConnection();
	}

	@Test
	public void getRetrieveWhatWasPut() throws IOException {

	
	    tyrant.put(KEY , VALUE);
	    
	    assertArrayEquals(VALUE, tyrant.get(KEY));
	}
	
	@Test
	public void getReturnNullIfKeyNotFound() throws IOException {
		
	    
		assertNull(tyrant.get(KEY));
	}
	
	@Test
	public void clearDeletesAllValues() throws IOException {


	    tyrant.put(KEY , VALUE);
	    assertArrayEquals(VALUE, tyrant.get(KEY));
	    tyrant.clear();
	    assertNull(tyrant.get(KEY));
	}
	
	@Test
	public void removeRemovesKey() throws IOException {

	    tyrant.put(KEY , VALUE);
	    tyrant.put(KEY1 , VALUE1);
	    assertArrayEquals(VALUE, tyrant.get(KEY));
	    assertArrayEquals(VALUE1, tyrant.get(KEY1));
	    tyrant.remove(VALUE);
	    assertNull(tyrant.get(VALUE));
	}
	
	@Test
	public void removeEmptyKeyDoesNothing() throws IOException {
	    tyrant.remove(KEY);
	    assertNull(tyrant.get(KEY));
	}
	
	@Test(expected = IllegalArgumentException.class )
	public void removeNullKeyThrowsAnIllegaArgumentException() throws IOException {
	    tyrant.remove(null);
	    fail();
	}
	
	@Test
	public void removesameElementKeyTwiceDoesNothing() throws IOException {
		tyrant.put(KEY , VALUE);
		assertNotNull(tyrant.get(KEY));
		tyrant.remove(KEY);
		assertNull(tyrant.get(KEY));
		tyrant.remove(KEY);
	    assertNull(tyrant.get(KEY));
	}
	
	@Test
	public void removeOneElementTyrant() throws IOException {
		tyrant.put(KEY , VALUE);
		tyrant.put(KEY1 , VALUE1);
		assertEquals(2, tyrant.size());		
		tyrant.remove(KEY);
		
		assertNull(tyrant.get(KEY));
		assertNotNull(tyrant.get(KEY1));
		assertEquals(1, tyrant.size());
		
	}
	
	@Test
	public void emptyMapSizeIsZero() throws IOException {
	    assertEquals(0, tyrant.size());
	}
	
	@Test
	public void oneElementMapSizeIsOne() throws IOException {

	    tyrant.put(KEY , VALUE);
	    assertEquals(1, tyrant.size());
	}
	
	@SuppressWarnings("unused")
	@Test
	public void iterateOverAnEmptyTyrant() throws IOException {
		for (byte[] each : tyrant) {
			fail() ;
			}
	}
		
	@Test
	public void iterateOverAnOneElementTyrant() throws IOException {
			int count =0 ;
		    tyrant.put(KEY , VALUE);
			for (byte[] each : tyrant) {
				assertArrayEquals(each, tyrant.get(KEY));
				count++;
				
			}
			assertEquals(1, count);
	    
	}
		
	@Test
	public void resetAndGetEmptyTyrant() throws IOException {
			assertNull(tyrant.getNextKey());
	}
	
	@Test
	public void resetAndGetOneElementTyrant() throws IOException {
		tyrant.put(KEY , VALUE);	
		assertArrayEquals(VALUE, tyrant.get(KEY));
	}
	
	@Test
	public void resetAndGetTwoElementTyrant() throws IOException {
		tyrant.put(KEY ,  VALUE);
		tyrant.put(KEY1 , VALUE1);
		int count =0 ;

		for (byte[] each : tyrant) {
			if ( count == 0)
				assertArrayEquals(each, tyrant.get(KEY));
			else 
				assertArrayEquals(each, tyrant.get(KEY1));
			count++;
			
		}
		assertEquals(2, count);

	}
	
	
	
	

}
