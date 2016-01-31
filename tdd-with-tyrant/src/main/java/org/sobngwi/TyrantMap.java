/**
 * 
 */
package org.sobngwi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;

/**
 * @author Alain Narcisse SOBNGWI
 *
 */
public class TyrantMap implements Iterable <byte[]>{

	private static final int GET_OPERATION = 0xC30;
	private static final int OPERATION_PREFIX = 0xC8;
	private static final int PUT_OPERATION = 0xC10;
	private static final int VANISH_OPERATION = 0xC72;
	private static final int REMOVE_OPERATION = 0xC20;
	private static final int SIZE_OPERATION = 0xC80;
	private static final int RESET_OPERATION = 0xC50;
	private static final int NEXTKEY_OPERATION = 0xC51;
	
	private Socket socket;
	private DataOutputStream writer;
	private DataInputStream reader;


	public void openConnection() throws UnknownHostException, IOException {
		socket = new Socket ( "localhost" , 1978);
		writer = new DataOutputStream(socket.getOutputStream());
		reader = new DataInputStream(socket.getInputStream());
	}

	/**
	 * @throws IOException 
	 * 
	 */
	public void closeConnection() throws IOException {
		socket.close();
	}
	
	public void put( byte[] key, byte[] value) throws  IOException {

		writer.write(OPERATION_PREFIX);
		writer.write(PUT_OPERATION);
		
		writer.writeInt(key.length);
		writer.writeInt(value.length);

		writer.write(key);
		writer.write(value);
		int status = reader.read();
		if ( status != 0 ) {
			throw new RuntimeException(" PUT : insertion Failed ");
		}
		return ;
	}

	public byte[] get(byte[] key) throws IOException {
		
		writer.write(OPERATION_PREFIX);
		writer.write(GET_OPERATION);
		
		writer.writeInt(key.length);
		writer.write(key);

		int status = reader.read();
		if ( status == 1 ) 
			return null ;
		if ( status != 0 ) 
			throw new RuntimeException(" READ : insertion Failed ");
		
		int length = reader.readInt();
		byte[] results= new  byte[length] ;
		reader.read(results) ; // TODO read longer values
		return results;
	}
	
	public void clear() throws IOException {
		writer.write(OPERATION_PREFIX);
		writer.write(VANISH_OPERATION);

		int status = reader.read();
		if ( status != 0 ) {
			throw new RuntimeException(" CLEAR : insertion Failed ");
		}
	}
	
	public void remove(byte[] key) throws IOException {
		writer.write(OPERATION_PREFIX);
		writer.write(REMOVE_OPERATION);
		
		writer.writeInt(key.length);
		writer.write(key);

		int status = reader.read();
		if ( status == 1 ) 
			return ;
		if ( status != 0 ) 
			throw new RuntimeException(" READ : insertion Failed ");
		
	}


	public long size() throws IOException {
		writer.write(OPERATION_PREFIX);
		writer.write(SIZE_OPERATION);

		int status = reader.read();
		if ( status != 0 ) {
			throw new RuntimeException(" CLEAR : insertion Failed ");
		}
		return reader.readLong();
	}

	@Override
	public Iterator<byte[]> iterator() {
		try {
			reset();
			byte[] firstKey = getNextKey();

		return new Iterator<byte[]>(){
			byte[]  nextKey = firstKey ;
			@Override
			public boolean hasNext() {
				return nextKey != null ;
			}

			@Override
			public byte[] next() {
				byte[]  result ;
				try {
					result = get(nextKey) ;
					nextKey = getNextKey();
				} catch (IOException e) {
					throw new RuntimeException(e) ;
				}
				return result;
			}
		} ;
		} catch (IOException e) {
			throw new RuntimeException(e) ;
		}
		
	}

	/**
	 * 
	 */
	public void reset() throws IOException {

		writer.write(OPERATION_PREFIX);
		writer.write(RESET_OPERATION);

		int status = reader.read();
		if ( status != 0 ) {
			throw new RuntimeException(" RESET  :  Failed ");
		}
	
		
	}

	public byte[] getNextKey() throws IOException{

		writer.write(OPERATION_PREFIX);
		writer.write(NEXTKEY_OPERATION);

		int status = reader.read();
		if ( status == 1 ) 
			return null ;
		if ( status != 0 ) 
			throw new RuntimeException(" GET NEXT KEY :  Failed ");
		
		int length = reader.readInt();
		byte[] results= new  byte[length] ;
		reader.read(results) ; // TODO read longer values
		return results;
	
	}
}


