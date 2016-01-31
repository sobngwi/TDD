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

	@Before
	public void setUp() throws Exception {
		tyrant = new TyrantMap() ;
		tyrant.openConnection();
	}

	@After
	public void tearDown() throws Exception {
		tyrant.clear();
		tyrant.closeConnection();
	}

	@Test
	public void getRetrieveWhatWasPut() throws IOException {

		byte[] key = new byte[]{'k','e','y'};
		byte[] value = new byte[]{'v','a','l', 'u', 'e'};
	    tyrant.put(key , value);
	    
	    assertArrayEquals(value, tyrant.get(key));
	}
	
	@Test
	public void getReturnNullIfKeyNotFound() throws IOException {
		
		byte[] key = new byte[]{'k','e','y'};
	    
		assertNull(tyrant.get(key));
	}
	
	@Test
	public void clearDeletesAllValues() throws IOException {

		byte[] key = new byte[]{'k','e','y'};
		byte[] value = new byte[]{'v','a','l', 'u', 'e'};
	    tyrant.put(key , value);
	    assertArrayEquals(value, tyrant.get(key));
	    tyrant.clear();
	    assertNull(tyrant.get(key));
	}
	
	@Test
	public void removeRemovesKey() throws IOException {

		byte[] key = new byte[]{'k','e','y'};
		byte[] value = new byte[]{'v','a','l', 'u', 'e'};
		byte[] key1 = new byte[]{'k','e','y', '1'};
		byte[] value1 = new byte[]{'v','a','l', 'u', 'e', '1'};
	    tyrant.put(key , value);
	    tyrant.put(key1 , value1);
	    assertArrayEquals(value, tyrant.get(key));
	    assertArrayEquals(value1, tyrant.get(key1));
	    tyrant.remove(key);
	    assertNull(tyrant.get(key));
	}
	
	@Test
	public void removeEmptyKeyDoesNothing() throws IOException {

		byte[] key = new byte[]{'k','e','y'};
	    tyrant.remove(key);
	    assertNull(tyrant.get(key));
	}
	
	@Test
	public void emptyMapSizeIsZero() throws IOException {

	    assertEquals(0, tyrant.size());
	}
	
	@Test
	public void oneElementMapSizeIsOne() throws IOException {
		byte[] key = new byte[]{'k','e','y'};
		byte[] value = new byte[]{'v','a','l', 'u', 'e'};
	    tyrant.put(key , value);
	    assertEquals(1, tyrant.size());
	}
	
	@Test
	public void iterateOverAnEmptyTyrant() throws IOException {
		for (byte[] each : tyrant) {
			fail() ;
		}
	    
	}
	
	

}
