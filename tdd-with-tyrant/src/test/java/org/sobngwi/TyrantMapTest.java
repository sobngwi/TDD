/**
 * 
 */
package org.sobngwi;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;

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

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		tyrant = new TyrantMap() ;
		tyrant.openConnection();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		tyrant.clear();
		tyrant.closeConnection();
	}

	@Test
	public void getRetrieveWhatWasPut() throws IOException {
		//Connection open with the tyrant map.

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
		//Connection open with the tyrant map.

		byte[] key = new byte[]{'k','e','y'};
		byte[] value = new byte[]{'v','a','l', 'u', 'e'};
	    tyrant.put(key , value);
	    assertArrayEquals(value, tyrant.get(key));
	    tyrant.clear();
	    assertNull(tyrant.get(key));
	}

}
