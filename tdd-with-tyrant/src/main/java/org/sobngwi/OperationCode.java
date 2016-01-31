/**
 * 
 */
package org.sobngwi;

/**
 * @author Alain Narcisse SOBNGWI
 *
 */
public enum OperationCode {
	 GET_OPERATION  (0xC30),
	 OPERATION_PREFIX ( 0xC8 ),
	 PUT_OPERATION (0xC10),
	 VANISH_OPERATION (0xC72),
	 REMOVE_OPERATION (0xC20),
	 SIZE_OPERATION   (0xC80),
	 RESET_OPERATION  ( 0xC50 ),
	 NEXTKEY_OPERATION (0xC51 );
	 
	 private int code ;
	 OperationCode( int code) {
		this.code = code;
	 }
	public int getCode() {
		return code;
	}
	
}
